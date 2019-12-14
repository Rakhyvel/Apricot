package test;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Map;
import com.josephs_projects.library.Registrar;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

public class Terrain implements Element {
	int width = 1025;
	int height = 1025;
	Map map = new Map(width, height, 2);
	int[] mapImage;
	int[] tile = Main.spritesheet.getSubset(2, 0, 64);
	Tuple offset = new Tuple(0, 0);

	int xOffset = 0;
	int yOffset = 0;

	public Terrain() {
		mapImage = map.getImage();
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Render r) {
		// Could be optimized to only iterate over tiles known to be visible, but
		// probably wouldn't help much
		int size = Main.zoom;
		r.drawRect(0, 0, 13 * 64, 7 * 64, 254 << 24);
//		int[] image = Render.getScreenBlend(mapImage[(int)Main.player.getPosition().getX() + (int)Main.player.getPosition().getY() * 1025], tile);
		for (int i = 0; i < mapImage.length; i++) {
			int x = (i % width) * size - Main.player.getX();
			int y = (i / width) * size - Main.player.getY();
			if (x < -size)
				continue;
			if (y < -size)
				continue;
			if (x - size > Registrar.canvas.getWidth())
				continue;
			if (y - size > Registrar.canvas.getHeight())
				continue;
//			r.drawImage(x, y, size, image, 1, 0);
			r.drawRect(x, y, size, size, mapImage[i]);
		}
	}

	@Override
	public void input() {
	}

	public Tuple getPosition() {
		// Should never be called
		return new Tuple(2000, -2000);
	}
	
	@Override
	public Element setPosition(Tuple position) {
		return this;
	}

	public void remove() {
		// Can't do that :)
	}

	public float getPlot(Tuple point) {
		return map.getPlot(point);
	}

	/**
	 * 
	 * @param point
	 * @return An double corresponding to the precipitation at that point ranging from 0-99, 0 being moist, 99 being dry.
	 */
	public double getPrecipitation(Tuple point) {
		return map.getPrecipitation(point);
	}

	public int getTemp(Tuple point) {
		return (int) map.getTemp(point);
	}
	
	public Element clone() {
		return null;
	}
}
