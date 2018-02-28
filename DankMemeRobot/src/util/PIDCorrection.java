package util;

public class PIDCorrection {
	
	private double pValue;
	private double correctionValue;
	//private double newCurrentPoint;
	double error;
	
	public PIDCorrection(double pValue) {
		this.pValue = pValue;
	}
	
	public double getpValue() {
		return pValue;
	}
	
	public void setpValue(double pValue) {
		this.pValue = pValue;
	}
	
	public double calculateCorrection(double setPoint, double currentPoint) {

		error = setPoint - currentPoint;
		
		/*if (setPoint < 0) {
			setPoint += 360;
		}
		if (currentPoint < 0) {
			currentPoint += 360;
		}
		error = Math.abs(setPoint - currentPoint);
		if (error > 180) {
			error = 360 - error;
		}*/
    	correctionValue = Math.abs(error) * pValue;
        return correctionValue;

    }
	
}
