package org.firstinspires.ftc.teamcode.Base;

public class RobotBase {
    public Chassis chasssisSubsystem;
    public RobotBase(Hardwaremap hwMap){
        chasssisSubsystem = new Chassis(
                hwMap.dcMotor.get("frontLeftMotor"),
                hwMap.dcMotor.get("frontRighttMotor"),
                hwMap.dcMotor.get("backLeftMotor"),
                hwMap.dcMotor.get("backRightMotor"));


        )
    }
}
