package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensor;
import org.firstinspires.ftc.teamcode.subsystems.DuckSpinner;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;

public class RobotBase {


    public Chassis chassisSubsystem;
    public Arm armSubsystem;

    public Intake intakeSubsystem;
    public Extension extensionSubsystem;
    public Limelight limelightSubsystem;
    public ColorSensor colorSensorSubsystem;
    public DuckSpinner duckSpinnerSubsystem;
    public Camera cameraSubsystem;


    public RobotBase(HardwareMap hwMap){
        chassisSubsystem = new Chassis(
                hwMap.dcMotor.get("leftFront"),
                hwMap.dcMotor.get("rightFront"),
                hwMap.dcMotor.get("leftRear"),
                hwMap.dcMotor.get("rightRear"));
        armSubsystem = new Arm(
                hwMap.servo.get("arm"));
        intakeSubsystem = new Intake(
                hwMap.servo.get("intake"));
        limelightSubsystem = new Limelight(hwMap.get(Limelight3A.class, "limelight"));
        limelightSubsystem.initLimelight(Limelight.LimelightPipeline.FIRST);
        colorSensorSubsystem = new ColorSensor(hwMap.get(RevColorSensorV3.class, "colorSensor"));
        duckSpinnerSubsystem = new DuckSpinner(hwMap.servo.get("duckSpinner"));
        cameraSubsystem = new Camera(hwMap.get(WebcamName.class, "camera"));


        /*extensionSubsystem = new Extension(
                hwMap.dcMotor.get("extensionMotor"),
                hwMap.get(DigitalChannel.class, "extensionLimitSwitch"));*/



    }
}
