package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.SetArmPositionCommandGroup;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Arm;

@TeleOp(name = "TeleOp")
public class RobotTeleOp extends OpMode {

    RobotBase robotBase;
    GamepadEx gamepad;
    Follower follower;

    boolean isFieldCentric = true;

    @Override
    public void init() {
        robotBase = new RobotBase(hardwareMap);
        follower = Constants.createFollower(hardwareMap);
        gamepad = new GamepadEx(gamepad1);

        gamepad.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->isFieldCentric = !isFieldCentric)));

        gamepad.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new SetArmPositionCommandGroup(robotBase, Arm.ArmPosition.HOME)));

        gamepad.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new SetArmPositionCommandGroup(robotBase, Arm.ArmPosition.LOW)));

        gamepad.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new SetArmPositionCommandGroup(robotBase, Arm.ArmPosition.MEDIUM)));

        gamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new SetArmPositionCommandGroup(robotBase, Arm.ArmPosition.HIGH)));

        new Trigger(()->robotBase.armSubsystem.getPosition() == Arm.ArmPosition.HIGH)
                .whenActive(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->gamepad.gamepad.rumble(500))));
    }

    @Override
    public void start() {
        follower.setStartingPose(new Pose(0, 0, Math.toRadians(0)));
    }

    @Override
    public void loop() {
        gamepad.readButtons();
        follower.update();

        robotBase.chassisSubsystem.drive(gamepad.getLeftX(), gamepad.getLeftY(), gamepad.getRightX(), follower.getHeading(), isFieldCentric);
        robotBase.intakeSubsystem.setPower(gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) / 2 + -1 * gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) / 2 + 0.5);
        CommandScheduler.getInstance().run();
    }
}
