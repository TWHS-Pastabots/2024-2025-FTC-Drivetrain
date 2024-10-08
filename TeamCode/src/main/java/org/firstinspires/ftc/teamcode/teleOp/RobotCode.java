package org.firstinspires.ftc.teamcode.teleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad.RumbleEffect;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.system.Assert;
import org.firstinspires.ftc.teamcode.Hardware.RobotHardware;
import org.firstinspires.ftc.teamcode.autonomous.NewUtilities;




@TeleOp(name="NewRigatoni")
public class RobotCode extends LinearOpMode
{
    private RobotHardware hardware;
    private NewUtilities utilities;


    // Lift Power
    final double FULL_POWER = 0.95;     // With two motors it's best not to run at full capacity
    final double SLOW_POWER = 0.4;
    final double SLOW_RAISE = 0.4;
    final double SLOW_FALL = 0.15;
    // Drive Power
    final double FAST_SPEED = 0.85;
    final double SLOW_SPEED = 0.7;
    final double SUPER_SLOW_SPEED = 0.5;
    double speed = FAST_SPEED;

    // Rumble timings
    final double ALMOST_END_GAME = 80;  // Wait this many seconds before rumble-alert.
    final double END_GAME = 90;         // Wait this many seconds before rumble-alert.

    //Field orientation
    double flipConstant = 1;

    ElapsedTime buttonTime = null;
    ElapsedTime timer = null;

    Telemetry.Item position1;
    Telemetry.Item position2;

    private boolean isTiltStraight = true;


    RumbleEffect customRumbleEffect = new RumbleEffect.Builder()
            .addStep(0.0, 1.0, 250)
            .addStep(1.0, 0.0, 250)
            .addStep(0.0, 0.0, 100)
            .addStep(1.0, 1.0, 500)
            .build();



    /**
     * This method initializes the Primary and supplementary motors
     * which consist of the wheels and lift motors, and the claw
     * motors which is a singular servo.
     */
    @Override
    public void runOpMode()
    {
        Assert.assertNotNull(hardwareMap);
        telemetry.setAutoClear(false);

        buttonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        hardware = new RobotHardware();
        utilities = new NewUtilities(hardware);

        hardware.initializePrimaryMotors(hardwareMap);
        hardware.initializeClawServosTeleOp(hardwareMap);
        hardware.initializeSupplementaryMotors(hardwareMap);


        waitForStart();
        if (isStopRequested()) return;
        if(!opModeIsActive()) return;

        runRumble(customRumbleEffect);
        timer = new ElapsedTime(ElapsedTime.Resolution.SECONDS);

        try {
            run();
        } catch (Throwable t) {
            hardware.robotStopAllMotion();
            utilities.wait(500, telemetry);

            telemetry.clearAll();
            telemetry.addData("Exception caught!", t);
            telemetry.update();
        }

    }



    /**
     * A method that calls the drive, moveArm, and rotateClaw
     * methods, known as helper methods.
     *
     * Loop/actions for Driver-Controlled
     */
    public void run()
    {
        double last = timer.milliseconds();

        boolean almostEndgame = false;
        boolean endgame = false;

        position1 = telemetry.addData("Lift1:", hardware.liftArm1.getCurrentPosition());
        position2 = telemetry.addData("Lift2:", hardware.liftArm2.getCurrentPosition());


        while (opModeIsActive()) {
            drive();
            presetLift();
            lift();
            rotateClaw();
            flipOrientation();


            if (timer.milliseconds() > (last+250)) {
                last = timer.milliseconds();

                position1.setValue(hardware.liftArm1.getCurrentPosition());
                position2.setValue(hardware.liftArm2.getCurrentPosition());
                telemetry.update();
            }


            if ((timer.seconds() > ALMOST_END_GAME) && !almostEndgame)  {
                runRumble(customRumbleEffect);
                almostEndgame = true;
            }

            if ((timer.seconds() > END_GAME) && !endgame)  {
                runRumble(customRumbleEffect);
                endgame = true;
            }


        }

    }



