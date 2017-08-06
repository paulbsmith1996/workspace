package handlers;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import Enums.BattleState;
import Enums.GameState;
import Items.HealthPotion;
import Items.ItemReference;
import Items.ManaPotion;
import components.StatDisplay;
import components.TextBox;
import components.TextMenu;
import gameobjects.Boss;
import gameobjects.Creature;
import gameobjects.NPC;
import gameobjects.Player;
import inventories.Pocket;
import misc.Game;
import misc.KeyInput;
import misc.Renderer;
import resourceloaders.Audio;
import resourceloaders.AudioPlayer;
import spell.Fire;
import spell.Ice;
import spell.Spell;
import spell.SpellReference;


public class BattleHandler {


	private final int TEXT_BOX_X = 0, TEXT_BOX_Y = Game.WINDOW_HEIGHT - 45;
	private final int TEXT_BOX_WIDTH = Game.WINDOW_WIDTH, TEXT_BOX_HEIGHT = 45;
	
    /**
     * int holding exp to be gained after battle
     */
    protected int exp;
   
    /**
     * Random object used to determine probabilities of random events
     */
    protected Random r;
    
    /**
     *  Represent Player and NPC objects engaging in the battle
     */
    private Player player;
    private NPC opponent;
    
    /**
     * Boolean determines whether or not Player object can escape from battle
     */
    private boolean canEscape = true;
    
    /**
     * KeyInput object to be passed to the battle to allow player to make
     * decisions using keyboard
     */
    private KeyInput kInput;
    
    /**
     * Objects that will display relevant information/choices to player. These
     * are toggled on/off by toggling their visibilities
     * 
     */
    private TextMenu m;
    private TextBox t;
    
    /**
     * Will be assigned the dimensions of the running Applet
     */
    private int width;
    private int height;
    
    /**
     * Variable that will be assigned to the current 
     * instance of Game in which the Battle is being run
     */
    private Game g;
    
    /**
     * Bars to display Player's and NPC's most important stats
     */
    private StatDisplay playStat;
	private StatDisplay oppStat;
	
	private final long LOSS_TIME = 1300;
	private final int SPELL_OFFSET = 2001;
    
	/**
	 * 
	 * @param player - user controlled object
	 * @param opponent - NPC object engaging in battle
	 * @param canEscape - determines whether user can run from battle or not
	 * @param kInput - KeyInput object interprets user keyboard input
	 * @param appBounds - Rectangle representing dimensions of Applet
	 * @param g - Game that battle is a part of
	 * 
	 * Initialize global variables and prepare Graphics for battle. Prepare
	 * kInput by setting correct State for appropriate key interpretation
	 */
    public BattleHandler(NPC opponent, Game g) {
    	
    	this.g = g;
    	this.kInput = g.getInterpreter();
    
    	this.opponent = opponent;
    	
    	// Immediately set correct state
    	kInput.setState(GameState.BATTLE);

    	this.player = g.getPlayer();
    	
    	Rectangle appBounds = g.getBounds();
    	
    	this.width = appBounds.width;
    	this.height = appBounds.height;
    	
    	t = new TextBox(TEXT_BOX_X, TEXT_BOX_Y, TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT, g, true);
    	
    	if(opponent instanceof Boss) {
    		AudioPlayer.playMusic(Audio.BOSS_MUSIC);
    		canEscape = false;
    	} else {
    		AudioPlayer.playMusic(Audio.BATTLE_MUSIC);
    	}
    	
    	bSetUp();
    	
    	playStat = new StatDisplay(player, 0);
		oppStat = new StatDisplay(opponent, 1);
    }
    
    // Returns user's opponent
    public NPC getOpponent() { return this.opponent; }
    
    // Prepares graphics for upcoming battle
    public void bSetUp() {	
    	// Assign player correct position and set total velocity to 0
		player.setPos(30, height - (player.getHeight()) - 60);
		player.setStill();
		
    	// Assign opposing NPC correct position and set total velocity to 0
		opponent.setPos(width - 30 - (opponent.getWidth()), 70);
		opponent.setStill();
		
	}

