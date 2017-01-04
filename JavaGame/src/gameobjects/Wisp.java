package gameobjects;

import resourceloaders.Images;

public class Wisp extends NPC {
	
	public Wisp(int x, int y, int velX, int velY, int level) {
		super(x, y, velX, velY, "Wisp", Images.wisp);
		
		this.maxHP = this.health = 50 + 30 * level;
		this.maxMana = this.mana = 100 + 20 * level;
		this.ap = 30 + 50 * level;
		this.dp = 20 + 5 * level;
		this.ma = 20 + 20 * level;
		this.md = 30 + 20 * level;
	}
	
}
