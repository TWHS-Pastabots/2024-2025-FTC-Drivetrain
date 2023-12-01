package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.internal.system.Assert;

public class LasagnaHardware {
    public DcMotorEx frontLeft = null;
    public DcMotorEx rearLeft = null;
    public DcMotorEx frontRight = null;
    public DcMotorEx rearRight = null;
    public DcMotorEx flyWheelMotor = null;

    public DcMotorEx intakeMotor = null;
    public DcMotorEx liftMotor = null;
    public Servo pushServo = null;
    public DcMotorEx armMotor = null;

    public DcMotorEx[] motors;

    public LasagnaHardware () {}

    public void init(HardwareMap hardwareMap){
        Assert.assertNotNull(hardwareMap);
        initializeDriveMotors(hardwareMap);
        initializeIntakeMotors(hardwareMap);
        initializeLaunchPadMotors(hardwareMap);
        initializeServos(hardwareMap);
    }

public void initializeDriveMotors(HardwareMap hardwareMap){
    frontLeft = hardwareMap.get(DcMotorEx.class, LasagnaIDS.LEFT_FRONT_MOTOR);
    frontRight = hardwareMap.get(DcMotorEx.class, LasagnaIDS.RIGHT_FRONT_MOTOR);
    rearLeft = hardwareMap.get(DcMotorEx.class, LasagnaIDS.LEFT_REAR_MOTOR);
    rearRight = hardwareMap.get(DcMotorEx.class, LasagnaIDS.RIGHT_REAR_MOTOR);



    motors = new DcMotorEx[]{frontLeft, frontRight, rearLeft, rearRight}; 

    frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    rearLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
    rearRight.setDirection(DcMotorSimple.Direction.FORWARD);


    for(DcMotorEx motor : motors ){
        motor.setPower(0.0);
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
    }
}

public void initializeIntakeMotors(HardwareMap hardwareMap){
    intakeMotor = hardwareMap.get(DcMotorEx.class, LasagnaIDS.INTAKE_MOTOR);

    intakeMotor.setPower(0.0);
    intakeMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
    intakeMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

    armMotor = hardwareMap.get(DcMotorEx.class, LasagnaIDS.ARM_MOTOR);

    armMotor.setPower(0.0);
    armMotor.setPositionPIDFCoefficients(1.0);
    armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    armMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
}

public void initializeLaunchPadMotors(HardwareMap hardwareMap){
    flyWheelMotor = hardwareMap.get(DcMotorEx.class, LasagnaIDS.FLYWHEEL_MOTOR);

    flyWheelMotor.setPower(0.0);
    flyWheelMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    flyWheelMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

    liftMotor = hardwareMap.get(DcMotorEx.class, LasagnaIDS.LIFT_MOTOR);

    liftMotor.setPower(0.0);
    liftMotor.setPositionPIDFCoefficients(1.0);
    liftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    liftMotor.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);


}

public void initializeServos(HardwareMap hardwareMap){
    pushServo = hardwareMap.get(Servo.class, LasagnaIDS.PUSH_SERVO);

    pushServo.setPosition(0.0);
}
}