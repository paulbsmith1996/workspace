package components;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NumberSelector extends KeyAdapter {

	private String display;
	private String tensDig;
	private int curPosition;
	private String onesDig;
	private int x, y;
	private boolean ready, visible;
	
	public NumberSelector(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.ready = false;
		this.visible = true;
		
		tensDig = "0";
		onesDig = "0";
		curPosition = 1;
		display = tensDig + onesDig;
	}
	
	public String getValue() { return this.display; }
	public boolean isReady() { return this.ready; }
	public void setReady(boolean b) { this.ready = b; }
	public void setVisible(boolean b) { this.visible = b; }
	
	@Override
	public void keyPressed(KeyEvent evt) {
		int key = evt.getKeyCode();
		if(key == KeyEvent.VK_UP) {
			if(curPosition == 0) {
				tensDig = add(tensDig, "1");
			} else if(curPosition == 1) {
				onesDig = add(onesDig, "1");
			}
		} else if(key == KeyEvent.VK_DOWN) {
			if(curPosition == 0) {
				tensDig = minus(tensDig, "1");
			} else if(curPosition == 1) {
				onesDig = minus(onesDig, "1");
			}
		} else if(key == KeyEvent.VK_RIGHT) {
			if(curPosition == 0) {
				curPosition = 1;
			}
		} else if(key == KeyEvent.VK_LEFT) {
			if(curPosition == 1) {
				curPosition = 0;
			}
		} else if(key == KeyEvent.VK_S) {
			ready = true;
		}
		display = tensDig + onesDig;
	}
	
	@Override
	public void keyReleased(KeyEvent evt) {
		
	}
	
	@Override
	public void keyTyped(KeyEvent evt) {
		
	}
	
	public void draw(Graphics g) {
		if (visible) {
			int OFFSET = curPosition * 15;
			g.setColor(Color.RED);
			g.fillRect(x, y + 10, 30, 20);
			g.setColor(Color.BLACK);
			g.drawRect(x, y + 10, 15, 20);
			g.drawRect(x + 15, y + 10, 15, 20);

			// Draw top arrow
			g.drawLine(x + 3 + OFFSET, y + 7, x + 7 + OFFSET, y + 3);
			g.drawLine(x + 7 + OFFSET, y + 3, x + 11 + OFFSET, y + 7);

			// Draw bottom arrow
			g.drawLine(x + 3 + OFFSET, y + 33, x + 7 + OFFSET, y + 37);
			g.drawLine(x + 7 + OFFSET, y + 37, x + 11 + OFFSET, y + 33);

			// Draw digits
			g.drawString(tensDig, x + 1, y + 26);
			g.drawString(onesDig, x + 16, y + 26);
		}
	}
	
	public String add(String a, String b) {
		int first = Integer.parseInt(a);
		int second = Integer.parseInt(b);
		int total = first + second;
		String result = Integer.toString(total);
		return result.substring(result.length() - 1);
	}
	
	public String minus(String a, String b) {
		if(a.equals("0")) {
			return "9";
		}
		int first = Integer.parseInt(a);
		int second = Integer.parseInt(b);
		int total = first - second;
		String result = Integer.toString(total);
		return result;
	}
}
