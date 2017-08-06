package gameobjects;

import resourceloaders.Images;

public class Goblin extends NPC {
	
	public Goblin(int x, int y, int velX, int velY, int level) {
		super(x, y, velX, velY, "Goblin", Images.goblin);
		
		this.maxHP = this.health = 	1000 + 40 * level;
		this.maxMana = this.mana = 	200 + 20 * level;
		this.ap = 					250 + 15 * level;
		this.dp = 					250  + 15 * level;
		this.ma = 					160  + 15 * level;
		this.md = 					160  + 15 * level;
	}
	
}
