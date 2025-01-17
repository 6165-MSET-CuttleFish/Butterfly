package org.firstinspires.ftc.teamcode.tele;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.gamepad.*;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Robot.Robot;
@Config
@TeleOp(name = "simpleMec")
public class simpleMec extends LinearOpMode {
    public static double flPos; // = 0.45; //butterfly off, for tuning
    public static double frPos; // = 0.65;
    public static double blPos; // = 0.73;
    public static double brPos; // = 0.67;
    public double ninjaSlow = 0.7, multiplier;
    Pose2d drivePower;
    Robot robot;
    GamepadEx primary;
    GamepadEx secondary;
    KeyReader[] keyReaders;
    ButtonReader butterflyToggle;
    TriggerReader ninjaMode;
    private double fb; //forward backward movement
    private double lr; //left right movement
    private double turn; //turning movement

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);
        primary = new GamepadEx(gamepad1);
        secondary = new GamepadEx(gamepad2);
        keyReaders = new KeyReader[]{
                butterflyToggle = new ButtonReader(primary, GamepadKeys.Button.LEFT_BUMPER),
                ninjaMode = new TriggerReader(primary, GamepadKeys.Trigger.LEFT_TRIGGER)
        };

        waitForStart();
        while (opModeIsActive()) {
            for (KeyReader reader : keyReaders)
                reader.readValue();
            drive();
//            setServoPos();
            switchDrive();
//            telemetry.addData("back left", robot.sbl.getPosition());
//            telemetry.addData("front left", robot.sfl.getPosition());
//            telemetry.addData("back right", robot.sbr.getPosition());
//            telemetry.addData("front right", robot.sfr.getPosition());
            telemetry.update();
        }
    }

    private void drive() {
        multiplier = 1;
        if (ninjaMode.isDown())
            multiplier = ninjaSlow;
        if (!robot.butterflyON) {
            //prevents accidental strafing
            if (Math.abs(gamepad1.left_stick_x) > 0.2)
                lr = gamepad1.left_stick_x;
            else
                lr = 0;
            fb = -gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;
            double r = Math.hypot(lr, fb);
            double target = Math.atan2(fb, lr) - Math.PI / 4;
            robot.fl.setPower((r * Math.cos(target) + turn) * multiplier);
            robot.fr.setPower((r * Math.sin(target) - turn) * multiplier);
            robot.bl.setPower((r * Math.sin(target) + turn) * multiplier);
            robot.br.setPower((r * Math.cos(target) - turn) * multiplier);
        } else {
            fb = -gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;
            robot.fl.setPower((fb + turn) * multiplier);
            robot.fr.setPower((fb - turn) * multiplier);
            robot.bl.setPower((fb + turn) * multiplier);
            robot.br.setPower((fb - turn)* multiplier);
        }
//        idle();
    }
    private void switchDrive() {
        if (butterflyToggle.wasJustPressed() && !robot.butterflyON) {
            robot.butterflyON = true;
            robot.butterflyOn();
            gamepad1.rumble(500);
        } else if (butterflyToggle.wasJustPressed() && robot.butterflyON) {
            robot.butterflyON = false;
            robot.butterflyOff();
            gamepad1.rumble(1.0, 1.0, 500);
        }
    }
    public void setServoPos() {
        robot.sfl.setPosition(flPos);
        robot.sfr.setPosition(frPos);
        robot.sbl.setPosition(blPos);
        robot.sbr.setPosition(brPos);
    }
}
