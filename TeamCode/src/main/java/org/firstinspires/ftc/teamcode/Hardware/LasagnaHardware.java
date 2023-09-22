package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.internal.system.Assert;

public class LasagnaHardware {
    public DcMotorEx frontLeft = null;
    public DcMotorEx rearLeft = null;
    public DcMotorEx frontRight = null;
    public DcMotorEx rearRight = null;
    public DcMotorEx intakeMotor = null;
    public DcMotorEx flyWheelMotor = null;

    public DcMoterEx motors[];

    public void init(){
        Assert.assertNotNull(hardwareMap);
        intializeDriveMotors(hardwareMap);
    }

public void intializeDriveMotors(HardwareMap hardwareMap){
    frontLeft = hardwareMap(DcMoterEx.class, LaagnaIDS.LEFT_FRONT_MOTOR);
    frontRight = hardwareMap(DcMoterEx.class, LaagnaIDS.RIGHT_FRONT_MOTOR);
    rearLeft = hardwareMap(DcMoterEx.class, LaagnaIDS.LEFT_REAR_MOTOR);
    rearRight = hardwareMap(DcMoterEx.class, LaagnaIDS.RIGHT_REAR_MOTOR);
    // write the flywheel and intake motors in here

    intakeMotor = hardwareMap(DcMoterEx.class, LasagnaIDS.INTAKE_MOTOR);

    motors = new DcMoterEx[]{frontLeft, frontRight, rearLeft, rearRight}; // add flywheel and intake motors to "motors"

    frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    rearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
    rearRight.setDirection(DcMotorSimple.Direction.FORWARD);
    // set flywheel and intake motor directions

    for(DcMotorEx motor : motors ){
        motor.setPower(0.0);
        motor.setZeroBehavior(DcMotorEx.setZeroBehavior.BRAKE);
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
}
}