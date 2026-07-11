package org.firstinspires.ftc.teamcode.robot;

/**
 * Centralized storage for robot configuration values such as hardware names, inversion settings,
 * and control constants.
 *
 * <p>Organizing constants in a single location makes maintenance easier and avoids hardcoded values
 * throughout the codebase.
 */
public class Constants {

  /** Drivetrain hardware configuration. */
  public static class DrivetrainBuilderConstants {
    public static final String MOTOR_RIGHT = "drivetrain_motorRight";
    public static final String MOTOR_LEFT = "drivetrain_motorLeft";
    public static final boolean MOTOR_RIGHT_INVERTED = false;
    public static final boolean MOTOR_LEFT_INVERTED = true;
  }

  public static class Intake {
    public static final String INTAKE_SPEED = "intake_motor";
  }
}
