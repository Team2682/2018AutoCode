package org.usfirst.frc.team2682.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RRRAutoPos2CommandGroup extends CommandGroup {

	//public double currentPoint = 0;
	
    public RRRAutoPos2CommandGroup() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	
    	addSequential(new DriveByGyro(0, .6, 20, false));
    	addSequential(new TurnByGyro(40, .4, 2, false));
    	addSequential(new DriveByGyro(40, .6, 93, false));
    	addSequential(new TurnByGyro(90, .4, 2, false));
    	addSequential(new DriveByGyro(90, .6, 36, false));
    	addSequential(new TurnByGyro(0, .4, 2, false));
    	addSequential(new DriveByGyro(0, .65, 156/2, false));
    	addSequential(new TurnByGyro(-20, .4, 2, false));
    	addSequential(new DriveByGyro(-20, .65, 96/2, false));
    	addSequential(new TurnByGyro(0, .4, 2, false));
    	addSequential(new DriveByGyro(0, .6, 20, false));
    	addSequential(new WaitCommand(.5));
    	addSequential(new TurnByGyro(180, .4, 2, false));
 

    	/*addSequential(new DriveByGyro(currentPoint, .4, 80));
    	addSequential(new TurnByGyro(179, .4, 1));

    	addSequential(new DriveByGyro(179, .4, 130));
    	addSequential(new TurnByGyro(90, .4, 1));
    	
    	addSequential(new DriveByGyro(90, .4, 80));
    	addSequential(new TurnByGyro(0, .4, 1));*/
    	
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
