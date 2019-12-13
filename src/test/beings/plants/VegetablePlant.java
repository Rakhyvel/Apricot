package test.beings.plants;

import com.josephs_projects.library.Element;
import com.josephs_projects.library.Tuple;
import com.josephs_projects.library.graphics.Render;

import test.Main;
import test.beings.Being;
import test.holdables.VegetableObject;
import test.interfaces.Holdable;
import test.interfaces.Interactable;

public class VegetablePlant extends Plant implements Element, Interactable{
	// 1841 2008
	Vegetable type;
	
	public VegetablePlant(Tuple position, Vegetable type) {
		super(position);
		growthStage = Being.GrowthStage.ADULT;
		setWaterHardiness(type.waterHardiness);
		preferedTemp = type.preferedTemp;
		Main.interactables.add(this);
		this.type = type;
	}

	@Override
	public void tick() {
		if (checkUnderWater())
			remove();
		
		grow();
		decayHunger();
		drinkWater();
		dieIfDehydrated();
		dieIfBadTemp();
	}

	@Override
	public void render(Render r) {
		int x = (int) position.getX() * Main.zoom - Main.player.getX() + Main.zoom / 2;
		int y = (int) position.getY() * Main.zoom - Main.player.getY() + Main.zoom / 2;
		switch (growthStage) {
		case BABY:
			r.drawImage(x, y, 64, type.babyImage, 1, 0);
			break;
		case CHILD:
			r.drawImage(x, y, 64, type.childImage, 1, 0);
			break;
		case SUBADULT:
			r.drawImage(x, y, 64, type.subAdultImage, 1, 0);
			break;
		case ADULT:
			r.drawImage(x, y, 64, type.adultImage, 1, 0);
			break;
		}
	}

	@Override
	public void input() {}

	public void remove() {
		Main.r.removeElement(this);
		Main.interactables.remove(this);
	}

	@Override
	public void interact(Holdable hand) {
		if(hand == null) {
			Main.r.addElement(new VegetableObject(new Tuple(position), type));
			remove();
		}
	}
}
