package gameobjects;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


public class GameObject {

	private int x,y,width,height;
	private BufferedImage image;
	
	public GameObject(int x, int y, BufferedImage image) {
		this.x = x;
		this.y = y;
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public void setX(int val) { this.x = val; }
	public int getX() { return this.x; }
	
	public void setY(int val) { this.y = val; }
	public int getY() { return this.y; }
	
	public void setWidth(int val) { this.width = val; }
	public int getWidth() { return this.width; }
	
	public void setHeight(int val) { this.height = val; }
	public int getHeight() { return this.height; }
	
	public void setPos(int x, int y) { this.x = x; this.y = y; }
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public Rectangle leftBounds() { return new Rectangle(x,y,5,getHeight()); }
	public Rectangle rightBounds() { return new Rectangle(x + getWidth() - 5,y,5,getHeight()); }
	public Rectangle topBounds() { return new Rectangle(x, y, getWidth(),5); }
	public Rectangle bottomBounds() { return new Rectangle(x, y + getHeight() - 5, getWidth(),5); }
	
	public boolean intersects(Rectangle r) {
		return getBounds().intersects(r);
	}
	
	public double getXDistance(GameObject obj) { return Math.abs(obj.getX() - x); }
	public double getYDistance(GameObject obj) { return Math.abs(obj.getY() - y); }
	public double getDistance(GameObject obj) {
		double deltaX = getXDistance(obj);
		double deltaY = getYDistance(obj);
		return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
	}
	
	// To override
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);
	}
}
