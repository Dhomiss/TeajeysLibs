package com.teajey.util;

public class TJUtil {
	public static double lirp(double start, double end, double fraction) {
		return start + (end - start) * fraction;
	}
}
