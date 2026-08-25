package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Elbow {
    Servo elbow;

    public Elbow(Servo m_elbow) {
        elbow = m_elbow;
    }

    public enum ElbowPositions {
        GROUND(1),
        MIDDLE(0.71),
        MOVING(0.9),
        DROPOFF(0.73);
        public final double value;
        ElbowPositions(double m_position) {
            value = m_position;
        }
    }

    public void goToPosition(ElbowPositions position) {
        elbow.setPosition(position.value);
    }
}
