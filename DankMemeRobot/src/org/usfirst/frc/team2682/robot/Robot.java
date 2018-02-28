/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2682.robot;

import org.opencv.core.Rect;
import org.usfirst.frc.team2682.robot.commands.DriveByGyro;
import org.usfirst.frc.team2682.robot.commands.DriveToRRTapeCommand;
import org.usfirst.frc.team2682.robot.commands.LLLAutoPos2CommandGroup;
import org.usfirst.frc.team2682.robot.commands.LLLAutoPos3CommandGroup;
import org.usfirst.frc.team2682.robot.commands.RRRAutoPos2CommandGroup;
import org.usfirst.frc.team2682.robot.commands.RRRAutoPos3CommandGroup;
import org.usfirst.frc.team2682.robot.commands.TurnToRRTapeCommand;
import org.usfirst.frc.team2682.robot.subsystems.DriveTrainSystem;
import org.usfirst.frc.team2682.robot.subsystems.ExampleSubsystem;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

	Command m_autonomousCommand;
	
	public static final String USBName = "/dev/ttyUSB0";

	//CameraServer cameraServer = CameraServer.getInstance();
	//UsbCamera camera;
	
	public static DigitalInput isObjectSeen = new DigitalInput(RobotMap.pixyCamDIOPin);
	public static AnalogInput objectX = new AnalogInput(RobotMap.pixyCamAnalogPin);
	public static AnalogInput objectWidth = new AnalogInput(1);
	
	public static int startingPos = 1;
	
	//private final Object imglock = new Object();
	
	public static double centerX = 0.0;
	public static Rect contourRect;
	
	int powercubeX;
	byte[] powerCubeData;
	
	//VisionThread turnDownForWhatVision;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	public static I2C comPort;
	
	@Override
	public void robotInit() {
		oi = new OI();
		SmartDashboard.putNumber("starting position", 1);
		
		comPort = new I2C(I2C.Port.kOnboard, 0x54);
		
		Thread thread = new Thread(() -> {
			while (!Thread.interrupted()) {
				
				try {
					comPort.readOnly(powerCubeData, 2);
//					//powercubeX = Integer.parseInt(powerCubeData);
				} catch (NumberFormatException e) {
					
				}
			}
		});
		thread.start();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		
		//byte[] readBytes = comPort.read(64);
		
		SmartDashboard.putNumber("encoder",drive.getDistance());
		
		SmartDashboard.putNumber("Gyro", drive.getCurrentHeading());
		
		//SmartDashboard.putNumber("powercube x",objectX.getValue());
		System.out.println(powerCubeData[0] >> 8 + powerCubeData[1]);
		
		SmartDashboard.putNumber("powercube width", objectWidth.getValue());
		SmartDashboard.putBoolean("PowerCube seen", isObjectSeen.get());
		
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
		drive.ahrs.reset();
		drive.resetEncoders();
		/*startingPos = (int) SmartDashboard.getNumber("starting position", 1);
		
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		switch (startingPos) {
		case 1:
			break;
		case 2:
			if (gameData.toUpperCase().charAt(0) == 'R' && gameData.toUpperCase().charAt(1) == 'R') {
				m_autonomousCommand = new RRRAutoPos2CommandGroup();
			} else if (gameData.toUpperCase().charAt(0) == 'L' && gameData.toUpperCase().charAt(1) == 'L') {
				m_autonomousCommand = new LLLAutoPos2CommandGroup();
				
			}
			break;
		case 3:
			if (gameData.toUpperCase().charAt(0) == 'R' && gameData.toUpperCase().charAt(1) == 'R') {
				m_autonomousCommand = new RRRAutoPos3CommandGroup();
			} else if (gameData.toUpperCase().charAt(0) == 'L' && gameData.toUpperCase().charAt(1) == 'L') {
				m_autonomousCommand = new LLLAutoPos3CommandGroup();
				
			}
			break;
		}

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		
		m_autonomousCommand = new TurnToRRTapeCommand();

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("encoder",drive.getDistance());
		SmartDashboard.putNumber("correction", DriveByGyro.correction);
	}
	
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
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
}
