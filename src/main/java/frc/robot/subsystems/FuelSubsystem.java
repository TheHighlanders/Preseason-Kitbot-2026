// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.units.measure.Frequency;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import static frc.robot.Constants.FuelConstants.*;
import static frc.robot.Constants.PIDConstants.*;

public class FuelSubsystem extends SubsystemBase {
  // declare variables
  private final SparkMax intakeLauncherRoller;
  private final SparkMax feederRoller;
  private final RelativeEncoder feederEncoder;
  private final RelativeEncoder shooterEncoder;


  /** Creates the fuel subsystem */
  public FuelSubsystem() {
    // create brushed motors for each of the motors on the launcher mechanism
    intakeLauncherRoller = new SparkMax(INTAKE_LAUNCHER_MOTOR_ID, MotorType.kBrushed);
    feederRoller = new SparkMax(FEEDER_MOTOR_ID, MotorType.kBrushed);

    // encoders (for data)
    shooterEncoder = intakeLauncherRoller.getEncoder();
    feederEncoder = intakeLauncherRoller.getEncoder();

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
    SmartDashboard.putNumber("Shooter RPM", shooterEncoder.getVelocity());
    SmartDashboard.putNumber("Feeder RPM", feederEncoder.getVelocity());
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

  // A method to stop the rollers
  public void stop() {
    feederRoller.set(0);
    intakeLauncherRoller.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
