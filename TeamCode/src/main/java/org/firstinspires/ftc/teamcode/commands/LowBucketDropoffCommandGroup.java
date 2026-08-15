package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;

public class LowBucketDropoffCommandGroup extends SequentialCommandGroup {
    RobotBase robotBase;
    public LowBucketDropoffCommandGroup(RobotBase m_robotBase) {
        robotBase = m_robotBase;
        addCommands(
                new InstantCommand(() -> robotBase.shoulderSubsystem.goToPosition(Shoulder.shoulderPosition.UPRIGHT)),
                new InstantCommand(() -> robotBase.extensionSubsystem.goToPosition(Extension.slidePosition.LOWBUCKET))
        );
    }
}
