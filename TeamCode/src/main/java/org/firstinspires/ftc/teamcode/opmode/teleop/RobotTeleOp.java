package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.DropoffCommandGroup;
import org.firstinspires.ftc.teamcode.commands.SetArmPositionCommandGroup;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Arm;

@TeleOp(name = "Best TeleOp")
public class RobotTeleOp extends OpMode {

    RobotBase robotBase;
    GamepadEx chassisController;
    boolean isFieldCentric = true;
    Follower follower;
    RevTouchSensor touchSensor;
    Servo light;
    double color;

    @Override
    public void init() {
        robotBase = new RobotBase(hardwareMap);
        chassisController = new GamepadEx(gamepad1);
        follower = Constants.createFollower(hardwareMap);
        touchSensor = hardwareMap.get(RevTouchSensor.class, "ts");
        light = hardwareMap.servo.get("light");
        color = 0;
        chassisController.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new InstantCommand(() -> isFieldCentric = !isFieldCentric)));
        chassisController.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new SetArmPositionCommandGroup(robotBase, Arm.ArmPosition.HOME)));
        chassisController.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new SetArmPositionCommandGroup(robotBase, Arm.ArmPosition.LOW)));
        chassisController.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new SetArmPositionCommandGroup(robotBase, Arm.ArmPosition.MEDIUM)));
        chassisController.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new SetArmPositionCommandGroup(robotBase, Arm.ArmPosition.HIGH)));
        chassisController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new DropoffCommandGroup(robotBase)));
        chassisController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new InstantCommand(() -> chassisController.gamepad.rumble(1000))));
        new Trigger(() -> touchSensor.isPressed())
                .whileActiveContinuous(() -> color = 0.15);
        new Trigger(() -> chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1)
                .or(new Trigger(() -> chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1))
                .whileActiveContinuous(() -> CommandScheduler.getInstance().schedule(new InstantCommand(() -> robotBase.intakeSubsystem.setPower(chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) / 2 + -1 * chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) / 2 + 0.5))));
        }

    @Override
    public void start() {
        follower.setStartingPose(new Pose(0, 0, Math.toRadians(0)));
    }

    @Override
    public void loop() {
        follower.update();
        chassisController.readButtons();
        color += 0.001;
        if (color > 1) color = 0;
        light.setPosition(color);
        robotBase.limelightSubsytem.update();
        robotBase.chassisSubsystem.drive(chassisController.getLeftX(), chassisController.getLeftY(), chassisController.getRightX(), chassisController.getRightY(), follower.getHeading(), isFieldCentric);
        CommandScheduler.getInstance().run();
        telemetry.addData("Right Stick X", chassisController.getRightX());
        telemetry.addData("Is Field Centric", isFieldCentric);
        telemetry.addData("Heading", Math.toDegrees(follower.getHeading()));
        telemetry.addData("Touch Sensor Pressed", touchSensor.isPressed());
        telemetry.addData("April Tag ID", robotBase.limelightSubsytem.getAprilTagID());
        telemetry.update();
    }
}
