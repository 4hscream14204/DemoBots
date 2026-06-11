package org.firstinspires.ftc.teamcode.pedropathing;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.teamcode.subsystems.Arm.ArmPosition.HIGH;
import static org.firstinspires.ftc.teamcode.subsystems.Arm.ArmPosition.HOME;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Intake;


@Autonomous(name = "Blue Preload and Storage Park ")
public class BlueStoragePreload {
    Follower follower;
    SequentialCommandGroup path;
    Arm arm;
    Intake intake;


    Pose startPose = new Pose(5, 35, Math.toRadians(0));
    Pose goesToHub = new Pose(47,33,Math.toRadians(90));
    Pose endPose = new Pose(35, 12, Math.toRadians(270));

    BezierLine startsAndGoingToLineUp = new BezierLine(startPose, goesToHub);
    BezierLine GoesToParkInZone = new BezierLine(goesToHub, endPose);


    PathChain startToHub;
    PathChain parksInZone;

    @Override
    public void init() {
        CommandScheduler.getInstance().reset();
        follower = Constants.createFollower(hardwareMap);


        startToHub = follower.pathBuilder()
                .addPath(startsAndGoingToLineUp)
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(90))
                .build();
        parksInZone = follower.pathBuilder()
                .addPath(GoesToParkInZone)
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(270))
                .build();

        path = new SequentialCommandGroup(
                new InstantCommand(() -> intake.intake(-1)),
                new FollowPathCommand(follower, startToHub, true, 1),
                new InstantCommand(() -> arm.goToPosition(HIGH),
                new InstantCommand(() -> intake.intake(-1)),
                new WaitCommand(500),
                new InstantCommand(() -> arm.goToPosition(HOME)),
                new FollowPathCommand(follower, parksInZone, true, 1);
    }
    @Override
    public void start(){
        follower.setStartingPose(startPose);
        path.schedule();
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        follower.update();
    }
}

