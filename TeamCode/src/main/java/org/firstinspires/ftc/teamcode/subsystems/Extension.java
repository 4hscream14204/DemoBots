package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class Extension {
    public DcMotor extensionMotor;
    public DigitalChannel tsExtensionLimitSwitch;

    public enum ExtensionPosition {
        HOME(0),
        LOWBUCKET(-1020),
        MAXSHOULDERDOWNPOSITION(-2933),
        HIGHCHAMBER(-1700);
        public final int height;
        ExtensionPosition(int high) {
            this.height = high;
        }
    }

    public double dblUpPower = -1;
    public double dblDownPower = 1;
    public boolean bolStopped = true;
    public int intCurrentPos;
    public int intMaxPosition;

    public ExtensionPosition enmExtensionPosition;

    public Extension(DcMotor m_extensionLeftMotor, DigitalChannel m_TsExtensionLimitSwitch) {
        extensionMotor = m_extensionLeftMotor;
        tsExtensionLimitSwitch = m_TsExtensionLimitSwitch;
        extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extensionMotor.setTargetPosition(0);
        extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        enmExtensionPosition = ExtensionPosition.HOME;
    }

    public void extend(double power) {
        if (extensionGetPosition() < intMaxPosition) {
            stopInPlace();
        }
        if (isExtensionHome() && power > 0) {
            extensionMotor.setPower(0);
            reset();
            return;
        }
        else {
            extensionMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            extensionMotor.setPower(power);
            bolStopped = false;
        }
    }
    public void extendForward(double power) {
        if (extensionGetPosition() < intMaxPosition) {
            stopInPlace();
        }
    }
    public void goToPosition(ExtensionPosition enmTargetPosition) {
        if (extensionGetPosition() < enmTargetPosition.height) {
            enmExtensionPosition = enmTargetPosition;
            extensionMotor.setPower(dblDownPower);
        }
        else if (extensionGetPosition() > enmTargetPosition.height) {
            enmExtensionPosition = enmTargetPosition;
            extensionMotor.setPower(dblUpPower);
        }
        else if (isAtPosition(enmTargetPosition)) {
            stopInPlace();
            return;
        }
        extensionMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extensionMotor.setTargetPosition(enmTargetPosition.height);
        bolStopped = false;
    }
    public void stopInPlace() {
        if (bolStopped) {
            return;
        }
        bolStopped = true;
        if(isExtensionHome()) {
            reset();
        }
        else {
            extensionMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            intCurrentPos = extensionGetPosition();
            extensionMotor.setTargetPosition(intCurrentPos);
            extensionMotor.setPower(-0.1);
        }
    }
    public boolean isAtPosition(ExtensionPosition targetPosition) {
        if (Math.abs(extensionGetPosition() - targetPosition.height) <= 50) {
            return true;
        }
        return false;
    }
    public boolean isExtensionHome() {
        return tsExtensionLimitSwitch.getState();
    }
    public void reset() {
        bolStopped = false;
        extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extensionMotor.setTargetPosition(0);
        extensionMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extensionMotor.setPower(0);
    }
    public int extensionGetPosition() {
        return extensionMotor.getCurrentPosition();
    }
}
