package handlers;

import Enums.GameState;
import Items.Item;
import Items.Pickaxe;
import Tiles.TileManager;
import components.TextBox;
import cutscene.HealingScene;
import gameobjects.Healer;
import gameobjects.Interactable;
import gameobjects.Merchant;
import gameobjects.Player;
import gameobjects.RawOre;
import gameobjects.Wizard;
import misc.Controller;
import misc.Game;
import misc.KeyInput;
import resourceloaders.AudioPlayer;

public class NPCIHandler {

	private static final int TEXT_X = 0, TEXT_Y = Game.WINDOW_HEIGHT - 51;
	private static final int TEXT_WIDTH = Game.WINDOW_WIDTH, TEXT_HEIGHT = 50;
	
	private static final int TEXT_WAIT_TIME = 60;
	
	public static void interact(Game game, KeyInput kInput, Controller c, Player player) {
		
		
		Interactable talker = player.checkInteraction(c);
		
		if(talker != null && kInput.isInteracting()) {
			TextBox t = new TextBox(TEXT_X, TEXT_Y, TEXT_WIDTH, TEXT_HEIGHT, game, false);
			t.setVisible(true);
			game.sleep(TEXT_WAIT_TIME);
			
			if(talker instanceof RawOre) {
				mineOre(game, player, (RawOre)talker);
			} else if(talker instanceof Merchant) {
				((Merchant)talker).converse(player);
			} else if(talker instanceof Healer) {
				talkToHealer(game, player, talker, t);	
			} else if(talker instanceof Wizard) {
				((Wizard)talker).converse(player);
			} else {
				for(String text: talker.interact()) {
					game.sleep(TEXT_WAIT_TIME);
					t = new TextBox(TEXT_X, TEXT_Y, TEXT_WIDTH, TEXT_HEIGHT, game, false);
					t.setText(text);
					t.setVisible(true);
					game.addTextBox(t);
					
					game.repaint();
					game.waitInput();
					game.removeTextBox(t);
				}
			}
			
			// Wait extra long to not start a new interaction
			game.sleep(TEXT_WAIT_TIME * 2);
			
			
			talker.setChecked(true);
		}
	}
	
	public static void talkToHealer(Game game, Player player, Interactable talker, TextBox t) {
		String[] dialogue = talker.interact();
		for(int i = 0; i < dialogue.length - 1; i++) {
			String text = dialogue[i];
			game.sleep(TEXT_WAIT_TIME);
			t.setText(text);
			game.addTextBox(t);
			game.repaint();
			game.waitInput();
			game.removeTextBox(t);
		}
		
		game.setState(GameState.CUTSCENE);
		HealingScene hs = new HealingScene(game);
		hs.play(game.getGraphics());
		((Healer) talker).heal(player);
		
		String text = dialogue[dialogue.length - 1];
		t.setText(text);
		game.addTextBox(t);
		game.repaint();
		game.waitInput();
		game.removeTextBox(t);
		
		AudioPlayer.playPrev();
	}
	
	public static void mineOre(Game game, Player player, RawOre ore) {
		
		TextBox t = new TextBox(TEXT_X, TEXT_Y, TEXT_WIDTH, TEXT_HEIGHT, game, false);
		t.setVisible(true);
		String text = "";
		
		Item pick = player.getInventory().findItem("Pickaxe");
		
		if(pick == null) {
			text = "You do not have the tools to mine this.";
		} else {
			if(((Pickaxe)pick).use() != 0) {
				ore.mine();
				player.getInventory().addItem(ore.getOre());
			}
			
			text = "You mined a(n) " + ore.getOre().getName() +
					".\n You have " + ((Pickaxe)pick).getUsesLeft() + " uses left on your " 
					+ pick.getName() + ".";
		}
		
		game.sleep(TEXT_WAIT_TIME);
		t.setText(text);
		game.addTextBox(t);
		game.repaint();
		game.waitInput();
		game.removeTextBox(t);
	}
	
	public static void checkBoundsHit(Game game, Player player, int width, int height) {
		
		if(player.intersects(game.getLeftBounds())) {
			game.setXCoord(game.getXCoord() - 1);
			player.setX(width - player.getWidth() - 5);
			game.setController(TileManager.getController(game.getXCoord(), game.getYCoord()));
			game.getController().add(player);
		}
		
		if(player.intersects(game.getRightBounds())) {
			player.setX(5);
			game.setXCoord(game.getXCoord() + 1);
			game.setController(TileManager.getController(game.getXCoord(), game.getYCoord()));
			game.getController().add(player);
		}
		
		if(player.intersects(game.getTopBounds())) {
			player.setY(height - player.getHeight() - 5);
			game.setYCoord(game.getYCoord() -1);
			game.setController(TileManager.getController(game.getXCoord(), game.getYCoord()));
			game.getController().add(player);
		}
		
		if(player.intersects(game.getBottomBounds())) {
			player.setY(5);
			game.setYCoord(game.getYCoord() + 1);
			game.setController(TileManager.getController(game.getXCoord(), game.getYCoord()));
			game.getController().add(player);
		}
	}
}
