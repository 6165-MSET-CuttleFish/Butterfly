package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
public class Paths extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        Pose2d startPose = new Pose2d(10, -8, Math.toRadians(90));

        drive.setPoseEstimate(startPose);

        Trajectory traj1 = drive.trajectoryBuilder(startPose)
                .forward(15)
                //.splineTo(new Vector2d(20, 9), Math.toRadians(45))
                .build();

        Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                .strafeLeft(20)
                //.splineTo(new Vector2d(10, -8), Math.toRadians(0))
                .build();
        Trajectory traj3 = drive.trajectoryBuilder(traj2.end())
                .splineToConstantHeading(   new Vector2d(10, -8), Math.toRadians(90))
                .build();

        telemetry.addData("inited", 1);
        telemetry.update();
        waitForStart();


        if(isStopRequested()) return;

        drive.followTrajectory(traj1);
        sleep(1000);
        drive.followTrajectory(traj2);
        sleep(1000);
        drive.followTrajectory(traj3);
    }
}
