package org.firstinspires.ftc.teamcode.teleOp;

import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp (name = "Robot")
public class RobotCode extends OpMode {



    RobotHardware hardware;
    //MAKE SURE TO CHANGE THESE. THESE ARE YOUR DRIVE MODES THAT YOU NEED FOR THE CHECKPOINT
    public static final double FAST_MODE = .9;
    public static final double SLOW_MODE = .45;
    double currentMode;
    ElapsedTime buttonTime = null;

    ElapsedTime launcherTime = null;

    public void init(){
        hardware = new RobotHardware();
        hardware.init(hardwareMap);
        currentMode = FAST_MODE;
        buttonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        launcherTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        telemetry.addData("Status: ", "Initialized");
        telemetry.update();
    }

    public void start(){
        telemetry.addData("Status: ", "Started");
        telemetry.update();
    }
    public void loop(){
        drive();
        intake();
        launch();
        lift();
        telemetry();
    }
    public void telemetry()
    {
        //this is the class you should put stuff in if you want to print to the phone.

    }

    public void drive() {
        double y = -gamepad1.left_stick_y; // This is reversed
        double x = gamepad1.left_stick_x; // Counteract imperfect strafing
        double z = gamepad1.right_stick_x;

        double leftFrontPower = y + x + z;
        double leftRearPower = y - x + z;
        double rightFrontPower = y - x - z;
        double rightRearPower = y + x - z;

        if (Math.abs(leftFrontPower) > 1 || Math.abs(leftRearPower) > 1 ||
                Math.abs(rightFrontPower) > 1 || Math.abs(rightRearPower) > 1 ){
            // Find the largest power
            double max;
            max = Math.max(Math.abs(leftFrontPower), Math.abs(leftRearPower));
            max = Math.max(Math.abs(rightFrontPower), max);
            max = Math.max(Math.abs(rightRearPower), max);

            // Everything is Positive, do not worry about signs
            leftFrontPower /= max;
            leftRearPower /= max;
            rightFrontPower /= max;
            rightRearPower /= max;
        }

        if(gamepad1.dpad_left){
            leftFrontPower = -1;
            rightRearPower = -1;
            leftRearPower = 1;
            rightFrontPower = 1;
        }
        else if(gamepad1.dpad_right){
            leftFrontPower = 1;
            rightRearPower = 1;
            leftRearPower = -1;
            rightFrontPower = -1;
        }
        else if (gamepad1.dpad_up)
        {
            leftFrontPower = 1;
            rightRearPower = 1;
            leftRearPower = 1;
            rightFrontPower = 1;
        }
        else if(gamepad1.dpad_down)
        {
            leftFrontPower = -1;
            leftRearPower = -1;
            rightRearPower = -1;
            rightFrontPower = -1;
        }
        if(gamepad2.circle)
        {
            hardware.intakeMotor.setPower(1);

        }
        else
        {
            hardware.intakeMotor.setPower(0);
        }

        if(gamepad2.square)
        {


            if (launcherTime.time() > 500)
            {
                hardware.launcherServo.setPosition(1);
            }

            hardware.launcherMotor.setPower(-1);

        }
        else
        {
            hardware.launcherServo.setPosition(0);

            hardware.launcherMotor.setPower(0);
        }
        if(gamepad1.left_bumper && currentMode == FAST_MODE && buttonTime.time() >= 500) {
            currentMode = SLOW_MODE;
            buttonTime.reset();
        }
        else if(gamepad1.left_bumper && currentMode == SLOW_MODE && buttonTime.time() >= 500) {
            currentMode = FAST_MODE;
            buttonTime.reset();
        }

        hardware.frontLeft.setPower(leftFrontPower * currentMode);
        hardware.rearLeft.setPower(leftRearPower * currentMode);
        hardware.frontRight.setPower(rightFrontPower * currentMode);
        hardware.rearRight.setPower(rightRearPower * currentMode);
    }

    public void intake(){
        //intake will go here

        hardware.intakeMotor.setPower(0);
        hardware.intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        hardware.intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void launch(){
        //the things you need to do for launch will go here
        hardware.launcherMotor.setPower(0);
        hardware.launcherMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        hardware.launcherMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        hardware.launcherServo.setPosition(0);



    }

    public void lift(){
        //climber code will go here
    }
}