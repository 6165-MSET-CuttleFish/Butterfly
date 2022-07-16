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
    ButtonReader butterflyToggle;

    private double fb; //forward backward movement
    private double lr; //left right movement
    private double turn; //turning movement

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(this);
        primary = new GamepadEx(gamepad1);
        secondary = new GamepadEx(gamepad2);
        keyReaders = new KeyReader[]{
                butterflyToggle = new ButtonReader(primary, GamepadKeys.Button.LEFT_BUMPER)
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
            fb = -gamepad1.left_stick_y;
            lr = gamepad1.left_stick_x;
            turn = gamepad1.right_stick_x;
            double r = Math.hypot(lr, fb);
            double target = Math.atan2(fb, lr) - Math.PI / 4;
            robot.fl.setPower(r * Math.cos(target) + turn);
            robot.fr.setPower(r * Math.sin(target) - turn);
            robot.bl.setPower(r * Math.sin(target) + turn);
            robot.br.setPower(r * Math.cos(target) - turn);
        } else {
            fb = Math.sqrt(gamepad1.left_stick_y);
            robot.fl.setPower(fb);
            robot.fr.setPower(fb);
            robot.bl.setPower(fb);
            robot.br.setPower(fb);
        }
//        idle();
    }

    private void switchDrive() {
        if (butterflyToggle.wasJustPressed() && !robot.butterflyON) {
            robot.butterflyON = true;
            robot.butterflyOn();
        } else if (butterflyToggle.wasJustPressed() && robot.butterflyON) {
            robot.butterflyON = false;
            robot.butterflyOn();
        }

    }
    public void setServoPos() {
        robot.sfl.setPosition(flPos);
        robot.sfr.setPosition(frPos);
        robot.sbl.setPosition(blPos);
        robot.sbr.setPosition(brPos);

    }
}
