package org.kutsuki.felt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomAdaptor;

public class Dices {
	private int index;
	private List<Integer> rollList;
	private Random random;

	public Dices() {
		this.random = RandomAdaptor.createAdaptor(new MersenneTwister());
	}

	public Dices(int... rolls) {
		this.index = 0;
		this.rollList = new ArrayList<Integer>();

		for (int i = 0; i < rolls.length; i++) {
			rollList.add(rolls[i]);
		}
	}

	public int roll() {
		int roll = 0;

		if (rollList == null) {
			int dice1 = random.nextInt(6) + 1;
			int dice2 = random.nextInt(6) + 1;
			roll = dice1 + dice2;
		} else {
			roll = rollList.get(index);

			if (index < rollList.size() - 1) {
				index++;
			}
		}

		return roll;
	}
}
