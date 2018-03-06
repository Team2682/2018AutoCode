package org.usfirst.frc.team2682.robot.commands;

import org.usfirst.frc.team2682.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RRRAutoPos3CommandGroup extends CommandGroup {

    public RRRAutoPos3CommandGroup() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

    	addSequential(new DriveByGyro(0, .75, 180, false));
    	addSequential(new TurnByGyro(-30, .4, 2, false));
    	addSequential(new DriveByGyro(-30, .75, 96/2, false));
    	addSequential(new TurnByGyro(0, .4, 2, false));
    	addSequential(new DriveByGyro(0, .75, 20, false));
    	addSequential(new WaitCommand(.5));
    	addSequential(new TurnByGyro(180, .4, 2, false));
    	addSequential(new DriveByGyro(180, .65, 12, false));
    	addSequential(new ResetDisplacementCommand());
    	addSequential(new DriveToRRTapeCommand(.5));
    	addSequential(new WaitCommand(3));
    	addSequential(new TurnToRRTapeCommand());
    	addSequential(new DriveToRRTapeCommand(.5));
    	addSequential(new WaitCommand(.6));
    	addSequential(new BackTrackCommand(.5));
    	
        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
