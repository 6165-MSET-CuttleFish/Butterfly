package org.firstinspires.ftc.teamcode.tele;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Robot.Robot;

@TeleOp(name="simpleMec")
public class simpleMec extends LinearOpMode {
    Robot robot;
    GamepadEx gp = new GamepadEx(gamepad1);
    private double fb; //forward backward movement
    private double lr; //left right movement
    private double turn; //turning movement
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot();

        init();
        waitForStart();
        while(opModeIsActive()){
            drive();
        }
    }
    private void drive(){
        if(!robot.butterflyON){
            fb = gp.getLeftY();
            lr = gp.getLeftX();
            turn = gp.getRightX();
            double r = Math.hypot(lr, fb);
            double target = Math.atan2(fb, lr) - Math.PI / 4;
            robot.fl.setPower(r * Math.cos(target) + turn);
            robot.fr.setPower(r * Math.sin(target) - turn);
            robot.bl.setPower(r * Math.sin(target) + turn);
            robot.br.setPower(r * Math.cos(target) - turn);
        }
        else{
            fb = Math.sqrt(gamepad1.left_stick_y);
            robot.fl.setPower(fb);
            robot.fr.setPower(fb);
            robot.bl.setPower(fb);
            robot.br.setPower(fb);
        }
    }
    private void switchDrive(){
        if(gp.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) && robot.butterflyON == false) robot.butterflyON = true;
        else if(gp.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER) && robot.butterflyON == true) robot.butterflyON = false;

    }
}
