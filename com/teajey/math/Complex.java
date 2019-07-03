package com.teajey.math;

import java.util.Objects;

public class Complex {
	
	private double re;
	private double im;
	
	public Complex(double real, double imaginary) {
		this.re = real;
		this.im = imaginary;
	}
	
	public Complex(double real) {
		this(real, 0);
	}
	
	public Complex() {
		this(0);
	}
	
	public static Complex unit(double theta) {
		return new Complex(Math.cos(theta), Math.sin(theta));
	}
	
	public static Complex real(double real) {
		return new Complex(real, 0);
	}
	
	public static Complex imag(double imaginary) {
		return new Complex(0, imaginary);
	}
	
	public double abs() {
		return Math.hypot(re, im);
	}
	
	public static double abs(Complex n) {
		return Math.hypot(n.re, n.im);
	}
	
	public static Complex hypot(Complex a, Complex b) {
		return sqrt(add(sq(a), sq(b)));
	}
	
	public double arg() {
		return Math.atan2(im, re);
	}
	
	public static double arg(Complex n) {
		return Math.atan2(n.im, n.re);
	}
	
	public Complex add(Complex n) {
		this.re += n.re;
		this.im += n.im;
		return this;
	}
	
	public Complex sub(Complex n) {
		this.re -= n.re;
		this.im -= n.im;
		return this;
	}
	
	public Complex mult(Complex n) {
		double real = this.re * n.re - this.im * n.im;
		double imag = this.re * n.im + this.im * n.re;
		return new Complex(real, imag);
	}
	
	public Complex div(Complex n) {
		return this.mult(n.reciprocal());
	}
	
	public static Complex sq(Complex n) {
		return n.mult(n);
	}
	
	/*public static pow(Complex n, Complex exponent) {
		
	}*/
	
	public static Complex sqrt(Complex n) {
		Complex sqrt = n.copy();
		double mag = sqrt.abs();
		double arg = sqrt.arg() / 2;
		mag = Math.sqrt(mag);
		return new Complex(Math.cos(arg), Math.sin(arg)).scale(mag);
	}
	
	public static Complex add(Complex n, Complex m) {
		return n.copy().add(m);
	}
	
	public static Complex sub(Complex n, Complex m) {
		return n.copy().sub(m);
	}
	
	public static Complex mult(Complex n, Complex m) {
		return n.copy().mult(m);
	}
	
	public static Complex div(Complex n, Complex m) {
		return n.copy().div(m);
	}
	
	public Complex scale(double n) {
		this.re *= n;
		this.im *= n;
		return this;
	}
	
	public double imag() {
		return im;
	}
	
	public double real() {
		return re;
	}
	
	public Complex conjugate() {
		return new Complex(this.re, -this.im);
	}
	
	public Complex reciprocal() {
		double scale = re * re + im * im;
		return new Complex(re / scale, -im / scale);
	}
	
	public static Complex exp(Complex n) {
		return new Complex(Math.exp(n.re) * Math.cos(n.im), Math.exp(n.re) * Math.sin(n.im));
	}
	
	public static Complex log(Complex n) {
		return new Complex(Math.log(n.abs()), n.arg());
	}
	
	public static Complex sin(Complex n) {
		return new Complex(Math.sin(n.re) * Math.cosh(n.im), Math.cos(n.re) * Math.sinh(n.im));
	}
	
	public static Complex cos(Complex n) {
		return new Complex(Math.cos(n.re) * Math.cosh(n.im), -Math.sin(n.re) * Math.sinh(n.im));
	}
	
	public static Complex tan(Complex n) {
		return Complex.sin(n).div(Complex.cos(n));
	}
	
	public static Complex atan(Complex n) {
		Complex multiplier = real(1).div(imag(2));
		Complex iSubN = sub(imag(1), n);
		Complex iAddN = imag(1).add(n);
		Complex div = div(iSubN, iAddN);
		Complex log = log(div);
		return mult(multiplier, log);
	}
	
	public String toString() {
		if (im == 0) return re + "";
		if (re == 0) return im + "i";
		if (im < 0) return "(" + re + " - " + -im + "i)";
		return "(" + re + " + " + im + "i)";
 	}
	
	public int hashCode() {
		return Objects.hash(re, im);
	}
	
	public Complex copy() {
		return new Complex(this.re, this.im);
	}
	
	public static void main(String...args) {
		System.out.println(sq(real(4)));
		System.out.println(Math.pow(4, 2));
	}
}
