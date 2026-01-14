from cscore import CameraServer, VideoSource, UsbCamera, MjpegServer
from ntcore import NetworkTableInstance, EventFlags
import ntcore

import apriltag
import cv2
import json
import numpy as np
import time


# Apriltag detection values
LINE_LENGTH = 2
CENTER_COLOR = (0, 255, 0)
CORNER_COLOR = (255, 0, 255)

# For distance calculation
h = 120
w = 160
K = [w, 0.0, w//2,
     0.0, w, h//2,
     0.0, 0.0, 1.0]
D = [0, 0, 0.0, 0.0, 0] # Assuming no distortion
# End Apriltag detection values


# WE NEED TO TUNE THESE VALUES
# Define lower and upper bounds for white color in HSV
lower_grey = np.array([200, 200, 200])
upper_grey = np.array([255, 255, 255]) 
# The minimum contour area to detect a note
min_contour_area = 5000
# END OF VALUES THAT NEED TUNING

# The threshold for a contour to be considered a disk
#CONTOUR_DISK_THRESHOLD = 0.9
#!/usr/bin/env python3

# Copyright (c) FIRST and other WPILib contributors.
# Open Source Software; you can modify and/or share it under the terms of
# the WPILib BSD license file in the root directory of this project.

import json
import time
import sys

from cscore import CameraServer, VideoSource, UsbCamera, MjpegServer
from ntcore import NetworkTableInstance, EventFlags

tag_size = 0.017
#   JSON format:
#   {
#       "team": <team number>,
#       "ntmode": <"client" or "server", "client" if unspecified>
#       "cameras": [
#           {
#               "name": <camera name>
#               "path": <path, e.g. "/dev/video0">
#               "pixel format": <"MJPEG", "YUYV", etc>   // optional
#               "width": <video mode width>              // optional
#               "height": <video mode height>            // optional
#               "fps": <video mode fps>                  // optional
#               "brightness": <percentage brightness>    // optional
#               "white balance": <"auto", "hold", value> // optional
#               "exposure": <"auto", "hold", value>      // optional
#               "properties": [                          // optional
#                   {
#                       "name": <property name>
#                       "value": <property value>
#                   }
#               ],
#               "stream": {                              // optional
#                   "properties": [
#                       {
#                           "name": <stream property name>
#                           "value": <stream property value>
#                       }
#                   ]
#               }
#           }
#       ]
#       "switched cameras": [
#           {
#               "name": <virtual camera name>
#               "key": <network table key used for selection>
#               // if NT value is a string, it's treated as a name
#               // if NT value is a double, it's treated as an integer index
#           }
#       ]
#   }

configFile = "/boot/frc.json"

class CameraConfig: pass

team = None
server = False
cameraConfigs = []
switchedCameraConfigs = []
cameras = []

def parseError(str):
    """Report parse error."""
    print("config error in '" + configFile + "': " + str, file=sys.stderr)

def readCameraConfig(config):
    """Read single camera configuration."""
    cam = CameraConfig()

    # name
    try:
        cam.name = config["name"]
    except KeyError:
        parseError("could not read camera name")
        return False

    # path
    try:
        cam.path = config["path"]
    except KeyError:
        parseError("camera '{}': could not read path".format(cam.name))
        return False

    # stream properties
    cam.streamConfig = config.get("stream")

    cam.config = config

    cameraConfigs.append(cam)
    return True

def readSwitchedCameraConfig(config):
    """Read single switched camera configuration."""
    cam = CameraConfig()

    # name
    try:
        cam.name = config["name"]
    except KeyError:
        parseError("could not read switched camera name")
        return False

    # path
    try:
        cam.key = config["key"]
    except KeyError:
        parseError("switched camera '{}': could not read key".format(cam.name))
        return False

    switchedCameraConfigs.append(cam)
    return True

def readConfig():
    """Read configuration file."""
    global team
    global server

    # parse file
    try:
        with open(configFile, "rt", encoding="utf-8") as f:
            j = json.load(f)
    except OSError as err:
        print("could not open '{}': {}".format(configFile, err), file=sys.stderr)
        return False

    # top level must be an object
    if not isinstance(j, dict):
        parseError("must be JSON object")
        return False

    # team number
    try:
        team = j["team"]
    except KeyError:
        parseError("could not read team number")
        return False

    # ntmode (optional)
    if "ntmode" in j:
        str = j["ntmode"]
        if str.lower() == "client":
            server = False
        elif str.lower() == "server":
            server = True
        else:
            parseError("could not understand ntmode value '{}'".format(str))

    # cameras
    try:
        cameras = j["cameras"]
    except KeyError:
        parseError("could not read cameras")
        return False
    for camera in cameras:
        if not readCameraConfig(camera):
            return False

    # switched cameras
    if "switched cameras" in j:
        for camera in j["switched cameras"]:
            if not readSwitchedCameraConfig(camera):
                return False

    return True

