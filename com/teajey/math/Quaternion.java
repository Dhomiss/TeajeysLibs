package com.teajey.math;

import java.util.Objects;

public class Quaternion {
	Complex direct;
	Complex lateral;
	
	public Quaternion(Complex d, Complex l) {
		direct = d;
		lateral = l;
	}
	
	public Quaternion(double a, double b, double c, double d) {
		this(new Complex(a, b), new Complex(c, d));
	}
	
	public Quaternion(Complex direct) {
		this(direct, new Complex());
	}
	
	public Quaternion(double real) {
		this(real, 0, 0, 0);
	}
	
	public Quaternion() {
		this(0);
	}
	
	public static Quaternion unit(Complex t, Complex u) {
		return new Quaternion(Complex.cos(t), Complex.sin(u));
	}
	
	public double abs() {
		return Math.sqrt(Math.pow(direct.real(), 2) + Math.pow(direct.imag(), 2) + Math.pow(lateral.real(), 2) + Math.pow(direct.imag(), 2));
	}
	
	public Quaternion add(Quaternion q) {
		this.direct.add(q.direct);
		this.lateral.add(q.lateral);
		return this;
	}
	
	public Complex direct() {
		return direct;
	}
	
	public Complex lateral() {
		return lateral;
	}
	
	public int hashCode() {
		return Objects.hash(direct, lateral);
	}
	
	public String toString() {
		return direct.real() + " + " + direct.imag() + "i + " + lateral.real() + "j + " + lateral.imag() + "k";
	}
	
	public static void main(String...args) {
		Quaternion q = new Quaternion(1, 2, 3, 4);
		System.out.println(q);
	}
}
