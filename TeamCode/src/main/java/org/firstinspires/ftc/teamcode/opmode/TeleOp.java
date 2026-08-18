package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.screamrobotics.SuperSCREAMLib.command.CommandScheduler;
import org.screamrobotics.SuperSCREAMLib.command.InstantCommand;
import org.screamrobotics.SuperSCREAMLib.command.button.Trigger;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadEx;
import org.screamrobotics.SuperSCREAMLib.gamepad.GamepadKeys;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {
    RobotBase robotBase;
    GamepadEx gamepadEx;
    double chance;

    @Override
    public void init() {
        robotBase = new RobotBase(hardwareMap);
        gamepadEx = new GamepadEx(gamepad1);
        chance = Math.random() * 100;

        new Trigger(()->robotBase.extensionSubsystem.slides.areSlidesHome())
                .whenActive(()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.extensionSubsystem.slides.reset())));

        new Trigger(()->gamepadEx.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .or(new Trigger(()->gamepadEx.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1))
                        .whenInactive(()-> CommandScheduler.getInstance()
                                .schedule(new InstantCommand(()->robotBase.intakeSubSystem.setOff())))
                        .whenActive(()-> CommandScheduler.getInstance()
                                .schedule(new InstantCommand(()->robotBase.intakeSubSystem.setIntake(((gamepadEx.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - (gamepadEx.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)) + 1 ) / 2)))));

    }

    @Override
    public void loop() {
        gamepadEx.readButtons();
        robotBase.chassisSubSystem.drive(gamepadEx.getLeftX(), gamepadEx.getLeftY(), gamepadEx.getRightX());


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
        telemetry.addData("Shoulder Position", robotBase.shoulderSubsystem.shoulder.getPosition());
    }
}