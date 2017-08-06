package handlers;
import java.awt.Graphics;

import Enums.GameState;
import Enums.HouseType;
import Tiles.HealerTile;
import Tiles.HouseT0;
import Tiles.HouseTile;
import Tiles.MerchantTile;
import Tiles.WizardTile;
import gameobjects.Player;
import misc.Controller;
import misc.Game;
import misc.KeyInput;
import misc.Renderer;


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
		} else if(xCoord == 11 && yCoord == 97 && tileX > 8) {
			curHouse = HouseType.HEALER;
		} else if(xCoord == 11 && yCoord == 97 && tileX >= 6 && tileX <= 8) {
			curHouse = HouseType.MERCHANT_1;
		} else if(xCoord == 7 && yCoord == 90) {
			curHouse = HouseType.WIZARD_1;
		}
		
		switch (curHouse) {
		case FIRST_HOUSE:
			environment = new HouseT0();
			break;
		case HEALER:
			environment = new HealerTile();
			break;
		case MERCHANT_1:
			environment = new MerchantTile(game);
			break;
		case WIZARD_1:
			environment = new WizardTile(game);
		}
		
		c = environment.getController();
		c.add(player);
	}
	
	public HouseTile houseBackground() { return this.environment; }
	
	public boolean isNewHouse() { return this.newHouse; }
	
	public void update() {
		newHouse = false;
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
