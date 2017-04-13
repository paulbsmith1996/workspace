package components;

import java.awt.Color;
import java.awt.Graphics;

import Enums.Stats;
import gameobjects.Creature;

public class StatBar {

	public static final int BAR_LENGTH = 150, BAR_HEIGHT = 5;
	private final int DEFAULT_X = 0, DEFAULT_Y = 0;
	
	private int max;
	private double proportion;
	private Stats stat;
	
	private Color color;
	
	private Creature c;
	private int x, y;
	
	private int pos;
	
	// pos 0 puts bar on right of creature, pos 1 puts on left
	public StatBar(Stats stat, Creature c, int x, int y, int pos, Color color) {
		
		this.x = x;
		this.y = y;
		
		switch(stat) {
		case HEALTH:
			max = c.getMaxHP();
			break;
		case MANA:
			max = c.getMaxMana();
			break;
		}	
		this.proportion = 1.0;
		
		this.color = color;
		
		this.c = c;
		this.stat = stat;

	}
	
	public StatBar(int max, Color color) {
		
		this.x = DEFAULT_X;
		this.y = DEFAULT_Y;
		
		this.max = max;
		this.proportion = 1.0;
		
		this.color = color;

	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int amountLeft() {
		int amountLeft = 0;
		switch(stat) {
		case HEALTH:
			amountLeft = c.getHp();
			break;
		case MANA:
			amountLeft = c.getMana();
			break;
		}	
		return amountLeft;
	}
	
	public int max() {
		int max = 0;
		switch(stat) {
		case HEALTH:
			max = c.getMaxHP();
			break;
		case MANA:
			max = c.getMaxMana();
			break;
		}	
		return max;
	}
	
	public void draw(Graphics g) {
		
		proportion = (double)amountLeft() / (double)max();
		
		g.setColor(color);
		if(pos == 0) {
			// To the right
			// All variables are good to go
		} else if(pos == 1) {
			// To the left
			x = (int)((double)BAR_LENGTH * (1 - proportion));
		}
		g.fillRect(x, y, (int)((double)BAR_LENGTH * proportion), BAR_HEIGHT);
		
		g.setColor(Color.BLACK);
		g.drawRect(x, y, BAR_LENGTH, BAR_HEIGHT);
	}
}
