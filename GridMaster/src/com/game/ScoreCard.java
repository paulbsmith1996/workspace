package com.game;

public class ScoreCard {
	
	private int[] probs;
	private double perf;
	
	public ScoreCard(int[] probs, double perf) {
		this.probs = probs;
		this.perf = perf;
	}
	
	public void setProbs (int[] probs) { this.probs = probs; }
	public int[] getProbs() { return this.probs; }
	
	public void setPerf (double perf) { this.perf = perf; }
	public double getPerf() { return this.perf; }
}
