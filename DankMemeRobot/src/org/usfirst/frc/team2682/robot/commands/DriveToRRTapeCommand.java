package org.usfirst.frc.team2682.robot.commands;

import org.usfirst.frc.team2682.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import util.PIDCorrection;

/**
 *
 */
public class DriveToRRTapeCommand extends Command {

	static double basePower;
	double correction = 0.0;
	public static double setPoint = 0;
	double error = 0;
	
	public static double leftPower = basePower;
	public static double rightPower = basePower;
	
    public DriveToRRTapeCommand(double basePower) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	this.basePower = basePower;
    	Robot.drive.resetGyro();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.contourRect.width >= 200) {
    		Robot.drive.stop();
    	}
    	else {
    		leftPower = basePower;
    		rightPower = basePower;
    		if (Robot.centerX >= 157 && Robot.centerX <= 162) {
    			
    		} else if (Robot.centerX > 157) {
    			leftPower -= .075;
    			rightPower += .075;
    		} else if (Robot.centerX < 162) {
    			leftPower += .075;
    			rightPower -= .075;
    		}
    		Robot.drive.tankMove(leftPower, rightPower);
    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
