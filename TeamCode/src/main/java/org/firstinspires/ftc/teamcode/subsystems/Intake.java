package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    Servo intakeServo;
    public Intake(Servo m_intakeServo) {
        intakeServo = m_intakeServo;
    }
    public void setPower(double power) {
        intakeServo.setPosition(power);
    }
}
