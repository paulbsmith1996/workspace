package handlers;
import java.awt.Rectangle;

import Enums.GameState;
import Tiles.TileManager;
import components.TextBox;
import gameobjects.Boss;
import gameobjects.Creature;
import gameobjects.GameObject;
import gameobjects.HouseDoor;
import gameobjects.MerchantCenter;
import gameobjects.NPC;
import gameobjects.Player;
import gameobjects.WizardDoor;
import misc.Controller;
import misc.Game;
import misc.KeyInput;


public class WorldHandler {

	private Game game;
	private Controller c;
	private Player player;
	private Rectangle appBounds;
	private int entranceX;
	private int entranceY;
	private int width, height;
	private KeyInput kInput;
	private NPC gob;
	
	private final int TEXT_X = 0, TEXT_Y = Game.WINDOW_HEIGHT - 51;
	private final int TEXT_WIDTH = Game.WINDOW_WIDTH, TEXT_HEIGHT = 50;
	
	public WorldHandler(Game game) {
		this.game = game;
		this.appBounds = game.getBounds();
		this.kInput = game.getInterpreter();
		
		this.width = appBounds.width;
		this.height = appBounds.height;
		
		this.entranceX = 0;
		this.entranceY = 0;
		
		this.c = TileManager.getController(game.getXCoord(), game.getYCoord());
		
	}
	
	public NPC getOpponent() { return this.gob; }
	
	public int getEntranceX() { return this.entranceX; }
	public int getEntranceY() { return this.entranceY; }
	
	public int wander() {
		
		c = TileManager.getController(game.getXCoord(), game.getYCoord());
		player = game.getPlayer();
		
		if(!c.contains(player)) {
			c.add(player);
		}
		
		c.move(appBounds);
		
		if(checkObstacleHit() == 0) return 0;
		
		for(GameObject obj: c) {
			if(obj instanceof Creature) {
				c.checkObstacleIntersect((Creature)obj);
			}
		}
		
		gob = c.checkMobIntersect(player);
		
		if(gob != null) {
			if(gob instanceof Boss) {
				TextBox t = new TextBox(TEXT_X, TEXT_Y, TEXT_WIDTH, TEXT_HEIGHT, game, false);
				t.setVisible(true);
				game.sleep(60);
				for(String line: ((Boss) gob).dialogue()) {
					game.sleep(60);
					t.setText(line);
					game.addTextBox(t);
					game.repaint();
					game.waitInput();
					game.removeTextBox(t);
	    		}
			}
			
			game.setState(GameState.BATTLE);
		}
		
			
		NPCIHandler.interact(game, kInput, c, player);
		
		NPCIHandler.checkBoundsHit(game, player, width, height);
		
		if (kInput.pause()) {
			game.setState(GameState.GAME_MENU);
			kInput.setBack(false);
		}
		
		return 0;
	}
	
	public int checkObstacleHit() {
		GameObject obstacle = player.move(c);
		
		entranceX = player.getX();
		entranceY = player.getY();
		
		if(obstacle instanceof MerchantCenter
				&& player.getSide() == Controller.TOP) {
			game.setState(GameState.HOUSE);
			
			player.setPos(appBounds.width / 2 - player.getWidth() / 2, 
					appBounds.height - player.getHeight() * 3 / 2);
			return 0; 
		} else if(obstacle instanceof HouseDoor 
				&& player.getSide() == Controller.TOP) {
			game.setState(GameState.HOUSE);
			player.setPos(appBounds.width / 2 - player.getWidth() / 2, 
					appBounds.height - player.getHeight() * 3 / 2);
			return 0;
		} else if(obstacle instanceof WizardDoor
				&& player.getSide() == Controller.TOP) {
			game.setState(GameState.HOUSE);
			player.setPos(appBounds.width / 2 - player.getWidth() / 2, 
					appBounds.height - player.getHeight() * 3 / 2);
			return 0;
		}
		
		return 1;
	}
}
