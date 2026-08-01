package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Arm;

public class DropOffCommandGroup extends SequentialCommandGroup {
    RobotBase robotBase;
    public DropOffCommandGroup(RobotBase m_robotBase){
        robotBase = m_robotBase;
        addCommands(
            new InstantCommand(()->robotBase.intakeSubsystem.setPower(0)),
                new WaitCommand(680),
                new InstantCommand(()->robotBase.armSubsystem.setPosition(Arm.ArmPosition.HOME)),
            new InstantCommand(()->robotBase.intakeSubsystem.setPower(0.5))
        );
    }
}
