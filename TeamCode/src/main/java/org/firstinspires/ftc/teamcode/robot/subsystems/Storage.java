package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.core.lib.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.core.lib.pid.PIDController;
import org.firstinspires.ftc.teamcode.robot.Constants;

import Ori.Coval.Logging.Logger.KoalaLog;

/** Subsystem for controlling a vertical Storage mechanism with PID control. */
public class Storage implements Subsystem {
  private static Storage instance;
  private DcMotorEx motor;
  private PIDController pidController;
  private boolean isPidEnabled = false;
  private boolean lastLimitBottomState = false;
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

  /** Returns the current position read by the encoder (in ticks). */
  public double getCurrentPosition() {
    return motor.getCurrentPosition();
  }

  /** Returns the current position in rotations. */
  public double getPosition() {
    return motor.getCurrentPosition() / 288.0;
  }

  /** Returns the current target position of the controller. */
  public double getTargetPosition() {
    return targetPosition;
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
    motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
    if (Constants.tuningMode) {
      updatePID();
    }

    double currentPosition = getCurrentPosition();
    boolean currentLimitBottom = isLimitBottom();

    if (currentLimitBottom && !lastLimitBottomState) {
      resetEncoderSafely(motor);
      currentPosition = 0;
    }
    lastLimitBottomState = currentLimitBottom;

    // PID
    double power;

    if (isPidEnabled) {
      power = pidController.calculate(targetPosition, currentPosition);
    } else {
      pidController.reset();
      power = manualPower;
    }

    if (isLimitBottom() && power < 0) power = 0;
    if (isLimitTop() && power > 0) power = 0;

    motor.setPower(power);

    if (Constants.tuningMode) {
      TelemetryPacket packet = new TelemetryPacket();
      packet.put("Storage Target Position", targetPosition);
      packet.put("Storage Current Position", currentPosition);
      packet.put("Storage Error", targetPosition - currentPosition);
      packet.put("Storage Power", power);
      FtcDashboard.getInstance().sendTelemetryPacket(packet);
    }

    // Logging
    KoalaLog.log("Storage/Power", motor.getPower(), true);
    KoalaLog.log("Storage/Position (rot)", getPosition(), true);
    KoalaLog.log("Storage/Velocity (deg/sec)", motor.getVelocity(AngleUnit.DEGREES), true);
  }

  /** Reset state when OpMode starts */
  @Override
  public void start() {
    isPidEnabled = false;
    stop();
    motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
  }

  /** Ensures safety when OpMode stops */
  @Override
  public void stop() {
    setPower(0);
    motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
  }
}