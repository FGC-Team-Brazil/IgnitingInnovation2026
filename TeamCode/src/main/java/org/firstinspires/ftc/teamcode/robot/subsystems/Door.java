package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.teamcode.core.lib.interfaces.Subsystem;
import org.firstinspires.ftc.teamcode.robot.Constants;

/**
 * Subsystem for controlling a door mechanism using servos or continuous rotation servos. Generated
 * by FGCLib Studio.
 */
public class Door implements Subsystem {
  private static Door instance;
  private Servo servo1;

  /** Private constructor for singleton pattern */
  protected Door() {}

  /** Returns the singleton instance of the subsystem */
  public static synchronized Door getInstance() {
    if (instance == null) {
      instance = new Door();
    }
    return instance;
  }

  /** Moves the mechanism to a preset position. */
  public void goToPosition(Constants.Door.Position position) {
    if (position == null) return;

    switch (position) {
      case OPEN:
        servo1.setPosition(Constants.Door.OPEN_POSITION_1);
        break;
      case CLOSED:
        servo1.setPosition(Constants.Door.CLOSED_POSITION_1);
        break;
    }
  }

  /** Initializes hardware and PID controllers */
  @Override
  public void initialize(HardwareMap hardwareMap) {
    servo1 = hardwareMap.get(Servo.class, Constants.Door.SERVO_1_NAME);
  }

  /** Main control loop, handled by GamepadManager */
  @Override
  public void execute() {
    // Empty execution logic for Door
  }

  /** Reset state when OpMode starts */
  @Override
  public void start() {}

  /** Ensures safety when OpMode stops */
  @Override
  public void stop() {}
}
