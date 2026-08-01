package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Base.RobotBase;

import Subsystem.Arm;

public class SetArmPositionCommandGroup extends CommandBase {

    RobotBase robotBase;
    Arm.ArmPosition armPosition;

    public SetArmPositionCommandGroup(RobotBase m_robotBase, Arm.ArmPosition m_armposition){
        robotBase = m_robotBase;
                armPosition = m_armposition;
    }

    @Override
    public void initialize(){
        robotBase.ArmSubsystem.setPosition(armPosition);
    }
}
