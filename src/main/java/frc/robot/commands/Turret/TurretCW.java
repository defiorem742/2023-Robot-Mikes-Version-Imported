// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Turret;
import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
public class TurretCW extends Command {
  private Turret turret2;
  private double speed6;
  /** Creates a new TurretClockwise. */
  public TurretCW(Turret turretcw, double speedcw) {
    this.turret2 = turretcw;
    this.speed6 = speedcw;
    addRequirements(turret2);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    turret2.openRampRate();
    turret2.turretRelease();
    new WaitCommand(0.25);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    turret2.setTurretSpeed(speed6);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret2.turretStop();
    turret2.turretBrake();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
  }