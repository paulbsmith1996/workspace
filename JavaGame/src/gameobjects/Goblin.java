package gameobjects;

import resourceloaders.Images;

public class Goblin extends NPC {
	
	public Goblin(int x, int y, int velX, int velY, int level) {
		super(x, y, velX, velY, "Goblin", Images.goblin);
		
		this.health = 50 + 40 * level;
		this.maxHP = 50 + 40 * level;
		this.maxMana = 100 + 20 * level;
		this.mana = 100 + 20 * level;
		this.ap = 30 + 15 * level;
		this.dp = 20 + 15 * level;
		this.ma = 20 + 15 * level;
		this.md = 30 + 15 * level;
	}
	
}
