package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.Servo;

import org.openftc.easyopencv.OpenCvCamera;

public class Robot extends LinearOpMode {
    public DcMotor fl,fr,bl,br;
    public Servo sfl,sfr,sbl,sbr;
    public OpenCvCamera camera;
    public boolean butterflyON;
    public Robot(){
        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fl");
        bl = hardwareMap.get(DcMotor.class, "fl");
        br = hardwareMap.get(DcMotor.class, "fl");
        sfl = hardwareMap.get(Servo.class, "sfl");
        sfr = hardwareMap.get(Servo.class, "sfr");
        sbl = hardwareMap.get(Servo.class, "sbl");
        sbr = hardwareMap.get(Servo.class, "sbr");
        butterflyON = false;
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}
