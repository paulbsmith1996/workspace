package gameobjects;

import resourceloaders.Images;

public class WizardDoor extends Obstacle{

	public WizardDoor(int x, int y) {
		super(x, y, Images.wizardDoor);
	}
	
	public WizardDoor() {
		super(0,0,Images.wizardDoor);
	}
}
