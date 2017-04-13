package cutscene;

import gameobjects.Player;
import gameobjects.Tella1;

import java.awt.Color;
import java.awt.Graphics;

import misc.Game;
import resourceloaders.Audio;
import resourceloaders.AudioPlayer;

import components.TextBox;

public class CutScene1 extends CutScene {
	
	private final int TEXT_X = 0, TEXT_Y = Game.WINDOW_HEIGHT - 50;
	private final int TEXT_WIDTH = Game.WINDOW_WIDTH, TEXT_HEIGHT = 50;
	
	private final int NUM_SECS_PER_TEXT = 4;
	
	public CutScene1(Game game) {
		super(game);
	}
	
	@Override
	public void play(Graphics g) {
		
		Game game = getGame();
		Player player = game.getPlayer();
		
		int playerX = game.getWidth() / 2 - player.getWidth() / 2;
		int playerY = game.getHeight() - player.getHeight() - 60;
		
		player.setStill();
		player.setPos(playerX, playerY);
				
		
		Tella1 t = new Tella1(0, 10);
		
		g.setColor(new Color(0, 225, 250));
		
		for(int x = 0; x < 90; x++) {
			AudioPlayer.playMusic(Audio.DREAM_MUSIC);
			t.setX(t.getX() + 2);
			
			g.fillRect(0, 0, game.getWidth(), game.getHeight());
			
			t.draw(g);
			player.draw(g);
			game.sleep(1000 / 30);
		}
		
		for(int y = 0; y < 30; y++) {
			t.setY(t.getY() + 2);
			
			g.fillRect(0, 0, game.getWidth(), game.getHeight());
			
			t.draw(g);
			player.draw(g);
			game.sleep(1000 / 30);
		}
		
		g.fillRect(0, 0, game.getWidth(), game.getHeight());
		t.draw(g);
		player.draw(g);
		
		TextBox tBox = new TextBox(TEXT_X, TEXT_Y, TEXT_WIDTH, TEXT_HEIGHT, game, false);
		
		tBox.setVisible(true);
		game.sleep(60);
		
		for(String line: t.interact()) {
			game.sleep(60);
			tBox.setText(line);
			tBox.draw(g);
			game.sleep(NUM_SECS_PER_TEXT * 1000);
		}
		
		setPlayed(true);
	}
}
