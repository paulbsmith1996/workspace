package gameobjects;

import resourceloaders.Images;

public class Falkon extends NPC {
	
	public Falkon(int x, int y, int velX, int velY, int level) {
		super(x, y, velX, velY, "Falkon", Images.falkon);
		
		this.maxHP = 100 + 40 * level;
		this.health = maxHP;
		this.maxMana = 100 + 20 * level;
		this.mana = maxMana;
		this.ap = 68 + 16 * level;
		this.dp = 63 + 14 * level;
		this.ma = 52 + 13 * level;
		this.md = 54 + 10 * level;
	}
	
}
