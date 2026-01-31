package frc.robot.resources.vision;

import static frc.robot.Constants.VisionConstants.*;
import edu.wpi.first.math.geometry.Transform3d;

enum PhotonCameraInfo {
    //LEFT("PC_Camera SIG", LEFT_CAMERA_TRANSFORM),
    RIGHT("PC_Camera MA", RIGHT_CAMERA_TRANSFORM);

    private final String name;

    private final Transform3d transform;

    private PhotonCameraInfo(String name, Transform3d transform) {
        this.name = name;
        this.transform = transform;
    }

    public String getName() {
        return name;
    }

    public Transform3d getTransform() {
        return transform;
    }
}