def startCamera(config):
    """Start running the camera."""
    print("Starting camera '{}' on {}".format(config.name, config.path))
    camera = UsbCamera(config.name, config.path)
    server = CameraServer.startAutomaticCapture(camera=camera)

    camera.setConfigJson(json.dumps(config.config))
    camera.setConnectionStrategy(VideoSource.ConnectionStrategy.kConnectionKeepOpen)

    if config.streamConfig is not None:
        server.setConfigJson(json.dumps(config.streamConfig))

    return camera

def startSwitchedCamera(config):
    """Start running the switched camera."""
    print("Starting switched camera '{}' on {}".format(config.name, config.key))
    server = CameraServer.addSwitchedCamera(config.name)

    def listener(event):
        data = event.data
        if data is not None:
            value = data.value.value()
            if isinstance(value, int):
                if value >= 0 and value < len(cameras):
                    server.setSource(cameras[value])
            elif isinstance(value, float):
                i = int(value)
                if i >= 0 and i < len(cameras):
                    server.setSource(cameras[i])
            elif isinstance(value, str):
                for i in range(len(cameraConfigs)):
                    if value == cameraConfigs[i].name:
                        server.setSource(cameras[i])
                        break

    NetworkTableInstance.getDefault().addListener(
        NetworkTableInstance.getDefault().getEntry(config.key),
        EventFlags.kImmediate | EventFlags.kValueAll,
        listener)

    return server


# ### AprilTag ###
### Some utility functions to simplify drawing on the camera feed
# draw a crosshair
def plotPoint(image, center, color):
    center = (int(center[0]), int(center[1]))
    image = cv2.line(image,
                     (center[0] - LINE_LENGTH, center[1]),
                     (center[0] + LINE_LENGTH, center[1]),
                     color,
                     3)
    image = cv2.line(image,
                     (center[0], center[1] - LINE_LENGTH),
                     (center[0], center[1] + LINE_LENGTH),
                     color,
                     3)
    return image

# plot a little text
def plotText(image, center, color, text):
    center = (int(center[0]) + 4, int(center[1]) - 4)
    return cv2.putText(image, str(text), center, cv2.FONT_HERSHEY_SIMPLEX,
                       1, color, 3)

