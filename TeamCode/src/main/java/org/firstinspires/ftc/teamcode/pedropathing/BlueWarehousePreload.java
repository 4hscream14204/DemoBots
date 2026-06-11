package org.firstinspires.ftc.teamcode.pedropathing;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.subsystems.Arm.ArmPosition.HOME;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@Autonomous (name ="Blue Preload and Warehouse Park")
public class BlueWarehousePreload extends OpMode {

    Follower follower;
    SequentialCommandGroup path;
    Arm arm;
    Intake intake;

    Pose startPose = new Pose(5, 89, Math.toRadians(90));
    Pose linesUpToHub = new Pose(48, 7, Math.toRadians(270));
    Pose hubToWall = new Pose(5, 86, Math.toRadians(90));
    Pose endPose = new Pose(5, 134, Math.toRadians(90));

    BezierLine startToHub = new BezierLine(startPose, linesUpToHub);
    BezierLine goesToParkInZone = new BezierLine(hubToWall, endPose);

    PathChain StartToHub;
    PathChain HubToPark;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.createFollower(hardwareMap);
        arm = new Arm(hardwareMap.servo.get("arm"));
        intake = new Intake(hardwareMap.servo.get("intake"));

        StartToHub = follower.pathBuilder()
                .addPath(startToHub)
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(270))
                .build();
        HubToPark = follower.pathBuilder()
                .addPath(goesToParkInZone)
                .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(90))
                .build();

        path = new SequentialCommandGroup(
                new InstantCommand(() -> intake.intake(-1)),
                new FollowPathCommand(follower, StartToHub, true, 1),
                new InstantCommand(() -> intake.intake(-1)),
                new WaitCommand(500),
                new InstantCommand(() -> arm.goToPosition(HOME)),
                new FollowPathCommand(follower, HubToPark, true, 1)
        );
    }

    @Override
    public void start() {
        follower.setStartingPose(startPose);
        path.schedule();
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        follower.update();
    }
}