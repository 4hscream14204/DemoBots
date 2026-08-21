package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.FixedShoulder;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.SequentialCommandGroup;
import org.screamrobotics.SuperSCREAMLib.command.WaitCommand;
import org.screamrobotics.SuperSCREAMLib.command.WaitUntilCommand;

public class HighBucketDropoffCommandGroup extends SequentialCommandGroup {
    RobotBase robotBase;
    public HighBucketDropoffCommandGroup(RobotBase m_robotBase) {
        robotBase = m_robotBase;
        addCommands(
                new InstantCommand(() -> robotBase.shoulderSubsystem.goToPosition(FixedShoulder.ShoulderPosition.TOGGLE)),
                new WaitUntilCommand(()->robotBase.shoulderSubsystem.isAtPosition(FixedShoulder.ShoulderPosition.TOGGLE)),
                new InstantCommand(() -> robotBase.extensionSubsystem.goToPosition(Extension.slidePosition.HIGHBUCKET)),
                new InstantCommand(() -> robotBase.elbowSubSystem.goToPosition(Elbow.ElbowPositions.DROPOFF)),
                new InstantCommand(()-> robotBase.wristSubSystem.goToPosition(Wrist.wristPosition.OUTTAKE)),
                new InstantCommand(() -> robotBase.gateSubsystem.goToPosition(Gate.gatePosition.OPEN)),
                new WaitCommand(1500),
                new InstantCommand(() -> robotBase.intakeSubSystem.setOuttake()),
                new WaitCommand(3000),
                new InstantCommand(() -> robotBase.intakeSubSystem.setOff()),
                new InstantCommand(() -> robotBase.gateSubsystem.goToPosition(Gate.gatePosition.CLOSED)),
                new InstantCommand(() -> robotBase.extensionSubsystem.goToPosition(Extension.slidePosition.HOME)),
                new InstantCommand(()->robotBase.wristSubSystem.goToPosition(Wrist.wristPosition.HOME)),
                new InstantCommand(() -> robotBase.shoulderSubsystem.goToPosition(FixedShoulder.ShoulderPosition.HOME)),
                new InstantCommand(()-> robotBase.elbowSubSystem.goToPosition(Elbow.ElbowPositions.GROUND))
        );
    }
}
