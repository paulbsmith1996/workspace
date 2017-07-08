package handlers;

import java.awt.Graphics;

import Enums.GameMenuState;
import Enums.GameState;
import components.TextBox;
import components.TextMenu;
import gameobjects.Player;
import inventories.Inventory;
import inventories.Pocket;
import misc.Game;
import misc.KeyInput;
import spell.Spell;
import spell.SpellBook;

public class GameMenuHandler {

	private final int OFFSET = 15;

	private KeyInput kInput;
	private Player player;
	private TextMenu gMenu;
	private TextBox tBox, infoBox;
	private int appWidth;
	private GameMenuState state;
	private boolean mainOpen = true;
	private Pocket p;
	private Game game;

	public GameMenuHandler(KeyInput kInput, Player player, int appWidth, Game game) {

		this.kInput = kInput;
		this.game = game;

		this.player = player;
		this.appWidth = appWidth;

		state = GameMenuState.MAIN;
		p = null;
	}

	public void setState(GameMenuState s) {
		this.state = s;
	}

	public GameMenuState getState() {
		return state;
	}

	public boolean mainOpen() {
		return mainOpen;
	}

	public void gMenuSetUp() {

		final int WIDTH = 80;
		final int X = appWidth - (WIDTH + OFFSET);

		int numOps = 0;
		int mWidth = 0;

		kInput.setState(GameState.GAME_MENU);

		if (state != GameMenuState.MAIN) {
			mainOpen = false;
		}

		switch (state) {
		case MAIN:
			String[] choices = { "Spells", "Inventory", "Stats", "Equipped", "Exit", "Main Menu" };

			if (gMenu == null || (gMenu.getOptions() != choices)) {
				gMenu = new TextMenu(choices, 1, X, 10, game, true);
			}

			numOps = choices.length;
			mWidth = 1;

			if (gMenu.getOptions() == choices && (kInput.numOps() != numOps || kInput.getMenuWidth() != mWidth)) {
				kInput.setOptionNum(numOps);
				kInput.setMenuWidth(mWidth);
			}

			if (kInput.ready()) {
				int selected = gMenu.getSelected();

				if (selected == 0) {
					// Spells selected
					state = GameMenuState.SPELLS;
				} else if (selected == 1) {
					// Inventory selected
					state = GameMenuState.INVENTORY;
				} else if (selected == 2) {
					// Stats Selected
					state = GameMenuState.STATS;
				} else if (selected == 3) {
					// Equipped Selected
					state = GameMenuState.EQUIPPED;
				} else if (selected == 4) {
					// Exit selected
					setPause(false);
				} else if (selected == 5) {
					// Main Menu Selected
					game.setState(GameState.MENU);
				}

				setReady(false);

			} else if (kInput.goBack()) {
				setPause(false);
			}
			break;
		case SPELLS:
			SpellBook sb = player.getBook();
			String[] spells = sb.getSpells();

			if (spells.length > 0) {
				numOps = spells.length;
				mWidth = 3;

				if (gMenu == null || gMenu.getOptions() != spells) {
					gMenu = new TextMenu(spells, mWidth, X, 10, game, true);
				}

				if (gMenu.getOptions() == spells && (kInput.numOps() != numOps || kInput.getMenuWidth() != mWidth)) {
					kInput.setOptionNum(numOps);
					kInput.setMenuWidth(mWidth);
				}

				if (kInput.ready()) {
					String spellName = gMenu.getSelectedOptionName();

					Spell selectedSpell = game.getSpellMap().get(spellName);
					displayInfo(selectedSpell.getDescription());
					kInput.setReady(false);
					kInput.setBack(false);

				} else if (kInput.goBack()) {
					state = GameMenuState.MAIN;
					kInput.setBack(false);
				}
			} else {
				displayMText("No spells are know yet!", true);
				state = GameMenuState.MAIN;
			}

			break;
		case INVENTORY:
			Inventory i = player.getInventory();
			String[] pockets = i.pocketNames();

			numOps = pockets.length;
			mWidth = 1;

			if (gMenu == null || gMenu.getOptions() != pockets) {
				gMenu = new TextMenu(pockets, 1, X, 10, game, true);
			}

			if (gMenu.getOptions() == pockets && (kInput.numOps() != numOps || kInput.getMenuWidth() != mWidth)) {
				kInput.setOptionNum(numOps);
				kInput.setMenuWidth(mWidth);
			}

			if (kInput.ready()) {
				int pocketType = gMenu.getSelected();
				p = i.findPocket(pocketType);
				state = GameMenuState.POCKET;
			} else if (kInput.goBack()) {
				state = GameMenuState.MAIN;
				kInput.setBack(false);
			}

			break;
		case POCKET:
			if (p != null && !p.isEmpty()) {
				String[] pocketContents = p.contents();
				if (gMenu.getOptions() != pocketContents) {
					gMenu = new TextMenu(pocketContents, 1, X - WIDTH / 2, 10, game, true);
				}

				numOps = pocketContents.length;
				mWidth = 1;
				if (gMenu.getOptions() == pocketContents
						&& (kInput.numOps() != numOps || kInput.getMenuWidth() != mWidth)) {
					kInput.setOptionNum(numOps);
					kInput.setMenuWidth(mWidth);
				}

				if (kInput.ready()) {
					String[] items = p.itemNames();
					displayInfo(player.use(items[gMenu.getSelected()]));
					kInput.setReady(false);
					kInput.setBack(false);
					state = GameMenuState.INVENTORY;
				} else if (kInput.goBack()) {
					kInput.setBack(false);
					state = GameMenuState.INVENTORY;
				}
			} else {
				displayMText("Pocket empty", true);
				state = GameMenuState.INVENTORY;
			}
			break;
		case STATS:
			displayMText(player.returnAllStats(), false);
			state = GameMenuState.MAIN;
			break;
		case EQUIPPED:
			displayMText(player.getHolster().toString(), false);
			state = GameMenuState.MAIN;
			break;
		}
	}

	public void displayInfo(String text) {

		this.infoBox = new TextBox(0, 0, game.getWidth(), 30, game, true);
		infoBox.setWrap(true);

		infoBox.setText(text);
		infoBox.setDelimiter("\n");

		gMenu.setVisible(false);

		game.sleep(100);

		infoBox.display();

		infoBox.setVisible(false);
	}

	public void displayMText(String text, boolean wrap) {
		int width = 200;

		tBox = new TextBox(game.getWidth() - width - OFFSET, 10, width, 105, game, true);
		tBox.setWrap(wrap);
		tBox.setText(text);
		tBox.setDelimiter("\n");

		gMenu.setVisible(false);

		tBox.display();

		tBox.setVisible(false);
	}

	public int getSelected() {
		return gMenu.getSelected();
	}

	public void awaitInput() {
		kInput.setReady(false);
	}

	public boolean ready() {
		return kInput.ready();
	}

	public void setReady(boolean ready) {
		kInput.setReady(ready);
	}

	public boolean pause() {
		return kInput.pause();
	}

	public void setPause(boolean pause) {
		kInput.setPause(pause);
	}

	public boolean back() {
		return kInput.goBack();
	}

	public void setBack(boolean back) {
		kInput.setBack(back);
	}

	public void draw(Graphics g) {
		if (gMenu != null) {
			gMenu.draw(g);
		}

		if (tBox != null) {
			tBox.draw(g);
		}

		if (infoBox != null) {
			infoBox.draw(g);
		}
	}
}
