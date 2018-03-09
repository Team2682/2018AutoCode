package util;

public class Misc {
	public static double map(double value, double oldMin, double oldMax, double newMin, double newMax) {
		return (((value-oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin;
	}
}
