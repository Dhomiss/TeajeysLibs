package com.teajey.util;

public class Words {
	public static String pluralise(String singular, String plural, double number) {
		return number == 1 ? singular : plural;
	}
	
	public static String ordinal(int place) {
		String[] placeStrings = { "th", "st", "nd", "rd" };
		String placeString = "th";
		if (place % 10 < 4 && (place % 100 < 10 || place % 100 > 20)) {
			placeString = placeStrings[place % 4];
		}
		return place + placeString;
	}
	
	public static String swap(int swap, String... strings) {
		return strings[swap];
	}
	
	public static String swap(boolean swap, String s, String t) {
		return swap ? s : t;
	}
}
