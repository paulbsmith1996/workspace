package components;

import java.awt.Graphics;

import gameobjects.Creature;

public class StatusBar {

	private HealthBar hBar;
	private ManaBar mBar;
	
	public StatusBar(Creature c) {
		hBar = new HealthBar(c);
		mBar = new ManaBar(c);
	}
	
	public void draw(Graphics g) {
		hBar.draw(g);
		mBar.draw(g);
	}
}
