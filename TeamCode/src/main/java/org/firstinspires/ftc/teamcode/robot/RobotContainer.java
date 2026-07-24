package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.core.lib.builders.DrivetrainBuilder;
import org.firstinspires.ftc.teamcode.core.lib.gamepad.SmartGamepad;
import org.firstinspires.ftc.teamcode.core.lib.gamepad.Trigger;
import org.firstinspires.ftc.teamcode.core.lib.internal.RobotContainerInternal;
<<<<<<< Updated upstream
import org.firstinspires.ftc.teamcode.robot.subsystems.SubsystemExample;
=======
import org.firstinspires.ftc.teamcode.robot.subsystems.Conveyor;
import org.firstinspires.ftc.teamcode.robot.subsystems.Intake;
import org.firstinspires.ftc.teamcode.robot.subsystems.Shooter;
>>>>>>> Stashed changes

/** Central robot container responsible for subsystem and control management. */
public class RobotContainer extends RobotContainerInternal {
  private final SmartGamepad driver;
  private final SmartGamepad operator;

  private final SubsystemExample subsystemExample;
  private final DrivetrainBuilder drivetrain;
<<<<<<< Updated upstream
=======
  private final Shooter shooter;
  private final Intake intake1;
  private final Conveyor conveyor;
>>>>>>> Stashed changes

  public RobotContainer(Gamepad driverGamepad, Gamepad operatorGamepad) {
    super(
<<<<<<< Updated upstream
        DrivetrainBuilder.getInstance(), SubsystemExample.getInstance()
        // Add more subsystems here.
        );
=======
        DrivetrainBuilder.getInstance(),
        Shooter.getInstance(),
        Intake.getInstance(),
        Conveyor.getInstance());
>>>>>>> Stashed changes

    this.driver = new SmartGamepad(driverGamepad);
    this.operator = new SmartGamepad(operatorGamepad);

    drivetrain =
        DrivetrainBuilder.build(
<<<<<<< Updated upstream
            Constants.DrivetrainBuilderConstants.MOTOR_RIGHT,
            Constants.DrivetrainBuilderConstants.MOTOR_LEFT,
            Constants.DrivetrainBuilderConstants.MOTOR_RIGHT_INVERTED,
            Constants.DrivetrainBuilderConstants.MOTOR_LEFT_INVERTED);
    subsystemExample = SubsystemExample.getInstance();
    // You need to add the subsystems here too.
=======
            Constants.DrivetrainBuilderConstants.MOTOR_RIGHT_NAME,
            Constants.DrivetrainBuilderConstants.MOTOR_LEFT_NAME,
            Constants.DrivetrainBuilderConstants.IS_MOTOR_RIGHT_INVERTED,
            Constants.DrivetrainBuilderConstants.IS_MOTOR_LEFT_INVERTED);
    shooter = Shooter.getInstance();
    intake1 = Intake.getInstance();
    conveyor = Conveyor.getInstance();
>>>>>>> Stashed changes
  }

  @Override
  public void configureBindings() {

    // Drivetrain Controls
    driver
        .leftY()
        .or(driver.rightX())
        .whileTrue(() -> drivetrain.arcadeDrive(-driver.getLeftY(), driver.getRightX()))
        .onFalse(drivetrain::stop);

<<<<<<< Updated upstream
    // Operator controller
    operator.y().onTrue(() -> subsystemExample.setTargetAngle(90));

    operator.a().onTrue(() -> subsystemExample.setTargetAngle(0));

    new Trigger(subsystemExample::isLimitLeft).onTrue(subsystemExample::resetEncoders);

    new Trigger(subsystemExample::isLimitRight).onTrue(subsystemExample::resetEncoders);

    operator.start().and(operator.back()).onTrue(subsystemExample::resetEncoders);

    operator.y().negate().and(operator.a().negate()).onTrue(() -> subsystemExample.setPower(0));
=======
    // Shooter Controls
    operator
        .rightTrigger()
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
>>>>>>> Stashed changes
  }
}
