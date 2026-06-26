package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.lib.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.robot.Constants;

public class Climber implements Subsystem {

    private static Climber instance;
    private DcMotorEx motor;

    protected Climber {

        public static synchronized Climber getInstance() {
            if (instance == null) {
                instance = new Climber();
            }
            return instance;
        }
    }

@Override
public void initialize() {
    motor = hardwareMap.get(DcMotor.class, Constants.Climber.MOTOR_NAME);
    motor.setDirection(DcMotorSimple.Direction.FORWARD);
    motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    motor.setZeroPowerBehavior(DcMotor.setZeroPowerBehavior.FLOAT);
}

@Override
public void start() {

}

@Override
public void execute() {

}

@Override
public void stop() {
    motor.setPower(o);
}

public void runMotorPower(double power) {
    motor.setPower(power);
}

public void stopMotor() {
    motor.setPower(0);
}

}