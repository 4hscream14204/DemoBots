package org.firstinspires.ftc.teamcode.opmode.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;


import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;
@Autonomous(name = "EasyRouteEatThatPollen")
public class EasyBioBuzzCheckpoint extends OpMode {
    Follower follower;
    RobotBase robotBase;
    SequentialCommandGroup path;

    Pose startPose = new Pose(13, 13, Math.toRadians(90));

    Pose endPose = new Pose(103, 11, Math.toRadians(180));

    BezierLine goingToCheckpointOne = new BezierLine(
            startPose, //90
            new Pose(41, 13) //90
            //path1
    );

    BezierLine checkpointOnePointFive = new BezierLine(
            new Pose(41, 13), //90
            new Pose(41, 70) //180
            //path2
    );

    BezierLine checkpointOne = new BezierLine (
            new Pose(41, 70), //180
            new Pose (20, 70) //180
            //path3
    );

    BezierLine goingToCheckpointTwo = new BezierLine(
            new Pose (20, 70), //180
            new Pose (20,129) //0
            //path4
    );

    BezierLine checkpointTwo = new BezierLine(
            new Pose (20,129), //0
            new Pose (37, 129) //0
            //path5
    );

    BezierLine goingCheckpointThree = new BezierLine(
            new Pose (37, 129), //
            new Pose (82,85) //270
            //path6
    );

    BezierLine CheckpointThree = new BezierLine(
            new Pose (82, 85),
            new Pose (82, 70)
            //path7
    );
    BezierLine goingToCheckpointFour = new BezierLine(
            new Pose (82,70), //270
            new Pose (91, 82) //270
            //path 8
    );

    BezierLine alsoGoingToCheckpointFour = new BezierLine(
            new Pose (91, 82), //270
            new Pose (127, 83) //0
            //path 9
    );

    BezierLine stillCheckpointFour = new BezierLine(
            new Pose (127, 83), //0
            new Pose (126, 131) //180
            //path 10
    );

    BezierLine checkpointFour = new BezierLine(
            new Pose (126, 131), //0
            new Pose (110, 131) //180
            //path 11
    );

    BezierLine goingToCheckpointFive(
      new Pose (110, 131),
      new Pose (126, 131)
        //path 12
    );

    BezierCurve checkpointFive = new BezierCurve(
            new Pose (126, 131), //180
            new Pose (129,11) //180
            // path 13
    );

    BezierLine finish = new BezierLine(
            new Pose (129, 11),
            endPose
            //path 14
    );

PathChain completeCheckpointOne; // does path 1 + 1.5 + 2
PathChain doTwo; // intakes on path 4
PathChain checkThree; //path5 goes to check point 3
PathChain doFour; // combines path 6 , 7 ,
PathChain doFive; // path 9
PathChain finished; // path 10



    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        robotBase = new RobotBase(hardwareMap);

        completeCheckpointOne = follower.pathBuilder()
                .addPath(goingToCheckpointOne)
                .setLinearHeadingInterpolation(startPose.getHeading(), Math.toRadians(180))
                .addPath(checkpointOnePointFive)
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(checkpointOne)
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();


        doTwo = follower.pathBuilder()
                .addPath(goingToCheckpointTwo)
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0))
                .addPath(checkpointTwo)
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        checkThree = follower.pathBuilder()
                .addPath(goingCheckpointThree)
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(270))
                .addPath(CheckpointThree)
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();

        doFour = follower.pathBuilder()
                .addPath(goingToCheckpointFour)
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .addPath(alsoGoingToCheckpointFour)
                .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(180))
                .addPath(stillCheckpointFour)
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(checkpointFour)
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        doFive = follower.pathBuilder()
                .addPath(goingToCheckpointFive)
                .addPath(checkpointFive)
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(270))
                .build();
        finished = follower.pathBuilder()
                .addPath(finish)
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();

        path = new SequentialCommandGroup(
                new FollowPathCommand(follower,completeCheckpointOne, false, 1),
                new FollowPathCommand(follower, doTwo, true, 1),
                new FollowPathCommand(follower, checkThree, true, 1)
                ,new FollowPathCommand(follower, doFour, true, 1)
                 ,new FollowPathCommand(follower, doFourAgain, true, 1)
                // new FollowPathCommand(follower, doFive, true, 1),
// new FollowPathCommand(follower, finished, true, 1)

        );

    }

    @Override
    public void start() {
        CommandScheduler.getInstance().schedule(path);
        follower.setStartingPose(startPose);

    }

    @Override
    public void loop() {
        follower.update();
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("X", follower.getPose().getY());
        telemetry.addData("Heading", Math.toRadians(follower.getPose().getHeading()));
        CommandScheduler.getInstance().run();
    }

    @Override
    public void stop() {

    }
}
