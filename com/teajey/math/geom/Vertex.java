package com.teajey.math.geom;

import java.util.Arrays;

public class Vertex {
	protected double[] coords;
	
	public Vertex(double... coords) {
		this.coords = new double[coords.length];
		for (int i = 0; i < this.coords.length; i++) {
			this.coords[i] = coords[i];
		}
	}
	
	public Vertex(Vertex p) {
		this.coords = new double[p.getCoords().length];
		for (int i = 0; i < this.coords.length; i++) {
			this.coords[i] = p.getCoords()[i];
		}
	}
	
	public double getX() {
		return this.coords.length > 0 ? this.coords[0] : 0;
	}
	
	public double getY() {
		return this.coords.length > 1 ? this.coords[1] : 0;
	}
	
	public double getZ() {
		return this.coords.length > 2 ? this.coords[2] : 0;
	}
	
	public double getW() {
		return this.coords.length > 3 ? this.coords[3] : 0;
	}
	
	public double getCoord(int dim) {
		return this.coords[dim];
	}
	
	public double[] getCoords() {
		return this.coords;
	}
	
	public boolean setX(double x) {
		if (this.coords.length > 0) {
			this.coords[0] = x;
			return true;
		} else {
			System.err.println(this + " does not have an X coordinate.");
			return false;
		}
	}
	
	public boolean setY(double y) {
		if (this.coords.length > 1) {
			this.coords[0] = y;
			return true;
		} else {
			System.err.println(this + " does not have a Y coordinate.");
			return false;
		}
	}
	
	public boolean setZ(double z) {
		if (this.coords.length > 2) {
			this.coords[0] = z;
			return true;
		} else {
			System.err.println(this.hashCode() + " does not have a Z coordinate.");
			return false;
		}
	}
	
	public boolean setW(double w) {
		if (this.coords.length > 3) {
			this.coords[0] = w;
			return true;
		} else {
			System.err.println(this + " does not have a W coordinate.");
			return false;
		}
	}
	
	public boolean setCoord(int dim, double pos) {
		if (this.coords.length >= dim) {
			this.coords[dim] = pos;
			return true;
		} else {
			System.out.println(this + " does not have " + dim + " dimensions");
			return false;
		}
	}
	
	public int getDimensions() {
		return this.coords.length;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "@" + Arrays.toString(coords);
	}
}
