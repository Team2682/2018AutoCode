package drive;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/*
 * Created by Grayson Amendt, 1/13/2018 (With code from WPILib)
 * Feel free to use
 * Don't claim this as your own please
 */

//BTW some of this is stolen from the WPILib drive methods for optimization and safety purposes
public class ButterflyDrive extends RobotDriveBase {
	//The speed controllers for all the motors
	//Works normally for 3 motors
	//Requires Y-Cables for 5 motors
	SpeedController leftMotor;
	SpeedController rightMotor;
	SpeedController middleMotor;

	//The 4 solenoids used in most butterfly drive designs
	Solenoid solenoidWheel1;
	Solenoid solenoidWheel2;
	Solenoid solenoidWheel3;
	Solenoid solenoidWheel4;
	
	//The default solenoid wheel
	//true - High Grip
	//false - OmniWheel
	boolean defaultSolenoidWheel;
	
	boolean m_reported = false;

	//Constructor for motors only
	public ButterflyDrive(SpeedController leftMotor, SpeedController rightMotor, SpeedController middleMotor) {
		//Set the motors
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.middleMotor = middleMotor;
		
		//Not really sure what this is used for, but I'm keeping it in here just in case (Was in the DifferetialDrive class)
		addChild(middleMotor);
		addChild(rightMotor);
		addChild(leftMotor);
	}

	//Constructor for motors and solenoids only
	public ButterflyDrive(SpeedController leftMotor, SpeedController rightMotor, SpeedController middleMotor,
			Solenoid solenoidWheel1, Solenoid solenoidWheel2, Solenoid solenoidWheel3, Solenoid solenoidWheel4) {
		//Set the motors
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.middleMotor = middleMotor;
		
		//Set the solenoids
		this.solenoidWheel1 = solenoidWheel1;
		this.solenoidWheel2 = solenoidWheel2;
		this.solenoidWheel3 = solenoidWheel3;
		this.solenoidWheel4 = solenoidWheel4;
		
		//Set the default solenoid wheel to a default value of High Grip
		this.defaultSolenoidWheel = true;

		//Not really sure what this is used for, but I'm keeping it in here just in case (Was in the DifferetialDrive class)
		addChild(middleMotor);
		addChild(rightMotor);
		addChild(leftMotor);
		
		addChild(solenoidWheel1);
		addChild(solenoidWheel2);
		addChild(solenoidWheel3);
		addChild(solenoidWheel4);
	}
	
	//Constructor for motors, solenoids, and the default wheel
	public ButterflyDrive(SpeedController leftMotor, SpeedController rightMotor, SpeedController middleMotor,
			Solenoid solenoidWheel1, Solenoid solenoidWheel2, Solenoid solenoidWheel3, Solenoid solenoidWheel4, boolean defaultSolenoidWheel) {
		//Set the motors
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.middleMotor = middleMotor;
		
		//Set the solenoids
		this.solenoidWheel1 = solenoidWheel1;
		this.solenoidWheel2 = solenoidWheel2;
		this.solenoidWheel3 = solenoidWheel3;
		this.solenoidWheel4 = solenoidWheel4;
		
		//Set the default wheel
		this.defaultSolenoidWheel = defaultSolenoidWheel;
		
		//Not really sure what this is used for, but I'm keeping it in here just in case (Was in the DifferetialDrive class)
		addChild(middleMotor);
		addChild(rightMotor);
		addChild(leftMotor);
		
		addChild(solenoidWheel1);
		addChild(solenoidWheel2);
		addChild(solenoidWheel3);
		addChild(solenoidWheel4);
	}

	//Allows driving horizontally only
	public void driveHorizontal(double speed) {
		//set solenoids if the design has them
		if (solenoidWheel1 != null) {
			solenoidWheel1.set(defaultSolenoidWheel);
			solenoidWheel1.set(defaultSolenoidWheel);
			solenoidWheel1.set(defaultSolenoidWheel);
			solenoidWheel4.set(defaultSolenoidWheel);
		}
		// limit and apply deadband for speed
		speed = limit(speed);
		speed = applyDeadband(speed, m_deadband);

		// square input for finer control
		speed = Math.copySign(speed * speed, speed);

		middleMotor.set(limit(speed) * m_maxOutput);
		
		m_safetyHelper.feed();
	}

