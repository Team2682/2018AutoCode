/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2682.robot;

import org.usfirst.frc.team2682.robot.commands.DriveByGyro;
import org.usfirst.frc.team2682.robot.commands.LAutoPos2CommandGroup;
import org.usfirst.frc.team2682.robot.commands.LLLAutoPos1CommandGroup;
import org.usfirst.frc.team2682.robot.commands.LLLAutoPos3CommandGroup;
import org.usfirst.frc.team2682.robot.commands.LRLAutoPos1CommandGroup;
import org.usfirst.frc.team2682.robot.commands.LRLAutoPos3CommandGroup;
import org.usfirst.frc.team2682.robot.commands.Pos1And3AutoLineCommandGroup;
import org.usfirst.frc.team2682.robot.commands.RAutoPos2CommandGroup;
import org.usfirst.frc.team2682.robot.commands.RLRAutoPos1CommandGroup;
import org.usfirst.frc.team2682.robot.commands.RLRAutoPos3CommandGroup;
import org.usfirst.frc.team2682.robot.commands.RRRAutoPos1CommandGroup;
import org.usfirst.frc.team2682.robot.commands.RRRAutoPos3CommandGroup;
import org.usfirst.frc.team2682.robot.subsystems.DriveTrainSystem;
import org.usfirst.frc.team2682.robot.subsystems.ExampleSubsystem;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import util.Misc;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static final ExampleSubsystem kExampleSubsystem
			= new ExampleSubsystem();
	public static final DriveTrainSystem drive = new DriveTrainSystem();
	public static OI oi;

	Command autonomousCommand;
	
	public static final String USBName = "/dev/ttyUSB0";

	//CameraServer cameraServer = CameraServer.getInstance();
	//UsbCamera camera;
	
	public static DigitalInput isObjectSeen = new DigitalInput(RobotMap.pixyCamDIOPin);
	public static AnalogInput objectX = new AnalogInput(RobotMap.pixyCamAnalogPin);
	
	public static AnalogInput ultraSonicSensor = new AnalogInput(1);
	
	public static int startingPos = 1;
	
	static double backTrackEncoder;
	static double backTrackAngle;
	
	static double backTrackEncoder2;
	static double backTrackAngle2;
	
	double[] backTrackVector1 = {0,0};
	double[] backTrackFinalVector = {0,0};
	
	public static double magnitude = 0.0;
	public static double angle = 0.0;
	
	public static double displaceXMeters;
	public static double displaceYMeters;
	
	boolean added = false;

	int powercubeX;
	byte[] powerCubeData;
	
	Accelerometer accel = new BuiltInAccelerometer(Accelerometer.Range.k4G);
	
	//VisionThread turnDownForWhatVision;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	public static I2C comPort;
	
	@Override
	public void robotInit() {
		oi = new OI();

		SmartDashboard.putNumber("starting position", 3);
		
		comPort = new I2C(I2C.Port.kOnboard, 0x54);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		drive.ahrs.resetDisplacement();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		
		//byte[] readBytes = comPort.read(64);
		
		SmartDashboard.putNumber("encoder",drive.getDistance());
		
		SmartDashboard.putNumber("Gyro Yaw", drive.getCurrentHeading());
		
		//SmartDashboard.putNumber("powercube x",objectX.getValue());

		SmartDashboard.putBoolean("PowerCube seen", isObjectSeen.get());
		//int pCubeX = PixyCamUtils.getLargestBlock(comPort).x;
//		SmartDashboard.putNumber("block x", Misc.map(objectX.getVoltage(),0,3.3,0,320));
//		SmartDashboard.putNumber("block distance", Misc.map(ultraSonicSensor.getValue(),0,90,0,12));
		
		SmartDashboard.putNumber("encoder 1", getBackTrackEncoder());
		SmartDashboard.putNumber("displaceX", accel.getX());
		
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit(){
		drive.ahrs.resetDisplacement();
		drive.ahrs.reset();
		drive.resetEncoders();
		startingPos = (int) SmartDashboard.getNumber("starting position", 1);
		
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		switch (startingPos) {
		case 1:

			if (gameData.toUpperCase().charAt(0) == 'R' && gameData.toUpperCase().charAt(1) == 'R') {
				autonomousCommand = new RRRAutoPos1CommandGroup();
			} else if (gameData.toUpperCase().charAt(0) == 'L' && gameData.toUpperCase().charAt(1) == 'L') {
				autonomousCommand = new LLLAutoPos1CommandGroup();
			} else if (gameData.toUpperCase().charAt(0) == 'R' && gameData.toUpperCase().charAt(1) == 'L') {
				autonomousCommand = new RLRAutoPos1CommandGroup();
			} else if (gameData.toUpperCase().charAt(0) == 'L' && gameData.toUpperCase().charAt(1) == 'R') {
				autonomousCommand = new LRLAutoPos1CommandGroup();
			}
			break;
		case 2:
			if (gameData.toUpperCase().charAt(0) == 'R') {
				autonomousCommand = new RAutoPos2CommandGroup();
			} else if (gameData.toUpperCase().charAt(0) == 'L') {
				autonomousCommand = new LAutoPos2CommandGroup();
				
			}
			break;
		case 3:
			if (gameData.toUpperCase().charAt(0) == 'R' && gameData.toUpperCase().charAt(1) == 'R') {
				autonomousCommand = new RRRAutoPos3CommandGroup();
			} else if (gameData.toUpperCase().charAt(0) == 'L' && gameData.toUpperCase().charAt(1) == 'L') {
				autonomousCommand = new LLLAutoPos3CommandGroup();
			} else if (gameData.toUpperCase().charAt(0) == 'R' && gameData.toUpperCase().charAt(1) == 'L') {
				autonomousCommand = new RLRAutoPos3CommandGroup();
			} else if (gameData.toUpperCase().charAt(0) == 'L' && gameData.toUpperCase().charAt(1) == 'R') {
				autonomousCommand = new LRLAutoPos3CommandGroup();
			}
			break;
		default:
			autonomousCommand = new Pos1And3AutoLineCommandGroup();
		}

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
    	Robot.displaceXMeters = Robot.drive.ahrs.getDisplacementX() / .02670288;
    	Robot.displaceXMeters = Robot.drive.ahrs.getDisplacementY() / .02670288;
		//SmartDashboard.putNumber("encoder backtrack", backTrackEncoder);
		//SmartDashboard.putNumber("angle backtrack", backTrackAngle);
		SmartDashboard.putNumber("encoder",drive.getDistance());
		SmartDashboard.putNumber("correction", DriveByGyro.correction);
	}
	
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Encoder count", drive.getDistance());
		
		/*SmartDashboard.putString("motor1", CAN_SYSTEM.motor1.getOutputCurrent() + ":" + CAN_SYSTEM.motor1.getMotorOutputVoltage());
		SmartDashboard.putString("motor2", CAN_SYSTEM.motor2.getOutputCurrent() + ":" + CAN_SYSTEM.motor2.getMotorOutputVoltage());
		SmartDashboard.putString("motor3", CAN_SYSTEM.motor3.getOutputCurrent() + ":" + CAN_SYSTEM.motor3.getMotorOutputVoltage());*/
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
	}

	public static double getBackTrackEncoder() {
		return backTrackEncoder;
	}

	public static void setBackTrackEncoder(double backTrackEncoder) {
		Robot.backTrackEncoder = backTrackEncoder;
	}

	public static double getBackTrackAngle() {
		return backTrackAngle;
	}

	public static void setBackTrackAngle(double backTrackAngle) {
		Robot.backTrackAngle = backTrackAngle;
	}

	public static double getBackTrackEncoder2() {
		return backTrackEncoder2;
	}

	public static void setBackTrackEncoder2(double backTrackEncoder2) {
		Robot.backTrackEncoder2 = backTrackEncoder2;
	}

	public static double getBackTrackAngle2() {
		return backTrackAngle2;
	}

	public static void setBackTrackAngle2(double backTrackAngle2) {
		Robot.backTrackAngle2 = backTrackAngle2;
	}
	
	
}
