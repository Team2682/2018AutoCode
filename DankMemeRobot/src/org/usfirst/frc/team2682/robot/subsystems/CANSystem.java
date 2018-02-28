package org.usfirst.frc.team2682.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class CANSystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public TalonSRX motor1 = new TalonSRX(1);
	public TalonSRX motor2 = new TalonSRX(2);
	public TalonSRX motor3 = new TalonSRX(3);
	
	//PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	public CANSystem() {
		motor2.follow(motor1);
		motor3.follow(motor1);
		motor2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void turnMotor1(double value) {
    	motor1.set(ControlMode.PercentOutput, value);
    	
    }
    
    public void turnMotor2(double value) {
    	motor2.set(ControlMode.PercentOutput, value);
    }
    
    public void turnMotor3(double value) {
    	motor3.set(ControlMode.PercentOutput, value);
    }
}

