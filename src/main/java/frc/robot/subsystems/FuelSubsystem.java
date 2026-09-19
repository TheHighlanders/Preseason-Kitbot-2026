// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.FuelConstants;

public class FuelSubsystem extends SubsystemBase {
  // declare variables
  private final SparkMax intakeLauncherRoller;
  private final SparkMax feederRoller;


  /** Creates the fuel subsystem */
  public FuelSubsystem() {
    feederRoller = new SparkMax(FuelConstants.FEEDER_ROLLER_ID, MotorType.kBrushed);
    intakeLauncherRoller = new SparkMax(FuelConstants.INTAKE_LAUNCHER_ID, MotorType.kBrushed);
    SmartDashboard.putNumber("Intaking feeder roller value", 0);
    SmartDashboard.putNumber("Intaking intake roller value", 0);
    SmartDashboard.putNumber("Launching feeder roller value", 0);
    SmartDashboard.putNumber("Launching launcher roller value", 0);
    SmartDashboard.putNumber("Spin-up feeder roller value", 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // sets intake launcher roller's voltage
  public void setIntakeLauncherRoller(double voltage) {
    intakeLauncherRoller.setVoltage(voltage);
  }

  // sets feeder roller's voltage
  public void setFeederRoller(double voltage) {
    feederRoller.setVoltage(voltage);
  }

  // stops all fuel motors
  public void stop() {
    feederRoller.set(0);
    intakeLauncherRoller.set(0);
  }

 // public Command spinUpCommand() {
 //   return this.run(() -> spinUp());
//  }
}
