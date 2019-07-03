package com.teajey.graphics;

import java.awt.Dimension;

import javax.swing.JFrame;

public class TJWindow extends JFrame {
	private static final long serialVersionUID = -5348776781226426249L;

	private boolean fullscreen = false;

	TJSketch content;

	public TJWindow(TJSketch content, boolean fullscreen) {
		super(content.getClass().getSimpleName());
		this.fullscreen = fullscreen;
		this.content = content;
		if (fullscreen) {
			this.setUndecorated(true);
			this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		this.setResizable(false);
		this.add(content);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public TJWindow(TJSketch content) {
		this(content, false);
	}

	public boolean isFullscreen() {
		return fullscreen;
	}
}
