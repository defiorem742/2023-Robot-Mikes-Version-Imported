// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ScoringPositions;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants;
import frc.robot.Constants.PivotConstants;
import frc.robot.commands.Arm.ArmExtend;
import frc.robot.commands.Arm.ArmPID;
import frc.robot.commands.Gripper.GrabReleaseToggle;
import frc.robot.commands.Gripper.ReleasePiece;
import frc.robot.commands.Pivot.PivotPID;
import frc.robot.commands.Turret.TurretPID;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Gripper;
import frc.robot.subsystems.Pivot;
import frc.robot.subsystems.Turret;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ScoreHigh90 extends SequentialCommandGroup {

  
  /** Creates a new ScoreHighPosition when Turret at 90 or 270 degrees. */
  public ScoreHigh90(Arm hiScore90, Pivot pvtHi90, Gripper gripHigh90, Turret turHigh90) {
   
    addCommands(
      new PivotPID(pvtHi90, PivotConstants.PVT_ENC_HIGH_SCORE).withTimeout(1.5),
      Commands.parallel(new TurretPID(turHigh90, 90).withTimeout(1),
      new ArmPID(hiScore90, Constants.ArmConstants.ARM_90_HIGH).withTimeout(2))//,
      //new ReleasePiece(gripHigh90).asProxy()
      );
      
  }
}