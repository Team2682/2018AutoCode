package org.usfirst.frc.team2682.robot.subsystems;

import org.usfirst.frc.team2682.robot.RobotMap;
import org.usfirst.frc.team2682.robot.commands.DriveCommand;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrainSystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	Talon leftDrive = new Talon(RobotMap.leftDriveMotor);
	Talon rightDrive = new Talon(RobotMap.rightDriveMotor);
	
	Encoder leftEncoder = new Encoder(RobotMap.leftEncoderChannelA, RobotMap.leftEncoderChannelB, false, EncodingType.k4X);
	//Encoder rightEncoder = new Encoder(RobotMap.rightEncoderChannelA, RobotMap.rightEncoderChannelB, false, EncodingType.k4X);
	
	public AHRS ahrs = new AHRS(I2C.Port.kOnboard);
	//public ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	
	DifferentialDrive drive2 = new DifferentialDrive(leftDrive, rightDrive);
	//ButterflyDrive drive = new ButterflyDrive(leftDrive, rightDrive, null);
	
	//public PIDController pidControllerLeft = new PIDController(.061, 0.005, 0, ahrs, leftDrive);
	//public PIDController pidControllerRight = new PIDController(.061, 0.005, 0, ahrs, rightDrive);
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveCommand());
    }

    public void move(double moveValue, double rotateValue) {
		drive2.arcadeDrive(moveValue, rotateValue);
	}
    
    public void tankMove(double leftMoveValue, double rightMoveValue) {
    	drive2.tankDrive(-leftMoveValue, -rightMoveValue);

    	SmartDashboard.putNumber("Gyro Yaw", getCurrentHeading());
    }
    
    public void stop() {
    	drive2.arcadeDrive(0.0, 0.0);
    }
    
    public void resetGyro() {
    	ahrs.reset();
    }
    
    public void resetEncoders() {
    	leftEncoder.reset();
    	//rightEncoder.reset();
    }
    
    public double getDistance() {
    	return leftEncoder.getDistance();
    }
    
    /*public double feetToEncoder(double feet) {
    	return feet/RobotMap.ENCODER_FOOT;
    }*/
    
    public double encoderToFeet(double targetFeet) {
    	return RobotMap.ENCODER_FOOT * targetFeet;
    }
    
    /*public void setPidSetPoint(double setPoint) {
    	pidControllerLeft.setSetpoint(setPoint);
    	pidControllerLeft.setSetpoint(setPoint);
    }
    
    public void disablePid() {
    	pidControllerLeft.disable();
    	pidControllerRight.disable();
    }*/
    
    public double getCurrentHeading() {
    	return ahrs.getAngle() - 5.2079;
    }
    
}

