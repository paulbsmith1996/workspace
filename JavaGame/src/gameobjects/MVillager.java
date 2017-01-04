package gameobjects;

import resourceloaders.Images;

public class MVillager extends Interactable {
	
	public MVillager(int x, int y) {		
		super(x, y, Images.maleVillager);
		this.interactType = Interactable.FRIENDLY;
		String[] text = {"Hello there. I am a villager."};
		setDialogue(text);
	}
	
	public MVillager() {
		super(Images.maleVillager);
		this.interactType = Interactable.FRIENDLY;
		String[] text = {"Hello there. I am a villager."};
		setDialogue(text);
	}
}