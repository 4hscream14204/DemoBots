package org.firstinspires.ftc.teamcode.subsystems;

import android.graphics.Color;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

public class ColorSensor {

    RevColorSensorV3 colorSensor;
    final float[] hsvValues = new float[3];
    int gain = 3;

    public ColorSensor(RevColorSensorV3 m_colorSensor){
        colorSensor = m_colorSensor;
        colorSensor.setGain(gain);
    }

    public double getHueValues(){
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        Color.colorToHSV(colors.toColor(), hsvValues);
        return hsvValues[0];
    }
}