def main():
    if len(sys.argv) >= 2:
        configFile = sys.argv[1]

    # read configuration
    if not readConfig():
        sys.exit(1)

    # start NetworkTables
    ntinst = NetworkTableInstance.getDefault()
    if server:
        print("Setting up NetworkTables server")
        ntinst.startServer()
    else:
        print("Setting up NetworkTables client for team {}".format(team))
        ntinst.startClient4("wpilibpi")
        ntinst.setServerTeam(team)
        ntinst.startDSClient()
        
    # Table for vision output information
    vision_nt = ntinst.getTable('Vision')
    
    # Apriltag setup
    options = apriltag.DetectorOptions(families='tag36h11',
                                 border=1,
                                 nthreads=4,
                                 quad_decimate=1.0,
                                 quad_blur=0.0,
                                 refine_edges=True,
                                 refine_decode=False,
                                 refine_pose=False,
                                 debug=False,
                                 quad_contours=True)
    detector = apriltag.Detector()

    # Reshape arrays for distance calculation
    camera_matrix = np.array(K).reshape(3, 3).astype(np.float32)
    dist_coeffs = np.array(D).reshape(5, 1).astype(np.float32)


    # start cameras
    # work around wpilibsuite/allwpilib#5055
    CameraServer.setSize(CameraServer.kSize160x120)
    for config in cameraConfigs:
        cameras.append(startCamera(config))

    # start switched cameras
    for config in switchedCameraConfigs:
        startSwitchedCamera(config)
    
    # Setup OpenCV Stream
    input_stream = CameraServer.getVideo()
    output_stream = CameraServer.putVideo("OpenCV - Coral Detect", 160, 120)

    # Allocating new images is very expensive, always try to preallocate
    img = np.zeros(shape=(240, 320, 3), dtype=np.uint8)


    # Wait for NetworkTables to start
    time.sleep(0.5)


    print("CTBT OpenCV Coral and Apriltag Detection --- v0")

    while True:
          #start_time = time.time()

          frame_time, input_img = input_stream.grabFrame(img)
          output_img = np.copy(input_img)
          output_img = cv2.flip(output_img, -1)  # Flip image

          # Notify output of error and skip iteration
          if frame_time == 0:
                output_stream.notifyError(input_stream.getError())
                continue

          # Reset Apriltag Detected Information
          tagfound = 0
          apriltag_id = 0
          apriltag_cX = 0
          apriltag_cY = 0
          distance = 0

          # Reset Detected Note information
          coralfound = 0
          cX = 0
          cY = 0

          # Converts from BGR to HSV
          frame_hsv = cv2.cvtColor(output_img, cv2.COLOR_BGR2HSV)
          contour = find_largest_orange_contour(frame_hsv)
          
          # Find apriltag
          grayimg = cv2.cvtColor(output_img, cv2.COLOR_BGR2GRAY)
          # look for tags
          detections = detector.detect(grayimg)
          if detections:
          # found some tags, report them and update the camera image
  
                for detect in detections:
                    print("tag_id: %s, center: %s" % (detect.tag_id, detect.center))
                    output_img = plotPoint(output_img, detect.center, CENTER_COLOR)
                    output_img = plotText(output_img, detect.center, CENTER_COLOR, detect.tag_id)
                    
                    _, rvec, tvec = cv2.solvePnP(
                        objectPoints = np.array([
                            [-tag_size / 2, -tag_size / 2, 0],
                            [tag_size / 2, -tag_size / 2, 0],
                            [tag_size / 2, tag_size / 2, 0],
                            [-tag_size / 2, tag_size / 2, 0]
                        ]),
                        imagePoints = detect.corners,
                        cameraMatrix = camera_matrix,
                        distCoeffs = dist_coeffs
                    )
                    distance = np.linalg.norm(tvec)

                    #print("Tag ID:", tag_id)
                    #print("Distance:", distance)

                    # Update values
                    tagfound = 1
                    apriltag_id = detect.tag_id
                    apriltag_cX = detect.center[0]
                    apriltag_cY = detect.center[1]
                    
          
                    
                    for corner in detect.corners:
                        output_img = plotPoint(output_img, corner, CORNER_COLOR)

          # Find Contour
          gray = cv2.cvtColor(output_img, cv2.COLOR_BGR2GRAY)
          mask = cv2.inRange(output_img, lower_grey, upper_grey)
          # ret, thresh = cv2.threshold(gray, 100, 255, cv2.THRESH_BINARY)
          # thresh = np.invert(thresh) # Invert colors
          contours,hierarchy = cv2.findContours(mask, 1, 2)
          #print("Number of contours detected:", len(contours))    
         
          # Loop through contours
          for contour in contours:
             # Approximate the contour with a polygon
             epsilon = 0.04 * cv2.arcLength(contour, True)
             approx = cv2.approxPolyDP(contour, epsilon, True)
     
             # Check if the polygon has 4 sides (rectangle)
             if len(approx) == 4 and cv2.contourArea(contour) >= min_contour_area:
                 coralfound = true
                  
                 # Draw the rectangle on the original image
                 cv2.drawContours(img, [approx], 0, (0, 255, 0), 2)

                 # compute the center of the contour
                 M = cv2.moments(contour)
                 cX = int(M["m10"] / M["m00"])
                 cY = int(M["m01"] / M["m00"])
                
                # print the detected note's center to console
                print("Coral Detected: Center X: " + str(cX) + " Y: " + str(cY))
     
         #largest_contour = max(contours, key=cv2.contourArea)
         #img = cv2.drawContours(img, largest_contour, -1, (0,255,0), 3)

          # This displays the frame as a new window, I was having trouble getting this to work on Windows
          #cv2.imshow("Frame", frame)
     
          vision_nt.putNumber('NoteDetect/note _found', coralfound)
          vision_nt.putNumber('NoteDetect/target_x', cX)
          vision_nt.putNumber('NoteDetect/target_y', cY)
          vision_nt.putNumber('AprilDetect/apriltag_found', tagfound) 
          vision_nt.putNumber('AprilDetect/apriltag_value', apriltag_id)
          vision_nt.putNumber('AprilDetect/target_x', apriltag_cX)
          vision_nt.putNumber('AprilDetect/target_y', apriltag_cY)
          vision_nt.putNumber('AprilDetect/distance', distance)

          #processing_time = time.time() - start_time
          #fps = 1 / processing_time
          #cv2.putText(output_img, str(round(fps, 1)), (0, 40), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 255, 255))
          
          output_stream.putFrame(output_img)
      


main()
