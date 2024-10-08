package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;



public class RobotHardware
{
    // Primary Motors
    public DcMotorEx leftFront = null;
    public DcMotorEx leftRear = null;
    public DcMotorEx rightFront = null;
    public DcMotorEx rightRear = null;

    public DcMotorEx[] motors;

    // Lift Motors
    public DcMotorEx liftArm1 = null;
    public DcMotorEx liftArm2 = null;

    // CLaw Servo
    public Servo grabServo = null;
    public Servo tiltServo = null;

    private PIDFCoefficients PIDF = new PIDFCoefficients(10,0.5,0,45);

    /**
     * Initializes the drive motors of the robot and
     * sets them to run without encoder.
     *
     * @param hardwareMap   robot's components map
     */
    public void initializePrimaryMotors(HardwareMap hardwareMap)
    {
        // Primary Motors
        leftFront = hardwareMap.get(DcMotorEx.class, RobotIDS.LEFT_FRONT_MOTOR);
        leftRear = hardwareMap.get(DcMotorEx.class, RobotIDS.LEFT_REAR_MOTOR);
        rightFront = hardwareMap.get(DcMotorEx.class, RobotIDS.RIGHT_FRONT_MOTOR);
        rightRear = hardwareMap.get(DcMotorEx.class, RobotIDS.RIGHT_REAR_MOTOR);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);


        // Set Zero Power Behavior and Initialize Motors
        leftRear.setPower(0);
        leftRear.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftFront.setPower(0);
        leftFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightFront.setPower(0);
        rightFront.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightRear.setPower(0);
        rightRear.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }



    /**
     * Sets drive motors to run with encoder.
     */
    public void turnOnDriveEncoders()
    {
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }



    /**
     * Initializes the claw servo, sets the position to closed,
     * and sets the default movement direction.
     *
     * @param hardwareMap   robot's components map
     */
    public void initializeClawServos(HardwareMap hardwareMap)
    {
        // Claw Servo
        grabServo = hardwareMap.get(Servo.class, RobotIDS.GRAB_SERVO);

        grabServo.setDirection(Servo.Direction.FORWARD);
        grabServo.setPosition(.6);

        tiltServo = hardwareMap.get(Servo.class, RobotIDS.TILT_SERVO);
        tiltServo.setDirection(Servo.Direction.FORWARD);
        tiltServo.setPosition(0.6);
    }
    public void initializeClawServosTeleOp(HardwareMap hardwareMap)
    {
        // Claw Servo
        grabServo = hardwareMap.get(Servo.class, RobotIDS.GRAB_SERVO);

        grabServo.setDirection(Servo.Direction.FORWARD);
        grabServo.setPosition(.33);

        tiltServo = hardwareMap.get(Servo.class, RobotIDS.TILT_SERVO);
        tiltServo.setDirection(Servo.Direction.FORWARD);
        tiltServo.setPosition(0.6);
    }



    /**
     * Initializes the lift motors.
     *
     * @param hardwareMap   robot's components map
     */
    public void initializeSupplementaryMotors(HardwareMap hardwareMap)
    {
        // LiftArm2
        liftArm2 = hardwareMap.get(DcMotorEx.class, RobotIDS.LIFT_ARM2);

        liftArm2.setDirection(DcMotorSimple.Direction.REVERSE);

        liftArm2.setPower(0);
        liftArm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftArm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftArm2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // LiftArm1
        liftArm1 = hardwareMap.get(DcMotorEx.class, RobotIDS.LIFT_ARM1);

        liftArm1.setDirection(DcMotorSimple.Direction.FORWARD);

        liftArm1.setPower(0);
        liftArm1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftArm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftArm1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftArm1.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, PIDF);
        liftArm2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, PIDF);
    }



    /**
     * Turns off all motors.
     */
    public void robotStopAllMotion() {
        leftFront.setPower(0);
        leftRear.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);

        liftArm1.setPower(0);
        liftArm2.setPower(0);

    }
}