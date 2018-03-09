package org.usfirst.frc.team2682.robot.commands;

import org.usfirst.frc.team2682.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import util.PIDCorrection;

/**
 *
 */
public class BackTrackCommand extends Command {

	PIDCorrection pidCorrection = new PIDCorrection(.006);
	double basePower;
	
	double xDist;
	double yDist;
	
    public BackTrackCommand(double basePower) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.basePower = basePower;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.resetEncoders();
    	xDist = 0 -Robot.drive.ahrs.getDisplacementX();
    	yDist = 0 -Robot.drive.ahrs.getDisplacementY();
    	Robot.angle = Math.toDegrees(Math.atan2(yDist, xDist));
    	Robot.magnitude = Math.sqrt(Math.pow(Robot.drive.ahrs.getDisplacementX(),2) + Math.pow(Robot.drive.ahrs.getDisplacementY(),2));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double currentHeading = Robot.drive.getCurrentHeading();
		double correction = pidCorrection.calculateCorrection(Robot.angle, currentHeading);
		// correction = Math.abs(correction);

		double leftPower = basePower;
		double rightPower = basePower;
			
		if (correction > .25) {
			correction = .25;
		}
		
		if (Robot.angle > currentHeading) {
			leftPower -= correction;
			rightPower += correction;
		} else {
			leftPower += correction;
			rightPower -= correction;
		}
		
		
		
		Robot.drive.tankMove(leftPower, rightPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(Robot.drive.getDistance()) >= Robot.magnitude;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
