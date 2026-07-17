package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class SetArmPositionCommandGroup extends CommandBase {

    RobotBase robotBase;

    public SetArmPositionCommandGroup(RobotBase m_robotBase, Arm.ArmPosition armPosition){
        robotBase = m_robotBase;
        robotBase.armSubsystem.setPosition(armPosition);
    }
}
