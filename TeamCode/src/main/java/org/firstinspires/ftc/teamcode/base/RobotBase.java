package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;

public class RobotBase {
    public Chassis chassisSubsystem;
    public Arm armSubsystem;
    public Intake intakeSubsystem;
    public Extension extensionSubsystem;
    public Limelight limelightSubsytem;

    public RobotBase(HardwareMap hwMap) {
        chassisSubsystem = new Chassis(
                hwMap.dcMotor.get("leftFront"),
                hwMap.dcMotor.get("rightFront"),
                hwMap.dcMotor.get("leftRear"),
                hwMap.dcMotor.get("rightRear"));
        armSubsystem = new Arm(hwMap.servo.get("armServo"));
        intakeSubsystem = new Intake(hwMap.servo.get("intakeServo"));
        limelightSubsytem = new Limelight(hwMap.get(Limelight3A.class, "limelight"));
        limelightSubsytem.initLimelight(Limelight.LimelightPipeline.FIRST);
        /*extensionSubsystem = new Extension(
                hwMap.dcMotor.get("extensionMotor"),
                hwMap.get(DigitalChannel.class, "extensionLimitSwitch"));*/
    }
}