package components;
import java.awt.Color;
import java.awt.Graphics;

import misc.Game;
import misc.KeyInput;


public class TextMenu {

	private String[] choices;
	private final int OFFSET = 10;
	private int x, y, width;
	private boolean visible = true;
	private int selected;
	private Game game;
	private KeyInput kInput;
	private boolean canExit;
	
	public TextMenu(String[] choices, int width, int x, int y, 
			Game game, boolean canExit) {
		this.x = x;
		this.y = y;
		
		this.canExit = canExit;
		this.width = width;
		
		this.game = game;
		this.kInput = game.getInterpreter();
		this.choices = choices;
		
		selected = 0;
	}
	
	public void setOptions(String[] options) { this.choices = options; }
	public String[] getOptions() { return choices; }
	public int getSelected() { 
		for(int x = 0; x < kInput.numOps(); x++) {
			if(kInput.getMenuOptions()[x]) {
				selected = x;
			}
		}
		return selected;
	}
	public void setSelected(int val) {
		for(int x = 0; x < kInput.numOps(); x++) {
			kInput.getMenuOptions()[x] = false;
		}
		if(val < kInput.getMenuOptions().length) {
			kInput.getMenuOptions()[val] = true;
		}
		selected = val; 
	}
	
	public int numChoices() { return choices.length; }
	
	public void draw(Graphics g) {
		int optionNum = 1;
		
		for(int x = 0; x < kInput.getMenuOptions().length; x++) {
			if(kInput.getMenuOptions()[x]) {
				selected = x;
				break;
			}
		}
		
		if (visible) {
			
			g.setColor(Color.RED);
			String longest = "";
			for(String s: choices) {
				if(s.length() > longest.length()) {
					longest = s;
				}
			}
			int length = longest.length();
			g.fillRect(x - 3, y - 3, length * 10 * width, 
					choices.length * g.getFontMetrics().getHeight());
			
			int tempX = x;
			int tempY = y;
			
			for (String s : choices) {
				
				if(optionNum == selected + 1) {
					g.setColor(Color.BLUE);
					g.fillOval(tempX, tempY + OFFSET / 2, 3, 3);
					
				}
				
				g.setColor(Color.BLACK);
				g.drawString(s, tempX + OFFSET, tempY + OFFSET);
				if (optionNum % width != 0) {
					tempX += s.length() * 10;
				} else {
					tempY += g.getFontMetrics().getHeight();
					tempX = x;
				}

				optionNum++;
			}
		}
	}
	
	public void setVisible(boolean isVisible) {
		visible = isVisible;
	}
	
	public void select() {
		game.sleep(60);
		// Update KeyInput's properties to reflect dimensions/choices of
		// menu m
		kInput.setMenuWidth(width);
		kInput.setOptionNum(choices.length);

		// Remove any text Objects still visible
		//t.setVisible(false);

		// Check this to see if better way of writing it
		// (Most likely there is)
		while (!kInput.ready()) {
			game.repaint();
			if (canExit && kInput.goBack()) {
				setSelected(numChoices());
				break;
			}
			game.sleep(1000 / 30);
		}

		// Reset KeyInput's booleans for next time menu must be used
		kInput.setBack(false);
		kInput.setReady(false);
	}
}
