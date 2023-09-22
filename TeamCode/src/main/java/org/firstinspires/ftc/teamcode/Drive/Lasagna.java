package org.firstinspires.ftc.teamcode.Drive;

import org.firstinspires.ftc.teamcode.Hardware.LasagnaHardware;

import com.qualcomm.robotcore.eventloop.opmode.Opmode;
import com.qualcomm.robotcore.hardware.DCcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@teleop(name = "Lasagna")
public class Lasagna extends OpMode{
    LasagnaHardware hardware;
   
    public final double FAST_MODE = .75;
    public final double PREC_MODE = .45;
    double currentMode = FAST_MODE;
    ElapsedTime buttonTime = null;

    public void init(){
        hardware = new LasagnaHardware();
        hardware.init(hardWareMap);
        buttonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        telemetry.addData("Status: ", "Initialized");
        telemetry.update();
    }
    public void start(){
        telemetry.addData("Status: ", "Started");
        telemetry.update();
    }
    public void loop(){
        drive();
    }

    public void drive(){
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

        if(gamepad1.dpad_up || gamepad1.dpad_right){
            leftFrontPower = -1;
            rightRearPower = -1;
            leftRearPower = 1;
            rightFrontPower = 1;
        }
        else if(gamepad1.dpad_down || gamepad1.dpad_left){
            leftFrontPower = 1;
            rightRearPower = 1;
            leftRearPower = -1;
            rightFrontPower = -1;
        }

        if(gamepad1.getSquare() && currentMode == FAST_MODE && buttonTime >= 500){
            currentMode = PREC_MODE;
            buttonTime.reset();
        }
        else if(gamepad1.getSquare() && currentMode == PREC_MODE && buttonTime >= 500){
            currentMode = FAST_MODE;
            buttonTime.reset();
        }

        hardware.frontLeft.setPower(leftFrontPower * currentMode);
        hardware.rearLeft.setPower(leftRearPower * currentMode);
        hardware.frontRight.setPower(rightFrontPower * currentMode);
        hardware.rearRight.setPower(rightRearPower * currentMode);
    }
}