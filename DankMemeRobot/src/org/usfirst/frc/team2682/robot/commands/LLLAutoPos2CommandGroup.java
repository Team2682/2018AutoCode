package org.usfirst.frc.team2682.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LLLAutoPos2CommandGroup extends CommandGroup {

    public LLLAutoPos2CommandGroup() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

    	addSequential(new DriveByGyro(0, .65, 20, false));
    	addSequential(new TurnByGyro(-45, .4, 2, false));
    	addSequential(new DriveByGyro(-45, .65, 80, false));
    	addSequential(new TurnByGyro(-90, .4, 2, false));
    	addSequential(new DriveByGyro(-90, .65, 36, false));
    	addSequential(new TurnByGyro(0, .4, 2, false));
    	addSequential(new DriveByGyro(0, .65, 156/2, false));
    	addSequential(new TurnByGyro(20, .4, 2, false));
    	addSequential(new DriveByGyro(20, .65, 96/2, false));
    	addSequential(new TurnByGyro(0, .4, 2, false));
    	addSequential(new DriveByGyro(0, .6, 40, false));
    	addSequential(new WaitCommand(.5));
    	addSequential(new TurnByGyro(180, .4, 2, false));
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
