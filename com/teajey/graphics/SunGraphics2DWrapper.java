package com.teajey.graphics;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import sun.java2d.SunGraphics2D;
import sun.java2d.SurfaceData;

public class SunGraphics2DWrapper extends Graphics2D {
	SunGraphics2D item;

	public SunGraphics2DWrapper(SurfaceData arg0, Color arg1, Color arg2, Font arg3) {
		item = new SunGraphics2D(arg0, arg1, arg2, arg3);
	}
	
	public SunGraphics2DWrapper(SunGraphics2D sg2d) {
		item = sg2d;
	}

	@Override
	public void fill(Shape arg0) {
		item.fill(arg0);
	}

	@Override
	public void rotate(double arg0, double arg1, double arg2) {
		item.rotate(arg0, arg1, arg2);
	}

	@Override
	public void rotate(double arg0) {
		item.rotate(arg0);
	}

	@Override
	public void scale(double arg0, double arg1) {
		item.scale(arg0, arg1);
	}

	@Override
	public void setBackground(Color arg0) {
		item.setBackground(arg0);
	}

	@Override
	public Color getBackground() {
		return item.getBackground();
	}

	@Override
	public boolean hit(Rectangle arg0, Shape arg1, boolean arg2) {
		return item.hit(arg0, arg1, arg2);
	}

	@Override
	public void drawImage(BufferedImage arg0, BufferedImageOp arg1, int arg2, int arg3) {
		item.drawImage(arg0, arg1, arg2, arg3);
	}

	@Override
	public boolean drawImage(Image arg0, AffineTransform arg1, ImageObserver arg2) {
		return item.drawImage(arg0, arg1, arg2);
	}

	@Override
	public void draw(Shape arg0) {
		item.draw(arg0);
	}

	@Override
	public void translate(int arg0, int arg1) {
		item.translate(arg0, arg1);
	}

	@Override
	public void translate(double arg0, double arg1) {
		item.translate(arg0, arg1);
	}

	@Override
	public void setRenderingHints(Map arg0) {
		item.setRenderingHints(arg0);
	}

	@Override
	public AffineTransform getTransform() {
		return item.getTransform();
	}

	@Override
	public void setTransform(AffineTransform arg0) {
		item.setTransform(arg0);
	}

	@Override
	public void shear(double arg0, double arg1) {
		item.shear(arg0, arg1);
	}

	@Override
	public void transform(AffineTransform arg0) {
		item.transform(arg0);
	}

	@Override
	public void drawString(AttributedCharacterIterator arg0, float arg1, float arg2) {
		item.drawString(arg0, arg1, arg2);
	}

	@Override
	public void drawString(AttributedCharacterIterator arg0, int arg1, int arg2) {
		item.drawString(arg0, arg1, arg2);
	}

	@Override
	public void drawString(String arg0, int arg1, int arg2) {
		item.drawString(arg0, arg1, arg2);
	}

	@Override
	public void drawString(String arg0, float arg1, float arg2) {
		item.drawString(arg0, arg1, arg2);
	}

	@Override
	public Object getRenderingHint(Key arg0) {
		return item.getRenderingHint(arg0);
	}

	@Override
	public void setRenderingHint(Key arg0, Object arg1) {
		item.setRenderingHint(arg0, arg1);
	}

	@Override
	public void clip(Shape arg0) {
		item.clip(arg0);
	}

	@Override
	public void addRenderingHints(Map<?, ?> arg0) {
		item.addRenderingHints(arg0);
	}

	@Override
	public void drawRenderableImage(RenderableImage arg0, AffineTransform arg1) {
		item.drawRenderableImage(arg0, arg1);
	}

	@Override
	public void drawRenderedImage(RenderedImage arg0, AffineTransform arg1) {
		item.drawRenderedImage(arg0, arg1);
	}

	@Override
	public Composite getComposite() {
		return item.getComposite();
	}

	@Override
	public Paint getPaint() {
		return item.getPaint();
	}

	@Override
	public Stroke getStroke() {
		return item.getStroke();
	}

	@Override
	public void setPaint(Paint arg0) {
		item.setPaint(arg0);
	}

	@Override
	public void setStroke(Stroke arg0) {
		item.setStroke(arg0);
	}

	@Override
	public void drawGlyphVector(GlyphVector arg0, float arg1, float arg2) {
		item.drawGlyphVector(arg0, arg1, arg2);
	}

