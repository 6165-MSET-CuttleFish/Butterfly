package org.firstinspires.ftc.teamcode.Robot;



import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.Servo;

import org.openftc.easyopencv.OpenCvCamera;
@Config
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
        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);
        fr.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.FORWARD);

        if (butterflyON) {
            butterflyOn();
            butterflyON = true;
        }
        if(!butterflyON) {
            butterflyOff();
            butterflyON = false;
        }
    }
    public void butterflyOn(){
        sfl.setPosition(0.47);
        sbr.setPosition(0.45);
        sfr.setPosition(0.5);
        sbl.setPosition(0.52);

    }
    public void butterflyOff(){
        sfl.setPosition(0.44);
        sbr.setPosition(0.75);
        sfr.setPosition(0.70);
        sbl.setPosition(0.80);
    }

}
