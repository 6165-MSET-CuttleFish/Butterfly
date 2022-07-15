package org.firstinspires.ftc.teamcode.Robot;



import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.Servo;

import org.openftc.easyopencv.OpenCvCamera;

public class Robot{
    public DcMotor fl,fr,bl,br;
    public Servo sfl,sfr,sbl,sbr;
    public OpenCvCamera camera;
    public boolean butterflyON;
    private final HardwareMap hardwareMap;
    public Robot(OpMode opmode){
        hardwareMap = opmode.hardwareMap;
        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "bl");
        br = hardwareMap.get(DcMotor.class, "br");
        sfl = hardwareMap.get(Servo.class, "sfl");
        sfr = hardwareMap.get(Servo.class, "sfr");
        sbl = hardwareMap.get(Servo.class, "sbl");
        sbr = hardwareMap.get(Servo.class, "sbr");
        butterflyON = false;
    }
    public void butterflyOn(){
        sfl.setPosition(0.46);
        sbr.setPosition(0.5);
        sfr.setPosition(0.47);
        sbl.setPosition(0.6);

    }
    public void butterflyOff(){
        sfl.setPosition(0.44);
        sbr.setPosition(0.75);
        sfr.setPosition(0.33);
        sbl.setPosition(0.8);
    }

}
