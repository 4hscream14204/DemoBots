package org.firstinspires.ftc.teamcode.opmode.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Base.RobotBase;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;
 @Autonomous(name = "EatThatPollenEasyPeasy")

public class EasyBioBuzzCheckpoint extends OpMode {
    Follower follower;
    RobotBase robotBase;
    SequentialCommandGroup path;
    Pose startPose = new Pose(10,10, Math.toRadians(90));
    Pose endPose = new Pose(83, 11, Math.toRadians(180));

    BezierLine GoingToPoseOne = new BezierLine(
            startPose, //90
           new Pose (38, 34));
    BezierLine GoingToCheckpointOne = new BezierLine(
            new Pose (38, 34),
            new Pose (13, 59));

BezierLine GoingToPoseThree = new BezierLine(
        new Pose (13,59),
        new Pose (15,128));

BezierLine GoingToCheckpointTwo = new BezierLine(
        new Pose (15, 128),
        new Pose (36,129));

BezierCurve GoingToCheckpointThree = new BezierCurve(
        new Pose (36,129),
    new Pose (89,110),
    new Pose (82, 59));

BezierCurve GoingToPoseSix = new BezierCurve(
       new Pose (82,59),
        new Pose (85,85),
        new Pose (106,84));

     BezierLine GoingToPoseSeven = new BezierLine(
             new Pose (106,84),
             new Pose(127,84));

     BezierCurve GoingToCheckpointFour = new BezierCurve(
             new Pose (127,84),
        new Pose(134,134),
        new Pose(105,128));
     BezierCurve GoingToPoseNine = new BezierCurve(
             new Pose (105,128),
             new Pose(136,136),
             new Pose(127,83));

     BezierLine GoingToCheckpointFive = new BezierLine(
             new Pose (127,83),
             new Pose(130,13));

     BezierLine GoingToEndPose = new BezierLine(
             new Pose(130,13),
            endPose); //270

     PathChain FinishCheckpointOne;
     PathChain FinishCheckpointTwo;
     PathChain FinishCheckpointThree;
     PathChain FinishPoseSix;
     PathChain FinishCheckpointFour;
     PathChain FinishPoseSeven;
     PathChain FinishPoseNine;
     PathChain FinishCheckpointFive;
     PathChain FinishCourse;


    @Override
    public void init(){
        CommandScheduler.getInstance().reset();
        follower = Constants.createFollower(hardwareMap);
        robotBase = new RobotBase(hardwareMap);

        FinishCheckpointOne = follower.pathBuilder()
                .addPath(GoingToPoseOne)
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .addPath(GoingToCheckpointOne)
                .setLinearHeadingInterpolation(startPose.getHeading(),Math.toRadians(180))
                .build();

        FinishCheckpointTwo = follower.pathBuilder()
                .addPath(GoingToPoseThree)
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .addPath(GoingToCheckpointTwo)
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .build();

        FinishCheckpointThree = follower.pathBuilder()
                .addPath(GoingToCheckpointThree)
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();

        FinishPoseSix = follower.pathBuilder()
                .addPath(GoingToPoseSix)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        FinishCheckpointFour = follower.pathBuilder()
                .addPath(GoingToCheckpointFour)
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        FinishPoseSeven = follower.pathBuilder()
                .addPath(GoingToPoseSeven)
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();

        FinishPoseNine = follower.pathBuilder()
                .addPath(GoingToPoseNine)
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();

        FinishCheckpointFive = follower.pathBuilder()
                .addPath(GoingToCheckpointFive)
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();

        FinishCourse = follower.pathBuilder()
                .addPath(GoingToEndPose)
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();

        path = new SequentialCommandGroup();
    }
    @Override
    public void start(){
        CommandScheduler.getInstance().schedule(path);
        follower.setStartingPose(startPose);

    }
    @Override
    public void loop(){
        follower.update();
        telemetry.addData("Y",follower.getPose().getY());
        telemetry.addData("X",follower.getPose().getX());
        telemetry.addData("Heading",Math.toRadians(follower.getPose().getHeading()));
        CommandScheduler.getInstance().run();
    }
    @Override
    public void stop(){
    }
}
