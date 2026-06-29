package org.firstinspires.ftc.teamcode.robot.subsystems;

import Ori.Coval.Logging.AutoLog;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@AutoLog
public class Intake implements Subsystem {

private HardwareMap hardwareMap;
private Motor first_motorIntake;
private DCMotor motor;

protected Intake() {}

public static synchronized Intake getInstance() {
    if (instance == null) {
      instance = new Intake();
    }
    return instance;
  }

public void initialize(HardwareMap hardwareMap) {
    this.hardwareMap = hardwareMap;
    motor = hardwareMap.get(DCMotor.class, Constants.Intake.MOTOR);
    motor.setDirection(DCMotor.Direction.FORWARD);
    motor.setMode(DCMotor.RunMode.RUN_WITHOUT_ENCODER);
  }

@Override
  public void start() {}

@Override
  public void execute() {}

public void runMotorPower(double power) {
    motor.setPower(power);
  }

@Override
  public void stop() {
    motor.setPower(0);
  }

public void stopMotor() {
  motor.setPower(0);
  }
}

