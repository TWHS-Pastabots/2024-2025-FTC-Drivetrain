package org.firstinspires.ftc.teamcode.autonomous;
import org.firstinspires.ftc.teamcode.drive.*;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

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

    //Blue Pos. + Vec.2D
    Pose2d blueStartPose = new Pose2d(-64, -48, Math.toRadians(90));

    Vector2d bluePark1 = new Vector2d(32, -36);
    Vector2d bluePark2 = new Vector2d(8, -60);
    Vector2d bluePark3 = new Vector2d(-12, -36);

    //Red Pos. + Vec.2D
    Pose2d redStartPose = new Pose2d(-64, 48, Math.toRadians(90));
    Vector2d redShoot = new Vector2d(-40, 40);

    Vector2d redPark1 = new Vector2d(34, 36);
    Vector2d redPark2 = new Vector2d(10, 60);
    Vector2d redPark3 = new Vector2d(-12, 36);

    public AutonSequences(HardwareMap hardwareMap, AutoUtil util){
        this.util = util;
        drive = new SampleMecanumDrive(HardwareMap);

        redFirstShootTrajectory = drive.trajectoryBuilder((redStartPose))
        .splineTo(redShoot, Math.toRadians(-15))
        .build();
        redPark1 = drive.trajectoryBuilder(new Pose2d(redShoot, Math.toRadians(-15)))
        .splineTo(redPark1, Math.toRadians(90))
        .build();
        redPark2 = drive.trajectoryBuilder(new Pose2d(redShoot, Math.toRadians(-15)))
        .splineTo(redPark2, Math.toRadians(90))
        .build();
        redPark3 = drive.trajectoryBuilder(new Pose2d(redShoot, Math.toRadians(-15)))
        .splineTo(redPark3, Math.toRadians(90))
        .build();

        blueFirstShootTrajectory = drive.trajectoryBuilder((blueStartPose))
        .splineTo(blueShoot, Math.toRadians(-15))
        .build();
        bluePark1 = drive.trajectoryBuilder(new Pose2d(blueShoot, Math.toRadians(-15)))
        .splineTo(bluePark1, Math.toRadians(90))
        .build();
        bluePark2 = drive.trajectoryBuilder(new Pose2d(blueShoot, Math.toRadians(-15)))
        .splineTo(bluePark2, Math.toRadians(90))
        .build();
        bluePark3 = drive.trajectoryBuilder(new Pose2d(blueShoot, Math.toRadians(-15)))
        .splineTo(bluePark3, Math.toRadians(90))
        .build();
    }
    
    public void redshort1(){
        drive.setPoseEsitmate(redStartPose);

        util.setLaunchAngle(midAng);
        util.flywheelPower(midVelo);

    }
    public void redshort2(){
        drive.setPoseEsitmate(redStartPose);
    }
    public void redshort3(){
        drive.setPoseEsitmate(redStartPose);
    }
}
