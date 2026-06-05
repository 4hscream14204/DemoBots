package org.firstinspires.ftc.teamcode.subsystems;

import android.util.Size;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.firstinspires.ftc.vision.opencv.PredominantColorProcessor;

public class Camera {
    WebcamName webcam;
    PredominantColorProcessor colorSensorMiddle;
    PredominantColorProcessor colorSensorLeft;
    PredominantColorProcessor colorSensorRight;
    public PredominantColorProcessor.Result resultMiddle;
    public PredominantColorProcessor.Result resultLeft;
    public PredominantColorProcessor.Result resultRight;

    boolean isFreightOnLeftMark;
    boolean isFreightOnMiddleMark;
    boolean isFreightOnRightMark;

    public Camera(WebcamName m_webcam){
        webcam = m_webcam;
        colorSensorMiddle = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(-0.1, -0.05, 0.3, -0.35))
                .setSwatches(
                        PredominantColorProcessor.Swatch.WHITE,
                        PredominantColorProcessor.Swatch.YELLOW)
                .build();

        colorSensorLeft = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(-.65, -0.05, -0.63, -0.25))
                .setSwatches(
                        PredominantColorProcessor.Swatch.WHITE,
                        PredominantColorProcessor.Swatch.YELLOW)
                .build();

        colorSensorRight = new PredominantColorProcessor.Builder()
                .setRoi(ImageRegion.asUnityCenterCoordinates(0.85, -0.05, 0.9, -0.25))
                .setSwatches(
                        PredominantColorProcessor.Swatch.WHITE,
                        PredominantColorProcessor.Swatch.YELLOW)
                .build();

        VisionPortal portal = new VisionPortal.Builder()
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG)
                .addProcessor(colorSensorMiddle)
                .addProcessor(colorSensorLeft)
                .addProcessor(colorSensorRight)
                .setCameraResolution(new Size(640, 480))
                .setCamera(m_webcam)
                .build();

    }

    public void getAnalysis(){
        resultMiddle = colorSensorMiddle.getAnalysis();
        resultLeft = colorSensorLeft.getAnalysis();
        resultRight = colorSensorRight.getAnalysis();

        if(resultMiddle.closestSwatch != PredominantColorProcessor.Swatch.WHITE){
            isFreightOnMiddleMark = true;
            isFreightOnLeftMark = false;
            isFreightOnRightMark = false;
        }
        else if(resultLeft.closestSwatch != PredominantColorProcessor.Swatch.WHITE){
            isFreightOnLeftMark = true;
            isFreightOnMiddleMark = false;
            isFreightOnRightMark = false;
        }
        else if(resultRight.closestSwatch != PredominantColorProcessor.Swatch.WHITE){
            isFreightOnRightMark = true;
            isFreightOnMiddleMark = false;
            isFreightOnLeftMark = false;
        }
    }

    public boolean isFreightOnLeftMark(){
        return isFreightOnLeftMark;
    }

    public boolean isFreightOnMiddleMark(){
        return isFreightOnMiddleMark;
    }

    public boolean isFreightOnRightMark(){
        return isFreightOnRightMark;
    }
}
