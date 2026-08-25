package org.firstinspires.ftc.teamcode.opmode;

import static org.firstinspires.ftc.teamcode.subsystems.Elbow.ElbowPositions.GROUND;
import static org.firstinspires.ftc.teamcode.subsystems.Elbow.ElbowPositions.MOVING;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.HighBucketDropoffCommandGroup;
import org.firstinspires.ftc.teamcode.commands.LowBucketDropoffCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.button.Trigger;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
    RobotBase robotBase;
    GamepadEx gamepadEx1;
    GamepadEx gamepadEx2;
    double chance;

    @Override
    public void init() {
        robotBase = new RobotBase(hardwareMap);
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
        chance = Math.round(Math.random() * 100);

        new Trigger(()->robotBase.extensionSubsystem.slides.areSlidesHome())
                .whenActive(()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.slides.reset())));

        new Trigger(()->robotBase.shoulderSubsystem.isShoulderHome())
                .whenActive(()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.shoulderSubsystem.reset())));

        new Trigger(()-> gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .or(new Trigger(()-> gamepadEx1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1))
                        .whenInactive(()-> CommandScheduler.getInstance()
                                .schedule(new InstantCommand(()->robotBase.intakeSubSystem.setOff()),
                                        new InstantCommand(()->robotBase.elbowSubSystem.goToPosition(MOVING)))
                        )
                        .whenActive(()-> CommandScheduler.getInstance()
                                .schedule( new InstantCommand(()->robotBase.elbowSubSystem.goToPosition(GROUND)),
                                        new InstantCommand(()->robotBase.intakeSubSystem.setIntake(((gamepadEx1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - (gamepadEx1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)) + 1 ) / 2))))

                        );
        gamepadEx1.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new LowBucketDropoffCommandGroup(robotBase)));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new HighBucketDropoffCommandGroup(robotBase)));

        gamepadEx1.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(()->CommandScheduler.getInstance()
                        .schedule(new InstantCommand(()->robotBase.wristSubSystem.goToPosition(Wrist.wristPosition.HOME))));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(()->CommandScheduler.getInstance()
                        .schedule(new InstantCommand(()->robotBase.wristSubSystem.goToPosition(Wrist.wristPosition.OUTTAKE))));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(()->CommandScheduler.getInstance()
                        .schedule(new InstantCommand(()->robotBase.elbowSubSystem.goToPosition(GROUND))));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(()->CommandScheduler.getInstance()
                        .schedule(new InstantCommand(()->robotBase.elbowSubSystem.goToPosition(Elbow.ElbowPositions.MIDDLE))));



    }

    @Override
    public void start() {
        robotBase.gateSubsystem.goToPosition(Gate.gatePosition.CLOSED);
        robotBase.elbowSubSystem.goToPosition(Elbow.ElbowPositions.MOVING);
        robotBase.wristSubSystem.goToPosition(Wrist.wristPosition.HOME);
    }

    @Override
    public void loop() {
        gamepadEx1.readButtons();
        robotBase.chassisSubSystem.drive(gamepadEx1.getLeftX(), gamepadEx1.getLeftY(), gamepadEx1.getRightX());
        robotBase.saltSubSystem.setPosition((gamepadEx2.getLeftX() + gamepadEx2.getRightX()) / 2, (gamepadEx2.getLeftY() + gamepadEx2.getRightY()) / 2);

        /*
        if(gamepadEx.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1) {
            robotBase.intakeSubSystem.setIntake();
        } else if(gamepadEx.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1) {
            robotBase.intakeSubSystem.setOuttake();
        } else {
            robotBase.intakeSubSystem.setOff();
        }

        if(gamepadEx.getButton(GamepadKeys.Button.LEFT_BUMPER)) {
            robotBase.wristSubSystem.goToPosition(Wrist.wristPosition.HOME);
        } else if(gamepadEx.getButton(GamepadKeys.Button.RIGHT_BUMPER)) {
            robotBase.wristSubSystem.goToPosition(Wrist.wristPosition.OUTTAKE);
        }

        if(gamepadEx.getButton(GamepadKeys.Button.A)) {
            robotBase.elbowSubSystem.GoToPosition(Elbow.ElbowPositions.GROUND);
        } else if(gamepadEx.getButton(GamepadKeys.Button.B)) {
            robotBase.elbowSubSystem.GoToPosition(Elbow.ElbowPositions.MIDDLE);
        }
        if(gamepadEx.getButton(GamepadKeys.Button.DPAD_UP)) {
            robotBase.extensionSubsystem.goToPosition(Extension.slidePosition.HIGHBUCKET);
        } else if(gamepadEx.getButton(GamepadKeys.Button.DPAD_LEFT) || gamepadEx.getButton(GamepadKeys.Button.DPAD_RIGHT)) {
            robotBase.extensionSubsystem.goToPosition(Extension.slidePosition.LOWBUCKET);
        } else if(gamepadEx.getButton(GamepadKeys.Button.DPAD_DOWN)) {
            robotBase.extensionSubsystem.goToPosition(Extension.slidePosition.HOME);
        }
        */

        CommandScheduler.getInstance().run();

        telemetry.addData("There is a '" + chance + "' percent chance that this works", "");
        telemetry.addData("Extension Position", robotBase.extensionSubsystem.slides.getPosition());
        telemetry.addData("Shoulder Position", robotBase.shoulderSubsystem.shoulderGetPosition());
    }
}