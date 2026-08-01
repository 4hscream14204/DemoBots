package org.firstinspires.ftc.teamcode.base;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class RobotBase {

    public Chassis chassisSubSystem;
   public Arm armSubSystem;
   public Intake intakeSubSystem;
  //  public Extension extensionSubSystem;

    public RobotBase(HardwareMap hwMap){
        chassisSubSystem = new Chassis(
                hwMap.dcMotor.get("frontLeftMotor"),
                hwMap.dcMotor.get("frontRightMotor"),
                hwMap.dcMotor.get("backLeftMotor"),
                hwMap.dcMotor.get("backRightMotor"));

       armSubSystem = new Arm(hwMap.servo.get("armServo"));
       intakeSubSystem = new Intake(hwMap.servo.get("intakeServo"));

       /* extensionSubSystem = new Extension(
                hwMap.dcMotor.get("extensionMotor"),
                hwMap.get(DigitalChannel.class, "extensionLimitSwitch")); */
    }


}
