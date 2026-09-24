// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class drivetrain extends SubsystemBase {
  /** Creates a new drivetrain. */
  private final SparkMax leftLeader;
  private final SparkMax leftFollower;
  private final SparkMax rightLeader;
  private final SparkMax rightFollower;

  private final DifferentialDrive drive;
  
  public drivetrain() {
  leftLeader = new SparkMax(drive.Constants.LEFT_LEADER_ID, MotarType.kbrushed);
  leftFollower = new SparkMax(drive.Constants.LEFT_FOLLOWER_ID, MotarType.kbrushed);
  rightLeader = new SparkMax(drive.Constants.RIGHT_LEADER_ID, MotarType.kbrushed);
  rightFollower = new SparkMax(drive.Constants.RIGHT_FOLLOWER_ID, MotarType.kbrushed);


  drive = new DifferentialDrive(leftLeader, rightLeader);

  public void driveArcade(double xSpeed, double zRotation) {
    drive.arcadeDrive(xSpeed,zRotation );

    SmartDashboard.putNumber(Key:"Intaking feeder role value", INTAKING_FEEDER_VOLTAGE);
    SmartDashboard.putNumber(Key:"Intaking intake roller value", INTAKING_INTAKE_VOLTAGE);
    SmartDashboard.putNumber(Key:"Launching feeder roller vlaue", LAUNCHING_FEEDER_VOLTAGE);
    SmartDashboard.putNumber(Key:"Launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE);
    SmartDashboard.putNumber(Key:"Spin-up feeder roller value", SPIN_UP_FEEDER_VOLTAGE);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