    /**
     * Method for battle engages two Creatures in a Battle until
     * one of the two dies or runs. Method called many times over
     * by Game class (once per 1000 / 30 secs) in run() method under
     * case BATTLE
     *
     * @param Player - Holds Player engaged in Battle
     * @param NPC - Holds NPC enemy engaged in Battle
     */
    public BattleState battle () {
    	
    	//kInput.setState(GameState.BATTLE);
	
    	// exp is equal to HP of opponent
    	exp = opponent.getHp();

    	// Create new scanner object
    	displayBText("Prepare for battle");

    	// User chooses desired option as long as neither creature is dead
    	while (!(player.dead() || opponent.dead())) {

	    // Present options to user
    	String[] options = {"FIGHT", "SPELL", "POTION", "RUN"};
    	displayBMenu(options, 2, false);

			if (m.getSelected() == 0) {
				// Player wants to fight
				fight();
			} else if (m.getSelected() == 2) {
				// Player wants to use a potion
				selectPotion();
			} else if (m.getSelected() == 1) {
				// Player wants to use a spell
				selectSpell();
			} else if (m.getSelected() == 3) {
				// Player tries to escape
				if (canEscape) {
					if (escape()) {
						// Successful escape by player
						
						// Update user
						displayBText("Escaped safely");
						
						// End method running and return BattleState
						return BattleState.ESCAPE;
					} else {
						// Unsuccessful escape by player
						
						// Update user
						displayBText("Unsuccessful escape");
						
						// Allow opponent to attack for its turn
						opponent.attack(player);
					}
				} else {
					// canEscape is set to false. Player cannot escape
					displayBText("Cannot run from this battle!");
				}

			}
		}

		// Determine outcome of battle
		if (player.dead()) {
			// Player dead
			displayBText("You lose");
			return BattleState.BWIN;
		} else if (opponent.dead()) {
			// Enemy dead

			if(opponent instanceof Boss) {
				for(String line: ((Boss) opponent).deathDialogue()) {
					displayBText(line);
				}
			}
			
			// Player gains exp
			if(player.gainEXP(exp) == 1) {
				displayBText(player.levelUp());
			}

			// Calculate random amount of money to give to player
			r = new Random();
			int money = r.nextInt(opponent.getMaxHP() / 2);
			player.getInventory().addMoney(money);

			// Inform player of changes in money and exp
			displayBText("Battle Won!");
			displayBText("You earned " + money + " coins");
			displayBText("You gained " + exp + "EXP! You need "
					+ (player.EXP_PER_LEVEL * player.getLevel() * player.getLevel() - player.getEXP())
					+ "EXP to level up!");
			
			// Remove any buffs Player may have cast
			player.resetStats();
			
			// Apply permanent stat changes implied by equipped
			// Armament objects
			player.getHolster().applyBuffs();
			// Notify that player won
			return BattleState.PLAYERWIN;
		}
		
		// Battle is still in progress
		return BattleState.ONGOING;
	}
    
	/**
	 * Two creatures fight one round. One hit for the player, one 
	 * for the opponent
	 */
	public void fight() {
		
		loseHealth(opponent, player.attack(opponent));
		
		if(!opponent.dead()) {
			loseHealth(player, opponent.attack(player));
		}
	}

	/**
	 * Determines if player successfully escaped from the battle
	 */
	public boolean escape() {
		// Use random num generator from Creature class
		int roll = player.getOutcome();
		int realRoll = roll % 100;

		// 2/3 chance the player escapes successfully
		if (realRoll < 67) {
			return true;
		}

		return false;
	}

	/**
	 * Allows player to select a potion to use in Battle
	 */
	public void selectPotion() {

		boolean successful = false;
		
		// Check if pocket is empty
		if (!player.getInventory().emptyPocket("Potion")) {

			// Get Player's Potion pocket
			Pocket potPocket = player.getInventory().findPocket(ItemReference.POTION);
			
			// At this point, manually add each different potion to the menu
			// and display quantities of each potion
			String[] choices = {
					potPocket.getQuantity(ItemReference.HEALTHPOTION) + " Health Potion",
					potPocket.getQuantity(ItemReference.MANAPOTION) + " Mana Potion" };
			
			// Allow user to select which potion to use
			displayBMenu(choices, 2, true);

			
			// Save amounts remaining of each kind of Potion
			// These few lines can be rewritten. Enumerating all
			// quantities of potions is unnecessary for the functioning
			// of the if else block
			int HP_potions = player.getInventory().getQuantity(new HealthPotion());
			int mana_potions = player.getInventory().getQuantity(new ManaPotion());

			if (m.getSelected() == 0 && HP_potions > 0) {
				// Player wants to use Health Potion
				HealthPotion p = new HealthPotion();
				displayBText(player.use(p));
				
				// Allow opponent to attack Player
				successful = true;
			} else if (m.getSelected() == 1 && mana_potions > 0) {
				// Player wants to use a Mana Potion
				ManaPotion p = new ManaPotion();
				displayBText(player.use(p));
				
				// Allow opponent to attack Player
				successful = true;
			} else {
				// If an invalid option is somehow selected from menu.
				// default is to set m's selected to m.numChoices()
				if(m.getSelected() >= m.numChoices()) {
					// Player requests an invalid Potion
					displayBText("No such potion");	
				}
			}
			
			if(successful) {
				loseHealth(player, opponent.attack(player));
			}
			

		} else {
			// Player has no Potions remaining
			displayBText("No potions left!");
		}
	}

