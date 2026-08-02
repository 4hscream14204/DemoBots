package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptGamepadRumble;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.DropoffCommandGroup;
import org.firstinspires.ftc.teamcode.commands.LightColorSensorControlCommand;
import org.firstinspires.ftc.teamcode.commands.SetArmPositionCommandGroup;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Arm;

import kotlin.time.Instant;

@Configurable
@TeleOp(name = "Best TeleOp")
public class RobotTeleOp extends OpMode {

    RobotBase robotBase;
    GamepadEx chassisController;
    boolean isFieldCentric = true;
    Follower follower;
    RevTouchSensor touchSensor;
    Servo light;
    ElapsedTime timer;
    public static double lightPosition = 0;
    public static double panelsHeading = 0;
    TelemetryManager telemetryM;

    @Override
    public void init() {
        robotBase = new RobotBase(hardwareMap);
        chassisController = new GamepadEx(gamepad1);
        follower = Constants.createFollower(hardwareMap);
        touchSensor = hardwareMap.get(RevTouchSensor.class, "ts");
        light = hardwareMap.servo.get("light");
        timer = new ElapsedTime();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
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
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new InstantCommand(() -> robotBase.chassisSubsystem.setTargetHeading(panelsHeading))));
        chassisController.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenActive(() -> new InstantCommand(() -> robotBase.duckSpinnerSubsystem.setPower(0)))
                .whenInactive(() -> new InstantCommand(() -> robotBase.duckSpinnerSubsystem.setPower(0.5)));
        new Trigger(() -> chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1)
                .or(new Trigger(() -> chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1))
                .whileActiveContinuous(() -> CommandScheduler.getInstance().schedule(new InstantCommand(() -> robotBase.intakeSubsystem.setPower(chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) / 2 + -1 * chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) / 2 + 0.5))))
                .whenInactive(() -> CommandScheduler.getInstance().schedule(new InstantCommand(() -> robotBase.intakeSubsystem.setPower(0.5))));
        }

    @Override
    public void start() {
        follower.setStartingPose(new Pose(0, 0, Math.toRadians(0)));
        //CommandScheduler.getInstance().schedule(new LightColorSensorControlCommand(robotBase, light, 60, 150));
        timer.reset();
    }

    @Override
    public void loop() {
        follower.update();
        chassisController.readButtons();
        robotBase.limelightSubsystem.update();
        robotBase.chassisSubsystem.drive(chassisController.getLeftX(), -chassisController.getLeftY(), chassisController.getRightX(), chassisController.getRightY(), follower.getHeading(), isFieldCentric, timer);
        light.setPosition(lightPosition);
        CommandScheduler.getInstance().run();
        telemetry.addData("Right Stick X", chassisController.getRightX());
        telemetry.addData("Is Field Centric", isFieldCentric);
        telemetry.addData("Heading", Math.toDegrees(follower.getHeading()));
        telemetry.addData("Touch Sensor Pressed", touchSensor.isPressed());
        telemetry.addData("April Tag ID", robotBase.limelightSubsystem.getAprilTagID());
        telemetry.addData("Target Heading", robotBase.chassisSubsystem.targetHeading);
        telemetry.addData("April Tag ID Webcam", robotBase.cameraSubsystem.getAprilTagID());
        telemetryM.addData("Light Position", lightPosition);
        //telemetry.addData("Hue Value", robotBase.colorSensorSubsystem.getHueValues());
        telemetryM.update();
        telemetry.update();
    }
}