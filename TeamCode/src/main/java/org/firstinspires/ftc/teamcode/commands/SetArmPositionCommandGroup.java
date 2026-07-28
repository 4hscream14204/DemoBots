package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class SetArmPositionCommandGroup extends CommandBase {

    RobotBase robotBase;
    Arm.ArmPosition armPosition;

    public SetArmPositionCommandGroup(RobotBase m_robotBase, Arm.ArmPosition m_armPosition){
        robotBase = m_robotBase;
        armPosition = m_armPosition;
    }

    @Override
    public void initialize(){
        robotBase.armSubsystem.setPosition(armPosition);
    }
}
