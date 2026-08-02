package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Chassis {
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;

    double frontLeftPower;
    double frontRightPower;
    double backLeftPower;
    double backRightPower;

    PIDFController headingControl = new PIDFController(2, 0, 0.1, 0.1);
    ElapsedTime timer;
    public double dblHeadingOutput;
    double headingDeviation;
    public double targetHeading;
    double lastStickTime;
    double currentTime;
    double delayTime = 1000;
    boolean isUsingPID = false;

    public Chassis (DcMotor m_frontLeftMotor, DcMotor m_frontRightMotor, DcMotor m_backLeftMotor, DcMotor m_backRightMotor) {
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
    public void drive(double m_gamepadLSX, double m_gamepadLSY, double m_gamepadRSX, double m_gamepadRSY, double botHeadingRad, boolean m_isFieldCentric, ElapsedTime m_timer) {

        timer = m_timer;
        currentTime = timer.milliseconds();
        double rx = m_gamepadRSX;

        if (m_isFieldCentric) {;
            double rotX = m_gamepadLSX * Math.cos(-botHeadingRad) - (-m_gamepadLSY) * Math.sin(-botHeadingRad);
            double rotY = m_gamepadLSX * Math.sin(-botHeadingRad) + (-m_gamepadLSY) * Math.cos(-botHeadingRad);

            if (Math.abs(m_gamepadRSX) > 0.1) {
                lastStickTime= currentTime;
            }
            else if ((currentTime - lastStickTime) < delayTime) {
                targetHeading = botHeadingRad;
            }
            else if (!isUsingPID) {
                headingDeviation = (botHeadingRad - targetHeading) * -1;
                headingDeviation = AngleUnit.normalizeRadians(headingDeviation);
                dblHeadingOutput = headingControl.calculate(headingDeviation);
                rx = dblHeadingOutput;
            }

            rotX = rotX * 1.1;

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            frontLeftPower = (rotY + rotX + rx) / denominator;
            backLeftPower = (rotY - rotX + rx) / denominator;
            frontRightPower = (rotY - rotX - rx) / denominator;
            backRightPower = (rotY + rotX - rx) / denominator;
        }
        else {
            double y = -m_gamepadLSY;
            double x = m_gamepadLSX * 1.1;
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            frontLeftPower = (y + x + rx) / denominator;
            backLeftPower = (y - x + rx) / denominator;
            frontRightPower = (y - x - rx) / denominator;
            backRightPower = (y + x - rx) / denominator;
        }
        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
    }

    public void setTargetHeading(double degrees) {
        targetHeading = Math.toRadians(degrees);
    }
}