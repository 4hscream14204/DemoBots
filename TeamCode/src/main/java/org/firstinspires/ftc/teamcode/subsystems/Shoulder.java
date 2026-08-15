package org.firstinspires.ftc.teamcode.subsystems;

import android.transition.Slide;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.screamrobotics.SuperSCREAMLib.hardware.Slides;

public class Shoulder {
    public Slides shoulder;
    public enum shoulderPosition{
        HOME(0),
        UPRIGHT(1029);
        public final int value;
        shoulderPosition(int position) {
            value = position;
        }
    }

    public Shoulder(Slides m_shoulder){
        shoulder = m_shoulder;
    }

    public void goToPosition(shoulderPosition position) {
        shoulder.goToPosition(position.value);
    }
}
