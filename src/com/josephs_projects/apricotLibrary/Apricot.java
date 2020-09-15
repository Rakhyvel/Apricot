package com.josephs_projects.apricotLibrary;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

import com.josephs_projects.apricotLibrary.graphics.ColorOld;
import com.josephs_projects.apricotLibrary.graphics.Image;
import com.josephs_projects.apricotLibrary.input.InputEvent;
import com.josephs_projects.apricotLibrary.input.Keyboard;
import com.josephs_projects.apricotLibrary.input.Mouse;

import sun.java2d.SunGraphicsEnvironment;

/**
 * This class can be used to organize your Java project. It provides a window, a
 * GameLoop, input listening, and an interface for World collections.
 * 
 * @author Joseph Shimel
 *
 */
public class Apricot extends Thread {
	// Reference to world that engine is working with
	private World world;

	// Graphics fields
	public final JFrame frame;
	public final Canvas canvas;

	// Input fields
	public final Mouse mouse;
	public final Keyboard keyboard;

	// Gameloop fields
	private double dt = 1000 / 60.0;
	public int ticks = 0;
	public int fps;

	// misc public-static access fields
	public static Random rand = new Random();
	public static final NoiseMap noiseMap = new NoiseMap();
	public static final Image image = new Image();
	public static final ColorOld color = new ColorOld();
	
	public static boolean maximized;
	
	public Cursor cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	
	public Apricot(String title, int width, int height) {
		frame = new JFrame(title);
		canvas = new Canvas();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(canvas);
		canvas.setSize(width, height);
		canvas.setPreferredSize(new Dimension(width, height));
		frame.pack();
		frame.setVisible(true);
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		frame.setMaximizedBounds(e.getMaximumWindowBounds());
		frame.setLocationRelativeTo(null);

		mouse = new Mouse(this);
		keyboard = new Keyboard(this);
	}
	
	public Apricot(String title, int width, int height, boolean undecorated) {
		frame = new JFrame(title);
		canvas = new Canvas();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setUndecorated(undecorated);
		frame.add(canvas);
		canvas.setSize(width, height);
		canvas.setPreferredSize(new Dimension(width, height));
		frame.pack();
		frame.setVisible(true);
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		frame.setMaximizedBounds(e.getMaximumWindowBounds());
		frame.setLocationRelativeTo(null);

		mouse = new Mouse(this);
		keyboard = new Keyboard(this);
	}
	
	

	/**
	 * Starts the gameloop. Note that this method will not run if the engine
	 * already has a loop running.
	 */
	@Override
	public void run() {
		// Game loop begins here
		double previous = System.currentTimeMillis();
		double lag = 0;
		int frames = 0;
		double elapsedFPS = 0;
		while (frame.isDisplayable()) {
			double current = System.currentTimeMillis();
			double elapsed = current - previous;

			previous = current;

			lag += elapsed;
			elapsedFPS += elapsed;

			while (lag >= dt) {
				if (world != null)
					world.tick();
				lag -= dt;
				ticks++;
				if(cursor.getType() != frame.getCursor().getType()) {
					frame.setCursor(cursor);
				}
			}
			frames++;
			render();
			if(elapsedFPS > 1000) {
				fps = frames;
				frames = 0;
				elapsedFPS = 0;
			}
		}
	}

	public void render() {
		BufferStrategy bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		if (world != null)
			world.render(g);

		g.dispose();
		bs.show();
	}

	public void input(InputEvent e) {
		if (world != null)
			world.input(e);
	}
	
	public void setWorld(World world) {
		this.world = world;
		input(InputEvent.WORLD_CHANGE);
	}
	
	public World getWorld(World world) {
		return world;
	}
	
	public void minimize() {
		frame.setState(JFrame.ICONIFIED);
	}

	/**
	 * Recovers JFrame from being minimized
	 */
	public void normalize() {
		frame.setState(JFrame.NORMAL);
		frame.requestFocusInWindow();
	}

	/**
	 * If JFrame is maximum size, sets to default size as dicted by settings. Else,
	 * sets to maximum size.
	 */
	public void maximize() {
		if (maximized) {
			frame.setSize(1106, 640);
			frame.setLocationRelativeTo(null);
		} else {
			Rectangle usableBounds = SunGraphicsEnvironment.getUsableBounds(frame.getGraphicsConfiguration().getDevice());
			frame.setMaximizedBounds(usableBounds);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		maximized = !maximized;
	}
	
	public int width() {
		return frame.getContentPane().getWidth();
	}
	
	public int height() {
		return frame.getContentPane().getHeight();
	}
	
	public void setDeltaT(double dt) {
		this.dt = dt;
	}
}
