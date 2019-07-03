package com.teajey.graphics;

import java.awt.AWTEvent;
import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.teajey.math.TJMath;

public abstract class TJSketch extends Canvas implements Runnable {
	private static final long serialVersionUID = 2388942391985542629L;

	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;
	private static final int NUMBER_OF_BUFFERS = 2;

	GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDefaultConfiguration();

	public final Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
			new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor");

	private BufferStrategy bs;

	protected TJWindow window;
	
	private BufferedImage drawingImage;

	protected boolean[] pressed = new boolean[221];
	private int windowMouseX = 0;
	private int windowMouseY = 0;
	protected int keyCode = 0;
	protected char key = 0;

	private boolean resizeRequest = false;
	private Dimension requestedSize;
	private boolean fullscreenRequested = false;

	private boolean loop = false;

	private int frameRateSampling = 1;
	private double[] frameRateSamples = new double[frameRateSampling];
	private double targetFrameRate = 60;
	private long drawStartTime = 0;
	private long drawEndTime = 0;
	private long latestFrameDuration = 0;
	private int frameCount = 0;
	private double frameRate = 0;

	public TJSketch() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public TJSketch(int width, int height) {
		this.setPreferredSize(new Dimension(width, height));
		this.setIgnoreRepaint(true);

		window = new TJWindow(this);
		
		drawingImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		enableEvents(KeyEvent.KEY_EVENT_MASK | MouseEvent.MOUSE_EVENT_MASK | MouseEvent.MOUSE_MOTION_EVENT_MASK);
		this.setFocusTraversalKeysEnabled(false);

		System.setProperty("sun.java2d.opengl", "true");

		initBufferStrategy();

		setup();
	}

	public void addNotify() {
		super.addNotify();

		initBufferStrategy();
	}

	public void initBufferStrategy() {
		createBufferStrategy(NUMBER_OF_BUFFERS);
		bs = getBufferStrategy();
	}

	public void render() {
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
		SimpleGraphics drawingGraphics = new SimpleGraphics(drawingImage.getGraphics(), this);

		long timeSinceLastDraw = System.nanoTime() - drawEndTime;
		if (timeSinceLastDraw > TJMath.secondsToNanoseconds(1 / targetFrameRate)) {
			latestFrameDuration = System.nanoTime() - drawStartTime;
			frameRate = 1 / TJMath.nanoSecondsToSeconds(latestFrameDuration);
			drawStartTime = System.nanoTime();
			draw(drawingGraphics);
			g2d.drawImage(drawingImage, 0, 0, null);
			drawEndTime = System.nanoTime();
			frameCount++;
		}
		
		drawingGraphics.dispose();
		g2d.dispose();
		bs.show();
	}

	public void setup() {};

	public abstract void draw(SimpleGraphics g);

	public void run() {
		loop = true;
		while (loop) {
			if (resizeRequest) {
				changeSize(requestedSize, fullscreenRequested);
			}
			render();
		}
	}
	
	public synchronized void stop() {
		this.loop = false;
	}

	private void changeSize(Dimension newSize, boolean fullscreen) {
		this.setPreferredSize(newSize);
		window.dispose();
		window = new TJWindow(this, fullscreen);

		drawingImage = new BufferedImage(newSize.width, newSize.height, BufferedImage.TYPE_INT_ARGB);
		
		initBufferStrategy();

		this.resizeRequest = false;
		this.fullscreenRequested = false;

		screenSizeChanged();
	}

	public void requestFullscreen() {
		this.resizeRequest = true;
		this.requestedSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.fullscreenRequested = true;
	}

	public void requestToResizeScreenTo(int w, int h) {
		this.resizeRequest = true;
		this.requestedSize = new Dimension(w, h);
	}

	public BufferedImage loadImage(String imgUrl) { // @TODO: Find better place for this?
		File imgSrc = new File(imgUrl);
		BufferedImage img;
		BufferedImage imgCompat;
		try {
			img = ImageIO.read(imgSrc);
			imgCompat = gfxConfig.createCompatibleImage(img.getWidth(), img.getHeight());
			Graphics g = imgCompat.getGraphics();
			g.drawImage(img, 0, 0, null);
			g.dispose();
			return img;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void processEvent(AWTEvent e) { // @TODO: Needs to be slimmed up
		switch (e.getID()) {
		case KeyEvent.KEY_PRESSED:
			pressed[((KeyEvent) e).getKeyCode()] = true;
			keyCode = ((KeyEvent) e).getKeyCode();
			key = ((KeyEvent) e).getKeyChar();
			keyPressed();
			break;
		case KeyEvent.KEY_RELEASED:
			pressed[((KeyEvent) e).getKeyCode()] = false;
			keyCode = ((KeyEvent) e).getKeyCode();
			key = ((KeyEvent) e).getKeyChar();
			keyReleased();
			break;
		case MouseEvent.MOUSE_PRESSED:
			pressed[((MouseEvent) e).getButton()] = true;
			keyCode = ((MouseEvent) e).getButton();
			windowMouseX = ((MouseEvent) e).getX();
			windowMouseY = ((MouseEvent) e).getY();
			mousePressed();
			break;
		case MouseEvent.MOUSE_RELEASED:
			pressed[((MouseEvent) e).getButton()] = false;
			keyCode = ((MouseEvent) e).getButton();
			windowMouseX = ((MouseEvent) e).getX();
			windowMouseY = ((MouseEvent) e).getY();
			mouseReleased();
			break;
		case MouseEvent.MOUSE_DRAGGED:
			pressed[((MouseEvent) e).getButton()] = true;
			keyCode = ((MouseEvent) e).getButton();
			windowMouseX = ((MouseEvent) e).getX();
			windowMouseY = ((MouseEvent) e).getY();
			mouseDragged();
		case MouseEvent.MOUSE_MOVED:
			windowMouseX = ((MouseEvent) e).getX();
			windowMouseY = ((MouseEvent) e).getY();
			mouseMoved();
			break;
		}
	}

	public int getWindowMouseX() {
		return windowMouseX;
	}

	public int getWindowMouseY() {
		return windowMouseY;
	}

	protected void keyPressed() {
	}

	protected void keyReleased() {
	}

	protected void mousePressed() {
	}

	protected void mouseReleased() {
	}

	protected void mouseMoved() {
	}

	protected void mouseDragged() {
	}

	protected void screenSizeChanged() {
	}

	public double frameRate() {
		frameRateSamples[frameCount % frameRateSampling] = frameRate;
		double simpleMovingAverage = 0;
		for (int i = 0; i < frameRateSamples.length; i++) {
			simpleMovingAverage += frameRateSamples[i];
		}
		simpleMovingAverage /= frameCount < frameRateSampling ? frameCount + 1 : frameRateSampling;
		return (double) Math.round(simpleMovingAverage * 10) / 10;
	}

	public BufferedImage getDrawingImage() {
		return drawingImage;
	}

	public int frameCount() {
		return frameCount;
	}

	public double getTargetFrameRate() {
		return targetFrameRate;
	}

	public void setTargetFrameRate(int targetFrameRate) {
		this.targetFrameRate = targetFrameRate;
	}

	public void disableCursor() {
		this.setCursor(blankCursor);
	}

	public void enableCursor() {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
}
