package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Arm {
    Servo armServo;
    ArmPosition savedArmPosition;
    public enum ArmPosition {
        HOME(0),
        LOW(0.041),
        MEDIUM(0.08),
        HIGH(0.14);
        public final double value;
        ArmPosition(double pos) {
            this.value = pos;
        }
    }
    public Arm(Servo m_armServo) {
        armServo = m_armServo;
    }
    public void setPosition(double position) {
        armServo.setPosition(position);
    }
    public void setPosition(ArmPosition position) {
        setPosition(position.value);
        savedArmPosition = position;
    }
    public ArmPosition getPosition() {
        return savedArmPosition;
    }
}
