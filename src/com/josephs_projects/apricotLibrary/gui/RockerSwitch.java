package com.josephs_projects.apricotLibrary.gui;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import com.josephs_projects.apricotLibrary.Apricot;
import com.josephs_projects.apricotLibrary.Tuple;
import com.josephs_projects.apricotLibrary.World;
import com.josephs_projects.apricotLibrary.input.InputEvent;

public class RockerSwitch extends GUIObject {
	int width;
	int height;
	ColorScheme scheme;
	public String text;
	public Label label;
	boolean isHovered;
	boolean isClicked;
	public boolean value = false;

	public RockerSwitch(String text, int width, int height, ColorScheme scheme, Apricot apricot, World world) {
		super(new Tuple(), apricot, world);
		this.width = width;
		this.height = height;
		this.scheme = scheme;
		this.text = text;
		world.add(this);
		label = new Label(text, scheme, apricot, world);
		label.fontSize = 17;
	}

	@Override
	public void render(Graphics2D g) {
		if (!shown)
			return;

		// Draw background
		if (value) {
			g.setColor(scheme.fillColor);
		} else {
			g.setColor(scheme.highlightColor);
		}
		g.fillRect((int) position.x + label.width(), (int) position.y, width, height);

		// Draw rocker fill
		int xOffset = value ? width / 2 + 4 : 0;
		g.setColor(scheme.backgroundColor);
		g.fillRect((int) position.x + label.width() + xOffset, (int) position.y, width / 2 - 4, height);

		// Draw rocker border
		g.setColor(scheme.borderColor);
		g.setStroke(new BasicStroke(2));
		g.drawRect((int) position.x + label.width() + xOffset, (int) position.y, width / 2 - 4, height);
	}

	@Override
	public void input(InputEvent e) {
		if (!shown)
			return;
		if (e == InputEvent.MOUSE_MOVED) {
			int diffX = (int) (apricot.mouse.position.x - position.x - label.width());
			int diffY = (int) (apricot.mouse.position.y - position.y);
			isHovered = (diffX > 0 && diffX < width) && (diffY > 0 && diffY < height);
		}
		if (e == InputEvent.WORLD_CHANGE) {
			isHovered = false;
		}
		if (e == InputEvent.MOUSE_LEFT_DOWN) {
			isClicked = isHovered;
		}
		if (e == InputEvent.MOUSE_LEFT_RELEASED) {
			// Call caller and let them know button is clicked.
			if (isClicked) {
				value = !value;
			}
			isClicked = false;
		}

	}

	@Override
	public void remove() {
		world.remove(this);
		label.remove();
	}

	@Override
	public int getRenderOrder() {
		return renderOrder;
	}

	@Override
	public void setShown(boolean shown) {
		this.shown = shown;
		label.setShown(shown);
	}

	public void updatePosition(Tuple position) {
		this.position = position;
		label.updatePosition(position.add(new Tuple(0, height / 2 - 4)));
	}

	public int height() {
		if (!shown)
			return 0;
		return height + 2 * margin;
	}

	public int width() {
		if (!shown)
			return 0;
		return label.width() + width + 2 * margin;
	}
}
