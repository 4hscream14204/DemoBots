package org.firstinspires.ftc.teamcode.pedropathing;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;


@Autonomous(name = "Park In Blue Zone")
public class BlueParkingAutoRoute extends OpMode{

    Follower follower;
    SequentialCommandGroup path;


    Pose startPose = new Pose(5, 35, Math.toRadians(0));
    Pose linesUpToBox = new Pose(24, 12, Math.toRadians(270));
    Pose endPose = new Pose(35, 12, Math.toRadians(0));

    BezierLine startsAndGoingToLineUp = new BezierLine(startPose, linesUpToBox);
    BezierLine GoesToParkInZone = new BezierLine(linesUpToBox, endPose);


    PathChain startToLineUp;
    PathChain parksInZone;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.createFollower(hardwareMap);

        startToLineUp = follower.pathBuilder()
                .addPath(startsAndGoingToLineUp)
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(270))
                .build();
        parksInZone = follower.pathBuilder()
                .addPath(GoesToParkInZone)
                .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(0))
                .build();

        path = new SequentialCommandGroup(
                new FollowPathCommand(follower, startToLineUp, true, 1),
                new FollowPathCommand(follower, parksInZone, true, 1)
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