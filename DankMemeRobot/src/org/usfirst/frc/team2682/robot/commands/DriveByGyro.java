package org.usfirst.frc.team2682.robot.commands;

import org.usfirst.frc.team2682.robot.Robot;
import org.usfirst.frc.team2682.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import util.PIDCorrection;
import util.RoboRioLogger;

/**
 *
 */
public class DriveByGyro extends Command {

	PIDCorrection pidCorrection = new PIDCorrection(0.006);

	double setPoint;
	double basePower;
	double targetDistance;
	double targetPulses;

	public static double correction;
	
	RoboRioLogger logger;
	boolean debug = false;
	
	Timer timer = new Timer();
	
	boolean outputBackTrack = false;

	public DriveByGyro(boolean backTracking, double setPoint, double basePower, double targetPulses, boolean debug) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		this.setPoint = setPoint;
		this.basePower = basePower;
		if (!backTracking)
			this.targetPulses = targetDistance * RobotMap.PULSES_PER_INCH;
		else
			this.targetPulses = targetPulses;
		if (debug) {
			this.logger = new RoboRioLogger();	
		}
		this.debug = debug;
	}
	
	public DriveByGyro(double setPoint, double basePower, double targetDistance, boolean debug) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		this.setPoint = setPoint;
		this.basePower = basePower;
		this.targetDistance = targetDistance;
		this.targetPulses = targetDistance * RobotMap.PULSES_PER_INCH;
		if (debug) {
			this.logger = new RoboRioLogger();	
		}
		this.debug = debug;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drive.resetEncoders();
		
		timer.reset();
		timer.start();
		// Robot.drive.resetGyro();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double error;
		double currentHeading = Robot.drive.getCurrentHeading();
		correction = pidCorrection.calculateCorrection(setPoint, currentHeading);
		// correction = Math.abs(correction);

		double leftPower = basePower;
		double rightPower = basePower;
			
		if (correction > .25) {
			correction = .25;
		}
		
		error = setPoint - currentHeading;
		if (setPoint > currentHeading) {
			leftPower -= correction;
			rightPower += correction;
		} else {
			leftPower += correction;
			rightPower -= correction;
		}
		Robot.drive.tankMove(leftPower, rightPower);
		//System.out.println("thing 2" + setPoint);

		//logger.flush();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Math.abs(Robot.drive.getDistance()) >= targetPulses;
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
