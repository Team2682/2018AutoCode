package org.usfirst.frc.team2682.robot.commands;

import org.usfirst.frc.team2682.robot.*;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveCommand extends Command {

    public DriveCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Robot.drive.disablePid();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double driveStickX = Robot.oi.driveStick.getRawAxis(RobotMap.driveStickX);
    	double driveStickY = Robot.oi.driveStick.getRawAxis(RobotMap.driveStickY);
    	
    	Robot.drive.move(driveStickY, driveStickX);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drive.stop();
    }
}
