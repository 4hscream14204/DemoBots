package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;

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
        CommandScheduler.getInstance().run();
    }
}
