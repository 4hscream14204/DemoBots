package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    Servo clawServo;
    ClawPosition savedClawPosition;

    public enum ClawPosition{
        OPEN(0),
        CLOSED(1);
        public final double value;
        ClawPosition(double pos){
            this.value = pos;
        }
    }

    public Claw(Servo m_claw){
        clawServo = m_claw;
    }

    public void setPosition(double position){
        clawServo.setPosition(position);
    }

    public void setPosition(ClawPosition position){
        setPosition(position.value);
        savedClawPosition = position;
    }

    public void openClaw(){
        setPosition(ClawPosition.OPEN);
        savedClawPosition = ClawPosition.OPEN;
    }

    public void closeClaw(){
        setPosition(ClawPosition.CLOSED);
        savedClawPosition = ClawPosition.CLOSED;
    }

    public void toggleClaw(){
        if(savedClawPosition == ClawPosition.OPEN){
            setPosition(ClawPosition.CLOSED);
        }
        else{
            setPosition(ClawPosition.OPEN);
        }
    }
}
