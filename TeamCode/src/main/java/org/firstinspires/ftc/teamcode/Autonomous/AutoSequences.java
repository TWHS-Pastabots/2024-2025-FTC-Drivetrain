package org.firstinspires.ftc.teamcode.Autonomous;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import org.firstinspires.ftc.teamcode.drive.*;
import org.firstinspires.ftc.teamcode.Hardware.LasagnaHardware;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class AutoSequences {

    SampleMecanumDrive drive;
    AutoUtil util;

    double lowVelo = 0.8;
    double midVelo = 0.95;
    double highVelo = 1.0;

    int lowAng = 0;
    int midAng = 30;
    int highAng = 60;

    Trajectory blueShoot1Traj;
    Trajectory blueShoot2Traj;
    
    //Blue Traj.
    Trajectory bluePark1Trajectory;
    Trajectory bluePark2Trajectory;
    Trajectory bluePark3Trajectory;
    // Red Traj.
    Trajectory redFirstShootTrajectory;
    
    Trajectory redPark1Trajectory;
    Trajectory redPark2Trajectory;
    Trajectory redPark3Trajectory;

    Trajectory clearWallRed;
    Trajectory clearWallBlue;

    //Blue Pos. + Vec.2D
    Pose2d blueStartPose = new Pose2d(-64, -48, Math.toRadians(90));
    Vector2d blueShoot1 = new Vector2d(-55, -45);
    Vector2d blueShoot2 = new Vector2d(-35, -25);
    Vector2d bluePark1 = new Vector2d(32, -37);
    Vector2d bluePark2 = new Vector2d(10, -59);
    Vector2d bluePark3 = new Vector2d(-12, -36);

    //Red Pos. + Vec.2D
    Pose2d redStartPose = new Pose2d(-64, 48, Math.toRadians(90));
    Vector2d redShoot = new Vector2d(-35, 35);

    Vector2d redPark1 = new Vector2d(36, 36);
    Vector2d redPark2 = new Vector2d(10, 60);
    Vector2d redPark3 = new Vector2d(-12, 36);

    Vector2d offWallRed = new Vector2d(-58,40);

    public AutoSequences(HardwareMap hardwareMap, AutoUtil util){
        this.util = util;
        drive = new SampleMecanumDrive(hardwareMap);

        clearWallRed = drive.trajectoryBuilder(redStartPose)
                .splineToLinearHeading(new Pose2d(offWallRed, 90), Math.toRadians(0))
                .build();
        redFirstShootTrajectory = drive.trajectoryBuilder( new Pose2d(offWallRed, Math.toRadians(0)))
        .splineToSplineHeading(new Pose2d(redShoot, Math.toRadians(-30)), Math.toRadians(-90))
        .build();
        redPark1Trajectory = drive.trajectoryBuilder(new Pose2d(redShoot, Math.toRadians(-30)))
        .splineTo(redPark1, Math.toRadians(90))
        .build();
        redPark2Trajectory = drive.trajectoryBuilder(new Pose2d(redShoot, Math.toRadians(-30)))
        .splineTo(redPark2, Math.toRadians(90))
        .build();
        redPark3Trajectory = drive.trajectoryBuilder(new Pose2d(redShoot, Math.toRadians(-30)))
        .splineTo(redPark3, Math.toRadians(90))
        .build();

        blueShoot1Traj = drive.trajectoryBuilder(blueStartPose)
                .splineToSplineHeading(new Pose2d(blueShoot1, Math.toRadians(30)), Math.toRadians(30))
                .build();
        blueShoot2Traj = drive.trajectoryBuilder(blueStartPose)
        .splineToSplineHeading(new Pose2d(blueShoot2,Math.toRadians(10)), Math.toRadians(-15))
        .build();
        bluePark1Trajectory = drive.trajectoryBuilder(new Pose2d(blueShoot1, Math.toRadians(30)))
        .splineTo(bluePark1, Math.toRadians(0))
        .build();
        bluePark2Trajectory = drive.trajectoryBuilder(new Pose2d(blueShoot2, Math.toRadians(10)))
        .splineTo(bluePark2, Math.toRadians(-90))
        .build();
        bluePark3Trajectory = drive.trajectoryBuilder(new Pose2d(blueShoot2, Math.toRadians(30)))
        .splineTo(bluePark3, Math.toRadians(90))
        .build();

    }
    public void redshort1(){
        drive.setPoseEstimate(redStartPose);
        util.setLaunchAngle(midAng);
        util.waitTime(5000);
    }
    public void redshort2(){
        drive.setPoseEstimate(redStartPose);
        util.clearServo();
        drive.followTrajectory(clearWallRed);
        drive.followTrajectory(redFirstShootTrajectory);

        util.flywheelPower(midVelo);
        util.waitTime(2000);
        util.launch();
        util.waitTime(500);

        util.flywheelPower(0.0);
        drive.followTrajectory(redPark2Trajectory);

    }
    
    public void redshort3(){
        drive.setPoseEstimate(redStartPose);
    }
    public void blueshort1(){
        drive.setPoseEstimate(blueStartPose);
        drive.followTrajectory(blueShoot1Traj);
        util.flywheelPower(midVelo);
        util.waitTime(1500);
        util.launch();
        util.waitTime(500);
        drive.followTrajectory(bluePark1Trajectory);
    }
    public void blueshort2(){
        drive.setPoseEstimate(blueStartPose);
        drive.followTrajectory(blueShoot2Traj);
        util.flywheelPower(lowVelo);
        util.waitTime(2000);
        util.launch();
        util.waitTime(500);

        drive.followTrajectory(bluePark2Trajectory);
    }
    
    public void blueshort3(){
        drive.setPoseEstimate(redStartPose);
    }
}
