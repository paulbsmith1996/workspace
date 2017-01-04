package components;
import gameobjects.Creature;

import java.awt.Color;
import java.awt.Graphics;


public class ManaBar {

	private Creature c;
	
	public ManaBar(Creature c) {
		this.c = c;
	}
	
	public void draw(Graphics g) {
		
		int cX = c.getX();
		int cY = c.getY();
		int height = c.getHeight() * 2;
		
		g.setColor(Color.BLACK);
		g.drawRect(cX - 12, cY - height / 2, 3, height);
		
		int mana = c.getMana();
		int maxMana = c.getMaxMana();
		int proportion = height * mana / maxMana;
		
		String manaDisplay = "Mana: " + Integer.toString(mana) + "/" 
				+ Integer.toString(maxMana);
		
		g.drawString(manaDisplay, cX - 30, cY - 45);
		
		if(mana > 0) {
			g.setColor(Color.BLUE);
			g.fillRect(cX - 12, cY + (height / 2 - proportion), 3, proportion);
		}
	}
}