	//Allows driving in tank mode only
	public void tankDrive(double leftSpeed, double rightSpeed) {
		//set solenoids if the design has them
		if (solenoidWheel1 != null) {
			solenoidWheel1.set(!defaultSolenoidWheel);
			solenoidWheel1.set(!defaultSolenoidWheel);
			solenoidWheel1.set(!defaultSolenoidWheel);
			solenoidWheel4.set(!defaultSolenoidWheel);
		}
		
		// limit and apply deadbands
		leftSpeed = limit(leftSpeed);
		leftSpeed = applyDeadband(leftSpeed, m_deadband);

		// limit and apply deadbands
		rightSpeed = limit(rightSpeed);
		rightSpeed = applyDeadband(leftSpeed, m_deadband);

		leftMotor.set(limit(leftSpeed) * m_maxOutput);
		rightMotor.set(-limit(rightSpeed) * m_maxOutput);
		
		m_safetyHelper.feed();
	}

	//Allows driving in arcade mode only
	public void arcadeDrive(double moveValue, double rotateValue) {
		if (!m_reported) {
		      HAL.report(tResourceType.kResourceType_RobotDrive, 2, tInstances.kRobotDrive_ArcadeStandard);
		      m_reported = true;
		}
		
		//set solenoids if the design has them
		if (solenoidWheel1 != null) {
			solenoidWheel1.set(!defaultSolenoidWheel);
			solenoidWheel1.set(!defaultSolenoidWheel);
			solenoidWheel1.set(!defaultSolenoidWheel);
			solenoidWheel4.set(!defaultSolenoidWheel);
		}
		
		// limit and apply deadband to the move value
		moveValue = limit(moveValue);
		moveValue = applyDeadband(moveValue, m_deadband);

		// limit and apply deadband to the rotate value;
		rotateValue = limit(rotateValue);
		rotateValue = applyDeadband(rotateValue, m_deadband);

		// square the inputs for finer control
		moveValue = Math.copySign(moveValue * moveValue, moveValue);
		rotateValue = Math.copySign(rotateValue * rotateValue, rotateValue);

		// set outputs
		double outputMoveL = moveValue - rotateValue;
		double outputMoveR = moveValue + rotateValue;

		// move the robot
		leftMotor.set(limit(outputMoveL));
		rightMotor.set(-limit(outputMoveR));
		
		m_safetyHelper.feed();
	}
	
	//Allows driving in every direction, and turning is supported (2 joysticks recommended)
	public void omniDirectionalDrive(double horizontalMove, double forwardMove, double rotateValue) {
		//Just uses 2 joysticks, one for driveHorizontal, one for arcadeDrive
		driveHorizontal(horizontalMove);
		arcadeDrive(forwardMove, rotateValue);
	}
	
	//Allows driving on the xy plane, turning is not supported
	public void xyDrive(double horizontalMove, double forwardMove) {
		//Set solenoids if design has them
		if (solenoidWheel1 != null) {
			solenoidWheel1.set(defaultSolenoidWheel);
			solenoidWheel1.set(defaultSolenoidWheel);
			solenoidWheel1.set(defaultSolenoidWheel);
			solenoidWheel4.set(defaultSolenoidWheel);
		}
		//limit and apply deadbands to movement values
		horizontalMove = limit(horizontalMove);
		applyDeadband(horizontalMove, m_deadband);
		
		forwardMove = limit(forwardMove);
		applyDeadband(forwardMove, m_deadband);
		
		//square values for precise movement
		horizontalMove = Math.copySign(horizontalMove * horizontalMove, horizontalMove);
		forwardMove = Math.copySign(forwardMove * forwardMove, forwardMove);
		
		//Move the robot
		leftMotor.set(limit(forwardMove) * m_maxOutput);
		rightMotor.set(-limit(forwardMove) * m_maxOutput);
		
		middleMotor.set(limit(horizontalMove) * m_maxOutput);
		
		m_safetyHelper.feed();
	}

	//Values for SmartDashboard
	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("Butterfly Drive");
		//Add get and set inputs in SmartDashoard for each value
		//builder.addDoubleProperty("Middle Motor Speed", middleMotor::get, middleMotor::set);
		builder.addDoubleProperty("Left Motor Speed", leftMotor::get, leftMotor::set);
		//I don't know why this uses lambda, but in DifferentialDrive, it does... So we are going with it
		builder.addDoubleProperty("Right Motor Speed", () -> -rightMotor.get(), x -> rightMotor.set(-x));
	}

	//Stops all the motors
	@Override
	public void stopMotor() {
		leftMotor.stopMotor();
		rightMotor.stopMotor();
		//middleMotor.stopMotor();
		m_safetyHelper.feed();
	}

	//Description for... something
	@Override
	public String getDescription() {
		return "Butterfly Drive";
	}
	
	//Get the default solenoid wheel
	public boolean isDefaultSolenoidWheel() {
		return defaultSolenoidWheel;
	}

	//Set the default solenoid wheel
	public void setDefaultSolenoidWheel(boolean defaultSolenoidWheel) {
		this.defaultSolenoidWheel = defaultSolenoidWheel;
	}
	
}
