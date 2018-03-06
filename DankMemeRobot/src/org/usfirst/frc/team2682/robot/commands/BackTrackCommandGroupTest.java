package org.usfirst.frc.team2682.robot.commands;

import org.usfirst.frc.team2682.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BackTrackCommandGroupTest extends CommandGroup {

    public BackTrackCommandGroupTest() {
        
    	addSequential(new ResetDisplacementCommand());
    	addSequential(new DriveToRRTapeCommand(.5));
    	addSequential(new WaitCommand(3));
    	addSequential(new DriveToRRTapeCommand(.5));
    	addSequential(new WaitCommand(.5));
    	addSequential(new BackTrackCommand(.65));
    }
}
