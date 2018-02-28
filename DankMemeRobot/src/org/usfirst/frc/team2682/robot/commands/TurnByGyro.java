package org.usfirst.frc.team2682.robot.commands;

import org.usfirst.frc.team2682.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import util.PIDCorrection;
import util.RoboRioLogger;

/**
 *
 */
public class TurnByGyro extends Command {

	PIDCorrection pidCorrection = new PIDCorrection(0.006);

	double setPoint;
	double basePower;
	double threshold;

	double correction;
	
	boolean isStabalizing;

	RoboRioLogger logger;
	boolean debug = false;

	Timer timer = new Timer();

	public TurnByGyro(double setAngle, double basePower, double threshold, boolean debug) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this.setPoint = setAngle;
		this.basePower = basePower;
		this.threshold = threshold;
		if (debug) {
			this.logger = new RoboRioLogger();	
		}
		
		this.debug = debug;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		timer.reset();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double currentHeading = Robot.drive.getCurrentHeading();
    	double correction = pidCorrection.calculateCorrection(setPoint, currentHeading);
    	double leftPower;
    	double rightPower;
    	if (correction > .25) {
    		correction = .25;
    	}
    	
    	if (setPoint < currentHeading)
    	{
    		leftPower = -(basePower + correction);
    		rightPower = (basePower + correction);
    	}
    	else
    	{
    		leftPower = (basePower + correction);
    		rightPower = -(basePower + correction);
    	}
		Robot.drive.tankMove(-leftPower, -rightPower);

		//logger.flush();
		if (!isStabalizing){
    		if (Robot.drive.getCurrentHeading() <= setPoint + threshold && Robot.drive.getCurrentHeading() >= setPoint - threshold) {
    			timer.start();
    			isStabalizing = true;
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (isStabalizing && (timer.get() > .25)) {
    		return true;
    	}
    	else {
    		return false;
    	}
}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drive.stop();
		if (debug) {
			logger.flush();
			logger.close();
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.drive.stop();
		if (debug) {
			logger.flush();
			logger.close();
		}
	}
}
