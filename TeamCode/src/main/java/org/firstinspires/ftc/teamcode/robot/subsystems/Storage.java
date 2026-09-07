package org.firstinspires.ftc.teamcode.robot.subsystems;

import Ori.Coval.Logging.Logger.KoalaLog;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.teamcode.core.lib.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.core.lib.pid.PIDController;
import org.firstinspires.ftc.teamcode.robot.Constants;

/** Subsystem for controlling a vertical Storage mechanism with PID control. */
public class Storage implements Subsystem {
  private static Storage instance;
  private DcMotorEx motor;
  private PIDController pidController;
  private boolean isPidEnabled = false;
  private double targetPosition = 0.0;
  private double manualPower = 0.0;

  /** Private constructor for singleton pattern */
  protected Storage() {}

  /** Returns the singleton instance of the subsystem */
  public static synchronized Storage getInstance() {
    if (instance == null) {
      instance = new Storage();
    }
    return instance;
  }

  /** Sets manual power to the mechanism motor. */
  public void setPower(double power) {
    this.isPidEnabled = false;
    this.manualPower = power;
    motor.setPower(power);
  }

  /** Sets the target position and enables the PID controller. */
  public void setTargetPosition(double position) {
    this.targetPosition = position;
    this.isPidEnabled = true;
  }

  /** Returns the current position read by the encoder. */
  public double getCurrentPosition() {
    return motor.getCurrentPosition();
  }

  /** Returns the current target position of the controller. */
  public double getTargetPosition() {
    return targetPosition;
  }

  /** Stops the mechanism movement. */
  public void stopStorage() {
    setPower(0);
  }

  /** Moves the mechanism to a preset position. */
  public void goToLowPosition() {
    setTargetPosition(Constants.Storage.LOW_POSITION);
  }

  /** Moves the mechanism to a preset position. */
  public void goToHighPosition() {
    setTargetPosition(Constants.Storage.HIGH_POSITION);
  }

  /** Safely resets the motor encoder to zero. */
  public void resetEncoders() {
    resetEncoderSafely(motor);
  }

  private void resetEncoderSafely(DcMotor motor) {
    if (motor.getCurrentPosition() != 0) {
      motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      if (pidController != null) {
        pidController.reset();
      }
    }
  }

  /** Checks if the limit switch is active. */
  public boolean isLimitTop() {
    return false;
  }

  /** Checks if the limit switch is active. */
  public boolean isLimitBottom() {
    return false;
  }

  /** Updates the PID controller coefficients from Constants. */
  public void updatePID() {
    pidController.setKP(Constants.Storage.PID.kP);
    pidController.setKI(Constants.Storage.PID.kI);
    pidController.setKD(Constants.Storage.PID.kD);
    pidController.setKF(Constants.Storage.PID.kF);
  }

  /** Initializes hardware and PID controllers */
  @Override
  public void initialize(HardwareMap hardwareMap) {
    motor = hardwareMap.get(DcMotorEx.class, Constants.Storage.MOTOR_NAME);
    motor.setDirection(
        Constants.Storage.IS_INVERTED ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD);
    motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    pidController =
        new PIDController(
            Constants.Storage.PID.kP,
            Constants.Storage.PID.kI,
            Constants.Storage.PID.kD,
            Constants.Storage.PID.kF);
    pidController.setTolerance(20.0);
  }

  /** Main control loop */
  @Override
  public void execute() {
    // double currentPosition = getCurrentPosition();

    // if (isLimitBottom()) {
    //   resetEncoderSafely(motor);
    //   currentPosition = 0;
    // }

    // double power;

    // if (isPidEnabled) {
    //   power = pidController.calculate(targetPosition, currentPosition);
    // } else {
    //   pidController.reset();
    //   power = manualPower;
    // }

    // if (isLimitBottom() && power < 0) power = 0;
    // if (isLimitTop() && power > 0) power = 0;

    // motor.setPower(power);
    KoalaLog.log("Armazenador Power", motor.getPower(), true);
  }

  /** Reset state when OpMode starts */
  @Override
  public void start() {
    isPidEnabled = false;
    stopStorage();
  }

  /** Ensures safety when OpMode stops */
  @Override
  public void stop() {
    stopStorage();
  }
}
