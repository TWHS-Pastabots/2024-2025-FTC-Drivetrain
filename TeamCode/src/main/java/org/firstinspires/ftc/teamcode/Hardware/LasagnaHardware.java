package main.java.org.firstinspires.ftc.teamcode.Hardware;

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

    public DcMoterEx motors[];


public void intializeDriveMotors(HardwareMap hardwareMap){
    frontLeft = hardwareMap(DcMoterEx.class, LaagnaIDS.LEFT_FRONT_MOTOR );
    frontRight = hardwareMap(DcMoterEx.class, LaagnaIDS.RIGHT_FRONT_MOTOR );
    rearLeft = hardwareMap(DcMoterEx.class, LaagnaIDS.LEFT_REAR_MOTOR );
    rearRight = hardwareMap(DcMoterEx.class, LaagnaIDS.RIGHT_REAR_MOTOR );

    motors = new DcMoterEx[]{frontLeft, frontRight, rearLeft, rearRight};

    leftFront.setDirection
}
}