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
import com.sun.tools.javac.code.Attribute;

import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;
@Autonomous(name = "EasyRouteEatThatPollen")
public class EasyBioBuzzCheckpoint extends OpMode {
    Follower follower;
    RobotBase robotBase;
    SequentialCommandGroup path;

    Pose startPose = new Pose(10, 10, Math.toRadians(90));

    Pose endPose = new Pose(82, 10, Math.toRadians(180));

    BezierLine goingToCheckpointOne = new BezierLine(
            startPose, //90
            new Pose(43, 31) //180
            //path1
    );

    BezierLine checkpointOne = new BezierLine (
            new Pose(43, 31), //90
            new Pose (15, 58) //143
            //path2
    );

    BezierCurve goingToCheckpointTwo = new BezierCurve(
            new Pose (15, 58), //90
            new Pose (2, 116),
            new Pose (15,128) //0
            //path3
    );

    BezierLine checkpointTwo = new BezierLine(
            new Pose (15,128), //0
            new Pose (35, 129) //0
            //path4
    );

    BezierCurve CheckpointThree = new BezierCurve(
            new Pose (35, 129), //0
            new Pose (93, 104),
            new Pose (84,59) //270
            //path5
    );

    BezierLine goingToCheckpointFour = new BezierLine(
            new Pose (84,59), //270
            new Pose (91, 81) //270
            //path 6
    );

    BezierLine alsoGoingToCheckpointFour = new BezierLine(
            new Pose (91, 81), //270
            new Pose (130, 85) //0
            //path 7
    );

    BezierCurve checkpointFour = new BezierCurve(
            new Pose (130, 85), //0
            new Pose (137, 139),
            new Pose (108, 129) //180
            //path 8
    );


    BezierCurve checkpointFive = new BezierCurve(
            new Pose (108, 129), //180
            new Pose (137, 139),
            new Pose (132,9) //180
            // path 9
    );

    BezierLine finish = new BezierLine(
            new Pose (132, 9),
            endPose
    );

PathChain completeCheckpointOne; // does path 1 + 2
PathChain curveCheckTwo; // does just path 3
PathChain doTwo; // intakes on path 4
PathChain checkThree; //path5 goes to check point 3
PathChain doFour; // combines path 6 , 7 ,
PathChain doFourAgain; // path 8
PathChain doFive; // path 9
PathChain finished; // path 10



    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        robotBase = new RobotBase(hardwareMap);

        completeCheckpointOne = follower.pathBuilder()
                .addPath(goingToCheckpointOne)
                .setConstantHeadingInterpolation(startPose.getHeading())
                .addPath(checkpointOne)
                .setLinearHeadingInterpolation(startPose.getHeading(), Math.toRadians(143))
                .build();

        curveCheckTwo = follower.pathBuilder()
                .addPath(goingToCheckpointTwo)
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
                .build();
        doTwo = follower.pathBuilder()
                .addPath(checkpointTwo)
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
                .build();

        checkThree = follower.pathBuilder()
                .addPath(CheckpointThree)
                .setLinearHeadingInterpolation(Math.toRadians(0) , Math.toRadians(270))
                .build();
        doFour = follower.pathBuilder()
                .addPath(goingToCheckpointFour)
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .addPath(alsoGoingToCheckpointFour)
                .setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(0))
                .build();
        doFourAgain = follower.pathBuilder()
                .addPath(checkpointFour)
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180))
                .build();
        doFive = follower.pathBuilder()
                .addPath(checkpointFive)
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(270))
                .build();
        finished = follower.pathBuilder()
                .addPath(finish)
                .setConstantHeadingInterpolation(Math.toRadians(270))
                .build();

        path = new SequentialCommandGroup(
                new FollowPathCommand(follower,completeCheckpointOne, true, 1),
                new FollowPathCommand(follower, curveCheckTwo, true, 1),
                new FollowPathCommand(follower, doTwo, false, 1)


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
