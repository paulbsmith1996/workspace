package cutscene;

import java.awt.Color;
import java.awt.Graphics;

import Tiles.Tile;
import handlers.HouseHandler;
import misc.Game;
import misc.Renderer;
import resourceloaders.Audio;
import resourceloaders.AudioPlayer;

public class HealingScene extends CutScene {
	
	// Play sound for this many ms
	private final int SOUND_LENGTH = 1500;
	private HouseHandler hh;
	
	public HealingScene(Game game) {
		super(game);
		hh = game.getHouseHandler();
	}
	
	@Override
	public void play(Graphics g) {
		
		Game game = getGame();
		
		for(int i = 0; i < 10; i++) {
		
			Renderer.renderHouse(g, hh);
			
			Color c = new Color(255, 255, 255, Math.min(i * 50, 255));
			g.setColor(c);
			g.fillRect(0, 0, game.getWidth(), game.getHeight());
			game.sleep(5 * Game.FPS);
		}
		
		AudioPlayer.playLimited(Audio.HEALING_SOUND, SOUND_LENGTH);
		
		setPlayed(true);
	}
}
