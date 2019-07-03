package com.teajey.math;

import javafx.geometry.Point2D;

public class Vector3D extends Vector2D {
	protected double z;
	
	public Vector3D(double x, double y, double z) {
		super(x, y);
		this.z = z;
	}
	
	public Vector3D(double latitude, double longitude) {
		super(Math.sin(latitude), Math.cos(latitude));
	}
	
	public Vector3D(Vector2D v) {
		this(v.x, v.y, 0);
	}
	
	public Vector3D(Vector3D v) {
		this(v.x, v.y, v.getZ());
	}
	
	public Vector3D() {
		this(0, 0, 0);
	}
	
	public double getZ() { return this.z; }
	public double getMag() { return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2)); }
	public void setZ(double z) { this.z = z; }
	public void set(double x, double y, double z) { super.set(x, y); this.z = z; }
	public void set(Vector2D v) { super.set(v); this.z = 0; }
	public void set(Vector3D v) { super.set(v); this.z = v.getZ(); }
	
	public void setMag(double magnitude) {
		/*this.x = magnitude * Math.sin(this.getAngle());
		this.y = magnitude * Math.cos(this.getAngle());*/
		this.normalize();
		this.mult(magnitude);
	}
	
	public void normalize() {
		this.div(this.getMag());
	}
	
	public void add(Vector3D v) {
		super.add(v);
		this.z += v.getZ();
	}
	
	public void sub(Vector3D v) {
		super.sub(v);
		this.z -= v.getZ();
	}
	
	public void mult(double s) {
		super.mult(s);
		this.z *= s;
	}
	
	public void div(double s) {
		if (s != 0) {
			super.div(s);
			this.z /= s;
		}
	}
	
	public double dist(Vector3D v) {
		double dx = this.x - v.x;
		double dy = this.y - v.y;
		double dz = this.z - v.getZ();
		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}
	
	public static Vector3D add(Vector3D... vectors) {
		Vector3D vec = new Vector3D();
		for (Vector3D v : vectors) {
			vec.add(v);
		}
		return vec;
	}
	
	public static Vector3D sub(Vector3D... vectors) {
		for (int i = 1; i < vectors.length; i++) {
			vectors[0].sub(vectors[i]);
		}
		return vectors[0];
	}
	
	public static Vector3D mult(Vector3D v, double s) {
		Vector3D vec = new Vector3D(v.x, v.y, v.getZ());
		vec.mult(s);
		return vec;
	}
	
	public static Vector3D div(Vector3D v, double s) {
		Vector3D vec = new Vector3D(v.x, v.y, v.getZ());
		vec.div(s);
		return vec;
	}
	
	public void setEqual(Vector3D v) {
		super.setEqualTo(v);
		this.z = v.getZ();
	}
	
	public void setEqual(Point2D p) {
		super.setEqualTo(p);
		this.z = 0;
	}
	
	public boolean equalTo(Vector3D v) {
		return (this.x == v.x) && (this.y == v.y) && (this.z == v.getZ());
	}
	
	public boolean equalTo(Point2D p) {
		return (this.x == p.getX()) && (this.y == p.getY()) && (this.z == 0);
	}

	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + ", " + this.z + "]";
	}
}
