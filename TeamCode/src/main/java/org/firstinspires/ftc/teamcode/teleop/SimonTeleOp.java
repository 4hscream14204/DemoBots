package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(name = "Simon TeleOp")
public class SimonTeleOp extends OpMode {
    public DcMotor frontLeftMotor;
    public DcMotor backLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backRightMotor;
    public Intake intakeSubsystem;
    public Arm armSubsystem;
    @Override
    public void init(){
        frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        frontRightMotor = hardwareMap.dcMotor.get("rightFront");
        backLeftMotor = hardwareMap.dcMotor.get("leftRear");
        backRightMotor = hardwareMap.dcMotor.get("rightRear");
        intakeSubsystem = new Intake(hardwareMap.servo.get("intake"));
        armSubsystem = new Arm(hardwareMap.servo.get("arm"));
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void loop(){

        if(gamepad1.aWasPressed()){
            armSubsystem.goToPosition(Arm.ArmPosition.HOME);
        }

        if(gamepad1.bWasPressed()){
            armSubsystem.goToPosition(Arm.ArmPosition.LOW);
        }

        if(gamepad1.xWasPressed()){
            armSubsystem.goToPosition(Arm.ArmPosition.MEDIUM);
        }

        if(gamepad1.yWasPressed()){
            armSubsystem.goToPosition(Arm.ArmPosition.HIGH);
        }

        if(gamepad1.rightBumperWasPressed()){
            armSubsystem.goToPosition(Arm.ArmPosition.POLE);
        }

        double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double rx = gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);

        intakeSubsystem.intake(gamepad1.left_trigger / 2 + -1 * gamepad1.right_trigger / 2 + 0.5);


    }
}
