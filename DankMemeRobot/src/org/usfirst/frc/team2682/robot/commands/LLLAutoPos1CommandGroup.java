package org.usfirst.frc.team2682.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LLLAutoPos1CommandGroup extends CommandGroup {

    public LLLAutoPos1CommandGroup() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	
<<<<<<< HEAD
    	addSequential(new DriveByGyro(0, .65, 180, false));
    	addSequential(new TurnByGyro(30, .4, 2, false));
    	addSequential(new DriveByGyro(30, .65, 96/2, false));
    	addSequential(new TurnByGyro(0, .4, 2, false));
    	addSequential(new DriveByGyro(0, .6, 20, false));
    	addSequential(new WaitCommand(.5));
    	addSequential(new TurnByGyro(180, .4, 2, false));
=======
    	addSequential(new DriveByGyro(false, 0, .75, 280, false));
>>>>>>> grayson-local
    	
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
