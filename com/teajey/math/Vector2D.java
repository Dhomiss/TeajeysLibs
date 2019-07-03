package com.teajey.math;

import com.teajey.util.TJUtil;

import javafx.geometry.Point2D;

public class Vector2D implements Cloneable {
	public double x;
	public double y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(double angle) {
		this(Math.sin(angle), Math.cos(angle));
	}
	
	public Vector2D(Vector2D v) {
		this(v.x, v.y);
	}
	
	public Vector2D() {
		this(0, 0);
	}
	
	public double getMag() { return Math.sqrt(Math.pow((double)this.x, 2) + Math.pow((double)this.y, 2)); }
	public double getAngle() { return Math.atan2((double)this.y, (double)this.x); }
	public void setX(double x) { this.x = x; }
	public void setY(double y) { this.y = y; }
	public int getIntX() { return (int)Math.round(this.x); }
	public int getIntY() { return (int)Math.round(this.y); }
	public Vector2D neg() { return new Vector2D(-this.x, -this.y); }
	public void set(double x, double y) { this.x = x; this.y = y; }
	public void set(Vector2D v) { this.x = v.x; this.y = v.y; }
	public void setAngle(double angle) {
		double mag = this.getMag();
		this.x = Math.sin(angle) * mag;
		this.y = Math.cos(angle) * mag;
	}
	
	public Vector2D rotate(double theta) {
		double tempx = this.x;
		double cos = Math.cos(theta);
		double sin = Math.sin(theta);
		this.x = this.x * cos - this.y * sin;
		this.y = tempx * sin + this.y * cos;
		return this;
	}
	
	public void setMag(double magnitude) {
		this.normalize();
		this.mult(magnitude);
	}
	
	public void normalize() {
		this.div(this.getMag());
	}
	
	public double angleTo(Vector2D v) {
		if ((this.x == 0 && this.y == 0) || (v.x == 0 && v.y == 0)) return 0.0;
		double amt = this.dot(v) / (this.getMag() * v.getMag());
		
		if (amt >= 1) return 0.0;
		if (amt <= -1) return Math.PI;
		
		double angle = Math.acos(amt);
		return angle;
	}
	
	public static double angleBetween(Vector2D v1, Vector2D v2) {
		return v1.angleTo(v2);
	}
	
	public void add(double x, double y) {
		this.x += x;
		this.y += y;
	}
	
	public void add(Vector2D v) {
		add(v.x, v.y);
	}
	
	public void sub(Vector2D v) {
		add(-v.x, -v.y);
	}
	
	public void sub(double x, double y) {
		add(-x, -y);
	}
	
	public void mult(double s) {
		this.x *= s;
		this.y *= s;
	}
	
	public void div(double s) {
		if (s != 0) {
			this.x /= s;
			this.y /= s;
		}
	}
	
	public double dot(Vector2D v) {
		return (this.x * v.x) + (this.y * v.y);
	}
	
	public static double dot(Vector2D v1, Vector2D v2) {
		return v1.dot(v2);
	}
	
	public Vector2D cross(Vector2D v) {
		Vector2D product = new Vector2D();
		product.x = this.x * v.y;
		product.y = v.x * this.y;
		return product;
	}
	
	public static Vector2D cross(Vector2D v1, Vector2D v2) {
		return v1.cross(v2);
	}
	
	public double dist(Vector2D v) {
		return dist(v.x, v.y);
	}
	
	public double dist(double x, double y) {
		return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
	}
	
	public static Vector2D add(Vector2D... vectors) {
		Vector2D vec = new Vector2D();
		for (Vector2D v : vectors) {
			vec.add(v);
		}
		return vec;
	}
	
	public static Vector2D sub(Vector2D... vectors) {
		for (int i = 1; i < vectors.length; i++) {
			vectors[0].sub(vectors[i]);
		}
		return vectors[0];
	}
	
	public static Vector2D mult(Vector2D v, double s) {
		Vector2D vec = v.copy();
		vec.mult(s);
		return vec;
	}
	
	public static Vector2D div(Vector2D v, double s) {
		Vector2D vec = new Vector2D(v.x, v.y);
		vec.div(s);
		return vec;
	}
	
	public Vector2D lirp(double x, double y, double fraction) {
		this.x = TJUtil.lirp(this.x, x, fraction);
		this.y = TJUtil.lirp(this.y, y, fraction);
		return this;
	}
	
	public Vector2D lirp(Vector2D v, double fraction) {
		this.lirp(v.x, v.y, fraction);
		return this;
	}
	
	public static Vector2D lirp(Vector2D v1, Vector2D v2, double fraction) {
		Vector2D v = v1.copy();
		v.lirp(v2, fraction);
		return v;
	}
	
	public void setEqualTo(Vector2D v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	public void setEqualTo(Point2D p) {
		this.x = p.getX();
		this.y = p.getY();
	}
	
	public boolean equalTo(Vector2D v) {
		return (this.x == v.x) && (this.y == v.y);
	}
	
	public boolean equalTo(Point2D p) {
		return (this.x == p.getX()) && (this.y == p.getY());
	}

	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + "]";
	}
	
	public Vector2D copy() {
		return new Vector2D(this.x, this.y);
	}
}
