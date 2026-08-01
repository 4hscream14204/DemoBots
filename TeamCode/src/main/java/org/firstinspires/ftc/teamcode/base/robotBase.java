package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Chassis;

public class robotBase {

    public Chassis chassisSubSystem;

    public robotBase(HardwareMap hwMap){
        chassisSubSystem = new Chassis(
                hwMap.dcMotor.get("frontLeftMotor"),
                hwMap.dcMotor.get("frontRightMotor"),
                hwMap.dcMotor.get("backLeftMotor"),
                hwMap.dcMotor.get("backRightMotor"));


    }


}
