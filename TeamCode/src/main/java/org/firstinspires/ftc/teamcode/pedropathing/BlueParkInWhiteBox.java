package org.firstinspires.ftc.teamcode.pedropathing;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.base.DataStorage;
import org.firstinspires.ftc.teamcode.base.RobotBase;

public class BlueParkInWhiteBox {


    @Autonomous(name = "Park In Warehouse Zone")
    Follower follower;
    Robotbase robotbase;
    SequentialCommandGroup path;


    Pose startPose = new Pose(5,89, Math.toRadians(0));
    Pose endPose = new Pose(6,134,Math.toRadians(0));

    BezierLine goesToParkInZone = new BezierLine(startPose,endPose);


    PathChain startToPark;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.createFollower(hardwareMap);
        robotBase = new RobotBase(hardwareMap);

        startToPark = follower.pathBuilder()
                .addPath(goesToParkInZone)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();


    }}