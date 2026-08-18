package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;
import org.screamrobotics.SuperSCREAMLib.command.WaitUntilCommand;

public class HighBucketDropoffCommandGroup extends SequentialCommandGroup {
    RobotBase robotBase;
    public HighBucketDropoffCommandGroup(RobotBase m_robotBase) {
        robotBase = m_robotBase;
        addCommands(
                new InstantCommand(() -> robotBase.shoulderSubsystem.goToPosition(Shoulder.shoulderPosition.UPRIGHT)),
                new WaitUntilCommand(()->robotBase.shoulderSubsystem.shoulder.isAtPosition(Shoulder.shoulderPosition.UPRIGHT.value)),
                new InstantCommand(() -> robotBase.extensionSubsystem.goToPosition(Extension.slidePosition.HIGHBUCKET)),
                new InstantCommand(() -> robotBase.gateSubsystem.goToPosition(Gate.gatePosition.OPEN)),
                new InstantCommand(() -> robotBase.intakeSubSystem.setOuttake()),
                new WaitCommand(1000),
                new InstantCommand(() -> robotBase.intakeSubSystem.setOff()),
                new InstantCommand(() -> robotBase.gateSubsystem.goToPosition(Gate.gatePosition.CLOSED)),
                new InstantCommand(() -> robotBase.extensionSubsystem.goToPosition(Extension.slidePosition.HOME)),
                new InstantCommand(() -> robotBase.shoulderSubsystem.goToPosition(Shoulder.shoulderPosition.HOME))
        );
    }
}
