package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private Servo srvIntake;

    public Intake(Servo m_srvIntake) {
        srvIntake = m_srvIntake;
    }

    public void intake(double power){
        srvIntake.setPosition(power);
    }

    public void stop(){
        srvIntake.setPosition(0.5);
    }

}
