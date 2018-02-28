package org.usfirst.frc.team2682.robot.commands;

import java.security.Timestamp;
import java.util.Date;

import org.usfirst.frc.team2682.robot.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
		/*} else {
			if (setPoint < 0) { setPoint += 360; }
			if (currentHeading < 0) {
				currentHeading += 360;
			}
			error = setPoint - currentHeading;

			if (setPoint > currentHeading && error <= 180) {
				leftPower -= correction;
				rightPower += correction;
			} else if (setPoint > currentHeading && error > 180) {
				leftPower += correction;
				rightPower -= correction;
			} else if (setPoint < currentHeading && error <= 180) {
				leftPower += correction;
				rightPower -= correction;
			} else {
				leftPower -= correction;
				rightPower += correction;
			}
		}*/
		if (debug) {
			logger.log("Drive By Gyro: SetPoint: " + setPoint + ", Current:" + currentHeading + ", Err:" + error + ", Correction:"
				+ correction + "T.D.:" + targetDistance + ", T.P.:" + targetPulses);
		}
		Robot.drive.tankMove(leftPower, rightPower);

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
