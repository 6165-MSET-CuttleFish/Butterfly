package org.firstinspires.ftc.teamcode.tele;

import com.acmerobotics.dashboard.config.Config;
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
    public static double flPos;
    public static double frPos;
    public static double blPos;
    public static double brPos;
    Robot robot;
    GamepadEx primary;
    GamepadEx secondary;
    KeyReader[] keyReaders;
    ButtonReader butterflyOnToggle, butterflyOffToggle;

    private double fb; //forward backward movement
    private double lr; //left right movement
    private double turn; //turning movement

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);
        primary = new GamepadEx(gamepad1);
        secondary = new GamepadEx(gamepad2);
        keyReaders = new KeyReader[]{
                butterflyOnToggle = new ButtonReader(primary, GamepadKeys.Button.LEFT_BUMPER),
//                butterflyOffToggle = new ButtonReader(primary, GamepadKeys.Button.RIGHT_BUMPER)
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
            robot.fl.setPower(r * Math.cos(target) + turn);
            robot.fr.setPower(r * Math.sin(target) - turn);
            robot.bl.setPower(r * Math.sin(target) + turn);
            robot.br.setPower(r * Math.cos(target) - turn);
        } else {
            fb = -gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;
            robot.fl.setPower(fb + turn);
            robot.fr.setPower(fb - turn);
            robot.bl.setPower(fb + turn);
            robot.br.setPower(fb - turn);
        }
//        idle();
    }

    private void switchDrive() {
        if (butterflyOnToggle.wasJustPressed() && !robot.butterflyON) {
            robot.butterflyON = true;
            robot.butterflyOn();
        } else if (butterflyOnToggle.wasJustPressed() && robot.butterflyON) {
            robot.butterflyON = false;
            robot.butterflyOff();
        }

    }
    public void setServoPos() {
        robot.sfl.setPosition(flPos);
        robot.sfr.setPosition(frPos);
        robot.sbl.setPosition(blPos);
        robot.sbr.setPosition(brPos);

    }
}
