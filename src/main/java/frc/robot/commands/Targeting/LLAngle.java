// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Targeting;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drive;
import java.lang.Math;

import edu.wpi.first.wpilibj2.command.Command;

public class LLAngle extends Command {
  private double kX = 0.017;  //0.005??
  private double tv, distX, errorX;
  private Drive drive10;
  private double pipeline10, cameraXoffset;

  public LLAngle(Drive a_drive, double a_pipeline) {
    //public LLAngle(Drive passed_drive, Limelight lime, double m_pipeline) {
      this.drive10 = a_drive;
      this.pipeline10 = a_pipeline;
     // this.limelight = lime;
      addRequirements(drive10);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    SmartDashboard.putNumber("LLangle init", pipeline10);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipeline10);
    cameraXoffset = 4; //need to figure out

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
    tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    distX = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    errorX = distX - cameraXoffset;////
//TRY FOR errorX
     // double errorY = NetworkTableInstance.getDefault().getTable("limelight").
       // getIntegerTopic("targetpose_cameraspace").subscribe(new double[]{}).get()[2];  //or 0?
    if(tv==1) {
      if (Math.abs(errorX)>0.5){
      //double x = (errorX-160)/320;
      //Establishes a minimum error in the x axis 
      SmartDashboard.putNumber("Adjust Angle, ErrorX is:", errorX);
        double steeringAdjust = kX * errorX;
        drive10.setLeftSpeed(steeringAdjust);
        drive10.setRightSpeed(-steeringAdjust); 
        }   
      else{
      SmartDashboard.putNumber("No Shoot Target", tv);
      }
     }
    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive10.stop();
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // return false;

    if(tv==1 && Math.abs(errorX)<=2){
      SmartDashboard.putBoolean("LLAngle isFinished:", true);
      return true;
      }   
      else if(tv==1 && Math.abs(errorX)>2){
        return false;
      }
      else
      {
      SmartDashboard.putNumber("No Shoot Target", tv);
      return true;
      }
}

}