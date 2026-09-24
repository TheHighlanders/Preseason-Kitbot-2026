// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class intake extends SubsystemBase {
  private static final double INTAKING_FEEDER_VOLTAGE = 0;
    private static final double INTAKING_INTAKE_VOLTAGE = 0;
        private static final double LAUNCHING_FEEDER_VOLTAGE = 0;
                private static final double LAUNCHING_LAUNCHER_VOLTAGE = 0;
                                private static final double SPIN_UP_FEEDER_VOLTAGE = 0;
                                  /** Creates a new intake. */
                                  public final SparkMax leftLeader;
                                  public final SparkMax leftFollower;
                                  public final SparkMax rightLeader;
                                  public final SparkMax rightFollower;
                                
                                  private final DifferentialDrive drive; 
                                  /**Class to drive the robot over CAN */
                                  public intake() {
                                    // create brushed motors for drive
                                    leftLeader = new SparkMax(DriveConstants.LEFT_LEADER_ID, MotorType.kBrushed);
                                    leftFollower = new SparkMax(DriveConstants.LEFT_FOLLOWER_ID, MotorType.kBrushed);
                                    rightLeader = new SparkMax(DriveConstants.RIGHT_LEADER_ID, MotorType.kBrushed);
                                    rightFollower = new SparkMax(DriveConstants.RIGHT_FOLLOWER_ID, MotorType.kBrushed);
                                    SmartDashboard.putNumber("Intaking feeder roller value", INTAKING_FEEDER_VOLTAGE);
                                    SmartDashboard.putNumber("Intaking intake roller value", INTAKING_INTAKE_VOLTAGE);
                                    SmartDashboard.putNumber("launching feeder roller value", LAUNCHING_FEEDER_VOLTAGE);
                                    SmartDashboard.putNumber("launching launcher roller value", LAUNCHING_LAUNCHER_VOLTAGE);
                                    SmartDashboard.putNumber("spin-up feeder roller value", SPIN_UP_FEEDER_VOLTAGE);

    // set up differential drive class
    drive = new DifferentialDrive(leftLeader, rightLeader);
    
  } 
  public void driveArcade(double xSpeed, double zRotation) {
      drive.arcadeDrive(xSpeed, zRotation);
  }

  public void setIntakeLauncherRoller(double voltage) {
    intakeLauncherRoller.setVoltage(voltage);
  }
  public void setFeederRoller(double voltage) {
    feederRoller.setVoltage(voltage);
  }
  public void stop() {
    feederRoller.set(0);
    
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

