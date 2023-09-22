package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.internal.system.Assert;

public class LasagnaHardware {
    public DcMotorEx frontLeft = null;
    public DcMotorEx rearLeft = null;
    public DcMotorEx frontRight = null;
    public DcMotorEx rearRight = null;
    public DcMotorEx intakeMotor = null;
    public DcMotorEx flyWheelMotor = null;

    public DcMotorEx[] motors;

    public LasagnaHardware () {}

    public void init(HardwareMap hardwareMap){
        Assert.assertNotNull(hardwareMap);
        initializeDriveMotors(hardwareMap);
    }

public void initializeDriveMotors(HardwareMap hardwareMap){
    frontLeft = hardwareMap.get(DcMotorEx.class, LasagnaIDS.LEFT_FRONT_MOTOR);
    frontRight = hardwareMap.get(DcMotorEx.class, LasagnaIDS.RIGHT_FRONT_MOTOR);
    rearLeft = hardwareMap.get(DcMotorEx.class, LasagnaIDS.LEFT_REAR_MOTOR);
    rearRight = hardwareMap.get(DcMotorEx.class, LasagnaIDS.RIGHT_REAR_MOTOR);
    // write the flywheel and intake motors in here

    intakeMotor = hardwareMap.get(DcMotorEx.class, LasagnaIDS.INTAKE_MOTOR);

    motors = new DcMotorEx[]{frontLeft, frontRight, rearLeft, rearRight}; // add flywheel and intake motors to "motors"

    frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    rearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
    rearRight.setDirection(DcMotorSimple.Direction.FORWARD);
    // set flywheel and intake motor directions

    for(DcMotorEx motor : motors ){
        motor.setPower(0.0);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
}
}