package components;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Scanner;
import java.util.Vector;

import misc.Game;

public class TextBox extends Rectangle {

	String text = "";
	Scanner textScan;
	private final int X_OFFSET = 10, Y_OFFSET = 10;
	private boolean visible;
	private boolean wrap = true;
	private Game game;

	public TextBox() {
		super();
	}

	public TextBox(Point p) {
		super(p);
	}

	public TextBox(Rectangle r) {
		super(r);
	}

	public TextBox(Dimension d) {
		super(d);
	}

	public TextBox(int width, int height) {
		super(width, height);
	}

	public TextBox(int x, int y, int width, int height, Game game) {
		super(x, y, width, height);
		this.game = game;
	}
	
	public TextBox(int x, int y, int width, int height, String text, Game game) {
		super(x, y, width, height);
		this.text = text;
		this.game = game;
		textScan = new Scanner(text);
	}

	public TextBox(Point p, Dimension d) {
		super(p, d);
	}

	public void setText(String text) {
		this.text = text;
		textScan = new Scanner(text);
	}
	
	public void setHeight(int val) { this.height = val; }

	public void draw(Graphics g) {
		if (visible) {
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
			g.setColor(Color.RED);
			g.fillRect(x,y,width,height);
			g.setColor(Color.BLACK);
			
			g.setFont(new Font("TimesRoman", Font.BOLD, 14));
			Vector<String> textParts = new Vector<String>();
			
			if (wrap && text.length() > width / 10) {
				String curPart = "";
				while (textScan.hasNext()) {
					String nextWord = textScan.next();
					curPart += nextWord + " ";

					if (curPart.length() > width / 10) {
						textParts.add(curPart);
						curPart = "";
					}

				}
				textParts.add(curPart);
			} else {			
				if(!textScan.delimiter().toString().equals("\\p{javaWhitespace}+")) {
					while(textScan.hasNext()) {
						textParts.add(textScan.next());
					}
				} else {
					textParts.add(text);
				}				
			}

			int tempY = y - 5;
			for (String s : textParts) {
				g.drawString(s, x + X_OFFSET, tempY += 5 + Y_OFFSET);
			}
		}
	}
	
	public void setDelimiter(String separator) {
		textScan.useDelimiter(separator);
	}
	
	public void setWrap(boolean wrap) { this.wrap = wrap; }
	
	public void setVisible(boolean isVisible) {
		visible = isVisible;
	}
	
	public void display() {
		setVisible(true);
		game.repaint();
		game.waitInput();
	}
}
