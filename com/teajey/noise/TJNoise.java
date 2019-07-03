package com.teajey.noise;

public class TJNoise extends RoughOpenSimplexNoise {
	private static final int DEFAULT_ITERATIONS = 16;
	private static final double DEFAULT_PERSISTENCE = 0.5;
	private static final double DEFAULT_SCALE = 1;
	
	private int iterations = DEFAULT_ITERATIONS;
	private double persistence = DEFAULT_PERSISTENCE;
	private double scale = DEFAULT_SCALE;
	
	private RoughOpenSimplexNoise roughOpenSimplexNoise;
	
	public TJNoise() {
		this(0);
	}
	
	public TJNoise(long seed) {
		roughOpenSimplexNoise = new RoughOpenSimplexNoise(seed);
	}
	
	public double noise(double x, double y, double z, double w) {
		return roughOpenSimplexNoise.sumOctave4D(iterations, x, y, z, w, persistence, scale);
	}
	
	public double noise(double x, double y, double z) {
		return roughOpenSimplexNoise.sumOctave3D(iterations, x, y, z, persistence, scale);
	}

	public double noise(double x, double y) {
		return roughOpenSimplexNoise.sumOctave2D(iterations, x, y, persistence, scale);
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public void setPersistence(double persistence) {
		this.persistence = persistence;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}
}
