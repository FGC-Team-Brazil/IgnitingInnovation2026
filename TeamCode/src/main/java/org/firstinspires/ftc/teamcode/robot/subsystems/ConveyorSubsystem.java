package org.firstinspires.ftc.teamcode.robot.subsystems;

import Ori.Coval.Logging.AutoLog;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.core.lib.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.core.lib.pid.PIDController;
import org.firstinspires.ftc.teamcode.robot.Constants;

@AutoLog
public class ConveyorSubsystem implements Subsystem {

  private static ConveyorSubsystemAutoLogged instance;

  private DcMotor conveyorMotor;
  private PIDController pidController;

  /** Encoder resolution of the motor. */
  private static final double TICKS_PER_REV = 560;

  /** Control mode. */
  private boolean velocityMode = false;

  /** Open-loop power (-1 to 1). */
  private double targetPower = 0.0;

  /** Target speed in RPM. */
  private double targetRPM = 0.0;

  protected ConveyorSubsystem() {}

  @Override
  public void initialize(HardwareMap hardwareMap) {

    conveyorMotor = hardwareMap.get(DcMotor.class, Constants.Conveyor.MOTOR);

    conveyorMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    conveyorMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    pidController =
        new PIDController(
            Constants.Conveyor.PID.kP,
            Constants.Conveyor.PID.kI,
            Constants.Conveyor.PID.kD,
            Constants.Conveyor.PID.kF);

    pidController.enableVoltageCompensation(hardwareMap);
  }

  @Override
  public void start() {}

  @Override
  public void execute() {

    if (velocityMode) {

      double targetTicksPerSecond = (targetRPM * TICKS_PER_REV) / 60.0;

      pidController.runVelocity(conveyorMotor, targetTicksPerSecond);

    } else {

      pidController.reset();
      conveyorMotor.setPower(targetPower);
    }
  }

  @Override
  public void stop() {

    targetPower = 0.0;
    targetRPM = 0.0;
    velocityMode = false;

    pidController.reset();
    conveyorMotor.setPower(0);
  }

  /**
   * Controls the conveyor using motor power.
   *
   * @param power Motor power from -1.0 to 1.0.
   */
  public void setPower(double power) {

    velocityMode = false;
    targetPower = power;
  }

  /**
   * Controls the conveyor using target speed.
   *
   * @param rpm Target motor speed in RPM.
   */
  public void setVelocity(double rpm) {

    velocityMode = true;
    targetRPM = rpm;
  }

  /** Stops the conveyor. */
  public void stopConveyor() {

    setPower(0);
  }

  /** Returns the current target power. */
  public double getPower() {

    return targetPower;
  }

  /** Returns the target speed in RPM. */
  public double getTargetVelocity() {

    return targetRPM;
  }

  public static synchronized ConveyorSubsystemAutoLogged getInstance() {

    if (instance == null) {
      instance = new ConveyorSubsystemAutoLogged();
    }

    return instance;
  }
}
