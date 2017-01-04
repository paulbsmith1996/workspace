package gameobjects;

import resourceloaders.Images;

public class GoblinBoss extends Boss{

	public GoblinBoss(int x, int y, int level) {
		super("Goblin Boss", x, y, Images.goblinBoss, Boss.GOBLIN, level);
	}
	
	public GoblinBoss(int level) {
		super("Goblin Boss", 0, 0, Images.goblinBoss, Boss.GOBLIN, level);
	}
}
