package components;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.rmi.server.SkeletonNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import misc.Game;

public class TextBox extends Rectangle {

	private final int INNER_X_OFFSET = 3, INNER_Y_OFFSET = 3;
	private final int TEXT_X_OFFSET = 5, TEXT_Y_OFFSET = 3;
	private final int BUFFER = INNER_Y_OFFSET + 3;
	private final int LINE_SPACING = 1;
	
	private String text = "";
	private Scanner textScan;
	private Vector<String> textParts;
	
	private boolean visible;
	private boolean wrap = true;
	private boolean resize = false;
	private Game game;
	
	private final int ARC_WIDTH = 10, ARC_HEIGHT = 10;

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

	public TextBox(int width, int height, boolean resize) {
		super(width, height);
		this.resize = resize;
		setText("");
		textParts = new Vector<String>();
	}

	public TextBox(int x, int y, int width, int height, Game game, boolean resize) {
		this(x, y, width, height, "", game, resize);
	}
	
	public TextBox(int x, int y, int width, int height, String text, Game game, boolean resize) {
		super(x, y, width, height);
		setText(text);
		this.game = game;
		this.resize = resize;
		textParts = new Vector<String>();
	}

	public TextBox(Point p, Dimension d) {
		super(p, d);
	}

	public void setText(String text) {
		this.text = text;
		textScan = new Scanner(text);
	}
	
	public void setHeight(int val) { this.height = val; }

	// Draw the text box
	public void draw(Graphics g) {
		if (!visible) {
			return;
		}
			
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));

		if (wrap) {
			textScan.useDelimiter(" ");
			String curLine = "";
			while (textScan.hasNext()) {
				String nextWord = textScan.next();
				int lineLength = g.getFontMetrics().stringWidth(curLine + nextWord);

				if (lineLength > this.width - 2 * (TEXT_X_OFFSET + INNER_X_OFFSET + 4 * BUFFER)) {
					textParts.addElement(curLine + nextWord);
					curLine = "";
				} else {
					curLine += nextWord + " ";
				}

			}

			if (!curLine.equals("")) {
				textParts.addElement(curLine);
			}

		} else {
			if (!textScan.delimiter().toString().equals("\\p{javaWhitespace}+")) {
				while (textScan.hasNext()) {
					String nextWord = textScan.next();
					if (!nextWord.equals("")) {
						textParts.add(nextWord);
					}
				}
			} else {
				if (!text.equals("")) {
					textParts.add(text);
				}
			}
		}
		
		if (textParts.isEmpty()) {
			return;
		}

		if (resize) {
			int fontHeight = g.getFontMetrics().getHeight();
			int numLines = textParts.size();
			height = fontHeight * numLines // Count for height of actual lines
					+ 2 * INNER_Y_OFFSET // Account for offset at top and bottom
					+ (numLines - 1) * LINE_SPACING // Account for spacing
													// between lines
					+ 2 * TEXT_Y_OFFSET; // Account for offset between inner
											// rect and text

			while (y + height > game.getHeight()) {
				y--;
			}
		}

		g.setColor(Color.WHITE);
		g.fillRoundRect(x, y, width, height - 3, ARC_WIDTH, ARC_HEIGHT);

		g.setColor(Color.BLACK);
		g.drawRoundRect(x, y, width, height - 3, ARC_WIDTH, ARC_HEIGHT);

		g.setColor(Color.BLACK);
		g.drawRoundRect(x + INNER_X_OFFSET, y + INNER_Y_OFFSET, width - 2 * INNER_X_OFFSET,
				height - 2 * INNER_Y_OFFSET - 3, ARC_WIDTH, ARC_HEIGHT);

		g.setColor(Color.BLACK);

		int tempY = y + g.getFontMetrics().getHeight() + INNER_Y_OFFSET + (TEXT_Y_OFFSET - BUFFER);
		for (String s : textParts) {
			g.drawString(s, x + INNER_X_OFFSET + TEXT_X_OFFSET, tempY);
			tempY += g.getFontMetrics().getHeight() + LINE_SPACING;
		}

		textParts = new Vector<String>();
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
