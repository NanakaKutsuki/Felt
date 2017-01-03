package org.kutsuki.felt;

import java.util.Random;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomAdaptor;

public class Dices {
	private Random random;

	public Dices() {
		this.random = RandomAdaptor.createAdaptor(new MersenneTwister());
	}

	public int roll() {
		int dice1 = random.nextInt(6) + 1;
		int dice2 = random.nextInt(6) + 1;
		return dice1 + dice2;
	}

	public int roll(int dice1, int dice2) {
		return dice1 + dice2;
	}
}
