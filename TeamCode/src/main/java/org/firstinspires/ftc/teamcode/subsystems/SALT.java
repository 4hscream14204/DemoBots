package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class SALT {
    Servo salt;
        double color;
        boolean gamepadD_down;
        boolean lastGamepadD_down;

     public SALT (Servo m_salt) {
         salt = m_salt;

     }
     public void setPosition (double x, double y) {
         salt.setPosition(((Math.toDegrees(Math.atan2(y, x)) + 180) / 1080) + 0.28);
     }
}
