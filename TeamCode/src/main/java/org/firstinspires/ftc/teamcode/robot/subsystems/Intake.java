package org.firstinspires.ftc.teamcode.robot.subsystems;

import Ori.Coval.Logging.AutoLog;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.lib.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.robot.Constants;

@AutoLog
public class Intake implements Subsystem {

private static Intake instance;
private HardwareMap hardwareMap;
private DcMotor Motor;

protected Intake() {}

public static synchronized Intake getInstance() {
    if (instance == null) {
      instance = new IntakeAutoLogged();
    }
    return instance;
  }

public void initialize(HardwareMap hardwareMap) {
    this.hardwareMap = hardwareMap;

    motor = hardwareMap.get(DcMotor.class, Constants.Intake.INTAKE_MOTOR);
    motor.setDirection(DcMotor.Direction.FORWARD);
    motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

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

