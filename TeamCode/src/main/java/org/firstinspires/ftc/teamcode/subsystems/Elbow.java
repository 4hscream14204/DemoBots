package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Elbow {
    Servo elbow;

    public Elbow(Servo m_elbow) {
        elbow = m_elbow;
    }

    public enum ElbowPositions {
        GROUND(1),
        MIDDLE(0.71);
        ElbowPositions(double m_position) {
            double position = m_position;
        }
    }

    public void GoToPosition(double position) {
        elbow.setPosition(position);
    }
}
