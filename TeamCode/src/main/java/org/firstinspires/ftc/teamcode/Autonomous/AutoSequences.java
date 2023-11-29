package org.firstinspires.ftc.teamcode.Autonomous;
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

    double lowVelo = 0.5;
    double midVelo = 0.75;
    double highVelo = 1.0;

    int lowAng = 0;
    int midAng = 0;
    int highAng = 0;

    Trajectory blueFirstShootTrajectory;
    
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
    Vector2d blueShoot = new Vector2d(-40, -40);
    Vector2d bluePark1 = new Vector2d(32, -36);
    Vector2d bluePark2 = new Vector2d(8, -60);
    Vector2d bluePark3 = new Vector2d(-12, -36);

    //Red Pos. + Vec.2D
    Pose2d redStartPose = new Pose2d(-64, 48, Math.toRadians(90));
    Vector2d redShoot = new Vector2d(-40, 40);

    Vector2d redPark1 = new Vector2d(34, 36);
    Vector2d redPark2 = new Vector2d(10, 60);
    Vector2d redPark3 = new Vector2d(-12, 36);

    Vector2d offWallRed = new Vector2d(-58,40);
    Vector2d offWallBlue = new Vector2d(-58,-40);

    public AutoSequences(HardwareMap hardwareMap, AutoUtil util){
        this.util = util;
        drive = new SampleMecanumDrive(hardwareMap);

        clearWallRed = drive.trajectoryBuilder(redStartPose)
                .splineTo(offWallRed, Math.toRadians(0))
                .build();
        redFirstShootTrajectory = drive.trajectoryBuilder( new Pose2d(offWallRed, Math.toRadians(0)))
        .splineTo(redShoot, Math.toRadians(-15))
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

        clearWallBlue = drive.trajectoryBuilder(blueStartPose)
                .splineTo(offWallBlue, Math.toRadians(0))
                .build();
        blueFirstShootTrajectory = drive.trajectoryBuilder(new Pose2d(offWallBlue, Math.toRadians(0)))
        .splineTo(blueShoot, Math.toRadians(-15))
        .build();
        bluePark1Trajectory = drive.trajectoryBuilder(new Pose2d(blueShoot, Math.toRadians(30)))
        .splineTo(bluePark1, Math.toRadians(90))
        .build();
        bluePark2Trajectory = drive.trajectoryBuilder(new Pose2d(blueShoot, Math.toRadians(30)))
        .splineTo(bluePark2, Math.toRadians(90))
        .build();
        bluePark3Trajectory = drive.trajectoryBuilder(new Pose2d(blueShoot, Math.toRadians(30)))
        .splineTo(bluePark3, Math.toRadians(90))
        .build();

    }
    
    public void redshort1(){
        drive.setPoseEstimate(redStartPose);
        util.clearServo();
        drive.followTrajectory(clearWallRed);
        drive.followTrajectory(redFirstShootTrajectory);

        util.flywheelPower(midVelo);
        util.waitTime(1500);
        util.launch();
        util.waitTime(500);

        util.flywheelPower(0.0);
        drive.followTrajectory(redPark1Trajectory);

    }
    public void redshort2(){
        drive.setPoseEstimate(redStartPose);
    }
    public void redshort3(){
        drive.setPoseEstimate(redStartPose);
    }
}
