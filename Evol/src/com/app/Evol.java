package com.app;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Evol extends Applet implements Runnable {

	private boolean running;
	private Thread ticker;
	
	private final int WINDOW_WIDTH = 700, WINDOW_HEIGHT = 700;
	private final int OFFSET = 50;
	private final int FPS = 30;
	
	private Controller controller;
	
	private Creature test, test2, enemy, enemy2;
	private Food testFood, testFood2;
	
	private int HERB_START_COUNT = 100, PRED_START_COUNT = 10;
	
	private Random r;
	
	// Number of food sources generated per frame rendering * 1000
	private final int FOOD_GEN_RATE = 600;
	
	public void start() {
		if (ticker == null || !ticker.isAlive()) {
			running = true;
			ticker = new Thread(this);
			ticker.setPriority(Thread.MIN_PRIORITY);
			ticker.start();
		}
	}
	
	public void init() {
		resize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
		r = new Random();
		
		controller = new Controller();
		
		for(int i = 0; i < HERB_START_COUNT; i++) {
			controller.add(new Creature(r.nextInt(WINDOW_WIDTH - OFFSET), r.nextInt(WINDOW_HEIGHT - OFFSET), 
									0, 0, this));
		}
		
		testFood = new Food(150, 400, 10000);
		testFood2 = new Food(350, 50, 5000);
		
		//controller.add(test);
		//controller.add(test2);
		
		
		for(int i = 0; i < PRED_START_COUNT; i++) {
			enemy = new Creature(r.nextInt(450), r.nextInt(450), 0, 1, this);
			controller.add(enemy);
		}

		controller.add(testFood);
		controller.add(testFood2);
	}
	
	public void run() {
		while(running) {
			
			int contSize = controller.size();
			int predCount = 0;
			int vegCount = 0;
			
			for(int i = 0; i < contSize; i++) {
				GameObject obj = controller.elementAt(i);
				if(obj instanceof Creature) {
					if(((Creature) obj).getSpecies() == 1) {
						predCount++;
					} else {
						vegCount++;
					}
					((Creature) obj).move();
				}
			}
			
			controller.testObjects();
			
			if(r.nextInt(1000) < FOOD_GEN_RATE) {
				controller.add(new Food(r.nextInt(WINDOW_WIDTH - OFFSET), r.nextInt(WINDOW_HEIGHT - OFFSET), r.nextInt(10000)));
			}
			/*
			if(predCount < 1 && r.nextInt(10) < 4) {
				Creature newPred = new Creature(r.nextInt(450), r.nextInt(450), 0, this);
				newPred.setSpecies(1);
				controller.add(newPred);
			}
			*/
			
			if(vegCount < 1 && r.nextInt(10) < 4) {
				Creature newVeg = new Creature(r.nextInt(450), r.nextInt(450), 0, 0, this);				
				controller.add(newVeg);
			}
			
			repaint();
			
			try {
				Thread.sleep(1000 / FPS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void paint(Graphics g) {
		
		// Draw background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		
		
		// Draw objects onto background
		controller.draw(g);
		
	}
	
	public void stop() {
		running = false;
	}
	
	public Controller getController() {
		return this.controller;
	}
}
