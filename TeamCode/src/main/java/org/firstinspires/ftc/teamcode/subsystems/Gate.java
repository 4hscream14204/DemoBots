package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Gate {
    Servo gateServo;
    public enum gatePosition {
        OPEN(0.35),
        CLOSED(0);
        private final double value;
        gatePosition(double m_position) {
            value = m_position;
        }
    }

    public Gate(Servo m_gateServo)  {
        gateServo = m_gateServo;
    }

    public void goToPosition(gatePosition position) {
        gateServo.setPosition(position.value);
    }
}
