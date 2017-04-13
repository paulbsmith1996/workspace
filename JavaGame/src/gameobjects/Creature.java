package gameobjects;
// Paul Baird-Smith 2015

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import Interfaces.CreatureInt;

/**
 *
 * Class Creature - Defines all active entities in Game that can engage
 * in a Battle. Holds relevant stats and methods to retrieve information
 * on each Object
 *
 */
public class Creature extends GameObject implements CreatureInt {

	
	private final int MIN_DAMAGE = 10;
	
	/**
	 * Ints holding Creature's maximal HP and mana
	 */
	protected int maxHP;
	protected int maxMana;

	/**
	 * Ints and String holding stats of the creature
	 */
	protected int health;
	protected int mana;
	protected int ap;
	protected int dp;
	protected int ma;
	protected int md;
	protected int level;
	protected String name;

	
	protected int velX, velY;

	/**  See below for constructor **/
	public Creature(int x, int y, int velX, int velY, BufferedImage image) {
		super(x,y,image);
		this.velX = velX;
		this.velY = velY;
	}
	
	public void setVelX(int val) {
		velX = val;
	}

	public void setVelY(int val) {
		velY = val;
	}
	
	public int getVelX() { return this.velX; }
	public int getVelY() { return this.velY; }

	public void setStill() {
		velX = 0;
		velY = 0;
	}
	
	public void move(Rectangle bounds) {
		int x = getX();
		int y = getY();
		
		setX(x + velX);
		setY(y + velY);

		// Check left edge collision
		if (x < bounds.x && velX < 0) {
			// Make ball stay inbounds and bounce off bound
			velX = -velX;
			setX(x + 2 * (bounds.x - x));

			// Check right edge collision
		} else if ((x + getWidth()) > (bounds.x + bounds.width) && velX > 0) {
			velX = -velX;
			setX(x - 2 * ((x + getWidth()) - (bounds.x + bounds.width)));

			// Check for top edge collision
		} else if (y < bounds.y && velY < 0) {
			velY = -velY;
			setY(y + 2 * (bounds.y - y));

			// Check for bottom edge collision
		} else if ((y + getHeight()) > (bounds.y + bounds.height) && velY > 0) {
			velY = -velY;
			setY(y - 2 * ((y + getHeight()) - (bounds.y + bounds.height)));
		}
	}

	
	/******************** Creature Game Logic ************/
	
	
	/**
	 * Returns level of Creature
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Methods used to set and get HP and to retrieve maximum HP for Creature
	 */
	public int getHp() {
		return health;
	}

	public int getMaxHP() {
		return this.maxHP;
	}

	public void setHP(int hp) {
		this.health = hp;
	}

	/**
	 * Methods to manipulate and retrieve Creature's Mana
	 */
	public int getMana() {
		return mana;
	}

	public int getMaxMana() {
		return this.maxMana;
	}

	public void setMana(int val) {
		this.mana = val;
	}

	public void setMaxMana(int val) {
		this.maxMana = val;
	}

	/**
	 * Get and manipulate the attack points of the creature
	 */
	public int getAP() {
		return ap;
	}

	public void setAP(int newVal) {
		this.ap = newVal;
	}

	/**
	 * Get and manipulate the defense points of the creature
	 */
	public int getDP() {
		return dp;
	}

	public void setDP(int newVal) {
		this.dp = newVal;
	}

	/**
	 * Get and manipulate Magic Attack of Creature
	 */
	public int getMA() {
		return ma;
	}

	public void setMA(int newVal) {
		this.ma = newVal;
	}

	/**
	 * Get and manipulate Magical Defense of Creature
	 */
	public int getMD() {
		return md;
	}

	public void setMD(int newVal) {
		this.md = newVal;
	}

	/**
	 * Deal damage to the creature
	 *
	 * @param int - represents damage dealt to Creature
	 */
	public void damage(int damage) {
		health -= damage;
	}

	/**
	 * Creature attacks another creature, dealing damage if ap is higher than
	 * other's dp otherwise there is no effect if other's defense is greater
	 * than player's attack.
	 *
	 * @param Creature
	 *            - Creature to be attacked by this
	 */
	public int attack(Creature other) {

		Random r = new Random();

		// Probabilistic determination of attack and defense strength of each
		// Creature
		int attack = 3 * (3 * getAP() / 4 + r.nextInt(getAP() / 2));
		int defense = 3 * (3 * getDP() / 4 + r.nextInt(getDP() / 2));

		if (attack > defense) {
			// Player's attack is higher
			return attack - defense;
		} else if (attack == defense) {
			// Player's attack matches
			damage(MIN_DAMAGE);
			return 1;
		}
		
		return 0;

	}

	/**
	 * Returns true if creature has no more health
	 */
	public boolean dead() {
		if (health <= 0) {
			return true;
		}

		return false;
	}

	/**
	 * Displays minimal amount of current stats of creature
	 */
	public String returnStats() {
		return (name + "'s Stats: Health = " + health + "/" + maxHP + "\n"
				+ "Mana = " + mana + "/" + maxMana);
	}

	/**
	 * A more verbose description of Creature's stats
	 */
	public String returnMoreStats() {
		return ("Attack = " + this.ap + " Defense = " + this.dp + " Mag Def = "
				+ this.md + " Mag Att = " + this.ma);
	}

	/**
	 * Most verbose stats: Displays all Creature's stats
	 */
	public String returnAllStats() {

		return ("Health = " + getHp() + "/" + maxHP + "\n" 
		+ "Mana = " + getMana() + "/" + maxMana + "\n" 
		+ "Attack = " + getAP() + "\n" 
		+ "Defense = " + getDP() + "\n" 
		+ "Mag Att= " + getMA() + "\n" 
		+ "Mag Def= " + getMD() + "\n" 
		+ "Level = " + getLevel());
	}

}