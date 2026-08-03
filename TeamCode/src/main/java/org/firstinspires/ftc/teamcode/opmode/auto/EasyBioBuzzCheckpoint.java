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
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

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
    BezierLine GoingToPoseTwo = new BezierLine(
            new Pose (38, 9),
            new Pose (39, 62));
     BezierLine GoingToCheckpointOne = new BezierLine(
             new Pose (39, 62),
             new Pose (12, 62));

BezierLine GoingToPoseThree = new BezierLine(
        new Pose (12,62),
        new Pose (12,127));

BezierLine GoingToCheckpointTwo = new BezierLine(
        new Pose (12, 127),
        new Pose (35,127));

BezierCurve GoingToCheckpointThree = new BezierCurve(
        new Pose (35,127),
    new Pose (76,110),
    new Pose (71, 68));

BezierCurve GoingToPoseSix = new BezierCurve(
       new Pose (71,68),
        new Pose (93,90),
        new Pose (117,84));

     BezierCurve GoingToCheckpointFour = new BezierCurve(
             new Pose (117,84),
        new Pose(140,140),
        new Pose(105,138));
     BezierCurve GoingToPoseNine = new BezierCurve(
             new Pose (105,138),
             new Pose(140,140),
             new Pose(117,84));

     BezierLine GoingToCheckpointFive = new BezierLine(
             new Pose (117,84),
             new Pose(125,12));

     BezierLine GoingToEndPose = new BezierLine(
             new Pose(125,12),
            endPose); //270

     PathChain FinishCheckpointOne;
     PathChain FinishCheckpointTwo;
     PathChain FinishCheckpointThree;
     PathChain FinishPoseSix;
     PathChain FinishCheckpointFour;
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
                .addPath(GoingToPoseTwo)
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .addPath(GoingToCheckpointOne)
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        FinishCheckpointTwo = follower.pathBuilder()
                .addPath(GoingToPoseThree)
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0))
                .addPath(GoingToCheckpointTwo)
                .setConstantHeadingInterpolation(Math.toRadians(0))
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

        path = new SequentialCommandGroup(
                new FollowPathCommand(follower,FinishCheckpointOne, true, 1),
                new FollowPathCommand(follower, FinishCheckpointTwo, true, 1),
                new FollowPathCommand(follower, FinishCheckpointThree, true, 1),
               new FollowPathCommand(follower, FinishPoseSix, true, 1),
                new FollowPathCommand(follower, FinishCheckpointFour, true, 1),
                new FollowPathCommand(follower, FinishPoseNine, true, 1),
                new FollowPathCommand(follower, FinishCheckpointFive, true, 1),
                new FollowPathCommand(follower, FinishCourse, true, 1)
        );
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
