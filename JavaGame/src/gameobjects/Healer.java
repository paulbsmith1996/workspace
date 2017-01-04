package gameobjects;

import resourceloaders.Images;

public class Healer extends Interactable {
	
	public Healer(int x, int y) {
		super(x, y, Images.healer);
		String[] dialogue = {"Hello. You seem tired and weary.",
				"I have the power of healing, let me help.", 
				"You were fully healed."};
		setDialogue(dialogue);
		this.interactType = Interactable.FRIENDLY;
	}
	
	public Healer() {
		super(Images.healer);
		String[] dialogue = {"Hello. You seem tired and weary.",
		"I have the power of healing, let me help.", 
		"You were fully healed."};
		setDialogue(dialogue);
		this.interactType = Interactable.FRIENDLY;
	}
	
	public void heal(Player p) {
		p.setHP(p.getMaxHP());
	}

}
