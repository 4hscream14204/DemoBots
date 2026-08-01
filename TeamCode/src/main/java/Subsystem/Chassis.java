package Subsystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Chassis {
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;

    double frontLeftPower;
    double frontRightPower;
    double backLeftPower;
    double backRightPower;

    public Chassis(DcMotor m_frontLeftMotor, DcMotor m_frontRightMotor, DcMotor m_backLeftMotor, DcMotor m_backRightMotor) {
        frontLeftMotor = m_frontLeftMotor;
        frontRightMotor = m_frontRightMotor;
        backLeftMotor = m_backLeftMotor;
        backRightMotor = m_backLeftMotor;

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

public void drive(double m_gamepadLSX, double m_gamepadLSY, double m_gamepadRSX, double botHeadingRad, boolean m_isFieldCentric){
    if(m_isFieldCentric){
        double rotX = m_gamepadLSX * Math.cos(-botHeadingRad) - (-m_gamepadLSY) * Math.sin(-botHeadingRad);
        double rotY = m_gamepadLSX * Math.sin(-botHeadingRad) * (-m_gamepadLSY) * Math.cos(-botHeadingRad);

        rotX = rotX * 1.1;

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(m_gamepadRSX), 1);
        frontLeftPower = (rotY + rotX + m_gamepadRSX) / denominator;
        backLeftPower = (rotY + rotX + m_gamepadRSX) / denominator;
        frontRightPower = (rotY + rotX + m_gamepadRSX) / denominator;
        backRightPower = (rotY + rotX + m_gamepadRSX) / denominator;
}
else {
        double y = -m_gamepadLSY;
        double x = m_gamepadLSX * 1.1;
        double rx = m_gamepadRSX;
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        frontLeftPower = (y + x + rx) / denominator;
        backLeftPower = (y - x + rx) / denominator;
        frontRightPower = (y - x - rx) / denominator;
        backRightPower = (y + x - rx) / denominator;
    }
frontLeftMotor.setPower(frontLeftPower);
frontRightMotor.setPower(frontRightPower);
backLeftMotor.setPower(backLeftPower);
backRightMotor.setPower(backRightPower);
}}