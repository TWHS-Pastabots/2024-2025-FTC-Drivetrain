package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.internal.system.Assert;
import org.firstinspires.ftc.teamcode.Hardware.LasagnaHardware;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;


import java.util.ArrayList;

@Autonomous(name = "RedAuto")
 public class RedShortAuto extends LinearOpMode
{
    LasagnaHardware hardware;
    AutoUtil utils;

    AutoSequences sequences;
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprltagPipeLine;

    static final double FEET_PER_METER = 3.28084;
    //Need to do some sort of calibration here (Replace #s below)
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    // Tag IDS
    int one = 48;
    int two = 56;
    int three = 59;


    AprilTagDetection tagOfIntrest = null;

    @Override
    public void runOpMode()
    {
        hardware = new LasagnaHardware();
        Assert.assertNotNull(hardwareMap);
        hardware.init(hardwareMap);
        utils = new AutoUtil(hardware);
        sequences = new AutoSequences(hardwareMap, utils);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprltagPipeLine = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprltagPipeLine);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener(){
            @Override
            public void onOpened(){
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode){

            }
        });
        telemetry.setMsTransmissionInterval(50);


        ArrayList<AprilTagDetection> currentDetections;

        while(!isStarted() && !isStopRequested()) {
            currentDetections = aprltagPipeLine.getLatestDetections();
            if (currentDetections.size() != 0) {
                boolean tagFound = false;
                for (AprilTagDetection tag : currentDetections) {
                    if (tag.id == one || tag.id == two || tag.id == three) {
                        tagFound = true;
                        tagOfIntrest = tag;
                        break;
                    }
                }
                if (tagFound) {
                    telemetry.addLine("Tag of Intrest found \n\n Location Data: ");
                    tagToTelemetry(tagOfIntrest);
                } else {
                    if (tagOfIntrest == null)
                        telemetry.addLine("No tags found yet");
                    else {
                        telemetry.addLine("\nTag previously seen at: ");
                        tagToTelemetry(tagOfIntrest);
                    }
                }
            } else {
                telemetry.addLine("No tag of intrest has been found");

                if (tagOfIntrest == null)
                    telemetry.addLine("No tags found yet");
                else {
                    telemetry.addLine("\nTag previously seen at: ");
                    tagToTelemetry(tagOfIntrest);
                }
            }
            telemetry.update();
            sleep(20);
        }

        if(tagOfIntrest == null || tagOfIntrest.id == one){
            telemetry.addLine("Position 1");
            telemetry.update();
            sequences.redshort1();
        }
        else if(tagOfIntrest.id == two){
            telemetry.addLine("Position 2");
            telemetry.update();
            sequences.redshort2();
        }
        else if(tagOfIntrest.id == three){
            telemetry.addLine("Position 3");
            telemetry.update();
            sequences.redshort3();
        }
        //while (opModeIsActive()) {sleep(20);}
    }

    void tagToTelemetry(AprilTagDetection detection)
    {
        Orientation rot = Orientation.getOrientation(detection.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);

        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));

        if(tagOfIntrest.id == one )
            telemetry.addLine("Tag 1");
        if(tagOfIntrest.id == two)
            telemetry.addLine("Tag 2");
        if(tagOfIntrest.id == three)
            telemetry.addLine("Tag 3");

        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", rot.firstAngle));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", rot.secondAngle));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", rot.thirdAngle));
    }
}