	@Override
	public FontRenderContext getFontRenderContext() {
		return item.getFontRenderContext();
	}

	@Override
	public RenderingHints getRenderingHints() {
		return item.getRenderingHints();
	}

	@Override
	public void setComposite(Composite arg0) {
		item.setComposite(arg0);
	}

	@Override
	public GraphicsConfiguration getDeviceConfiguration() {
		return item.getDeviceConfiguration();
	}

	@Override
	public Graphics create() {
		return item.create();
	}

	@Override
	public void setColor(Color arg0) {
		item.setColor(arg0);
	}

	@Override
	public void setClip(Shape arg0) {
		item.setClip(arg0);
	}

	@Override
	public void setClip(int arg0, int arg1, int arg2, int arg3) {
		item.setClip(arg0, arg1, arg2, arg3);
	}

	@Override
	public void setFont(Font arg0) {
		item.setFont(arg0);
	}

	@Override
	public void clearRect(int arg0, int arg1, int arg2, int arg3) {
		item.clearRect(arg0, arg1, arg2, arg3);
	}

	@Override
	public Rectangle getClipBounds() {
		return item.getClipBounds();
	}

	@Override
	public Font getFont() {
		return item.getFont();
	}

	@Override
	public FontMetrics getFontMetrics(Font arg0) {
		return item.getFontMetrics(arg0);
	}

	@Override
	public boolean drawImage(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8, Color arg9, ImageObserver arg10) {
		return item.drawImage(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10);
	}

	@Override
	public boolean drawImage(Image arg0, int arg1, int arg2, Color arg3, ImageObserver arg4) {
		return item.drawImage(arg0, arg1, arg2, arg3, arg4);
	}

	@Override
	public boolean drawImage(Image arg0, int arg1, int arg2, int arg3, int arg4, Color arg5, ImageObserver arg6) {
		return item.drawImage(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
	}

	@Override
	public boolean drawImage(Image arg0, int arg1, int arg2, int arg3, int arg4, ImageObserver arg5) {
		return item.drawImage(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public boolean drawImage(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7, int arg8, ImageObserver arg9) {
		return item.drawImage(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
	}

	@Override
	public boolean drawImage(Image arg0, int arg1, int arg2, ImageObserver arg3) {
		return item.drawImage(arg0, arg1, arg2, arg3);
	}

	@Override
	public Shape getClip() {
		return item.getClip();
	}

	@Override
	public void dispose() {
		item.dispose();
	}

	@Override
	public void copyArea(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		item.copyArea(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void drawArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		item.drawArc(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void drawLine(int arg0, int arg1, int arg2, int arg3) {
		item.drawLine(arg0, arg1, arg2, arg3);
	}

	@Override
	public void drawOval(int arg0, int arg1, int arg2, int arg3) {
		item.drawOval(arg0, arg1, arg2, arg3);
	}

	@Override
	public void drawPolygon(int[] arg0, int[] arg1, int arg2) {
		item.drawPolygon(arg0, arg1, arg2);
	}

	@Override
	public void drawPolyline(int[] arg0, int[] arg1, int arg2) {
		item.drawPolyline(arg0, arg1, arg2);
	}

	@Override
	public void drawRoundRect(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		item.drawRoundRect(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void fillArc(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		item.fillArc(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public void fillOval(int arg0, int arg1, int arg2, int arg3) {
		item.fillOval(arg0, arg1, arg2, arg3);
	}

	@Override
	public void fillPolygon(int[] arg0, int[] arg1, int arg2) {
		item.fillPolygon(arg0, arg1, arg2);
	}

	@Override
	public void fillRect(int arg0, int arg1, int arg2, int arg3) {
		item.fillRect(arg0, arg1, arg2, arg3);
	}

	@Override
	public void fillRoundRect(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		item.fillRoundRect(arg0, arg1, arg2, arg3, arg4, arg5);
	}

	@Override
	public Color getColor() {
		return item.getColor();
	}

	@Override
	public void setPaintMode() {
		item.setPaintMode();
	}

	@Override
	public void setXORMode(Color arg0) {
		item.setXORMode(arg0);
	}

	@Override
	public void clipRect(int arg0, int arg1, int arg2, int arg3) {
		item.clipRect(arg0, arg1, arg2, arg3);
	}
}