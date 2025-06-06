package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;

@TeleOp(name = "Simon TeleOp")
public class SimonTeleOp extends OpMode {
    public DcMotor frontLeftMotor;
    public DcMotor backLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backRightMotor;
    public Claw clawSubsystem;
    public Arm armSubsystem;
    @Override
    public void init(){
        frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        frontRightMotor = hardwareMap.dcMotor.get("rightFront");
        backLeftMotor = hardwareMap.dcMotor.get("leftRear");
        backRightMotor = hardwareMap.dcMotor.get("rightRear");
        clawSubsystem = new Claw(hardwareMap.servo.get("claw"));
        armSubsystem = new Arm(hardwareMap.servo.get("arm"));
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void loop(){

        if(gamepad1.left_bumper){
            clawSubsystem.toggleClaw();
        }

        if(gamepad1.a){
            armSubsystem.goToPosition(Arm.ArmPosition.HOME);
        }

        if(gamepad1.b){
            armSubsystem.goToPosition(Arm.ArmPosition.GROUND);
        }

        if(gamepad1.x){
            armSubsystem.goToPosition(Arm.ArmPosition.LOW);
        }

        if(gamepad1.y){
            armSubsystem.goToPosition(Arm.ArmPosition.MEDIUM);
        }

        if(gamepad1.right_bumper){
            armSubsystem.goToPosition(Arm.ArmPosition.HIGH);
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
    }
}
