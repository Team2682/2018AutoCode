package org.usfirst.frc.team2682.robot.commands;

import org.usfirst.frc.team2682.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import util.Misc;

/**
 *
 */
public class TurnToRRTapeCommand extends Command {

	double basePower;
	int powercubeX;
	
    public TurnToRRTapeCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	powercubeX = (int) Misc.map(Robot.objectX.getVoltage(),0,3.3,0,320);
		
		if (powercubeX > 170) {
			Robot.drive.tankMove(-.4, .4);
		} else if (powercubeX < 1650) {
			Robot.drive.tankMove(.4, -.4);
		} else {
			Robot.drive.stop();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //return Misc.map(Robot.ultraSonicSensor.getValue(),0,90,0,12) <= 14;
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
