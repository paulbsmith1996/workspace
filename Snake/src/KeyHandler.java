
/*                                                                                
 * A class to handle all applet keyboard interactions.                            
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;

public class KeyHandler extends KeyAdapter implements KeyListener {

	public boolean up = false;
	public boolean down = false;
	public boolean left = false;
	public boolean right = true;

	public boolean moveExecuted = false;

	public void keyPressed(KeyEvent e) {
		if (!moveExecuted) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				if (!down) {
					up = true;
					down = false;
					left = false;
					right = false;
				}
				break;
			case KeyEvent.VK_DOWN:
				if (!up) {
					up = false;
					down = true;
					left = false;
					right = false;
				}
				break;
			case KeyEvent.VK_LEFT:
				if (!right) {
					up = false;
					down = false;
					left = true;
					right = false;
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (!left) {
					up = false;
					down = false;
					left = false;
					right = true;
				}
				break;
			default:
				break;
			}
			moveExecuted = true;
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

}