    /**
     * Initializes variables fundamental to driving. Then sets power
     * assigned to each variable in series of if-statements.
     *
     * Set the driver motors' power
     */
    public void drive()
    {
        // Mecanum drivecode
        double y = -gamepad1.left_stick_y;        // Remember, this is reversed!
        double x = gamepad1.left_stick_x * 1.05;
        double rx = gamepad1.right_stick_x;

        // Setting up dead-zones
        double deadZone = 0.3;
        //if(Math.abs(y)  < deadZone) y = 0;
        //if(Math.abs(x) < deadZone) x = 0;
        if(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))  < deadZone) {y = 0; x = 0;}

        //Smooth transition from dead-zone
        if(y > 0) y = (y - Math.signum(y) * deadZone) * (1 / (1 - deadZone));
        if(x > 0) x = (x - Math.signum(x) * deadZone) * (1 / (1 - deadZone));

        //Quadratic acceleration for easier maneuverability at slow speeds
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double leftFrontPower = Math.signum((y + x + rx) / denominator) * Math.pow((y + x + rx) / denominator, 2);
        double leftRearPower = Math.signum((y - x + rx) / denominator) * Math.pow((y - x + rx) / denominator, 2);
        double rightFrontPower = Math.signum((y - x - rx) / denominator) * Math.pow((y - x - rx) / denominator, 2);
        double rightRearPower = Math.signum((y + x - rx) / denominator) * Math.pow((y + x - rx) / denominator, 2);


        changeSpeed();

        hardware.leftFront.setPower(leftFrontPower * speed * flipConstant);
        hardware.leftRear.setPower(leftRearPower * speed * flipConstant);
        hardware.rightFront.setPower(rightFrontPower * speed * flipConstant);
        hardware.rightRear.setPower(rightRearPower * speed * flipConstant);
    }




    /**
     * Creates the slow and super slow speeds through if-statements that change the
     * drive speed based on the input of the square (slow) and circle (super slow) button.
     *
     * Change the motors' speed mode:
     * - Fast Speed
     * - Slow Speed
     * - SuperSlow Speed
     */
    public void changeSpeed()
    {
        // Slow Speed
        if (gamepad1.square && speed == SLOW_SPEED && buttonTime.time() >= 500)
        {
            speed = FAST_SPEED;
            buttonTime.reset();
        }
        else if (gamepad1.square && buttonTime.time() >= 500)
        {
            speed = SLOW_SPEED;
            buttonTime.reset();
        }


        // SuperSlow Speed
        if ((gamepad1.circle || gamepad1.right_bumper) && speed == SUPER_SLOW_SPEED && buttonTime.time() >= 500)
        {
            speed = FAST_SPEED;
            buttonTime.reset();
        }
        else if ((gamepad1.circle || gamepad1.right_bumper) && buttonTime.time() >= 500)
        {
            speed = SUPER_SLOW_SPEED;
            buttonTime.reset();
        }

    }


    /**
     * This method integrates specific junction
     * heights to each d-pad button on the
     * gamepad 2 allowing for efficient means
     * of scoring
     */
    public void presetLift()
    {
        // High Junction
        if (gamepad2.dpad_up)
        {
            telemetry.addLine("Up");
            utilities.liftArmAbsolutePosition(324);
        }

        // Low Junction
        else if (gamepad2.dpad_left)
        {
            utilities.liftArmAbsolutePosition(145);
        }

        // Ground Level
        else if (gamepad2.dpad_down)
        {
            utilities.liftArmAbsolutePosition(15);
        }

        // Medium Level
        else if (gamepad2.dpad_right)
        {
            utilities.liftArmAbsolutePosition(235);
        }
        if(gamepad2.right_bumper)
            utilities.liftArmAbsolutePosition(100);

        // Reset Encoders
        if (gamepad2.share)
        {
            hardware.liftArm1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            hardware.liftArm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }



    /**
     * This method moves the arm up and down based on the input of the right
     * and left triggers, which moves the pulley up and down respectively.
     *
     * Move the lift up or down
     * - Move up at RAISE_POWER
     * - Move down at LOWER_POWER
     */
    public void lift()
    {
        // Raising the lift
        if ( (gamepad2.right_trigger > 0) && (gamepad2.right_trigger > gamepad2.left_trigger) ) {
//            if(hardware.liftArm2.getCurrentPosition() > 100)
//            {
//                utilities.tiltClaw(false);
//            }
            hardware.liftArm1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            hardware.liftArm2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

            hardware.liftArm1.setPower(gamepad2.right_trigger * FULL_POWER);
            hardware.liftArm2.setPower(gamepad2.right_trigger * FULL_POWER);
        }

        // Lowering the lift (slow)
        else if ( (gamepad2.left_trigger > 0) && (gamepad2.left_trigger > gamepad2.right_trigger) ) {
//            if(hardware.liftArm2.getCurrentPosition() < 100)
//            {
//                utilities.tiltClaw(true);
//            }
            hardware.liftArm1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            hardware.liftArm2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

            hardware.liftArm1.setPower(-gamepad2.left_trigger * SLOW_POWER);
            hardware.liftArm2.setPower(-gamepad2.left_trigger * SLOW_POWER);
        }

        //Lowering the lift
        else if(gamepad2.left_bumper) {
            hardware.liftArm1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            hardware.liftArm2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            hardware.liftArm1.setPower(-SLOW_FALL);
            hardware.liftArm2.setPower(-SLOW_FALL);
        }
        else if (gamepad2.right_bumper)
        {
            hardware.liftArm1.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            hardware.liftArm2.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            hardware.liftArm1.setPower(SLOW_RAISE);
            hardware.liftArm2.setPower(SLOW_RAISE);
        }

        // Nothing happens
        else if(!hardware.liftArm1.isBusy() && !hardware.liftArm2.isBusy()) {
            hardware.liftArm1.setPower(0);
            hardware.liftArm2.setPower(0);
        }

    }



    /**
     * The circle and square buttons allow the claw to open and close its prongs.
     *
     * Open and Close the grabber/claw
     */
    public void rotateClaw()
    {
        if(isTiltStraight && gamepad2.triangle && buttonTime.time() >= 500)
        {
            hardware.tiltServo.setPosition(0.45);
            buttonTime.reset();
            isTiltStraight = false;
        }
        if(!isTiltStraight && gamepad2.triangle && buttonTime.time() >= 500)
        {
            buttonTime.reset();
            hardware.tiltServo.setPosition(0.6);
            isTiltStraight = true;
        }
        if(gamepad2.square)
            hardware.grabServo.setPosition(.6);
        if(gamepad2.circle)
            hardware.grabServo.setPosition(0.33);


    }



    /**
     * Make controllers vibrate
     */
    public void runRumble(RumbleEffect e) {
        gamepad1.runRumbleEffect(e);
        gamepad2.runRumbleEffect(e);

    }
    public void flipOrientation() {
        if(flipConstant == 1 && buttonTime.time() >= 500 && gamepad1.triangle)
        {
            flipConstant = -1;
            buttonTime.reset();
        }
        else if(flipConstant == -1 && buttonTime.time() >= 500 && gamepad1.triangle)
        {
            flipConstant = 1;
            buttonTime.reset();
        }
    }


}