package com.teajey.math;

public class Rectangle {
	public double width;
	public double height;
	
	public Rectangle(double w, double h) {
		this.width = w;
		this.height = h;
	}
	
	public double getArea() {
		return width * height;
	}
	
	public int intWidth() {
		return (int)Math.round(width);
	}
	
	public int intHeight() {
		return (int)Math.round(height);
	}
}
