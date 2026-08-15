package org.firstinspires.ftc.teamcode.base;
import org.firstinspires.ftc.teamcode.subsystems.Chassis;
import org.firstinspires.ftc.teamcode.subsystems.Elbow;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotBase {

    public Chassis ChassisSubSystem;
    public Elbow ElbowSubSystem;
    public Wrist WristSubSystem;
    public Intake IntakeSubSystem;

    public RobotBase(HardwareMap hw) {



        ChassisSubSystem = new Chassis(
                hw.dcMotor.get("frontLeftMotor"),
                hw.dcMotor.get("frontRightMotor"),
                hw.dcMotor.get("backLeftMotor"),
                hw.dcMotor.get("backRightMotor")
        );

        WristSubSystem = new Wrist(hw.servo.get("wristServo"));
        IntakeSubSystem = new Intake(hw.servo.get("intakeServoL"), hw.servo.get("intakeServoR"));
        ElbowSubSystem = new Elbow(hw.servo.get("elbowServo"));
    }


}
