package misc;
import gameobjects.Player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Enums.GameState;
import Enums.MerchantState;

public class KeyInput extends KeyAdapter {

	private Player player;
	private boolean[] keyDown = new boolean[4];
	private boolean next, back, talking;
	protected GameState state;
	
	protected boolean[] options;
	private int menuWidth;
	
	private boolean pause;
	private boolean interacting;
	
	private MerchantState merchState;
	
	private int mainMovement;
	
	public KeyInput(Player player) {
		this.player = player;
		this.state = GameState.WANDER;
		
		this.mainMovement = 0;
		
		next = false;
		back = false;
		talking = false;
		
		options = new boolean[4];
		options[0] = true;
		menuWidth = 2;
		
		pause = false;
		
		merchState = MerchantState.PERUSE;
	}
	
	public void setState(GameState state) { this.state  = state; }
	public GameState getState() { return this.state; }
	
	public void setMerchState(MerchantState m) { this.merchState = m; }
	public MerchantState getMerchState() { return this.merchState; }
	
	public void setReady(boolean next) { this.next = next; }
	public void setBack(boolean back) { this.back = back; }
	public boolean goBack() { return this.back; }
	public boolean ready() { return this.next; }
	
	public boolean isTalking() { return this.talking; }
	public void setTalking(boolean b) { this.talking = b; }
	
	public boolean pause() { return pause; }
	public void setPause(boolean pause) { this.pause = pause; }
	
	public boolean isInteracting() { return this.interacting; }
	public void setInteracting(boolean b) { this.interacting = b; }
	
	public int getMainMovement() { return this.mainMovement; }
	
	public boolean[] getMenuOptions() { return this.options; }
		
	public void setOptionNum(int num) { 
		options = new boolean[num]; 
		options[0] = true;
	}
	
	public int numOps() { return options.length; }
	public boolean[] options() { return this.options; }
	
