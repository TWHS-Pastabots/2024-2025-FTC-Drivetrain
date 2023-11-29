package org.firstinspires.ftc.teamcode.autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

public class AutoUtil {
    private Hardware hardware;
    public AutoUtil(Hardware hardware){
        this.hardware = hardware;
    }
    public void waitTime(int waitTime){
        ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        time.reset();
        while(time.time()<waitTime){}
    }
    public void flywheelPower(double power){
        hardware.flyWheelMotor.setPower(power);
    }
    public void launch(double power){
        hardware.pushServo.setPosition(1.0);
        wait(50);
        hardware.pushServo.setPosition(0.5);
        hardware.flyWheelMotor.setPower(0.0);
    }
    public void intake(int intakeTime){
        ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
        time.reset();
        while(iTime.time() < intakeTime){}
    }
    public void setLaunchAngle(int pose){
        hardware.liftMotor.setTargetPosition(pose);
        hardware.liftMotor.RunMode(DcMotorEx.setMode.RUN_TO_POSITION);
        hardware.liftMotor.setPower(0.4);
    }

}
