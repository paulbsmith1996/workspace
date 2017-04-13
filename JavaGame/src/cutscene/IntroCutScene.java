package cutscene;

import java.awt.Color;
import java.awt.Graphics;

import components.TextBox;
import gameobjects.Player;
import misc.Game;
import resourceloaders.Audio;
import resourceloaders.AudioPlayer;

public class IntroCutScene extends CutScene {

	private final int TEXT_X = 0, TEXT_Y = Game.WINDOW_HEIGHT - 50;
	private final int TEXT_WIDTH = Game.WINDOW_WIDTH, TEXT_HEIGHT = 50; 
	
	private final int NUM_SECS_PER_TEXT = 4;
	
	private String[] text;
	
	public IntroCutScene(Game game) {
		super(game);
		
		String[] temp = {"You were born Ethaniel of Aria, but this name no longer belongs to you.",
						 "You are the prince of Aria, and heir to the throne which your father holds.",
						 "From a young age, you were taught to fight with a sword,",
						 "and you always believed yourself to be a natural warrior.",
						 "The people of the kingdom adored and admired you, and would follow you",
						 "to the ends of the Earth, if only you were to ask this of them.",
						 "But now you are an outcast, banished for a crime...",
						 "What crime?",
						 "The fog in your mind is still so thick.",
						 "You know that somehow you must find a way to clear your name.",
						 "What did you do?",
						 "You need to return to Aria,",
						 "Even if it may cost you your life.", 
						 "Why did this happen?",
						 "Where are you?", 
						 "What shall you do?",
						 "Remember who you are! It is within you, I know.",
						 "Go! I will not be far away."
						 };
		
		this.text = temp;
		
	}
	
	@Override
	public void play(Graphics g) {
		
		Game game = getGame();
		
		Player player = game.getPlayer();
		
		int playerX = game.getWidth() / 2 - player.getWidth() / 2;
		int playerY = game.getHeight() / 2 - player.getHeight() / 2;
		
		player.setStill();
		player.setPos(playerX, playerY);
		
		for(int i = 0; i < 20; i++) {
			
			AudioPlayer.playMusic(Audio.INTRO);
			
			g.clearRect(0, 0, game.getWidth(), game.getHeight());
			
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, game.getWidth(), game.getHeight());
			
			player.draw(g);
			
			Color c = new Color(0, 0, 0, Math.max(255 - (i * 15), 0));
			g.setColor(c);
			g.fillRect(0, 0, game.getWidth(), game.getHeight());
			game.sleep(10 * Game.FPS);
		}
		
		TextBox tBox = new TextBox(TEXT_X, TEXT_Y, TEXT_WIDTH, TEXT_HEIGHT, game, false);
		
		tBox.setVisible(true);
		game.sleep(60);
		
		for(String line: text) {
			game.sleep(60);
			tBox.setText(line);
			tBox.draw(g);
			game.sleep(1000 * NUM_SECS_PER_TEXT);
			//tBox.setVisible(false);
			//tBox.draw(g);
			//tBox = new TextBox(TEXT_X, TEXT_Y, TEXT_WIDTH, TEXT_HEIGHT, game, true);
		}
		
		for(int i = 0; i < 20; i++) {
			AudioPlayer.playMusic(Audio.INTRO);
			
			g.clearRect(0, 0, game.getWidth(), game.getHeight());

			Color c = new Color(0, 0, 0, Math.max(255 - (i * 15), 0));
			g.setColor(c);
			g.fillRect(0, 0, game.getWidth(), game.getHeight());
			game.sleep(10 * Game.FPS);
			
			player.draw(g);
		}
		
		setPlayed(true);
	}
	
	
	
}
