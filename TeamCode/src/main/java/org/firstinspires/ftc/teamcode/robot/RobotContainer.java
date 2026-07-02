package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.core.lib.builders.DrivetrainBuilder;
import org.firstinspires.ftc.teamcode.core.lib.gamepad.SmartGamepad;
import org.firstinspires.ftc.teamcode.core.lib.internal.RobotContainerInternal;
import org.firstinspires.ftc.teamcode.robot.subsystems.ConveyorSubsystem;

/**
 * RobotContainer class handle instance configurations. All the subsystems listed in constructor
 * here will be execute when the library classes run.
 */
public class RobotContainer extends RobotContainerInternal {

  private final SmartGamepad driver;
  private final SmartGamepad operator;
  private final ConveyorSubsystem conveyor;

  private final DrivetrainBuilder drivetrain;

  public RobotContainer(Gamepad driver, Gamepad operator) {
    super(
        DrivetrainBuilder.getInstance(), ConveyorSubsystem.getInstance()



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
    conveyor = ConveyorSubsystem.getInstance();
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

    operator.x().whileTrue(() -> conveyor.setPower(1)).onFalse(conveyor::stopConveyor);
  }
}
