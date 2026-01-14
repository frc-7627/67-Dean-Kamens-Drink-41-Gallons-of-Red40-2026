import numpy as np
import cv2
from matplotlib import pyplot as plt


lower_grey = np.array([200, 200, 200])
upper_grey = np.array([255, 255, 255]) 

min_contour_area = 5000

# Create a VideoCapture object
cap = cv2.VideoCapture(0)  # 0 is the default camera index

# Check if camera opened successfully
if not cap.isOpened():
    print("Error opening video stream or file")


while True:
    # load the image in full color
    # Read a frame from the camera
    ret, img = cap.read()

    # Check if frame is read correctly
    if not ret:
        print("Can't receive frame (stream end?)")

    
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)


    mask = cv2.inRange(img, lower_grey, upper_grey)
    # ret, thresh = cv2.threshold(gray, 100, 255, cv2.THRESH_BINARY)
   # thresh = np.invert(thresh) # Invert colors
    contours,hierarchy = cv2.findContours(mask, 1, 2)
    print("Number of contours detected:", len(contours))
    
        # Loop through contours
    for contour in contours:
        # Approximate the contour with a polygon
        epsilon = 0.04 * cv2.arcLength(contour, True)
        approx = cv2.approxPolyDP(contour, epsilon, True)

        # Check if the polygon has 4 sides (rectangle)
        if len(approx) == 4 and cv2.contourArea(contour) >= min_contour_area:
            # Draw the rectangle on the original image
            cv2.drawContours(img, [approx], 0, (0, 255, 0), 2)

    #largest_contour = max(contours, key=cv2.contourArea)
    #img = cv2.drawContours(img, largest_contour, -1, (0,255,0), 3)
                
            
                



    cv2.imshow("Thresh", mask)
    cv2.imshow("Frame", img)
    
        # Check if the user pressed 'q' to quit
    if cv2.waitKey(1) == ord('q'):
        break
            
# Release the camera and close all windows
cap.release()
cv2.destroyAllWindows()

