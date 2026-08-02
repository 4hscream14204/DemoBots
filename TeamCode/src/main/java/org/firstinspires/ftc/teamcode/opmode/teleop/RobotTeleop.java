package org.firstinspires.ftc.teamcode.opmode.teleop;


import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import org.firstinspires.ftc.teamcode.base.RobotBase;
import org.firstinspires.ftc.teamcode.commands.DropOffCommandGroup;
import org.firstinspires.ftc.teamcode.commands.LightColorSensorControlCommand;
import org.firstinspires.ftc.teamcode.commands.SetArmPositionCommandGroup;
import org.firstinspires.ftc.teamcode.pedropathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.DuckSpinner;


@Configurable
@TeleOp(name = "TeleOp")
public class RobotTeleop extends OpMode {

    RobotBase robotBase;
    GamepadEx chassisController;
    boolean isFieldCentric = true;
    Follower follower;
    RevTouchSensor touchSensor;
    Servo light;
    double red = .28;
    double green = .47;
    ElapsedTime timer;
    public static double lightPosition = 0;
    TelemetryManager telemetryM;
    public static double panelsHeading = 0;
    Servo duckSpinner;

    @Override
    public void init() {
        robotBase = new RobotBase(hardwareMap);
        chassisController = new GamepadEx(gamepad1);
        follower = Constants.createFollower(hardwareMap);
        touchSensor = hardwareMap.get(RevTouchSensor.class, "ts");
        light = hardwareMap.servo.get("light");
        timer = new ElapsedTime();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        chassisController.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(()-> CommandScheduler.getInstance().schedule(new InstantCommand(()->isFieldCentric = !isFieldCentric)));

        chassisController.getGamepadButton(GamepadKeys.Button.A)

                .whenPressed(()->CommandScheduler.getInstance().schedule(new SetArmPositionCommandGroup(robotBase, Arm.ArmPosition.HOME)));
        chassisController.getGamepadButton(GamepadKeys.Button.B)

                .whenPressed(()->CommandScheduler.getInstance().schedule(new SetArmPositionCommandGroup(robotBase, Arm.ArmPosition.LOW)));
        chassisController.getGamepadButton(GamepadKeys.Button.X)

                .whenPressed(()->CommandScheduler.getInstance().schedule(new SetArmPositionCommandGroup(robotBase, Arm.ArmPosition.MEDIUM)));
        chassisController.getGamepadButton(GamepadKeys.Button.Y)

                .whenPressed(()->CommandScheduler.getInstance().schedule(new SetArmPositionCommandGroup(robotBase, Arm.ArmPosition.HIGH)));

        chassisController.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)

                .whenPressed(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.duckSpinnerSubsystem.setPower(1))));


        chassisController.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new DropOffCommandGroup(robotBase)));
        chassisController.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.chassisSubsystem.setTargetHeading(panelsHeading))));



        new Trigger(()->chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.1)
                .or(new Trigger(()->chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1))
                .whileActiveContinuous(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->robotBase.intakeSubsystem.setPower(chassisController.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) / 2 + -1 * chassisController.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) / 2 + 0.5))));
        /*new Trigger(()->touchSensor.isPressed())
                .toggleWhenActive(()->CommandScheduler.getInstance().schedule(new InstantCommand(()->light.setPosition(green))), ()->CommandScheduler.getInstance().schedule(new InstantCommand(()->light.setPosition(red))));*/
    }

    @Override
    public void start() {
        follower.setStartingPose(new Pose(0,0, Math.toRadians(0)));
        timer.reset();
        //CommandScheduler.getInstance().schedule(new LightColorSensorControlCommand(robotBase, light, 60, 150));
    }

    @Override
    public void loop() {
       follower.update();
       chassisController.readButtons();

       robotBase.limelightSubsystem.update();
       robotBase.chassisSubsystem.drive(chassisController.getLeftX(), chassisController.getLeftY(), chassisController.getRightX(), follower.getHeading(), isFieldCentric, timer);
       light.setPosition(lightPosition);
       CommandScheduler.getInstance().run();

       telemetry.addData("Right Stick X", chassisController.getRightX());
       telemetry.addData("Is Field Centric", isFieldCentric);
       telemetry.addData("Heading", Math.toDegrees(follower.getHeading()));
       telemetry.addData("Touch Sensor Pressed", touchSensor.isPressed());
       telemetry.addData("April Tag ID", robotBase.limelightSubsystem.getAprilTagID());
       telemetry.addData("Target Heading", robotBase.chassisSubsystem.targetHeading);
       telemetry.addData("April Tag ID Webcam", robotBase.cameraSubsystem.getAprilTagID());
       telemetryM.addData("Light Position", lightPosition);
       telemetryM.update();
       //telemetry.addData("Hue Value", robotBase.colorSensorSubsystem.getHueValues());
    }
}


