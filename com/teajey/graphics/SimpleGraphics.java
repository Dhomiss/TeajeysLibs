package com.teajey.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.teajey.math.Vector2D;

import sun.java2d.SunGraphics2D;

public class SimpleGraphics extends SunGraphics2DWrapper {
	public static final int CENTER = 0x00001; // -1;
	public static final int TOP = 0x00010; // 0;
	public static final int BOTTOM = 0x00100; // -2;
	public static final int LEFT = 0x01000; // 0;
	public static final int RIGHT = 0x10000; // -2;
	
	private int textAlignment = LEFT;

	private static final Font DEFAULT_FONT = new Font("Arial", Font.BOLD, 20);
	
	public static String wordBreakMark = "-";
	public static String textRunoffMark = "...";

	private Vector2D origin = new Vector2D();
	private Vector2D cameraPosition = new Vector2D();
	private Vector2D scale = new Vector2D(1, 1);
	private Vector2D offset = new Vector2D();

	private boolean doStroke = true;
	private Paint stroke = Color.WHITE;
	private boolean doFill = true;
	private Paint fill = Color.MAGENTA;
	private Vector2D translation = new Vector2D();
	private double rotation = 0;
	private Polygon polygon = null;
	private Area area = null;
	private Path2D path = null;
	
	private TJSketch displaySketch;
	private int width;
	private int height;
	
	public SimpleGraphics(Graphics g, int width, int height, TJSketch tjs) {
		super((SunGraphics2D)g);
		if (tjs != null)
			this.displaySketch = tjs;
		this.width = width;
		this.height = height;
		this.setFont(DEFAULT_FONT);
		this.enableRenderingSettings(ANTIALIASING | TEXT_ANTIALIASING);
		this.setBackground(Color.BLACK);
	}

	public SimpleGraphics(Graphics g, TJSketch tjs) {
		this(g, tjs.getWidth(), tjs.getHeight(), tjs);
	}
	
	public SimpleGraphics(Graphics g, int width, int height) {
		this(g, width, height, null);
	}
	
	public void setOrigin(double x, double y) {
		translate(-origin.x, -origin.y);
		origin.set(x, y);
		translate(x, y);
	}
	
	public void setCameraPosition(double x, double y) {
		translate(-cameraPosition.x, -cameraPosition.y);
		cameraPosition.set(x, y);
		translate(cameraPosition.x, cameraPosition.y);
	}
	
	public void setCameraRotation(double theta) {
		rotate(theta);
	}
	
	public void setCameraZoom(double zoom) {
		scale.x = zoom;
		scale.y = zoom;
	}
	
	public Vector2D getCameraPosition() {
		return cameraPosition;
	}

	public void simpleTranslate(double tx, double ty) {
		translation.add(tx, ty);
		translate(tx, ty);
	}

	public void simpleTranslate(Vector2D t) {
		translate(t.x, t.y);
	}

	public void resetTransformations() {
		unRotate();
		unTranslate();
	}

	public void unTranslate() {
		simpleTranslate(translation.neg());
	}

	public void simpleRotate(double theta) {
		rotation += theta;
		rotate(theta, 0, 0);
	}

	public void unRotate() {
		rotate(-rotation);
	}

	public void startArea() {
		area = new Area();
	}

	public void addToArea(Shape s) {
		if (area != null) {
			area.add(new Area(s));
		} else {
			System.err.println("Error: addToArea() called before startArea()");
		}
	}

	public void subtractFromArea(Shape s) {
		if (area != null) {
			area.subtract(new Area(s));
		} else {
			System.err.println("Error: subtractFromArea() called before startArea()");
		}
	}

	public void drawShape(Shape shape) {
		drawShape(shape, fill, stroke, doFill, doStroke);
	}

	public void drawShape(Shape shape, Paint fill, Paint stroke, boolean doFill, boolean doStroke) {
		Rectangle2D bounds = shape.getBounds();
		int xPos = offset.getIntX() * (int) (bounds.getWidth() / 2);
		int yPos = offset.getIntY() * (int) (bounds.getHeight() / 2);
		translate(xPos, yPos);
		if (doFill) {
			setPaint(fill);
			fill(shape);
		}
		if (doStroke) {
			setPaint(stroke);
			draw(shape);
		}
		translate(-xPos, -yPos);
	}
	
