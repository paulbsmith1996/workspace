package handlers;
import gameobjects.Healer;
import gameobjects.Interactable;
import gameobjects.Player;

import java.awt.Graphics;

import misc.Controller;
import misc.Game;
import misc.KeyInput;
import misc.Renderer;
import Enums.GameState;
import Enums.HouseType;
import Tiles.HealerTile;
import Tiles.HouseT0;
import Tiles.HouseTile;

import components.TextBox;
import cutscene.HealingScene;


public class HouseHandler {
	
	private HouseTile environment;
	private KeyInput kInput;
	private Game game;
	private HouseType curHouse;
	private Controller c;
	private Player player;
	
	private int xCoord, yCoord;
	
	private boolean newHouse = true;
	
	public HouseHandler(Game game, Player player, int tileX, int tileY) {
		this.game = game;
		this.player = player;
		this.c = new Controller();

		this.xCoord = game.getXCoord();
		this.yCoord = game.getYCoord();
		
		this.kInput = game.getInterpreter();
		
		this.environment = null;
		this.curHouse = null;
		
		if(xCoord == 11 && yCoord == 97 && tileX < 6) {
			curHouse = HouseType.FIRST_HOUSE;
		} else if(xCoord == 11 && yCoord == 97) {
			curHouse = HouseType.HEALER;
		}
		
		switch (curHouse) {
		case FIRST_HOUSE:
			environment = new HouseT0();
			break;
		case HEALER:
			environment = new HealerTile();
			break;
		}
		
		c = environment.getController();
		c.add(player);
	}
	
	public HouseTile houseBakground() { return this.environment; }
	
	public boolean isNewHouse() { return this.newHouse; }
	
	public void update() {
		kInput.setState(GameState.HOUSE);
		player.move(c);
		
		NPCIHandler.interact(game, kInput, c, player);
		
		
		if(player.getY() + player.getHeight() > game.getHeight()) {
			player.setPos(game.entranceX(), game.entranceY());
			newHouse = true;
			game.setState(GameState.WANDER);
		}
		
		if (kInput.pause()) {
			game.setState(GameState.GAME_MENU);
			kInput.setBack(false);
		}
	}
	
	public void draw(Graphics g) {
		Renderer.renderHouse(g, this);
	}
}
