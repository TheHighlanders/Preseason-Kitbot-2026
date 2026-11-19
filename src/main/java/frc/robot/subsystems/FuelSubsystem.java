// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.FuelConstants.FEEDER_MOTOR_CURRENT_LIMIT;
import static frc.robot.Constants.FuelConstants.FEEDER_MOTOR_ID;
import static frc.robot.Constants.FuelConstants.INTAKE_LAUNCHER_MOTOR_ID;
import static frc.robot.Constants.FuelConstants.INTAKING_FEEDER_VOLTAGE;
import static frc.robot.Constants.FuelConstants.INTAKING_INTAKE_VOLTAGE;
import static frc.robot.Constants.FuelConstants.LAUNCHER_MOTOR_CURRENT_LIMIT;
import static frc.robot.Constants.FuelConstants.LAUNCHING_FEEDER_VOLTAGE;
import static frc.robot.Constants.FuelConstants.LAUNCHING_LAUNCHER_VOLTAGE;
import static frc.robot.Constants.FuelConstants.SPIN_UP_FEEDER_VOLTAGE;
import static frc.robot.Constants.PIDConstants.FEEDER_KD;
import static frc.robot.Constants.PIDConstants.FEEDER_KI;
import static frc.robot.Constants.PIDConstants.FEEDER_KP;
import static frc.robot.Constants.PIDConstants.SHOOTER_KD;
import static frc.robot.Constants.PIDConstants.SHOOTER_KI;
import static frc.robot.Constants.PIDConstants.SHOOTER_KP;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;

public class FuelSubsystem extends SubsystemBase {
  // declare variables
  private final SparkMax intakeLauncherRoller;
  private final SparkMax feederRoller;
  private final RelativeEncoder feederEncoder;
  private final RelativeEncoder shooterEncoder;

  // pid controllers
  private final SparkClosedLoopController shooterPID;
  private final SparkClosedLoopController feederPID;

  // target RPM
  private double shooterTargetRPM = 0;
  private double intakeTargetRPM = 0;


  /** Creates the fuel subsystem */
  public FuelSubsystem() {
    // create brushed motors for each of the motors on the launcher mechanism
    intakeLauncherRoller = new SparkMax(INTAKE_LAUNCHER_MOTOR_ID, MotorType.kBrushed);
    feederRoller = new SparkMax(FEEDER_MOTOR_ID, MotorType.kBrushed);

    // encoders (for data)
    shooterEncoder = intakeLauncherRoller.getEncoder();
    feederEncoder = feederRoller.getEncoder();

    // initialize the pid controllers
    shooterPID = intakeLauncherRoller.getClosedLoopController();
    feederPID = feederRoller.getClosedLoopController();

    // create the configuration for the feeder roller, set a current limit and apply
    // the config to the controller
    SparkMaxConfig feederConfig = new SparkMaxConfig();

    // pid
    feederConfig.closedLoop
      .p(FEEDER_KP)
      .i(FEEDER_KI)
      .d(FEEDER_KD)
      .outputRange(-1.0, 1.0);
    feederConfig.smartCurrentLimit(FEEDER_MOTOR_CURRENT_LIMIT);
    feederRoller.configure(feederConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // create the configuration for the launcher roller, set a current limit, set
    // the motor to inverted so that positive values are used for both intaking and
    // launching, and apply the config to the controller
    SparkMaxConfig launcherConfig = new SparkMaxConfig();
    launcherConfig.inverted(true);
    launcherConfig.smartCurrentLimit(LAUNCHER_MOTOR_CURRENT_LIMIT);
    // pid
    launcherConfig.closedLoop
      .p(SHOOTER_KP)
      .i(SHOOTER_KI)
      .d(SHOOTER_KD)
      .outputRange(-1.0, 1.0);
    intakeLauncherRoller.configure(launcherConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // put default values for various fuel operations onto the dashboard
    // all commands using this subsystem pull values from the dashbaord to allow
    // you to tune the values easily, and then replace the values in Constants.java
    // with your new values. For more information, see the Software Guide.
    SmartDashboard.putNumber("Shooter kP", SHOOTER_KP);
    SmartDashboard.putNumber("Shooter kI", SHOOTER_KI);
    SmartDashboard.putNumber("Shooter kD", SHOOTER_KD);
    SmartDashboard.putNumber("Feeder kP", FEEDER_KP);
    SmartDashboard.putNumber("Feeder kI", FEEDER_KI);
    SmartDashboard.putNumber("Feeder kD", FEEDER_KD);
    SmartDashboard.putNumber("Intaking feeder roller value", INTAKING_FEEDER_VOLTAGE);
    SmartDashboard.putNumber("Intaking intake roller value", INTAKING_INTAKE_VOLTAGE);
    SmartDashboard.putNumber("Launching feeder roller value", LAUNCHING_FEEDER_VOLTAGE);
    SmartDashboard.putNumber("Launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE);
    SmartDashboard.putNumber("Spin-up feeder roller value", SPIN_UP_FEEDER_VOLTAGE);
  }

  // A method to set the voltage of the intake roller
  public void setIntakeLauncherRoller(double voltage) {
    intakeLauncherRoller.setVoltage(voltage);
  }

  // A method to set the voltage of the intake roller
  public void setFeederRoller(double voltage) {
    feederRoller.setVoltage(voltage);
  }

  public void setShooterRPM(double rpm) {
    shooterTargetRPM = rpm;
    shooterPID.setSetpoint(rpm, ControlType.kVelocity);
  }

  public void setFeederRPM(double rpm) {
    feederPID.setSetpoint(rpm, ControlType.kVelocity);
  }

  // A method to stop the rollers
  public void stop() {
    feederRoller.set(0);
    intakeLauncherRoller.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // put the rpm values
    SmartDashboard.putNumber("Shooter/Actual RPM", shooterEncoder.getVelocity());
    SmartDashboard.putNumber("Feeder/Actual RPM", feederEncoder.getVelocity());
    SmartDashboard.putNumber("Shooter/Target RPM", shooterTargetRPM);
    SmartDashboard.putNumber(
        "Shooter/Error RPM",
        shooterTargetRPM - shooterEncoder.getVelocity()
    );
    SmartDashboard.putNumber("Feeder/Target RPM", intakeTargetRPM);
    SmartDashboard.putNumber(
        "Feeder/Error RPM",
        intakeTargetRPM - feederEncoder.getVelocity()
    );
  }
}