	public void clear(int x, int y, int w, int h) {
		int scaledX = scaleX(x);
		int scaledY = scaleY(y);
		int scaledWidth = scaleX(w);
		int scaledHeight = scaleY(h);
		this.clearRect(scaledX + (int)(offset.x * scaledWidth / 2), scaledY + (int)(offset.y * scaledHeight / 2), scaledWidth, scaledHeight);
	}

	public void drawArea() {
		if (area != null) {
			drawShape(area);
			area = null;
		} else {
			System.err.println("Error: drawArea() called before startArea()");
		}
	}

	public void startPath() {
		path = new Path2D.Double();
	}

	public void pathMoveTo(int x, int y) {
		if (path != null) {
			path.moveTo(x, y);
		} else {
			System.err.println("Error: pathMoveTo() called before startPath()");
		}
	}

	public void pathLineTo(int x, int y) {
		if (path != null) {
			path.lineTo(x, y);
		} else {
			System.err.println("Error: pathLineTo() called before startPath()");
		}
	}

	public void drawPath() {
		if (path != null) {
			drawShape(path);
			path = null;
		} else {
			System.err.println("Error: drawPath() called before startPath()");
		}
	}

	public void ellipse(int x, int y, int w, int h) {
		Ellipse2D ellipse = new Ellipse2D.Double(x * scale.x, y * scale.y, w * scale.x, h * scale.x);
		drawShape(ellipse);
	}

	public void ellipse(int x, int y, int d) {
		ellipse(x, y, d, d);
	}

	public void ellipse(int d) {
		ellipse(0, 0, d);
	}

	public void rect(int x, int y, int w, int h) {
		Rectangle2D rect = new Rectangle2D.Double(x * scale.x, y * scale.y, w * scale.x, h * scale.y);
		drawShape(rect);
	}

	public void rect(int x, int y, int w) {
		rect(x, y, w, w);
	}

	public void rect(int w) {
		rect(0, 0, w, w);
	}

	public void beginPolygon() {
		polygon = new Polygon();
	}

	public void vertex(int x, int y) {
		if (polygon != null) {
			polygon.addPoint((int)(x * scale.x), (int)(y * scale.y));
		} else {
			System.err.println("Error: vertex() was called before beginShape().");
		}
	}

	public void drawPolygon() {
		if (polygon != null) {
			setPaint(stroke);
			drawPolygon(polygon);
			polygon = null;
		} else {
			System.err.println("Error: endShape() was called before beginShape().");
		}
	}

	public void drawAlign(int code) {
		final int center = -1;
		final int top = 0;
		final int bottom = -2;
		final int left = 0;
		final int right = -2;

		offset.set(center, center);

		if ((TOP & code) == TOP) {
			offset.y = top;
		} else if ((BOTTOM & code) == BOTTOM) {
			offset.y = bottom;
		}
		if ((LEFT & code) == LEFT) {
			offset.x = left;
		} else if ((RIGHT & code) == RIGHT) {
			offset.x = right;
		}
	}
	
	public void text(String text) {
		text(text, 1.0);
	}
	
	public void text(String text, int x, int y) {
		text(text, x, y, 1.0);
	}
	
	public void text(String text, double lineSpacing) {
		text(text, 0, 0, lineSpacing);
	}
	
	public void text(String text, int x, int y, double lineSpacing) {
		text(text, x, y, -1, lineSpacing);
	}
	
	public void text(String text, int x, int y, int maxWidth, double lineSpacing) {
		text(text, x, y, maxWidth, -1, lineSpacing);
	}
	
