package org.firstinspires.ftc.teamcode.robot.subsystems;

import Ori.Coval.Logging.AutoLog;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import org.firstinspires.ftc.teamcode.core.lib.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.core.lib.pid.PIDController;
import org.firstinspires.ftc.teamcode.robot.Constants;

/**
 * Example subsystem for FGCLib.
 *
 * <p>This subsystem isolates the hardware logic from the control logic. Methods like setPower() and
 * setTargetAngle() should be called by Commands or the RobotContainer to control the mechanism. The
 * execute() method runs the PID loop and hardware protections automatically.
 */
@AutoLog
public class IntakeSubsystem implements Subsystem {

  private static Intake instance;

  private IntakeMotor motor;

  protected IntakeSubsystem() {}

  // ── Lifecycle ──────────────────────────────────────────────────────────────
  @Override
  public void initialize(HardwareMap hardwareMap) {
        motor = hardwareMap.get(motor.class, Constants.IntakeSubsystem.MOTOR_NAME);
        motor.setDirection(motor.Direction.FORWARD);
        motor.setMode(motor.RunMode.STOP_AND_RESET_ENCODER);  
  }

  @Override
  public void start() {
    // Called when the OpMode starts
  }

  /**
   * Periodic method called every loop iteration. Handles hardware safety (limit switches) and PID
   * calculations.
   */
  @Override
  public void execute() {
    int currentPosition = motor.getCurrentPosition();

    motor.setPower(power);
  }

  @Override
  public void stop() {
    setPower(0);
    isPidEnabled = false;
  }

  public void RunMotorPower(double power) {
    motor.setPower(power);
  }

  public void stopMotor() {
    motor.setPower(0);
  }

  // ── Control Methods ───────────────────────────────────────────────────────

  /**
   * Sets the manual power to the motors and disables the PID controller.
   *
   * @param power Motor power from -1.0 to 1.0
   */
  public void setPower(double power) {}

  /**
   * Sets the target angle and enables the PID controller.
   *
   * @param angle Target angle in degrees
   */
  public void setTargetAngle(double angle) {}

  // ── Hardware Helpers ──────────────────────────────────────────────────────

  /**
   * Safely resets the motor encoder without completely stopping the loop flow.
   *
   * @param motor The DcMotor to reset
   */
  private void resetEncoderSafely(Intake motor) {}
  

  /** Resets both motor encoders manually. */
  public void resetEncoders() {}

  public boolean isLimitRight() {}

  public boolean isLimitLeft() {}

  // ── Singleton ─────────────────────────────────────────────────────────────

  /**
   * Returns the singleton instance of the subsystem.
   *
   * @return IntakeSubsystemAutoLogged instance
   */
  public static synchronized IntakeSubsystemAutoLogged getInstance() {
    if (instance == null) {
      instance = new IntakeSubsystemAutoLogged();
    }
    return instance;
  }
}
