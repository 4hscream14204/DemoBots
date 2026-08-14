package org.firstinspires.ftc.teamcode.commands;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "THIS IS AMERICA NO 'COLOUR'")
public class CircleColor extends OpMode {
    double color;
    boolean gamepadD_down;
    boolean lastGamepadD_down;
    Servo light;

    @Override
    public void init() {
       double color = 0;
       light = hardwareMap.servo.get("light");
    }

    @Override
    public void loop() {
        /*lastGamepadD_down = gamepadD_down;
        gamepadD_down = gamepad1.dpad_down;
        if (gamepadD_down && !lastGamepadD_down) {
            color = Math.random();
            while (color < 0.28 || color > 0.73) {
                color = Math.random();
            }
        }
        light.setPosition(color);*/
        light.setPosition(((Math.toDegrees(Math.atan2(gamepad1.right_stick_y, gamepad1.right_stick_x)) + 180) / 1080) + 0.28);
    }
}
