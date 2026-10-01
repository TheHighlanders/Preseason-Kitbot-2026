// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class shooter extends SubsystemBase {
  private final SparkMax shooter_moter;

  private final DifferentialDrive drive;

  /** Creates a new shooter. */
  public shooter() {
    // crate brushed motors for dirve
    shooter_moter = new SparkMax(shooterConstants.ID, MotorType.kBrushed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run\
    drive.arcadeDrive(xSpeed, zRotation);
}

SmartDashboard.putNumber(key:"Intaking feeder roller value", INTAKINGFEEDER_VOLTAGE)
SmartDashboard.putNumber(key:"Intaking intake roller value", INTAKING_FEEDER_VOLTAGE)
SmartDashboard.putNumber(key:"Launching feeder roller value", LAUNCHING_FEEDER_VOLTAGE)
SmartDashboard.putNumber(key:"Launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE)
SmartDashboard.putNumber(key:"Spin-up feeder roller value", SPIN_UP_FEEDER_VOLTAGE)

public void setIntakeLauncherRoller(double value){
  intakeLauncherRoller.setVoltage(voltage);
}

public void setFeederRoller(double value){
  feederRoller.setVoltage(voltage);
}

public void stop(){
  feederRoller.set(0);
  intakeLauncherRoller.set(0);
}
