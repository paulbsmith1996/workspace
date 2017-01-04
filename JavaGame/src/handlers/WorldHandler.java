package handlers;
import gameobjects.Boss;
import gameobjects.Creature;
import gameobjects.GameObject;
import gameobjects.Healer;
import gameobjects.HouseDoor;
import gameobjects.Interactable;
import gameobjects.MerchantCenter;
import gameobjects.NPC;
import gameobjects.Player;
import gameobjects.RawOre;

import java.awt.Rectangle;

import misc.Controller;
import misc.Game;
import misc.KeyInput;
import misc.Renderer;
import Enums.GameState;
import Items.ItemReference;

import components.TextBox;


public class WorldHandler {

	private Game game;
	private Controller c;
	private Renderer renderer;
	private Player player;
	private Rectangle appBounds;
	private int entranceX;
	private int entranceY;
	private int width, height;
	private KeyInput kInput;
	private NPC gob;
	
	public WorldHandler(Game game) {
		this.game = game;
		this.appBounds = game.getBounds();
		this.kInput = game.getInterpreter();
		
		this.width = appBounds.width;
		this.height = appBounds.height;
		
		this.entranceX = 0;
		this.entranceY = 0;
		
	}
	
	public NPC getOpponent() { return this.gob; }
	
	public int getEntranceX() { return this.entranceX; }
	public int getEntranceY() { return this.entranceY; }
	
	public int wander() {
		
		renderer = game.getRenderer();
		player = game.getPlayer();
		
		if(renderer != null) {
			c = renderer.getController();
		}
		
		if(!c.contains(player)) {
			c.add(player);
		}
		
		c.move(appBounds);
		
		GameObject obstacle = player.move(c);
		
		entranceX = player.getX();
		entranceY = player.getY();
		
		if(obstacle instanceof MerchantCenter
				&& player.getSide() == Controller.TOP) {
			game.setState(GameState.MERCHANT);
			
			player.setPos(appBounds.width / 2 - player.getWidth() / 2, 
					appBounds.height - player.getHeight() * 3 / 2);
			return 0; 
		} else if(obstacle instanceof HouseDoor 
				&& player.getSide() == Controller.TOP) {
			game.setState(GameState.HOUSE);
			player.setPos(appBounds.width / 2 - player.getWidth() / 2, 
					appBounds.height - player.getHeight() * 3 / 2);
			return 0;
		}
		
		for(GameObject obj: c) {
			if(obj instanceof Creature) {
				c.checkObstacleIntersect((Creature)obj);
			}
		}
		
		gob = c.checkMobIntersect(player);
		
		if(gob != null) {
			if(gob instanceof Boss) {
				TextBox t = new TextBox(0, 270, 300, 30, game);
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
		
		Interactable talker = player.checkInteraction(c);
		
		if(talker != null && kInput.isInteracting()) {
			if (talker.getInteractType() == Interactable.FRIENDLY) {
				
				TextBox t = new TextBox(0, 270, 300, 30, game);
				t.setVisible(true);
				game.sleep(60);
				
				for (String text : talker.interact()) {
					game.sleep(60);
					t.setText(text);
					game.addTextBox(t);
					game.repaint();
					game.waitInput();
					game.removeTextBox(t);
				}
				kInput.setInteracting(false);
				if(talker instanceof Healer) {
					((Healer) talker).heal(player);
				}
				
			} else if(talker.getInteractType() == Interactable.ORE) {
				TextBox t = new TextBox(0, 270, 300, 30, game);
				if(player.getInventory().hasItem(ItemReference.PICKAXE)) {
					player.getInventory().addItem(((RawOre)talker).getOre());
					((RawOre)talker).mine();
					
					game.sleep(100);
					t.setVisible(true);
					t.setText("Mined 1 " + ((RawOre)talker).getOre().getName());
					
					game.addTextBox(t);
					game.repaint();
					game.waitInput();
					
					game.removeTextBox(t);
					game.sleep(100);
				} else {					
					game.sleep(100);
					t.setVisible(true);
					t.setText("You do not have the materials to mine this.");
					
					game.addTextBox(t);
					game.repaint();
					game.waitInput();
					
					game.removeTextBox(t);
					game.sleep(100);
				}
				talker.setChecked(true);
				
			}
		}
		
		if(player.intersects(game.getLeftBounds())) {
			game.setXCoord(game.getXCoord() - 1);
			player.setX(width - player.getWidth() - 5);
		}
		if(player.intersects(game.getRightBounds())) {
			player.setX(5);
			game.setXCoord(game.getXCoord() + 1);
		}
		if(player.intersects(game.getTopBounds())) {
			player.setY(height - player.getHeight() - 5);
			game.setYCoord(game.getYCoord() -1);
		}
		if(player.intersects(game.getBottomBounds())) {
			player.setY(5);
			game.setYCoord(game.getYCoord() + 1);
		}
		
		if (kInput.pause()) {
			game.setState(GameState.GAME_MENU);
			kInput.setBack(false);
		}
		
		return 0;
	}
}
