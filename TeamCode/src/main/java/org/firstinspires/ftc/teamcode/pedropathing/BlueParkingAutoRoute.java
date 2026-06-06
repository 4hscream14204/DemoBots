package org.firstinspires.ftc.teamcode.pedropathing;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;

@Autonomous(name = "Park In Blue Zone")
public class BlueParkingAutoRoute extends OpMode {
    Follower follower;
    Robotbase robotbase;
    SequentialCommandGroup path;


    Pose startPose = new Pose(5,83, Math.toRadians(0));
    Pose linesUpToBox = new Pose(6,9,Math.toRadians(270));
    Pose endPose = new Pose(36,10,Math.toRadians(0));

    BezierLine startsAndGoingToLineUp = new BezierLine(startPose,linesUpToBox);
    BezierLine GoesToParkInZone = new BezierLine(linesUpToBox,endPose);


    PathChain startToLineUp;
    PathChain parksInZone;
    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.createFollower(hardwareMap);
        robotBase = new RobotBase(hardwareMap);

        startToLineUp = follower.pathBuilder()
                .addPath(startsAndGoingToLineUp)
                .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(270))
                .build();
        parksInZone = follower.pathBuilder()
                .addPath(GoesToParkInZone)
                .setLinearHeadingInterpolation(Math.toRadians(270),Math.toRadians(0))
                .build();









    }}