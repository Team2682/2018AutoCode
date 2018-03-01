package org.usfirst.frc.team2682.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LRLAutoPos1CommandGroup extends CommandGroup {

    public LRLAutoPos1CommandGroup() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	
    	addSequential(new DriveByGyro(0, .65, 180, false));
    	addSequential(new TurnByGyro(90, .4, 2, false));
    	addSequential(new DriveByGyro(90, .65, 8, false));
    	addSequential(new DriveByGyro(90, -.65, 8, false));
    	addSequential(new TurnByGyro(0, .4, 2, false));
    	addSequential(new DriveByGyro(0, .65, 45, false));
    	addSequential(new DriveByGyro(0, -.65, 10, false));
    	addSequential(new TurnByGyro(90, .4, 2, false));
    	addSequential(new DriveByGyro(90, .65, 80, false));
    	addSequential(new DriveByGyro(90, .4, 20, false));
    	addSequential(new DriveByGyro(90, .65, 80, false));
    	addSequential(new TurnByGyro(0, .4, 2, false));
    	addSequential(new DriveByGyro(0, .65, 8, false));
    	
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
