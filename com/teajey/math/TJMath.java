package com.teajey.math;

public class TJMath {
	public static final double TAU = Math.PI * 2;
	
	public static double nanoSecondsToSeconds(long nanoSeconds) {
		return (double)nanoSeconds / 1_000_000_000;
	}
	
	public static long secondsToNanoseconds(double seconds) {
		return (long)(seconds * 1_000_000_000);
	}
	
	public static double random(double min, double max) {
		return min + Math.random() * (max - min);
	}
	
	public static double random(double max) {
		return Math.random() * max;
	}
	
	public static double map(double input, double originalRangeMin, double originalRangeMax, double newRangeMin, double newRangeMax) {
		return (input - originalRangeMin) / (originalRangeMax - originalRangeMin) * (newRangeMax - newRangeMin) + newRangeMin;
	}
	
	public static double acosh(double a) {
		return Math.log(a + Math.sqrt(a + 1) * Math.sqrt(a - 1));
	}
	
	public static double asinh(double a) {
		return Math.log(a + Math.sqrt(1 + a * a));
	}
}
