package components;
import gameobjects.Creature;

import java.awt.Color;
import java.awt.Graphics;


public class HealthBar {

	private Creature c;
	
	public HealthBar(Creature c) {
		this.c = c;
	}
	
	public void draw(Graphics g) {
		
		int cX = c.getX();
		int cY = c.getY();
		int height = c.getHeight() * 2;
		
		g.setColor(Color.BLACK);
		g.drawRect(cX - 8, cY - height / 2, 3, height);
		
		int health = c.getHp();
		int maxHealth = c.getMaxHP();
		int proportion = height * health / maxHealth;
		
		String healthDisplay = "Health: " + Integer.toString(health) + "/" 
				+ Integer.toString(maxHealth);
		
		g.drawString(healthDisplay, cX - 30, cY - 60);
		
		if(health > 0) {
			g.setColor(Color.GREEN);
			g.fillRect(cX - 8, cY + (height / 2 - proportion), 3, proportion);
		}
	}
}
