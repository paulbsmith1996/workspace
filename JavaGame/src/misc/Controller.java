package misc;
import gameobjects.Boss;
import gameobjects.Creature;
import gameobjects.GameObject;
import gameobjects.NPC;
import gameobjects.Obstacle;
import gameobjects.Player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

/**
 * Controller - Holds all objects currently being rendered by the
 * Applet. checkIntersect methods are called often to check if
 * any of the objects contained in this object are touching
 * 
 * @author Paul
 *
 */
public class Controller extends Vector<GameObject> {
	
	public static final int NOINTERSECT = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int TOP = 3;
	public static final int BOTTOM = 4;
	
	// Keeps track of where the Creature intersected the Obstacle
	// in the checkObstacleIntersect method
	private int side = 0;
	
	// Instructions to draw all objects currently in the
	// Controller object
	public void draw(Graphics g) {
		// Loop through all objects contained by Controller
		for(GameObject obj: this) {
			// Draw object if it exists
			if(obj != null) {
				obj.draw(g);
			}
		}
	}
	
	public int getSide() { return this.side; }
	
	// Checks to see if the passed Creature object intersects any of
	// the Creatures/NPCs in the Controller. If it does, method returns the
	// intersecting object. If it doesn't method returns null.
	public NPC checkMobIntersect(Creature creature) {
		// Iterate through all GameObjects contained in Controller
		for(GameObject obj: this) {
			// Checks that GameObject is an NPC and it intersects
			// passed Creature
			
			if(obj instanceof Boss && obj.intersects(creature.getBounds())) {
				//int creatureY = creature.getY();
				//int creatureX = creature.getX();
				//int bossY = obj.getY();
				//int bossX = obj.getX();
				//if(creatureY > bossY && creatureY <= bossY + obj.getHeight()) {
					return (NPC) obj;
				//}
			}
			
			if(obj instanceof NPC && obj.intersects(creature.getBounds())) {
				return (NPC)obj;
			}
		}
		
		// creature does not intersect any NPC in Controller
		return null;
	}
	
	// Moves all the objects contained in the Controller according
	// to the move() method belonging to each object
	public void move(Rectangle r) {
		
		Player player = null;
		
		for(GameObject obj: this) {
			if(obj instanceof Player) {
				player = (Player)obj;
			}
		}
		
		// Iterate through all GameObjects in Controller
		for(GameObject obj: this) {
			// If GameObject is a Creature, move it
			if(obj instanceof Creature && !(obj instanceof Player)) {
				if( ! (obj instanceof NPC)) {
					((Creature)obj).move(r);
				} else {
					((NPC)obj).move(r, player);
				}
			}
		}
		
	}
	
	// In future, will check if any Creature is intersecting one of
	// its allies
	public boolean checkFriendIntersect() {
		return false;
	}
	
	// Checks to see if the passed Creature intersects any of the
	// Obstacles being held in the Controller object. If yes,
	// method returns intersecting object. If no, method returns null
	public GameObject checkObstacleIntersect(Creature creature) {
		
		// Default is that Creature is not intersecting anything
		GameObject intersecting = null;
		side = 0;
		
		// Iterate through all GameObjects contained in Controller
		for (GameObject obstacle : this) {
			// Check the GameObject is an Obstacle
			if (obstacle instanceof Obstacle) {
				
				if (creature.leftBounds().intersects(obstacle.rightBounds())) {
					
					// Creature intersecting from the left side
					creature.setVelX(-creature.getVelX());
					
					// Move Creature out of object it is intersecting
					creature.setX(obstacle.getX() + obstacle.getWidth() + 4);
					
					// Update intersecting to current GameObject
					intersecting = obstacle;
					side = 1;
				}
				
				if (creature.rightBounds().intersects(obstacle.leftBounds())) {
					
					// Creature intersecting from the right side					
					creature.setVelX(-creature.getVelX());
					
					// Move Creature out of object if intersecting
					creature.setX(obstacle.getX() - creature.getWidth() - 4);
					
					// Update intersecting to current GameObject
					intersecting = obstacle;
					side = 2;
				}
				
				if (creature.topBounds().intersects(obstacle.bottomBounds())) {
					
					// Top of Creature hit something
					creature.setVelY(-creature.getVelY());
					
					// Move Creature out of object it intersecting
					creature.setY(obstacle.getY() + obstacle.getHeight() + 4);
					
					// Update intersecting to current GameObject
					intersecting = obstacle;
					side = 3;
				}
				
				if (creature.bottomBounds().intersects(obstacle.topBounds())) {
					
					// Bottom of Creature hit something					
					creature.setVelY(-creature.getVelY());
					
					// Move Creature out of object if intersecting
					creature.setY(obstacle.getY() - creature.getHeight() - 4);
					
					// Update intersecting to current GameObject
					intersecting = obstacle;
					side = 4;
				}
			}
		}
		
		// Return GameObject that creature is currently intersecting
		return intersecting;
	}

}
