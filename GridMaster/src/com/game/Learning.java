package com.game;

import java.util.Random;

public class Learning {

	public static void main(String[] args) {
		
		int numGens = 20;
		int numSims = 5;
		int learningRate = 50;
		
		int oneAlgNumSims = 2000;
		
		/*
		 *  Probabaility of moving RIGHT
		 */
		int p1 = 250;

		/*
		 *  Probability of moving LEFT
		 */
		int p2 = 250;

		/*
		 *  Probability of moving DOWN
		 */
		int p3 = 250;

		/*
		 *  Probability of moving UP
		 */
		int p4 = 1000 - p3 - p2 - p1;

		int[] probs = { p1, p2, p3, p4 };
		
		long sTime = System.currentTimeMillis();
		
		//learn(numGens, numSims, learningRate);
		simOneAlg(probs, oneAlgNumSims);
		
		long tTime = System.currentTimeMillis();
		
		System.out.println("Executed in: " + (double)(tTime - sTime) / 1000D + "sec");

	}
	
	public static void learn(int numGens, int numSims, int learningRate) {
		
		Random r = new Random();
		
		ScoreCard[] scores = new ScoreCard[1000];
		
		// Number of generations that creatures will have to evolve
		int NUM_GENS = numGens;
		
		// Number of trials each creature gets to perform
		int NUM_SIMS = numSims;
		
		double[] genScores = new double[NUM_GENS];
		
		for (int i = 0; i < scores.length; i++) {
			int p1 = 0;
			int p2 = 0;
			int p3 = 0;
			int p4 = 0;
			
			do {
			// Prob right
			p1 = r.nextInt(999) + 1;
			
			// Prob left
			p2 = r.nextInt(999) + 1;
			
			// Prob down
			p3 = r.nextInt(999) + 1;
			
			// Prob up
			p4 = 1000 - p3 - p2 - p1;
			
			} while(p4 <= 0);

			int[] probs = { p1, p2, p3, p4 };
			
			ScoreCard card = new ScoreCard(probs, 0);
			
			scores[i] = card;
		}
		
		
		for (int x = 0; x < NUM_GENS; x++) {
			for (int i = 0; i < scores.length; i++) {
				
				if(i % 100 == 0) {
					System.out.println(i + " creatures verified in gen " + x);
				}
				
				ScoreCard card = scores[i];
				int total = 0;
				
				for (int j = 0; j < NUM_SIMS; j++) {
					GridGame game = new GridGame();
					game.init();

					game.setPlayer(card.getProbs());

					while (!game.getGrid().isFull()) {
						game.execute();
					}

					int[] tempCount = game.gameEnd();

					total += tempCount[0];

				}
				card.setPerf((double)total / (double)NUM_SIMS);

				scores[i] = card;
				
			}

			System.out.println("Generation: " + x);
			System.out.println();
			
			double topScore = newGeneration(scores, learningRate);
			
			
			
			if(learningRate - 2 > 0) {
				learningRate -= 2;
			}
			
			NUM_SIMS += 10;
		
			
			genScores[x] = topScore;
		}
		
		for(double i: genScores) {
			System.out.println(i);
		}
	}
	
	public static double newGeneration(ScoreCard[] scores, int learningRate) {
		
		int LEARNING_RATE = learningRate;
		
		Random r = new Random();
		
		for(int x = 0; x < scores.length; x++) {
			for(int y = 0; y < scores.length - 1; y++) {
				if(scores[y].getPerf() < scores[y+1].getPerf()) {
					ScoreCard temp = scores[y];
					scores[y] = scores[y+1];
					scores[y+1] = temp;
				}
			}
		}
		
		double topScore = scores[0].getPerf();
		double medScore = scores[scores.length / 2].getPerf();
		
		System.out.println("Best score was: " 
				+ topScore + " cells" + " with probs: ");
		
		for(int i: scores[0].getProbs()) {
			System.out.print(i + ", ");
		}
		System.out.println();
		
		System.out.println("Median score was: " 
				+ medScore + " cells");
		
		for(int i: scores[scores.length / 2].getProbs()) {
			System.out.print(i + ", ");
		}
		System.out.println();
		
		System.out.println();
		System.out.println("------------------------");
		System.out.println();
		
		ScoreCard[] newArr = new ScoreCard[1000];
		
		for(int x = 0; x < scores.length / 2; x++) {
			
			newArr[x] = new ScoreCard(scores[x].getProbs(), scores[x].getPerf());
			newArr[x + scores.length / 2] = new ScoreCard(scores[x].getProbs(), scores[x].getPerf());
			
			ScoreCard card = scores[x];
	
			int[] tempProbs = card.getProbs();
			int[] playProbs = new int[4];
			
			for(int y = 0; y < 4; y++) {
				int t = tempProbs[y];
				playProbs[y] = t;
			}
			
			do {
				
				//playProbs = card.getProbs();
				
				for(int i = 0; i < playProbs.length - 1; i++) {
					
					//int delta = (r.nextInt(3) - 1) * LEARNING_RATE;
					int delta = r.nextInt(LEARNING_RATE) - LEARNING_RATE / 2;
				
					if(playProbs[i] + delta < 1000 && playProbs[i] + delta > 0) {
						playProbs[i] += delta;
					}
				}
				
				playProbs[3] = 1000 - playProbs[0] - playProbs[1] - playProbs[2];
				
			} while (playProbs[3] <= 0);
			
			newArr[x + scores.length / 2].setProbs(playProbs);
			//scores[x].setProbs(temp);
		}
		
		for(int x = 0; x < newArr.length; x++) {
			scores[x] = newArr[x];
		}
		
		return medScore;
	}
	
	public static void simOneAlg(int[] probs, int numSims) {
		final int NUM_SIMS = numSims;
		
		Random r = new Random();
		
		int[] ranks = new int[NUM_SIMS];
		
		for (int a = 0; a < NUM_SIMS; a++) {
			GridGame game = new GridGame();
			game.init();
			
			game.setPlayer(probs);
			
			while(!game.getGrid().isFull()) {
				game.execute();
			}
			
			int[] tempCount = game.gameEnd();

			ranks[a] = tempCount[0];
		}
		
		int total = 0;
		
		for(int x = 0; x < ranks.length; x++) {
			total += ranks[x];
			System.out.println((double)total / (double)(x + 1));
		}
		
		System.out.println("On average, Red will have: " 
		+ (double)total / (double)NUM_SIMS + " cells");

	}
}
