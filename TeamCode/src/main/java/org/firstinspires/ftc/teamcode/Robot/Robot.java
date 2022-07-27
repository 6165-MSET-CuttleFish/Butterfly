package org.firstinspires.ftc.teamcode.Robot;



import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
@Config
public class Robot extends SampleMecanumDrive {
    public DcMotor fl,fr,bl,br;
    public Servo sfl, sfr, sbl, sbr;
    public OpenCvCamera camera;
    public boolean butterflyON;
    private final HardwareMap hardwareMap;
    public BNO055IMU imu;
    public Orientation lastAngles = new Orientation();
    public double globalAngle;

    public Robot(OpMode opmode) {
        super(opmode.hardwareMap);

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

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

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
        sbr.setPosition(0.80);
        sfr.setPosition(0.50);
        sbl.setPosition(0.52);

    }
    public void butterflyOff(){
        sfl.setPosition(0.45);
        sbr.setPosition(0.68);
        sfr.setPosition(0.67);
        sbl.setPosition(0.8);
    }
    public double getAngle() {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }
}
