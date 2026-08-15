package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Chassis {
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;


    double frontLeftPower;
    double frontRightPower;
    double backLeftPower;
    double backRightPower;

    public  Chassis (DcMotor m_frontLeftMotor , DcMotor m_frontRightMotor , DcMotor m_backLeftMotor , DcMotor m_backRightMotor) {
        frontLeftMotor = m_frontLeftMotor;
        frontRightMotor = m_frontRightMotor;
        backLeftMotor = m_backLeftMotor;
        backRightMotor = m_backRightMotor;

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setPowers(double p_frontLeftPower , double p_frontRightPower, double p_backLeftPower, double p_backRightPower) {

        double maxSpeed = 1.0;

        backLeftPower = p_backLeftPower;
        backRightPower = p_backRightPower;
        frontLeftPower = p_frontLeftPower;
        frontRightPower = p_frontRightPower;

        maxSpeed = Math.max(maxSpeed, Math.abs(frontLeftPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(frontRightPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(backLeftPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(backRightPower));

        frontLeftPower /= maxSpeed;
        frontRightPower /= maxSpeed;
        backLeftPower /= maxSpeed;
        backRightPower /= maxSpeed;

        frontLeftMotor.setPower(frontLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backLeftMotor.setPower(backLeftPower);
        backRightMotor.setPower(backRightPower);

    }

    public void drive(double forward, double right, double rotate) {
        double frontLeftPower = forward + right + rotate;
        double frontRightPower = forward - right - rotate;
        double backLeftPower = forward - right + rotate;
        double backRightPower = forward + right - rotate;


        setPowers(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

    }


}
