package cutscene;

import java.awt.Graphics;

import misc.Game;

public class CutScene {

	private Game game;
	private boolean played;
	
	public CutScene(Game game) {
		this.game = game;
		this.played = false;
	}
	
	public Game getGame() { return this.game; }
	public void setGame(Game game) { this.game = game; }
	
	public boolean played() { return this.played; }
	public void setPlayed(boolean b) { this.played = b; }
	
	// To override
	public void play(Graphics g) {
		
	}
}
