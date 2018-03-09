package org.usfirst.frc.team2682.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RLRAutoPos3CommandGroup extends CommandGroup {

    public RLRAutoPos3CommandGroup() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

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
    	
    	
    	
    	addSequential(new DriveByGyro(false,0, .75, 225, false));
    	addSequential(new TurnByGyro(-90, .5, 1.5, false));
    	addSequential(new DriveByGyro(false,-90, .75, 78, false));
    	addSequential(new DriveByGyro(false,-90, .65, 78/2, false));
    	addSequential(new DriveByGyro(false,-90, .75, 70, false));
    	addSequential(new TurnByGyro(0, .5, 1.5, false));
    	addSequential(new DriveByGyro(false,0, .75, 10, false));
    	addSequential(new WaitCommand(1));
    	addSequential(new DriveByGyro(false,0, -.75, 10, false));
    	addSequential(new TurnByGyro(90, .5, 1.5, false));
    	addSequential(new DriveByGyro(false,90, .75, 60, false));
    	addSequential(new DriveByGyro(false,90, .65, 78/2, false));
    	addSequential(new DriveByGyro(false,90, .75, 45, false));
    	addSequential(new TurnByGyro(180, .5, 1.5, false));
    	addSequential(new DriveToRRTapeCommand(false,.5));
    }
}
