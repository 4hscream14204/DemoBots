package org.firstinspires.ftc.teamcode.Base;

import com.qualcomm.robotcore.hardware.HardwareMap;

import Subsystem.Chassis;

public class RobotBase {
    public Chassis chassisSubsystem;
    public RobotBase(HardwareMap hwMap){
        chassisSubsystem = new Chassis(
                hwMap.dcMotor.get("frontLeftMotor"),
                hwMap.dcMotor.get("frontRightMotor"),
                hwMap.dcMotor.get("backLeftMotor"),
                hwMap.dcMotor.get("backRightMotor"));



    }
}
