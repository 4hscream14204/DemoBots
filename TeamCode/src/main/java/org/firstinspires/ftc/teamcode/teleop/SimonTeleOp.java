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
    Gamepad currentGamepad;
    Gamepad previousGamepad;
    @Override
    public void init(){
        frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        frontRightMotor = hardwareMap.dcMotor.get("rightFront");
        backLeftMotor = hardwareMap.dcMotor.get("leftRear");
        backRightMotor = hardwareMap.dcMotor.get("rightRear");
        intakeSubsystem = new Intake(hardwareMap.servo.get("intake"));
        armSubsystem = new Arm(hardwareMap.servo.get("arm"));
        currentGamepad = new Gamepad();
        previousGamepad = new Gamepad();
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addLine("Left Stick - Move");
        telemetry.addLine("Right Stick - Turn");
        telemetry.addLine("Left Trigger - Outtake. Right Trigger - Intake");
        telemetry.addLine("A - Home arm");
        telemetry.addLine("B - Low goal");
        telemetry.addLine("X - Mid Goal");
        telemetry.addLine("Y - High Goal");

    }
    public void loop(){
        previousGamepad.copy(currentGamepad);
        currentGamepad.copy(gamepad1);

        if(currentGamepad.a && !previousGamepad.a){
            armSubsystem.goToPosition(Arm.ArmPosition.HOME);
        }

        if(currentGamepad.b && !previousGamepad.b){
            armSubsystem.goToPosition(Arm.ArmPosition.LOW);
        }

        if(currentGamepad.x && !previousGamepad.x){
            armSubsystem.goToPosition(Arm.ArmPosition.MEDIUM);
        }

        if(currentGamepad.y && !previousGamepad.y){
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

        intakeSubsystem.intake(gamepad1.left_trigger / 2 + -1 * gamepad1.right_trigger / 2 + 0.5);



    }
}