	/**
	 * Allows user to select desired spell in Battle
	 */
	public void selectSpell() {
		
		boolean success = false;

		if (player.getBook().size() > 0) {
			// Player has at least one Spell
			String[] choices = player.getBook().getSpells();

			// Allow user to select desired Spell
			displayBMenu(choices, 3, true);

			// Important to add Spells in correct order to SpellBook
			int spellID = m.getSelected() + 1;
			// Determine which spell needs to be cast
			Spell toCast = null;
			
			if (spellID == SpellReference.FIRE) {
				// Selected Spell is Fire
				toCast = new Fire();
			} else if (spellID == SpellReference.ICE) {
				// Selected Spell is Ice
				toCast = new Ice();
			}

			// Determine if the spell could even be cast
			// Set to 0 so that default says Spell didn't cast
			int successfulCast = 0;
			
			if (toCast != null) {
				// Cast spell and determine whether it was successful
				// or not
				successfulCast = player.cast(toCast, opponent);
			}

			if (successfulCast == 1) {
				// Spell succeeded and hit enemy
				String toDisplay = toCast.addedEffects(opponent);
				if (!toDisplay.equals("")) {
					// Only display addedEffects if there were added effects
					displayBText(toDisplay);
				}
				
				loseHealth(opponent, toCast.getRealDamage());
				
				// Inform user of change to opp's health
				//displayBText("Enemy took " + toCast.getRealDamage()
				//		+ " damage!");
				
				// Allow opp to attack player
				success = true;
			} else if (successfulCast == 2) {
				// Spell hit. Enemy not affected. Ditto prior comments
				String toDisplay = toCast.addedEffects(opponent);
				if (!toDisplay.equals("")) {
					displayBText(toDisplay);
				}
				
				// Change here: enemy took 0 damage. Inform user of this
				displayBText("Enemy successfully defended spell cast");
				success = true;
			} else if (successfulCast == 3) {
				// Healing spell has been cast
				// Instead of adding effects to opponent, add to player
				// and display as much
				String toDisplay = toCast.addedEffects(player);
				if (!toDisplay.equals("")) {
					displayBText(toDisplay);
				}
				player.cast(toCast, player);
				success = true;
			} else if (successfulCast == 0) {
				// Spell cannot be cast. Not enough mana
				displayBText("Not enough mana!");
			} else {
				// User selected an option not displayed in menu
				if (m.getSelected() > m.numChoices()) {
					displayBText("No such spell!");
				}
			}
		} else {
			displayBText("You do not know any spells!");
		}
		
		if(success && !opponent.dead()) {
			loseHealth(player, opponent.attack(player));
		}
	}
	
	// Method displays a textBox containing drawn representation of
	// text String. Waits for input from user before disappearing
	// and allowing the user to do something else
	public void displayBText(String text) {
		// Change text to passed String and update t's Scanner object
		t.setText(text);
	
		// Make sure t is the only visible text Object
		if(m != null) {
			m.setVisible(false);
		}
		
		t.display();
	}
	
	/**
	 * 
	 * @param choices - String[] holding all choices presented in menu
	 * @param width - number of columns the menu has
	 * @param canExit - if true, user can leave menu at any time
	 * @return 0 if method finished running
	 */
	public void displayBMenu(String[] choices, int width, boolean canExit) {
		// Re-assign m to reflect passed variables
		m = new TextMenu(choices, width, TEXT_BOX_X, TEXT_BOX_Y, g, canExit);
		t.setVisible(false);
		m.select();
	}
	
	// Method that tells Applet what to draw for BattleHandler
	// Called in the paint method of Game class
	public void draw(Graphics g) {
		// Draw background of battle
		Renderer.renderBattle(g, width, height, this.g.getXCoord(), this.g.getYCoord());
		
		// Draw player and his opponent 
		player.draw(g);
		opponent.draw(g);
		
		// Draw t and m if they exist
		if(t != null) {
			t.draw(g);
		}
		
		if(m != null) {
			m.draw(g);
		}
		
		// Draw important health/mana bars if they exist
		if(playStat != null && oppStat != null) {
			playStat.draw(g);
			oppStat.draw(g);
		}
	}
	
	public void loseHealth(Creature c, int loss) {
		int update = 0;
		
		while (update < loss && c.getHp() > 0) {
			c.damage(1);
			g.repaint();
			g.sleep(LOSS_TIME / loss);
			update++;
		}
		
		if(c instanceof Player) {
			displayBText("Took " + loss + " damage!");
		} else {
			displayBText("Dealt " + loss + " damage!");
		}
	
	}
}
