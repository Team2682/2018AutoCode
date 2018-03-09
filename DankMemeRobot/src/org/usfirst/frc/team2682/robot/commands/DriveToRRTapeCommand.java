package org.usfirst.frc.team2682.robot.commands;

import org.usfirst.frc.team2682.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import util.Misc;

/**
 *
 */
public class DriveToRRTapeCommand extends Command {

    double encoderValue;

    double totalEncoderValue;

    double vectorComponentX;

    double vectorComponentY;

    double distance;
	
	double basePower;
	double correction = 0.0;
	public static double setPoint = 0;
	double error = 0;
	
	public double leftPower = basePower;
	public double rightPower = basePower;
	
	boolean secondOrNot;
	
    public DriveToRRTapeCommand(boolean secondOrNot, double basePower) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	this.basePower = basePower;
    	this.secondOrNot = secondOrNot;
    	//Robot.drive.resetGyro();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double currentPulses;
    	
    	int powercubeX = (int) Misc.map(Robot.objectX.getVoltage(),0,3.3,0,320);
    		leftPower = basePower;
    		rightPower = basePower;
    		if (powercubeX >= 157 && powercubeX <= 162) {
    			leftPower = basePower;
    			rightPower = basePower;
    		} else if (powercubeX < 157) {
    			leftPower += .075;
    			rightPower -= .075;
    		} else if (powercubeX > 162) {
    			leftPower -= .075;
    			rightPower += .075;
    		}
    		Robot.drive.tankMove(leftPower, rightPower);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Misc.map(Robot.ultraSonicSensor.getValue(),0,90,0,12) <= 20;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (!secondOrNot) {
    		Robot.setBackTrackEncoder(Robot.drive.getDistance());
    		Robot.setBackTrackAngle(Robot.drive.getCurrentHeading());
    	} else {
    		Robot.setBackTrackEncoder2(Robot.drive.getDistance());
    		Robot.setBackTrackAngle2(Robot.drive.getCurrentHeading());
    	}
    	Robot.drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	if (!secondOrNot) {
    		Robot.setBackTrackEncoder(Robot.drive.getDistance());
    		Robot.setBackTrackAngle(Robot.drive.getCurrentHeading());
    	} else {
    		Robot.setBackTrackEncoder2(Robot.drive.getDistance());
    		Robot.setBackTrackAngle2(Robot.drive.getCurrentHeading());
    	}
    	Robot.drive.stop();
    }
}
