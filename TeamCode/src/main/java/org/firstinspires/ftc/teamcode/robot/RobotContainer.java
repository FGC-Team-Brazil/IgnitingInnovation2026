package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.core.lib.builders.DrivetrainBuilder;
import org.firstinspires.ftc.teamcode.core.lib.gamepad.SmartGamepad;
import org.firstinspires.ftc.teamcode.core.lib.gamepad.Trigger;
import org.firstinspires.ftc.teamcode.core.lib.internal.RobotContainerInternal;
import org.firstinspires.ftc.teamcode.robot.subsystems.SubsystemExample;
import org.firstinspires.ftc.teamcode.robot.subsystems.Intake;


/**
 * RobotContainer class handle instance configurations. All the subsystems listed in constructor
 * here will be execute when the library classes run.
 */
public class RobotContainer extends RobotContainerInternal {

  private final SmartGamepad driver;
  private final SmartGamepad operator;

  private final DrivetrainBuilder drivetrain;
  private final Intake intake;

  public RobotContainer(Gamepad driver, Gamepad operator) {
    super(
        DrivetrainBuilder.getInstance(), Intake.getInstance()
        // Add more subsystems here.
        );

    this.driver = new SmartGamepad(driver);
    this.operator = new SmartGamepad(operator);

    drivetrain =
        DrivetrainBuilder.build(
            Constants.DrivetrainBuilderConstants.MOTOR_RIGHT,
            Constants.DrivetrainBuilderConstants.MOTOR_LEFT,
            Constants.DrivetrainBuilderConstants.MOTOR_RIGHT_INVERTED,
            Constants.DrivetrainBuilderConstants.MOTOR_LEFT_INVERTED);
    intake = Intake.getInstance();
    // You need to add the subsystems here too.
  }

  @Override
  public void configureBindings() {

    // Driver controller
    driver
        .leftY()
        .or(driver.rightX())
        .whileTrue(() -> drivetrain.arcadeDrive(-driver.getLeftY(), driver.getRightX()))
        .onFalse(drivetrain::stop);

    // Operator controller
    operator.b().onTrue(() -> intake.runMotorPower(0.8)).onFalse(intake::stopMotor); // Example button biding for intake 

  }
}
