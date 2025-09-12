package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.HeadingLock;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@TeleOp(name = "LimelightTest")
public class LimelightTesting extends OpMode {
    DcMotor frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
    DcMotor frontRightMotor = hardwareMap.dcMotor.get("rightFront");
    DcMotor backLeftMotor = hardwareMap.dcMotor.get("leftRear");
    DcMotor backRightMotor = hardwareMap.dcMotor.get("rightRear");
    SparkFunOTOS otos = hardwareMap.get(SparkFunOTOS.class, "otos");
    Limelight3A limelight = hardwareMap.get(Limelight3A.class, "limelight");
    Servo armServo = hardwareMap.servo.get("arm");
    Servo clawServo = hardwareMap.servo.get("claw");
    ElapsedTime timer;
    Follower follower;
    HeadingLock headingLock = new HeadingLock(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor, timer, follower);
    GamepadEx chassis = new GamepadEx(gamepad1);
    LLResult result;
    Arm armSubsystem;
    Claw clawSubsystem;
    @Override
    public void init(){
        armSubsystem = new Arm(armServo);
        clawSubsystem = new Claw(clawServo);
        CommandScheduler.getInstance().reset();
        timer = new ElapsedTime();
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        chassis.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->headingLock.setTargetDegrees(45))));
        limelight.start();
        CommandScheduler.getInstance().schedule(new InstantCommand(()->armSubsystem.goToPosition(Arm.ArmPosition.MEDIUM)));
    }

    @Override
    public void loop(){
        CommandScheduler.getInstance().run();
        result = limelight.getLatestResult();
        headingLock.drive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        telemetry.addData("tX", result.getTx());
        telemetry.addData("Ty", result.getTy());
    }
}
