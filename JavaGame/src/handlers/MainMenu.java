package handlers;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import misc.Game;
import misc.KeyInput;
import resourceloaders.AudioPlayer;
import Enums.GameState;
import Enums.MainMenuState;

import components.Button;

public class MainMenu {
	
	private Button play, options, exit;
	private Button music;
	
	private final int BUTTON_WIDTH = 200, BUTTON_HEIGHT = 30;
	private int x = 10, y = 90;
	
	private Vector<Button> buttons = new Vector<Button>();
	private Vector<Button> mainButtons = new Vector<Button>();
	private Vector<Button> optionButtons = new Vector<Button>();
	
	private int selected;
	private Game game;
	private KeyInput kInput;
	
	private MainMenuState menuState;
	private boolean playMusic;
	
	public MainMenu(Game game) {
		this.game = game;
		this.kInput = game.getInterpreter();
		this.selected = 0;
		this.menuState = MainMenuState.MAIN;
		this.playMusic = true;
		
		kInput.setState(GameState.MENU);
		
		this.x = (game.getWidth() / 2) - (BUTTON_WIDTH / 2) - 10;
		
		play = makeMainButton("Play");
		options = makeMainButton("Options");
		exit = makeMainButton("Exit");
		
		music = makeOptionButton("Music: On");
		selected = 0;
	}
	
	public int numOps() { return buttons.size(); }
	public int getSelected() { return selected; }
	public void setSelected(int val) { this.selected = val; }
	
	public boolean musicOn() { return this.playMusic; }
	
	public void update() {
		
		AudioPlayer.stopPlaying();

		if(kInput.getMainMovement() == -1 && selected + 1 < buttons.size()) {
			selected++;
			game.sleep(100);
		} else if(kInput.getMainMovement() == 1 && selected - 1 >= 0) {
			selected--;
			game.sleep(100);
		}
		
		switch(menuState) {
		case MAIN:
			buttons = mainButtons;
			if (kInput.ready()) {
				if (selected == 0) {
					// Play button selected
					game.setState(GameState.WANDER);
					kInput.setReady(false);
				} else if (selected == buttons.size() - 1) {
					// Exit button selected
					game.stop();
				} else if(selected == 1) {
					// Options button selected
					menuState = MainMenuState.OPTIONS;
					selected = 0;
					game.sleep(100);
				}
			}
			break;
		case OPTIONS:
			buttons = optionButtons;
			if(kInput.ready()) {
				if(selected == 0) {
					// User selected music button
					playMusic = !playMusic;
					if(playMusic) {
						music.setText("Music: On");
					} else {
						music.setText("Music: Off");
					}
					game.sleep(100);
				}
			} else if(kInput.goBack()) {
				menuState = MainMenuState.MAIN;
			}
			break;
		}
	}
	
	private Button makeOptionButton(String text) {
		int tempY = y;
		y += BUTTON_HEIGHT;
		
		Button b = new Button(x, tempY, BUTTON_WIDTH, BUTTON_HEIGHT, text);
		optionButtons.add(b);
		
		return b;
	}
	
	private Button makeMainButton(String text) {
		int tempY = y;
		y += BUTTON_HEIGHT;
		
		Button b = new Button(x, tempY, BUTTON_WIDTH, BUTTON_HEIGHT, text);
		mainButtons.add(b);
		
		return b;
	}
	
	public void draw(Graphics g, int selected) {
		
		//g.setColor(Color.ORANGE);
		//g.fillRect(0, 0, game.getWidth(), game.getHeight());
		
		for(int x = 0; x < buttons.size(); x++) {
			Button b = buttons.elementAt(x);
			b.draw(g, x == selected);
		}
	}
}