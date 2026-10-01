// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class drivetrain extends SubsystemBase {
  /** Creates a new drivetrain. */
  private final SparkMax leftLeader;
  private final SparkMax leftFollower;
  private final SparkMax rightLeader;
  private final SparkMax rightFollower;

  private final DifferentialDrive drive;
  
  public drivetrain() {
  leftLeader = new SparkMax(Constants.DriveConstants.LEFT_LEADER_ID, MotorType.kBrushed);
  leftFollower = new SparkMax(Constants.DriveConstants.LEFT_FOLLOWER_ID, MotorType.kBrushed);
  rightLeader = new SparkMax(Constants.DriveConstants.RIGHT_LEADER_ID, MotorType.kBrushed);
  rightFollower = new SparkMax(Constants.DriveConstants.RIGHT_FOLLOWER_ID, MotorType.kBrushed);



  drive = new DifferentialDrive(leftLeader, rightLeader);
  }
  public void driveArcade(double xSpeed, double zRotation) {
    drive.arcadeDrive(xSpeed,zRotation );
  }

  @Override
  public void periodic() {
    
  }
}

