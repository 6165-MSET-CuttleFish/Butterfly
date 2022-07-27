package org.firstinspires.ftc.teamcode.tele;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.KeyReader;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.Robot.Robot;

@Config
@TeleOp(name = "ServoPositioner")
public class
ServoPositioner extends LinearOpMode {
    public static double flPos = 0.44; // butterfly off, for tuning
    public static double frPos = 0.70;
    public static double blPos = 0.80;
    public static double brPos = 0.75;
    public double ninjaSlow = 0.7, multiplier;
    Robot robot;
    GamepadEx primary;
    GamepadEx secondary;
    KeyReader[] keyReaders;
    ButtonReader butterflyToggle;
    TriggerReader ninjaMode;
    private double fb; //forward backward movement
    private double lr; //left right movement
    private double turn; //turning movement
    double gamepadHeading, robotHeading, difference, fbControl, lrControl;

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

            telemetry.addData("1 imu heading", robot.lastAngles.firstAngle);
            telemetry.addData("2 global heading", robot.globalAngle);
            drive();
            setServoPos();
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
        final double startingHeading = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;

        if (ninjaMode.isDown())
            multiplier = ninjaSlow;
        if (!robot.butterflyON) {
            //prevents accidental strafing
            lr = gamepad1.left_stick_x;
            fb = -gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;
            double r = Range.clip(Math.hypot(lr, fb), 0, 1);
            double target = Math.atan2(fb, lr) - Math.PI / 4;
            gamepadHeading = Math.toDegrees(Math.atan2(fb, lr));
            robotHeading = robot.getAngle();
            difference = gamepadHeading - robotHeading;
            telemetry.addData("gamepadHeading: ", gamepadHeading);
            telemetry.addData("robotHeading: ", robotHeading);
            telemetry.addData("startingHeading: ", startingHeading);
            telemetry.addData("difference: ", difference);

            lrControl = Math.cos(Math.toRadians(difference)) * r;
            fbControl = Math.sin(Math.toRadians(difference)) * r;

            robot.fl.setPower((fbControl * Math.abs(fbControl) + lrControl * Math.abs(lrControl) + (turn * 0.7)));
            robot.fr.setPower((fbControl * Math.abs(fbControl) - lrControl * Math.abs(lrControl) - (turn * 0.7)));
            robot.bl.setPower((fbControl * Math.abs(fbControl) - lrControl * Math.abs(lrControl) + (turn * 0.7)));
            robot.br.setPower((fbControl * Math.abs(fbControl) + lrControl * Math.abs(lrControl) - (turn * 0.7)));
        } else {
            fb = -gamepad1.left_stick_y;
            turn = gamepad1.right_stick_x;
            robot.fl.setPower((fb + turn) * multiplier);
            robot.fr.setPower((fb - turn) * multiplier);
            robot.bl.setPower((fb + turn) * multiplier);
            robot.br.setPower((fb - turn) * multiplier);
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
