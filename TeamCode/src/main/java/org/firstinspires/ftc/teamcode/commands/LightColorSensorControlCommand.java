package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.base.RobotBase;

public class LightColorSensorControlCommand extends CommandBase {
    RobotBase robotBase;
    Servo lightServo;
    double redValue;
    double greenValue;

    public LightColorSensorControlCommand(RobotBase m_robotBase, Servo m_lightServo, double m_redValue, double m_greenValue){
        robotBase = m_robotBase;
        lightServo = m_lightServo;
        redValue = m_redValue;
        greenValue = m_greenValue;
    }

    @Override
    public void execute() {
        if(Math.abs(robotBase.colorSensorSubsystem.getHueValues() - redValue) <= 10){
            lightServo.setPosition(.47);
        }
        else if(Math.abs(robotBase.colorSensorSubsystem.getHueValues() - greenValue) <= 10){
            lightServo.setPosition(.27);
        }
        else{
            lightServo.setPosition(0);
        }
    }
    @Override
    public boolean isFinished(){
        return false;
    }
}
