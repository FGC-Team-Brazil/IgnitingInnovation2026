package org.firstinspires.ftc.teamcode.robot.subsystems;

import Ori.Coval.Logging.AutoLog;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@AutoLog
public class IntakeSubsystem implements Subsystem {

private static IntakeSubsystemAutoLogged instance;

private HardwareMap hardwareMap;
private Motor INTAKE_SPEED;
private DCMotor intakeMotor;

protected Intake() {}

public static synchronized Intake getInstance() {
    if (instance == null) {
      instance = new Intake();
    }
    return instance;
  }

public void initialize(HardwareMap hardwareMap) {
    this.hardwareMap = hardwareMap;

    intakeMotor = hardwareMap.get(DCMotor.class, Constants.Intake.INTAKE_SPEED);
    intakeMotor.setDirection(DCMotor.Direction.FORWARD);
    intakeMotor.setMode(DCMotor.RunMode.RUN_WITHOUT_ENCODER);

  }

@Override
  public void start() {}

@Override
  public void execute() {}

public void runMotorPower(double power) {
    intakeMotor.setPower(power);
  }

@Override
  public void stop() {
    intakeMotor.setPower(0);
  }

public void stopMotor() {
    intakeMotor.setPower(0);
  }
}

