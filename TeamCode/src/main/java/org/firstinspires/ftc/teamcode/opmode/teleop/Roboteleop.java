package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Base.RobotBase;
import org.firstinspires.ftc.teamcode.Commands.SetArmPositionCommandGroup;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;

import Subsystem.Arm;

@TeleOp (name = "TeleOp")
public class Roboteleop extends OpMode {
    RobotBase robotBase;
    GamepadEx chassisController;
    boolean isFieldCentric = true;
    Follower follower;

    @Override
    public void start() {
        super.start();
        follower.setStartingPose(new Pose(0,0, Math.toRadians(0)));
    }

    @Override
    public void init() {
        robotBase = new RobotBase(hardwareMap);
        chassisController = new GamepadEx(gamepad1);
        follower = Constants.createFollower(hardwareMap);

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
    }

    @Override
    public void loop() {
        follower.update();
        chassisController.readButtons();
        robotBase.IntakeSubsystem.setPower (chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) / 2 + -1 * chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) / 2 + 0.5);
        robotBase.chassisSubsystem.drive(chassisController.getLeftX(), chassisController.getLeftY(), chassisController.getRightX(), follower.getHeading(), isFieldCentric);
CommandScheduler.getInstance().run();
telemetry.addData("Right Stick X", chassisController.getRightX());
telemetry.addData("Is Field Centric", isFieldCentric);
telemetry.addData("Heading", Math.toDegrees(follower.getHeading()));
    }
}
