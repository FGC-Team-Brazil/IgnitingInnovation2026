package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.core.lib.builders.DrivetrainBuilder;
import org.firstinspires.ftc.teamcode.core.lib.gamepad.SmartGamepad;
import org.firstinspires.ftc.teamcode.core.lib.internal.RobotContainerInternal;
import org.firstinspires.ftc.teamcode.robot.subsystems.ContinuousServo;
import org.firstinspires.ftc.teamcode.robot.subsystems.Conveyor;
import org.firstinspires.ftc.teamcode.robot.subsystems.Door;
import org.firstinspires.ftc.teamcode.robot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.robot.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.robot.subsystems.Storage;

/** Central robot container responsible for subsystem and control management. */
public class RobotContainer extends RobotContainerInternal {
  private final SmartGamepad driver;
  private final SmartGamepad operator;

  private final DrivetrainBuilder drivetrain;
  private final Shooter shooter;
  private final Intake intake1;
  private final Conveyor conveyor;
  private final Door door;
  private final Storage storage;

  private final ContinuousServo slider;

  public RobotContainer(Gamepad driverGamepad, Gamepad operatorGamepad) {
    super(
        DrivetrainBuilder.getInstance(),
        Shooter.getInstance(),
        Intake.getInstance(),
        Conveyor.getInstance(),
        Door.getInstance(),
        Storage.getInstance(),
        ContinuousServo.getInstance());

    this.driver = new SmartGamepad(driverGamepad);
    this.operator = new SmartGamepad(operatorGamepad);

    drivetrain =
        DrivetrainBuilder.build(
            Constants.DrivetrainBuilderConstants.MOTOR_RIGHT_NAME,
            Constants.DrivetrainBuilderConstants.MOTOR_LEFT_NAME,
            Constants.DrivetrainBuilderConstants.IS_MOTOR_RIGHT_INVERTED,
            Constants.DrivetrainBuilderConstants.IS_MOTOR_LEFT_INVERTED);
    shooter = Shooter.getInstance();
    intake1 = Intake.getInstance();
    conveyor = Conveyor.getInstance();
    door = Door.getInstance();
    storage = Storage.getInstance();
    slider = ContinuousServo.getInstance();
  }

  @Override
  public void configureBindings() {

    // Drivetrain Controls
    driver
        .leftY()
        .or(driver.rightX())
        .whileTrue(() -> drivetrain.arcadeDrive(driver.getLeftY(), driver.getRightX()))
        .onFalse(drivetrain::stop);

    driver.dpadUp().whileTrue(slider::rotateClockwise).onFalse(slider::stop);
    driver.dpadDown().whileTrue(slider::rotateCounterClockwise).onFalse(slider::stop);

    // Shooter Controls
    operator
        .rightBumper()
        .whileTrue(() -> shooter.runMotorPower(1.0))
        .onFalse(() -> shooter.runMotorPower(0));

    // Intake1 Controls
    operator
        .a()
        .whileTrue(() -> intake1.setPower(Constants.Intake.INTAKE_SPEED))
        .onFalse(() -> intake1.setPower(0));
    operator
        .b()
        .whileTrue(() -> intake1.setPower(-Constants.Intake.INTAKE_SPEED))
        .onFalse(() -> intake1.setPower(0));

    // UnnamedComponent Controls
    operator
        .x()
        .whileTrue(() -> conveyor.setPower(Constants.Conveyor.INTAKE_SPEED))
        .onFalse(() -> conveyor.setPower(0));
    operator
        .y()
        .whileTrue(() -> conveyor.setPower(-Constants.Conveyor.INTAKE_SPEED))
        .onFalse(() -> conveyor.setPower(0));

    operator.dpadUp().onTrue(() -> door.goToPosition(Constants.Door.Position.OPEN));
    operator.dpadDown().onTrue(() -> door.goToPosition(Constants.Door.Position.CLOSED));

    operator.dpadLeft().whileTrue(() -> storage.setPower(1.0)).onFalse(storage::stop);
    operator.dpadRight().whileTrue(() -> storage.setPower(-1.0)).onFalse(storage::stop);

    operator
        .rightTrigger(0.1)
        .whileTrue(() -> storage.setPower(operator.getRightTriggerAxis()))
        .onFalse(storage::stop);
    operator
        .leftTrigger(0.1)
        .whileTrue(() -> storage.setPower(-operator.getLeftTriggerAxis()))
        .onFalse(storage::stop);
  }
}