	public void text(String text, int x, int y, int maxWidth, int maxHeight, double lineSpacing) {
		int lineWidth = 0;
		int widestLine = 0;
		int thisWordWidth = 0;
		int lineStart = 0;
		int lineEnd = 0;
		int lineIndex = 0;
		int wordBreakMarkWidth = getFontMetrics().stringWidth(wordBreakMark);
		int textRunoffMarkWidth = getFontMetrics().stringWidth(textRunoffMark);
		int ascent = getFontMetrics().getAscent();
		String breakMark = "";
		setPaint(fill);
		boolean textAreaTooSmall = (textRunoffMarkWidth > maxWidth || wordBreakMarkWidth > maxWidth) && maxWidth >= 0;
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			int charWidth = getFontMetrics().charWidth(c);
			boolean nextWordExceedsWidth = false;
			lineWidth += charWidth;
			boolean nextLineExceedsHeight = (lineIndex + 2) * ascent > maxHeight && maxHeight >= 0;
			boolean thisLineExceedsHeight = (lineIndex + 1) * ascent > maxHeight && maxHeight >= 0;
			if (c == ' ') {
				thisWordWidth = 0;
				int nextCharIndex = i;
				int nextWordWidth = 0;
				while (++nextCharIndex < text.length() && text.charAt(nextCharIndex) != ' ' && !nextWordExceedsWidth) {
					char nextChar = text.charAt(nextCharIndex);
					nextWordWidth += getFontMetrics().charWidth(nextChar);
					nextWordExceedsWidth = lineWidth + nextWordWidth > maxWidth && maxWidth >= 0;
				}
			} else {
				thisWordWidth += charWidth;
			}
			boolean newLineChar = c == '\n';
			
			boolean endOfString = i == text.length() - 1;
			int thisWordPlusBreakMarkWidth = thisWordWidth + (!endOfString ? getFontMetrics().charWidth(text.charAt(i + 1)) : 0) + (nextLineExceedsHeight ? textRunoffMarkWidth : wordBreakMarkWidth);
			boolean thisWordExceedsWidth = thisWordPlusBreakMarkWidth > maxWidth && maxWidth >= 0;
			boolean drawTextLine = ((endOfString || thisWordExceedsWidth || nextWordExceedsWidth || newLineChar) && !thisLineExceedsHeight) && !textAreaTooSmall;
			if (drawTextLine) {
				lineIndex++;
				lineEnd = i - (endOfString ? 0 : 1);
				String textToDraw = text.substring(lineStart, lineEnd + 1);
				if (thisWordExceedsWidth) {
					breakMark = wordBreakMark;
					if (nextLineExceedsHeight) {
						breakMark = textRunoffMark;
					}
					textToDraw += breakMark;
				}
				int drawX = x;
				if (textAlignment == CENTER) {
					drawX -= lineWidth / 2;
				} else if (textAlignment == RIGHT) {
					drawX -= lineWidth;
				}
				drawString(textToDraw, scaleX(drawX), scaleY(y + ascent + (int)(ascent * lineSpacing) * (lineIndex - 1)));
				lineStart = i + (endOfString ? 0 : 1);
				thisWordWidth = 0;
				if (lineWidth > widestLine) widestLine = lineWidth;
				lineWidth = 0;
			}
		}
	}
	
	public void textAlign(int code) {
		this.textAlignment = code;
	}

	public void line(int x1, int y1, int x2, int y2) {
		if (doStroke) {
			setPaint(stroke);
			drawLine((int)(x1 * scale.x), (int)(y1 * scale.y), (int)(x2 * scale.x), (int)(y2 * scale.y));
		}
	}

	public void arc(int x, int y, int w, int h, double start, double extent) {
		arc(scaleX(x), scaleY(y), scaleX(w), scaleY(h), start, extent, Arc2D.OPEN);
	}

	public void arc(int x, int y, int w, int h, double start, double extent, int type) {
		Arc2D arc = new Arc2D.Double(scaleX(x), scaleY(y), scaleX(w), scaleY(h), start, extent, type);
		drawShape(arc);
	}

	public void quadCurve(int x1, int y1, int ctrlx, int ctrly, int x2, int y2) {
		QuadCurve2D quadCurve = new QuadCurve2D.Double(scaleX(x1), scaleY(y1), scaleX(ctrlx), scaleY(ctrly), scaleX(x2), scaleY(y2));
		drawShape(quadCurve);
	}

	public void cubicCurve(int x1, int y1, int ctrlx1, int ctrly1, int ctrlx2, int ctrly2, int x2, int y2) {
		CubicCurve2D cubicCurve = new CubicCurve2D.Double(scaleX(x1), scaleY(y1), scaleX(ctrlx1), scaleY(ctrly1), scaleX(ctrlx2), scaleY(ctrly2), scaleX(x2), scaleY(y2));
		drawShape(cubicCurve);
	}

	public void image(BufferedImage img, int x, int y) {
		image(img, x, y, img.getWidth(null), img.getHeight(null));
	}

	public void image(BufferedImage img, int x, int y, int w, int h) {
		drawImage(img, scaleX(x + offset.x * w / 2), scaleY(y + offset.y * h / 2), scaleX(w), scaleY(h), null);
	}
	
	public int[] getPixels() {
		return ((DataBufferInt)displaySketch.getDrawingImage().getRaster().getDataBuffer()).getData();
	}
	
	public void updatePixels(int[] pixels) {
		
	}

	public void stroke(Paint stroke) {
		doStroke = true;
		this.stroke = stroke;
	}

	public void strokeWeight(float weight) {
		strokeStyle(weight * (float)scale.x, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	}

	public void strokeStyle(float weight, int cap, int join) {
		setStroke(new BasicStroke(weight, cap, join));
	}

	public void fill(Paint fill) {
		doFill = true;
		this.fill = fill;
	}

	public void noStroke() {
		doStroke = false;
	}

	public void noFill() {
		doFill = false;
	}

	public void background() {
		translate(-origin.x, -origin.y);
		clearRect(0, 0, width, height);
		translate(origin.x, origin.y);
	}
	
	public int getMouseX() {
		return displaySketch.getWindowMouseX() - (int)(origin.x - cameraPosition.x);
	}
	
	public int getMouseY() {
		return displaySketch.getWindowMouseY() - (int)(origin.y - cameraPosition.y);
	}
	
	private int scaleX(double x) {
		return (int)(x * scale.x);
	}
	
	private int scaleY(double y) {
		return (int)(y * scale.y);
	}
	
	public static final int ANTIALIASING = 0b0000001;
	public static final int ALPHA_INTERPOLATION = 0b0000010;
	public static final int QUALITY_COLOR_RENDERING = 0b0000100;
	public static final int DITHERING = 0b0001000;
	public static final int BICUBIC_INTERPOLATION = 0b0010000;
	public static final int QUALITY_RENDERING = 0b0100000;
	public static final int TEXT_ANTIALIASING = 0b1000000;

	public void enableRenderingSettings(int options) {
		boolean antialias = (options & ANTIALIASING) == ANTIALIASING;
		boolean alphaInterp = (options & ALPHA_INTERPOLATION) == ALPHA_INTERPOLATION;
		boolean colorRendering = (options & QUALITY_COLOR_RENDERING) == QUALITY_COLOR_RENDERING;
		boolean dithering = (options & DITHERING) == DITHERING;
		boolean bicubic = (options & BICUBIC_INTERPOLATION) == BICUBIC_INTERPOLATION;
		boolean quality = (options & QUALITY_RENDERING) == QUALITY_RENDERING;
		boolean textAntialias = (options & TEXT_ANTIALIASING) == TEXT_ANTIALIASING;

		setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				antialias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
		setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				alphaInterp ? RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY
						: RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				colorRendering ? RenderingHints.VALUE_COLOR_RENDER_QUALITY : RenderingHints.VALUE_COLOR_RENDER_SPEED);
		setRenderingHint(RenderingHints.KEY_DITHERING,
				dithering ? RenderingHints.VALUE_DITHER_ENABLE : RenderingHints.VALUE_DITHER_DISABLE);
		setRenderingHint(RenderingHints.KEY_INTERPOLATION, bicubic ? RenderingHints.VALUE_INTERPOLATION_BICUBIC
				: RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		setRenderingHint(RenderingHints.KEY_RENDERING,
				quality ? RenderingHints.VALUE_RENDER_QUALITY : RenderingHints.VALUE_RENDER_SPEED);
		setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				textAntialias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	}
}
