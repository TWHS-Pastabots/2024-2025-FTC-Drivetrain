package main.java.org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.roadrunner.util.NanoClock;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Encoder {
    public enum Direction{
        FORWARD(1),
        REVERSE(-1);

        private int multiplier;

        Direction(int multiplier){
            this.multiplier = multiplier;
        }
        public int getMultiplier(){
            return multiplier;
        }
    }

    private DcMoterEx motor;
    private NanoClock clock;

    private Direction direction;

    public Encoder(DcMoterEx motor, NanoClock clock){
        this.motor = motor;
        this.clock = clock;
        
        this.direction = Direction.FORWARD;
    }
}
