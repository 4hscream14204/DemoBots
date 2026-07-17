package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class RobotBase {

    public Chassis chassisSubsystem;
    public Arm armSubsystem;
    public Intake intakeSubsystem;

    public RobotBase(HardwareMap hwMap){
        chassisSubsystem = new Chassis(
                hwMap.dcMotor.get("frontLeftMotor"),
                hwMap.dcMotor.get("frontRightMotor"),
                hwMap.dcMotor.get("backLeftMotor"),
                hwMap.dcMotor.get("backRightMotor"));

        armSubsystem = new Arm(hwMap.servo.get("armServo"));
        intakeSubsystem = new Intake(hwMap.servo.get("intakeServo"));
    }

}
