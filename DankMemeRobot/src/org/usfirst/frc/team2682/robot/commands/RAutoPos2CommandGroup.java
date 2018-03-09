package org.usfirst.frc.team2682.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RAutoPos2CommandGroup extends CommandGroup {

    public RAutoPos2CommandGroup() {
    	addSequential(new DriveByGyro(false,0, .65, 36, false));
    	addSequential(new TurnByGyro(45, .65, 1, false));
    	addSequential(new DriveByGyro(false,45, .65, 108, false));
    	addSequential(new TurnByGyro(0, .65, 1, false));
    	addSequential(new DriveByGyro(false,0, .65, 36, false));
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
    }
}
