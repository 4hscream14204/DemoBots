package org.firstinspires.ftc.teamcode.base;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.FixedShoulder;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.SALT;
import org.firstinspires.ftc.teamcode.subsystems.Shoulder;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.screamrobotics.SuperSCREAMLib.hardware.Slides;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotBase {

    public Chassis chassisSubSystem;
    public Elbow elbowSubSystem;
    public Wrist wristSubSystem;
    public Intake intakeSubSystem;
    public Extension extensionSubsystem;
    public FixedShoulder shoulderSubsystem;
    public Gate gateSubsystem;

    public SALT saltSubSystem;

    public RobotBase(HardwareMap hw) {



        chassisSubSystem = new Chassis(
                hw.dcMotor.get("left_front"),
                hw.dcMotor.get("right_front"),
                hw.dcMotor.get("left_back"),
                hw.dcMotor.get("right_back"),
                hw.get(SparkFunOTOS.class, "sensor_otos")
        );


        saltSubSystem = new SALT(hw.servo.get("salt"));
        intakeSubSystem = new Intake(hw.servo.get("intakeServoLeft"), hw.servo.get("intakeServoRight"));
        elbowSubSystem = new Elbow(hw.servo.get("elbowServo"));
        extensionSubsystem = new Extension(new Slides(hw.get(DcMotorEx.class, "extensionLeftMotor"), hw.get(DcMotorEx.class, "extensionRightMotor"), Slides.LimitSwitchUsage.YES_TRUE_WHEN_PRESSED, hw.digitalChannel.get("extensionLimitSwitch")));
        extensionSubsystem.slides.setAutomaticExtendPower(-1);
        extensionSubsystem.slides.setAutomaticRetractPower(1);
        shoulderSubsystem = new FixedShoulder(hw.get(DcMotorEx.class, "shoulderMotor"), hw.get(DcMotorEx.class, "rightShoulderMotor"),hw.digitalChannel.get("shoulderLimitSwitch"));
        gateSubsystem = new Gate(hw.servo.get("gateServo"));
        wristSubSystem = new Wrist(hw.servo.get("wristServo"));
    }
}
