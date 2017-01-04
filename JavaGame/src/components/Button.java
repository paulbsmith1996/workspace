package components;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Button - This object is a selectable button, used for the main
 * menu. Button is drawn differently depending on whether it is
 * selected according to the relevant KeyInput object
 * 
 * @author Paul
 *
 */
public class Button extends Rectangle {

	/** 
	 * Ints representing the position of top left corner of button and
	 * its dimensions 
	 */
	private int x, y, width, height;
	private String text;
	
	/**
	 * Represents the amount of offset the text will have relative to
	 * Button's boundaries 
	 */
	private int OFFSET = 20;
	
	/**
	 * Constructor taking all the variables needed to create Rectangle,
	 * as well as a text String representing Button's text
	 * 
	 * @param x - x coordinate of top left corner of Button
	 * @param y - y coordinate of top left corner of Button
	 * @param width - desired width of Button
	 * @param height - desired height of Button
	 * @param text - text to appear inside Button
	 */
	public Button(int x, int y, int width, int height, String text) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
	}
	
	public void setText(String text) { this.text = text; }
	
	// Instructions to draw Button object
	// Takes boolean selected to determine how Button object should be
	// drawn
	public void draw(Graphics g, boolean selected) {
		
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, height);
		
		// Change color depending on whether or not current Button
		// is selected
		if(!selected) {
			g.setColor(Color.BLACK);
		} else {
			g.setColor(Color.RED);
		}
		
		// Draw boundary of Button object
		g.drawRect(x, y, width, height);
		
		// Set correct Font and draw String
		g.setFont(new Font("TimesRoman", Font.BOLD, 18));
		
		FontMetrics m = g.getFontMetrics();
		int strLen = m.stringWidth(text);
		g.drawString(text, x + (width / 2) - (strLen / 2), y + OFFSET);
	}
}
