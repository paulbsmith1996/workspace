package com.link;
import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * @author paulbairdsmith
 * No longer included in systems. Only studying circular cranks, and not linear
 * ones.
 */
public class Slider extends LComponent {

	private int speed;
	private int x1, y1, z1;
	private int x2, y2, z2;
	
	private double deltaY;
	private double deltaX;
	
	private int length;
	private int dir;
	
	public Slider(int x, int y, int z, int x2, int y2, int z2, int speed, Color color) {
		super(x, y, z, color);
		this.speed = speed;
		this.dir = 2;
		
		this.x1 = x;
		this.y1 = y;
		this.z1 = z;
		
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
		
		length = (int)Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));
		
		deltaY = (y2 - y1) / length;
		deltaX = (x2 - x1) / length;
		
	}
	
	public void move() {
		
		int curX = getX();
		int curY = getY();
		
		if( !(((curX <= x1 && curX >= x2) || (curX >= x1 && curX <= x2)) &&
				((curY <= y1 && curY >= y2) || (curY >= y1 && curY <= y2)))) {
			dir = -dir;
		}
		
		setX((int)(getX() + dir * deltaX));
		setY((int)(getY() + dir * deltaY));
		
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawLine(x1 + (COMPONENT_SIZE / 2), 
				y1 + (COMPONENT_SIZE / 2), 
				x2 + (COMPONENT_SIZE / 2), 
				y2 + (COMPONENT_SIZE / 2));
		g.drawOval(getX(), getY(), COMPONENT_SIZE, COMPONENT_SIZE);
	}
}
