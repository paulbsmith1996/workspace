package gameobjects;
// Paul Baird-Smith 2015

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * Class NPC - Defines objects of type Creature that are Not Player Controlled.
 * Method of stats determination is different from objects of class Player
 * to account for random generation.
 *
 */
public class NPC extends Creature {

	/******* Graphics Handled Here *******/

	public void setStill() {
		velX = 0;
		velY = 0;
	}

	/******* Game Logic Handled Here *******************/
	
	private final int VISION = 110;

	/**
	 * Single constructor for an NPC object that randomly generates stats for a
	 * Creature object
	 *
	 * @param String
	 *            - Define name of NPC
	 * @param int - Determine level and thus stats of the NPC
	 */
	public NPC(int x, int y, int velX, int velY, String name, int level, BufferedImage image) {

		super(x,y,velX,velY,image);

		/***** Game Logic Constructed *****/

		// New random to help generate stats
		Random stats = new Random();

		// Incorporate added level of variation to the stats to have them
		// more centered around the user's own stats
		int var;

		if (level < 9) {
			var = 3;
		} else {
			var = level / 3;
		}

		int mult;

		if (level < 2) {
			mult = 0;
		} else {
			mult = level - 2;
		}

		/**
		 * Define stats and evolution of stats for all objects of class NPC
		 */
		this.maxMana = this.mana = (7 + (3 * mult) + stats.nextInt(var));
		this.ap = (1 + (3 * mult) + stats.nextInt(var));
		this.maxHP = this.health = (5 + (2 * ap) + stats.nextInt(var));
		this.dp = (1 + (3 * mult) + stats.nextInt(var));
		this.ma = (1 + (2 * mult) + stats.nextInt(var));
		this.md = (2 + (2 * mult) + stats.nextInt(var));
		this.name = name;
		this.level = level;
	}
	
	public NPC(int x, int y, int velX, int velY, String name, BufferedImage image) {

		super(x,y,velX,velY,image);
	}
	
	public void move(Rectangle bounds, Player player) {
		if (player.getDistance(this) < VISION) {
			int x = getX();
			int y = getY();

			int newVelX = (player.getX() - x);
			int newVelY = (player.getY() - y);

			int hyp = (int) Math.sqrt((newVelX * newVelX) + (newVelY * newVelY));

			newVelX = (int) (2 * (double) newVelX / (double) hyp);
			newVelY = (int) (2 * (double) newVelY / (double) hyp);

			setX(x + newVelX);
			setY(y + newVelY);

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
		} else {
			((Creature)this).move(bounds);
		}
	}

}