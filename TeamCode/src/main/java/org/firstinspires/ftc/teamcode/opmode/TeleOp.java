package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {

    Chassis drive;
    IMU imu;

    @Override
    public void init() {
    drive.init(hardwareMap);

    imu = hardwareMap.get(IMU.class, "h");
        RevHubOrientationOnRobot revHubOrientationOnRobot = new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD);

        imu.initialize(new IMU.Parameters(revHubOrientationOnRobot));
    }

    private void driveFieldRelative(double forward, double right, double rotate){
        double robotAngle = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double theta = Math.atan2(forward, right);
        double r = Math.hypot(forward, right);
        double newForward = r * Math.sin(theta);
        double newRight = r * Math.cos(theta);

        drive.drive(newForward, newRight, rotate);
    }

    @Override
    public void loop() {
        double forward = -gamepad1.left_stick_y;
        double right = gamepad1.left_stick_x;
        double rotate = gamepad1.right_stick_x;

        driveFieldRelative(forward, right, rotate);


        telemetry.addData("There is a '" + Math.random() + "' percent chance that this works", "");
    }
}
