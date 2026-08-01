package org.firstinspires.ftc.teamcode.Base;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import Subsystem.Arm;
import Subsystem.Chassis;
import Subsystem.Extension;
import Subsystem.Intake;

public class RobotBase {
    public Chassis chassisSubsystem;
    public Arm ArmSubsystem;
    public Intake IntakeSubsystem;
    public Extension ExtensionSubsystem;

    public RobotBase(HardwareMap hwMap) {
        chassisSubsystem = new Chassis(
                hwMap.dcMotor.get("frontLeftMotor"),
                hwMap.dcMotor.get("frontRightMotor"),
                hwMap.dcMotor.get("backLeftMotor"),
                hwMap.dcMotor.get("backRightMotor"));

        ArmSubsystem = new Arm(hwMap.servo.get("armServo"));
        IntakeSubsystem = new Intake(hwMap.servo.get("intakeServo"));

    }
}