	public void setMenuWidth(int width) { menuWidth = width; }
	public int getMenuWidth() { return menuWidth; }
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		switch(state) {
		case MENU:
			mainMovement = 0;
			if(key == KeyEvent.VK_UP) {
				mainMovement = 1;
			} else if(key == KeyEvent.VK_DOWN) {
				mainMovement = -1;
			} else if(key == KeyEvent.VK_LEFT) {
				
			} else if(key == KeyEvent.VK_RIGHT) {
				
			} else if(key == KeyEvent.VK_S) {
				next = true;
			} else if(key == KeyEvent.VK_D) {
				back = true;
			}
			break;
		case MERCHANT:
			switch (merchState) {
			case CONVERSE:
				state = GameState.GAME_MENU;
				break;
			case PERUSE:
				if (key == KeyEvent.VK_S) {
					talking = true;
				} else if (key == KeyEvent.VK_D) {
					talking = false;
				}
			}
		case HOUSE:
		case WANDER:
			if(key == KeyEvent.VK_UP) {
				player.setVelY(-5);
				keyDown[2] = true;
			} else if(key == KeyEvent.VK_DOWN) {
				player.setVelY(5);
				keyDown[3] = true;
			} else if(key == KeyEvent.VK_LEFT) {
				player.setVelX(-5);
				keyDown[0] = true;
			} else if(key == KeyEvent.VK_RIGHT) {
				player.setVelX(5);
				keyDown[1] = true;
			} else if(key == KeyEvent.VK_ENTER) {
				pause = true;
			} else if(key == KeyEvent.VK_S) {
				interacting = true;
				next = true;
			} else if(key == KeyEvent.VK_D) {
				back = true;
			}
			break;
		case BATTLE:
			if(key == KeyEvent.VK_UP) {
				for(int x = 0; x < options.length; x++) {
					if(options[x] && x - menuWidth >= 0) {
						options[x] = false;
						options[x - menuWidth] = true;
						break;
					}
				}
			} else if(key == KeyEvent.VK_DOWN) {
				for(int x = 0; x < options.length - menuWidth; x++) {
					if(options[x] && x + menuWidth < options.length) {
						options[x] = false;
						options[x + menuWidth] = true;
						break;
					}
				}
			} else if(key == KeyEvent.VK_LEFT) {
				for(int x = 1; x < options.length; x++) {
					if(options[x] && x % menuWidth != 0) {
						options[x] = false;
						options[x - 1] = true;
						break;
					}
				}
			} else if(key == KeyEvent.VK_RIGHT) {
				for(int x = 0; x < options.length - 1; x++) {
					if(options[x] && (x + 1) % menuWidth != 0) {
						options[x] = false;
						options[x +1] = true;
						break;
					}
				}
			} else if(key == KeyEvent.VK_S) {
				next = true;
			} else if(key == KeyEvent.VK_D) {
				back = true;
			}
			break;
		case GAME_MENU:
			if(key == KeyEvent.VK_ENTER) {
				pause = false;
			} 
			
			if(key == KeyEvent.VK_UP) {
				for(int x = 0; x < options.length; x++) {
					if(options[x] && x - menuWidth >= 0) {
						options[x] = false;
						options[x - menuWidth] = true;
						break;
					}
				}
			} else if(key == KeyEvent.VK_DOWN) {
				for(int x = 0; x < options.length - menuWidth; x++) {
					if(options[x] && x + menuWidth < options.length) {
						options[x] = false;
						options[x + menuWidth] = true;
						break;
					}
				}
			} else if(key == KeyEvent.VK_S) {
				next = true;
			} else if(key == KeyEvent.VK_D) {
				back = true;
			}
			break;
		}
	 }

	 @Override
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();

		switch (state) {
		case MENU:
			if(key == KeyEvent.VK_S) {
				next = false;
			} else if(key == KeyEvent.VK_UP) {
				mainMovement = 0;
			} else if(key == KeyEvent.VK_DOWN) {
				mainMovement = 0;
			} else if(key == KeyEvent.VK_D) {
				back = false;
			}
			break;
		case MERCHANT:
		case HOUSE:
		case WANDER:
			if (key == KeyEvent.VK_UP) {
				player.setVelY(0);
				keyDown[2] = false;
			} else if (key == KeyEvent.VK_DOWN) {
				player.setVelY(0);
				keyDown[3] = false;
			} else if (key == KeyEvent.VK_LEFT) {
				player.setVelX(0);
				keyDown[0] = false;
			} else if (key == KeyEvent.VK_RIGHT) {
				player.setVelX(0);
				keyDown[1] = false;
			} else if(key == KeyEvent.VK_S) {
				interacting = false;
				next = false;
			} else if(key == KeyEvent.VK_D) {
				back = false;
			}
			break;
		case BATTLE:
			if(key == KeyEvent.VK_UP) {
				
			} else if(key == KeyEvent.VK_DOWN) {
				
			} else if(key == KeyEvent.VK_LEFT) {
				
			} else if(key == KeyEvent.VK_RIGHT) {
				
			} else if(key == KeyEvent.VK_S) {
				next = false;
			} else if(key == KeyEvent.VK_D) {
				back = false;
			}
			break;
		case GAME_MENU:		
			if(key == KeyEvent.VK_S) {
				next = false;
			} else if(key == KeyEvent.VK_D) {
				back = false;
			}
			break;
		}
	 }
	 
	 /*
	 public interface KeyHandler {
		 public void run();
	 }
	 
	 private static List<Map<Integer,KeyHandler>> KEY_REGISTRY = new ArrayList<Map<Integer,KeyHandler>>();
	 
	 public static void pushKeyRegistry() {
		 KEY_REGISTRY.add(new HashMap<Integer,KeyHandler>());
	 }
	 public static void popKeyRegistry() {
		 // R
	 }
	 
	 public void enterMenu() {
		 try {
			 KeyRegistry kr = pushKeyRegistry();
			 kr.registerKeyHandler(KEY_M, doMenuEntry());
		 } finally {
			 popKeyRegistry();
		 }
	 }
	 public static void registerKeyHandler(int key, KeyHandler handler) {
		 KEY_REGISTRY.put(key, handler);
	 }
	 */
	 
	 public void keyTyped(KeyEvent e) {
		 int key = e.getKeyCode();
			
			switch(state) {
			case MENU:
				break;
			case WANDER:
				break;
			case BATTLE:
				if(key == KeyEvent.VK_UP) {
					if (options[2] == true) {
						options[2] = false;
						options[0] = true;
					} else if (options[3] == true) {
						options[3] = false;
						options[1] = true;
					}
				} else if(key == KeyEvent.VK_DOWN) {
					if(options[0] == true) {
						options[0] = false;
						options[2] = true;
					} else if(options[1] == true) {
						options[1] = false;
						options[3] = true;
					}
				} else if(key == KeyEvent.VK_LEFT) {
					if(options[1] == true) {
						options[1] = false;
						options[0] = true;
					} else if(options[3] == true) {
						options[3] = false;
						options[2] = true;
					}
				} else if(key == KeyEvent.VK_RIGHT) {
					if(options[0] == true) {
						options[0] = false;
						options[1] = true;
					} else if(options[2] == true) {
						options[2] = false;
						options[3] = true;
					}
				} else if(key == KeyEvent.VK_S) {
					next = true;
				}
				break;
			case GAME_MENU:
				break;
				
			}
		 
	 }
}
