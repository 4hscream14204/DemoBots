package org.firstinspires.ftc.teamcode.subsystems;

import android.util.Size;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Camera {
    WebcamName webcam;
    AprilTagProcessor aprilTagProcessor;
    VisionPortal portal;


    public Camera(WebcamName m_webcam) {
        webcam = m_webcam;
        portal = new VisionPortal.Builder()
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .addProcessor(aprilTagProcessor)
                .setCameraResolution(new Size(640, 480))
                .setCamera(webcam)
                .build();
    }

    public int getAprilTagID(){
        int id = 0;
        List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();
        for(AprilTagDetection detection: currentDetections){
            id = detection.id;
        }
        return id;
    }
}
