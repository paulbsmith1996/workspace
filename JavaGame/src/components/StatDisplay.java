package components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Enums.Stats;
import gameobjects.Creature;

public class StatDisplay {

	private final int ARC_WIDTH = 10, ARC_HEIGHT = 10;
	private final int X_OFFSET = 5, Y_OFFSET = 0;
	private final int INNER_X_OFFSET = 3, INNER_Y_OFFSET = 3;
	private final int X_BAR_OFFSET = 5;
	private final int Y_TOP_OFFSET = 40, Y_BOTTOM_OFFSET = 10;
	private final int TEXT_OFFSET_X = 10, TEXT_OFFSET_Y = 3;
	
	private Creature crt;
	private int pos;
	
	private StatBar healthBar;
	private StatBar manaBar;
	
	private int x, y;
	private int width, height;
	
	// If pos is 0 put on right of c if pos is 1 put on left
	public StatDisplay(Creature c, int pos) {
		this.crt = c;
		this.pos = pos;
		
		this.width = 2*INNER_X_OFFSET + 2*X_BAR_OFFSET + StatBar.BAR_LENGTH;
		this.height = 2*INNER_Y_OFFSET + (Y_TOP_OFFSET + Y_BOTTOM_OFFSET) + StatBar.BAR_HEIGHT;
		
		// On right
		if(pos == 0) {
			this.x = crt.getX() + crt.getWidth() + X_OFFSET; 
		// On left
		} else if(pos == 1) {
			this.x = crt.getX() - X_OFFSET - width;
		}
		
		this.y = crt.getY() + crt.getHeight() - Y_OFFSET - height;
		
		this.healthBar = new StatBar(Stats.HEALTH, crt,
									 x + INNER_X_OFFSET + X_BAR_OFFSET, 
									 y + INNER_Y_OFFSET + Y_TOP_OFFSET,
									 pos,
									 Color.GREEN);
		
		this.manaBar = new StatBar(Stats.MANA, crt,
								   x + INNER_X_OFFSET + X_BAR_OFFSET, 
								   y + INNER_Y_OFFSET + Y_TOP_OFFSET + StatBar.BAR_HEIGHT,
								   pos,
								   Color.BLUE);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRoundRect(x, y, width, height, ARC_WIDTH, ARC_HEIGHT);
		
		g.setColor(Color.BLACK);
		g.drawRoundRect(x, y, width, height, ARC_WIDTH, ARC_HEIGHT);
		g.drawRoundRect(x + INNER_X_OFFSET, 
						y + INNER_Y_OFFSET,
						width - 2*INNER_X_OFFSET,
						height - 2*INNER_Y_OFFSET, ARC_WIDTH, ARC_HEIGHT);
		
		healthBar.draw(g);
		manaBar.draw(g);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("TimesRoman", Font.BOLD, 16));
		g.drawString("Health: " + crt.getHp() + "/" + crt.getMaxHP(),
					x + INNER_X_OFFSET + TEXT_OFFSET_X,
					y + INNER_Y_OFFSET + TEXT_OFFSET_Y + g.getFontMetrics().getHeight());
		
		g.drawString("Mana: " + crt.getMana() + "/" + crt.getMaxMana(),
					x + INNER_X_OFFSET + TEXT_OFFSET_X,
					y + INNER_Y_OFFSET + TEXT_OFFSET_Y + 2*g.getFontMetrics().getHeight());
		
	}
}
