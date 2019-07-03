package com.teajey.math.geom;

import java.util.Arrays;

public class Path {
	private Vertex[] vertices;
	
	public Path(Vertex... vertices) {
		this.vertices = new Vertex[vertices.length];
		for (int i = 0; i < this.vertices.length; i++) {
			this.vertices[i] = vertices[i];
		}
	}
	
	public Path(Path p) {
		this.vertices = new Vertex[p.getVertices().length];
		for (int i = 0; i < this.vertices.length; i++) {
			this.vertices[i] = p.getVertices()[i];
		}
	}
	
	public Vertex getVertex(int n) {
		if (this.vertices.length >= n) {
			return this.vertices[n];
		} else {
			return null;
		}
	}
	
	public boolean setVertex(int n, Vertex v) {
		if (this.vertices.length >= n) {
			this.vertices[n] = v;
			return true;
		} else {
			System.err.println(this + " does not have " + n + " vertices.");
			return false;
		}
	}
	
	public Vertex[] getVertices() {
		return this.vertices;
	}

	@Override
	public String toString() {
		return "Path [vertices=" + Arrays.toString(vertices) + "]";
	}
}
