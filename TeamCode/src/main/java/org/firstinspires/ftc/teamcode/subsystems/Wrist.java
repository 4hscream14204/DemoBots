package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.google.blocks.ftcrobotcontroller.runtime.BlocksOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
    public class Wrist {
        public Servo wristServo;
         public enum wristPosition {
            HOME(0.25),
             OUTTAKE(0.3);
            wristPosition(double m_position){
                double position = m_position;
            }
        }

    }
