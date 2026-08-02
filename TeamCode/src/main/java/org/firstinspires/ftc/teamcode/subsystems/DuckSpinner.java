package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class DuckSpinner {
    Servo duckSpinnerServo;

    public DuckSpinner(Servo m_duckSpinnerServo){
        duckSpinnerServo = m_duckSpinnerServo;
    }

    public void setPower(double power){
        duckSpinnerServo.setPosition(power);
    }
}
