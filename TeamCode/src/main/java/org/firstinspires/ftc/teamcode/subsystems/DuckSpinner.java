package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class DuckSpinner {
    Servo duckServo;
    public DuckSpinner(Servo m_duckServo) {
        duckServo = m_duckServo;
    }
    public void setPower(double power) {
        duckServo.setPosition(power);
    }
}
