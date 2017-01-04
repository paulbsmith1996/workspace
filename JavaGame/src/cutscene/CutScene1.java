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
	
	public CutScene1(Game game) {
		super(game);
	}
	
	@Override
	public void play(Graphics g) {
		
		Game game = getGame();
		Player player = game.getPlayer();
		
		player.setStill();
		player.setPos(game.getWidth() / 2 - player.getWidth() / 2,
				game.getHeight() - player.getHeight() - 30);
		
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
		
		TextBox tBox = new TextBox(0, 270, 500, 30, game);
		
		tBox.setVisible(true);
		game.sleep(60);
		
		for(String line: t.interact()) {
			game.sleep(60);
			tBox.setText(line);
			tBox.draw(g);
			game.sleep(6 * 1000);
		}
		
		setPlayed(true);
	}
}
