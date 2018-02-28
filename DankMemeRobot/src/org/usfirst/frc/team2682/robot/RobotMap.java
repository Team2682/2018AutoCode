/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2682.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	//public static final boolean isContinuousGyro = true;
	
	public static final String logDirectory = "/home/lvuser/";
	public static final String logFileName = "log.txt";
	
	//1 Foot on encoder:
	public static final double ENCODER_FOOT = 210;
	public static final double PULSES_PER_INCH = ENCODER_FOOT/12;
	
	/*Alternate method for finding encoder.getDistance()
	public static double wheelDiameter = 6;
	public static double circumference = Math.PI * wheelDiameter;
	public static double pulsesPerRevolution = 1400;
	public static double inputGearTeeth = 40;
	public static double outputGearTeeth = 40;
	public static double gearRatio = outputGearTeeth / inputGearTeeth;
	public static double pulsesPerInch = (pulsesPerRevolution/circumference) * gearRatio;
	public static double inchesPerPulse = (circumference/pulsesPerRevolution) * gearRatio;*/
	
	//Encoder Channels
	public static int leftEncoderChannelA = 0;
	public static int leftEncoderChannelB = 1;
	public static int rightEncoderChannelA = 6;
	public static int rightEncoderChannelB = 7;
	
	//The motor channels (hooked to Y-cables)
	public static int leftDriveMotor = 0;
	public static int rightDriveMotor = 1;
	
	//Joystick channel
	public static int driveStickChannel = 0;
	
	//Joystick axis channels
	public static int driveStickX = 0;
	public static int driveStickY = 1;
	
	//PixyCam pins
	public static int pixyCamDIOPin = 0;
	public static int pixyCamAnalogPin = 0;
}
