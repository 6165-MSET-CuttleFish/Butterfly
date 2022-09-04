package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.auto.BlockDetector;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous
public class Paths extends LinearOpMode {
    SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        BlockDetector path = new BlockDetector(drive);

        public void runOpMode() throws InterruptedException {

            Pose2d startPose = new Pose2d(0, 0, Math.toRadians(90));

            drive.setPoseEstimate(startPose);
            for (int i = 0; i < path.getVectors(startPose).size(); i++) {
                Trajectory traj = drive.trajectoryBuilder(startPose)
                        .splineToConstantHeading(path.getVectors(startPose).get(i), Math.toRadians(90))
                        .build();
                drive.followTrajectory(traj);
            }


            telemetry.addData("inited", 1);
            telemetry.update();
            waitForStart();


            if (isStopRequested()) return;

        }
}
