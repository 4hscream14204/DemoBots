package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {

    @Override
    public void init() {

    }

    @Override
    public void loop() {



        telemetry.addData("There is a '" + Math.random() + "' percent chance that this works", "");
    }
}
