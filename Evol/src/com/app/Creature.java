package com.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Creature extends GameObject {

	private double rot;

	private double[] xPoints;
	private double[] yPoints;

	private int width = 10;
	private int height = 20;

	private Random rand;

	private int count;
	private static int divideCount = 0;

	private final int ROTATE_SPEED = 10;
	private final int DEFAULT_DIVISION_THRESHOLD = 30000;
	//private final int DEFAULT_DIVISION_THRESHOLD = 10000;
	private final int START_MOVE_SPEED = 10;
	private final int START_EAT_SPEED = 100;
	private final int DEFAULT_FP = 3000;
	private final int DEFAULT_STARVING_RATE = 20;
	private final int VISION_DISTANCE = 100;
	private final int MUTATION_RATE = 10;

	private int starvingRate = DEFAULT_STARVING_RATE;
	private int divisionThreshold = DEFAULT_DIVISION_THRESHOLD; 

	// How far off the Creature can be from the center of a food source
	private final int MARGIN_OF_ERROR = 25;

	private Evol game;

	private int gameWidth, gameHeight;
	private Rectangle gameBounds;

	// This will hold the x/y coordinates of two points:
	// the x/y coordinate of the creature and the edge of
	// the window that the creature is looking at
	private int[][] vision;

	private int gene1 = 0;
	private int gene2 = 0;
	private int gene3 = 0;
	private int gene4 = 0;
	private int gene5 = 0;
	private int stimuli = 0;
	
	private boolean stimulated;

	private final int STIM_FOOD = 1;
	private final int STIM_FLEE = 2;
	private final int STIM_FIGHT = 4;
	private final int ON_FOOD = 8;
	
	// NUM_INSTRUCTIONS * INSTRUCTION_LENGTH <= 32
	private final int NUM_INSTRUCTIONS = 10;
	private final int INSTRUCTION_LENGTH = 2;
	
	private int fleeing;
	private boolean dead;

	private Controller enviObjects;

	private int foodPoints;

	private int eatSpeed;
	private int movementSpeed;

	private int species;
	
	private int instCount;

	public Creature(int x, int y, double rot, int species, Evol game) {
		super(x, y);
		
		rand = new Random();

		this.game = game;

		this.gameWidth = game.getWidth();
		this.gameHeight = game.getHeight();
		
		this.gameBounds = game.getBounds();

		this.enviObjects = game.getController();

		this.species = species;

		this.vision = new int[2][2];

		this.xPoints = new double[3];
		this.yPoints = new double[3];

		this.rot = 0;

		this.stimulated = false;
		
		//this.stimFood = false;
		//this.stimFlee = false;
		//this.stimFight = false;
		//this.eating = false;
		
		// Pick a random sequence of 0's and 1's so long as the first 12 digits are 1
		//this.gene = rand.nextLong() | 4095;
		
		// Instructions for on food source
		this.gene1 = rand.nextInt();
		
		// Instructions for seeing predator
		this.gene2 = rand.nextInt();
		this.gene2 |= 2;
		this.gene2 &= (~1);
		
		// Instructions for seeing prey
		this.gene3 = rand.nextInt();
		
		// Instructions for seeing a food source 
		this.gene4 = rand.nextInt();
		
		// Instructions for no stimulation
		this.gene5 = rand.nextInt();
		
		this.fleeing = 0;
		this.dead = false;

		this.foodPoints = DEFAULT_FP * (species + 1);
		this.movementSpeed = START_MOVE_SPEED;
		this.eatSpeed = START_EAT_SPEED;
		
		this.starvingRate *= (2 * species + 1);
		
		this.divisionThreshold *= (4 * species) + 1;
		//this.divisionThreshold *= (12 * species) + 1;

		xPoints[0] = x;
		xPoints[1] = x - width / 2;
		xPoints[2] = x + width / 2;

		yPoints[0] = y - 3 * height / 4;
		yPoints[1] = yPoints[2] = y + height / 4;

		setSize(foodPoints);

		rotate(rot);

		count = 0;
		instCount = 0;
	}
	
	public void setGene1(int gene1) { this.gene1 = gene1; }
	public int getGene1() { return this.gene1; }
	
	public int getGene2() {
		return gene2;
	}

	public void setGene2(int gene2) {
		this.gene2 = gene2;
	}

	public int getGene3() {
		return gene3;
	}

	public void setGene3(int gene3) {
		this.gene3 = gene3;
	}

	public int getGene4() {
		return gene4;
	}

	public void setGene4(int gene4) {
		this.gene4 = gene4;
	}

	public int getGene5() {
		return gene5;
	}

	public void setGene5(int gene5) {
		this.gene5 = gene5;
	}

	public void setStimFood() { this.stimuli |= STIM_FOOD; }
	public boolean getStimFood() { return (this.stimuli& STIM_FOOD) != 0; }
	
	public void setStimFlee() { this.stimuli |= STIM_FLEE; }
	public boolean getStimFlee() { return (this.stimuli & STIM_FLEE) != 0; }
	
	public void setStimFight() { this.stimuli |= STIM_FIGHT; }
	public boolean getStimFight() { return (this.stimuli & STIM_FIGHT) != 0; }
	
	public void setOnFood() { this.stimuli |= ON_FOOD; }
	public boolean getOnFood() { return (this.stimuli & ON_FOOD) != 0; }
	

	public int getStarvingRate() {
		return starvingRate;
	}



	public void setStarvingRate(int starvingRate) {
		this.starvingRate = starvingRate;
	}



	public int getSpecies() {
		return species;
	}

	public void setSpecies(int species) {
		this.species = species;
	}

	public int getFoodPoints() {
		return this.foodPoints;
	}

	public void setFoodPoints(int points) {
		this.foodPoints = points;
	}

	public void incX(int increment) {
		for (int i = 0; i < 3; i++) {
			xPoints[i] += increment;
		}

		setX(getX() + increment);
	}

	public void incY(int increment) {
		for (int i = 0; i < 3; i++) {
			yPoints[i] += increment;
		}

		setY(getY() + increment);
	}

	public int[][] getVision() {
		return this.vision;
	}

	public void setVision(double rot) {

		vision[0][0] = getX();
		vision[0][1] = getY();

		if (rot == 0) {
			// Facing up
			vision[1][0] = vision[0][0];
			vision[1][1] = Math.max(0, vision[0][1] - VISION_DISTANCE);
		} else if (rot == Math.PI / 2) {
			// Facing left
			vision[1][0] = Math.max(0, vision[0][0] - VISION_DISTANCE);
			vision[1][1] = vision[0][1];
		} else if (rot == Math.PI) {
			// Facing down
			vision[1][0] = vision[0][0];
			vision[1][1] = Math.min(gameHeight, vision[0][1] + VISION_DISTANCE);
		} else {
			// Facing right
			vision[1][0] = Math.min(gameWidth, vision[0][0] + VISION_DISTANCE);
			vision[1][1] = vision[0][1];
		}
	}

	// The result is that when the creature is facing "towards"
	// an object, it is stimulated
	public void checkStimulus() {

		int numObjects = enviObjects.size();

		stimulated = false;
		//stimFood = false;
		//stimFlee = false;
		//stimFight = false;
		
		// Sets the first 4 bits to 0 
		stimuli &= (~15);

		for (int i = 0; i < numObjects; i++) {
			GameObject obj = enviObjects.elementAt(i);

			int objX = obj.getX();
			int objY = obj.getY();

			// Check to see if Creature is on a food source
			if (Math.abs(vision[0][0] - objX) < MARGIN_OF_ERROR && Math.abs(vision[0][1] - objY) < MARGIN_OF_ERROR) {

				// Make separate if check to check if food in case add more
				// objects later
				if (obj instanceof Food && species < 1 && fleeing <= 0) {
					//eating = true;
					setOnFood();
					
					eat((Food) obj);
					
					break;
				} else if (obj instanceof Creature) {
					
					// Creature is on a prey
					if (((Creature) obj).getSpecies() < species) {
						kill((Creature) obj);
					}
				}

			}

			if (vision[1][1] - vision[0][1] < 0 && objY - vision[0][1] < 0 
					&& objY - vision[0][1] > -VISION_DISTANCE
					&& Math.abs(objX - vision[0][0]) < MARGIN_OF_ERROR) {

				// Creature is facing up and sees an object
				stimulated = true;

				if (obj instanceof Food && species < 1) {
					//stimFood = true;
					setStimFood();
				} else if (obj instanceof Creature) {
					if (((Creature) obj).getSpecies() > species) {
						//stimFlee = true; // die() if too close;
						setStimFlee();
					} else if (((Creature) obj).getSpecies() < species) {
						//stimFight = true; // kill((Creature)obj) if close
											// enough;
						setStimFight();
					}
				}

			} else if (vision[1][0] - vision[0][0] < 0 && objX - vision[0][0] < 0
					&& objX - vision[0][0] > -VISION_DISTANCE
					&& Math.abs(objY - vision[0][1]) < MARGIN_OF_ERROR) {

				// Creature is facing left and sees an object
				stimulated = true;

				if (obj instanceof Food && species < 1) {
					//stimFood = true;
					setStimFood();
				} else if (obj instanceof Creature) {
					if (((Creature) obj).getSpecies() > species) {
						//stimFlee = true; // die() if too close;
						setStimFlee();
					} else if (((Creature) obj).getSpecies() < species) {
						//stimFight = true; // kill((Creature)obj) if close
											// enough;
						setStimFight();
					}
				}

			} else if (vision[1][1] - vision[0][1] > 0 && objY - vision[0][1] > 0
					&& objY - vision[0][1] < VISION_DISTANCE
					&& Math.abs(objX - vision[0][0]) < MARGIN_OF_ERROR) {

				// Creature is facing down and sees an object
				stimulated = true;

				if (obj instanceof Food && species < 1) {
					//stimFood = true;
					setStimFood();
				} else if (obj instanceof Creature) {
					if (((Creature) obj).getSpecies() > species) {
						//stimFlee = true; // die() if too close;
						setStimFlee();
					} else if (((Creature) obj).getSpecies() < species) {
						//stimFight = true; // kill((Creature)obj) if close
											// enough;
						setStimFight();
					}
				}

			} else if (vision[1][0] - vision[0][0] > 0 && objX - vision[0][0] > 0
					&& objX - vision[0][0] < VISION_DISTANCE
					&& Math.abs(objY - vision[0][1]) < MARGIN_OF_ERROR) {

				// Creature is facing right and sees an object
				stimulated = true;

				if (obj instanceof Food && species < 1) {
					//stimFood = true;
					setStimFood();
				} else if (obj instanceof Creature) {
					if (((Creature) obj).getSpecies() > species) {
						//stimFlee = true; // die() if too close;
						setStimFlee();
					} else if (((Creature) obj).getSpecies() < species) {
						//stimFight = true; // kill((Creature)obj) if close
											// enough;
						setStimFight();
					}
				}

			}

			//eating = false;
			stimuli &= (~ON_FOOD);

		}

	}

	public void die() {
		this.foodPoints = 0;
		dead = true;
	}

	public void kill(Creature enemy) {
		foodPoints += enemy.getFoodPoints();
		enemy.die();
	}

	public void eat(Food food) {

		int foodAmount = food.getAmount();

		if (eatSpeed <= foodAmount) {
			// There is enough for the Creature to have a full bite
			foodPoints += eatSpeed;
			food.setAmount(foodAmount - eatSpeed);
		} else {
			// Not enough food for the Creature to have a full bite
			foodPoints += foodAmount;
			food.setAmount(0);
		}

		setSize(foodPoints);
	}

	public void setSize(int size) {
		width = ((int) Math.pow(size, 0.25));
		height = 2 * width;

		xPoints[0] = getX();
		xPoints[1] = getX() - width / 2;
		xPoints[2] = getX() + width / 2;

		yPoints[0] = getY() - 3 * height / 4;
		yPoints[1] = yPoints[2] = getY() + height / 4;

		for (int i = 0; i < 3; i++) {
			double xTemp = xPoints[i];
			double yTemp = yPoints[i];

			xPoints[i] = (Math.cos(rot) * (xTemp - getX()) + Math.sin(rot) * (yTemp - getY())) + getX();
			yPoints[i] = (-Math.sin(rot) * (xTemp - getX()) + Math.cos(rot) * (yTemp - getY())) + getY();

		}

		int dec = 6000 * (species + 1);
		
		if((START_MOVE_SPEED - (foodPoints / dec)) / (species + 1) > 0) {
			movementSpeed = (START_MOVE_SPEED - (foodPoints / dec)) / (species + 1);
		}

	}

	public void rotate(double angle) {

		for (int i = 0; i < 3; i++) {
			double xTemp = xPoints[i];
			double yTemp = yPoints[i];

			xPoints[i] = (Math.cos(angle) * (xTemp - getX()) + Math.sin(angle) * (yTemp - getY())) + getX();
			yPoints[i] = (-Math.sin(angle) * (xTemp - getX()) + Math.cos(angle) * (yTemp - getY())) + getY();

		}

		rot += angle;
		rot %= (2 * Math.PI);

		setVision(rot);

	}

	public void move() {
		if (!dead) {
			
			
			
			
			if (species < 2) {
				foodPoints -= starvingRate;
				setSize(foodPoints);

				/*
				if (!getStimFlee()) {
					checkStimulus();
				}
				

				if (instCount == 0) {
					stimuli &= (~STIM_FLEE);
				}
				*/

				// 64 bits in a long - 4 bits for conds = 60 bits to use for
				// sequence
				if (getStimFlee()) {
					// Spotted a predator
					execute(gene2, instCount);
				} else if (getOnFood()) {
					// On a food source
					execute(gene1, instCount);
				} else if (getStimFight()) {
					// Spotted prey
					execute(gene3, instCount);
				} else if (getStimFood()) {
					// Spotted food
					execute(gene4, instCount);
				} else {
					// Unstimulated
					execute(gene5, instCount);
				}

				instCount++;
				instCount %= NUM_INSTRUCTIONS;

				if (checkDivide()) {
					divide();
				}
				
			}
			
			
			
			/*
			if (species == 1) {
				int turn = rand.nextInt(100);

				foodPoints -= starvingRate;
				setSize(foodPoints);

				checkStimulus();

				if (getStimFlee()) {
					fleeing = 11;
					rotate((Math.PI / 2) * (2 * rand.nextInt(2) - 1));
					advance(game.getBounds());
				}

				if (!getOnFood()) {

					if (getStimFood() || getStimFight()) {
						advance(game.getBounds());
					} else {
						if (count == 0) {
							if (turn < 30) {
								rotate((Math.PI / 2) * (2 * rand.nextInt(2) - 1));
							} else {
								advance(game.getBounds());
								count = rand.nextInt(10) * 10 / movementSpeed;
							}
						} else {
							advance(game.getBounds());
							count--;
						}
					}
				} else {
					count = 0;
					rotate(Math.PI / 2);
				}

				if (checkDivide()) {
					divide();
				}

				fleeing--;
			}
			*/
		}

	}
	
	// Will execute the moves corresponding to the sequence of moves 
	// starting at the indicated start bit in the gene
	public void execute(int gene, int inst) {
		// 111111111111 = 2^13 - 1 = 4095
		
		//long moveSeq = (((4095 << start) & gene) >> (64 - start));
		
		int move = (int)((gene >> (INSTRUCTION_LENGTH * inst)) & 3);
		
		if(move == 3) {
			rotate(Math.PI / 2);
		} else if(move == 2) {
			rotate(3 * Math.PI / 2);
		} else if(move == 1) {
			checkStimulus();
		} else {
			advance(gameBounds);
		}
	}

	public void advance(Rectangle bounds) {

		// The tip of the creature
		int point = 3 * height / 4;

		if (rot == 0 && getY() - movementSpeed > point) {
			// Facing up
			incY(-movementSpeed);
		} else if (rot == Math.PI / 2 && getX() - movementSpeed > point) {
			// Facing left
			incX(-movementSpeed);
		} else if (rot == Math.PI && getY() + movementSpeed < bounds.getHeight() - point) {
			// Facing down
			incY(movementSpeed);
		} else if (rot == 3 * Math.PI / 2 && getX() + movementSpeed < bounds.getWidth() - point) {
			// Facing right
			incX(movementSpeed);
		} else {
			//rotate((Math.PI / 2) * (2 * rand.nextInt(2) - 1));
			rotate(Math.PI);
		}

		setVision(rot);
	}

	public boolean checkDivide() {
		return foodPoints > divisionThreshold;
	}

	public void divide() {
		divideCount++;
		
		if(divideCount % 10 == 0) {
			System.out.println(divideCount + " divisions have occurred --- Species: " + species + "\n\n" 
							 + "Gene 1: " + Integer.toBinaryString(gene1) + "\n"
							 + "Gene 2: " + Integer.toBinaryString(gene2) + "\n"
							 + "Gene 3: " + Integer.toBinaryString(gene3) + "\n"
							 + "Gene 4: " + Integer.toBinaryString(gene4) + "\n"
							 + "Gene 5: " + Integer.toBinaryString(gene5) + "\n\n"
							 + "----------------------------------------------\n\n");
		}
		
		Rectangle bounds = game.getBounds();
		
		foodPoints = DEFAULT_FP;
		setSize(foodPoints);
		
		rotate(-rot);
		
		for(int i = 0; i < 3 - (2 * species); i++) {
			Creature child = new Creature(getX(), getY(), (Math.PI / 2) * (i + 1), species, game);
			
			child.setGene1(gene1);
			child.setGene2(gene2);
			child.setGene3(gene3);
			child.setGene4(gene4);
			child.setGene5(gene5);
			
			int numMutations = rand.nextInt(MUTATION_RATE);
			
			for(int j = 0; j < numMutations; j++) {
				child.mutate();
			}
			
			enviObjects.add(child);
			child.advance(bounds);
		}
		
		advance(bounds);

		//enviObjects.printCreatureNum();
	}
	
	// Mutate one instruction in one gene
	public void mutate() {
		//int instIndex = rand.nextInt(NUM_INSTRUCTIONS);
		//int mutIndex = instIndex * INSTRUCTION_LENGTH;
		
		//int inst1s = (int)Math.pow(2, INSTRUCTION_LENGTH) - 1;
		
		int mutIndex = 1 << rand.nextInt(32);
		
		int geneNum = rand.nextInt(5) + 1;
		
		// Toggles the bit at the mutIndex index
		if(geneNum == 1) {
			setGene1(gene1 ^= mutIndex);
		} else if(geneNum == 2) {
			setGene2(gene2 ^= mutIndex);
		} else if(geneNum == 3) {
			setGene3(gene3 ^= mutIndex);
		} else if(geneNum == 4) {
			setGene4(gene4 ^= mutIndex);
		} else {
			setGene5(gene5 ^= mutIndex);
		}
	}

	@Override
	public void draw(Graphics g) {

		int[] approxX = new int[3];
		int[] approxY = new int[3];

		for (int i = 0; i < 3; i++) {
			approxX[i] = (int) xPoints[i];
			approxY[i] = (int) yPoints[i];
		}

		g.setColor(Color.BLACK);
		g.drawPolygon(approxX, approxY, 3);

		if (species == 0) {
			g.setColor(Color.BLUE);
		} else {
			g.setColor(Color.RED);
		}

		g.fillPolygon(approxX, approxY, 3);

		if (stimulated) {
			if (getStimFlee()) {
				// Sees another Creature
				g.setColor(Color.RED);
			} else if (getStimFood() || getStimFight()) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.ORANGE);
			}
		} else {
			g.setColor(Color.ORANGE);
		}

		g.drawLine(vision[0][0], vision[0][1], vision[1][0], vision[1][1]);
	}

}
