package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    Servo servoL;
    Servo servoR;

    public Intake(Servo m_servoL, Servo m_servoR){
        servoL = m_servoL;
        servoR = m_servoR;
    }

    public enum intakePower{
        INTAKEL(1),
        INTAKER(0),
        OUTTAKEL(0),
        OUTTAKER(1),
        STOP(.5);
        intakePower(double m_power){
            double power = m_power;
        }
    }


    public void setOuttake(){
        servoR.setPosition(1);
        servoL.setPosition(0);
    }

    public void setIntake(double power){
        servoR.setPosition(power);
        servoL.setPosition(1 - power);
    }

    public void setOff(){
        servoR.setPosition(0.5);
        servoL.setPosition(0.5);
    }
}
