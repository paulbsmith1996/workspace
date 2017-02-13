package com.apprun;
import java.awt.Color;
import java.awt.Rectangle;

/**
 * 
 * @author paulbairdsmith
 * Holds a rectangle and a color. Serves as a "breadcrumb" that is laid down by a crank
 * as it moves. The color of a ColoredRect object signifies the color of the rectangle's
 * border.
 */
public class ColoredRect {

	// Declare rectangle and color
	private Rectangle rect;
	private Color color;
	
	
	public ColoredRect(Rectangle rect, Color color) {
		this.rect = rect;
		this.color = color;
	}

	/**
	 * Get/Set the rectangle of the ColoredRect object
	 */
	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	/**
	 * Get/Set the color of the ColoredRect object
	 */
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
