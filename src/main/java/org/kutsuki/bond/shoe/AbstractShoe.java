package org.kutsuki.bond.shoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomAdaptor;

public abstract class AbstractShoe {
	private int decks;
	private int index;
	private int reshuffle;
	private List<Card> shoe;
	private Random random;

	public abstract int getCount();

	public abstract void resetCount();

	// constructor
	public AbstractShoe(int decks) {
		this.decks = decks;
		this.random = RandomAdaptor.createAdaptor(new MersenneTwister());
		this.reshuffle = (52 * getDecks()) - 16;
		this.index = reshuffle + 1;
		this.shoe = new ArrayList<Card>();

		for (int i = 0; i < getDecks(); i++) {
			for (int j = 2; j < 15; j++) {
				Card c = new Card(j, 'c');
				Card d = new Card(j, 'd');
				Card h = new Card(j, 'h');
				Card s = new Card(j, 's');
				shoe.add(c);
				shoe.add(d);
				shoe.add(h);
				shoe.add(s);
			}
		}

		reshuffle();
	}

	// constructor
	public AbstractShoe(int decks, int... c) {
		this.decks = decks;
		this.reshuffle = (52 * getDecks()) - 16;
		this.index = 0;
		this.shoe = new ArrayList<Card>();

		for (int i = 0; i < c.length; i++) {
			Card card = new Card(c[i], 'c');
			shoe.add(card);
		}

		// replace the rest of the deck with Ts
		for (int i = 0; i < (52 * decks) - c.length; i++) {
			Card card = new Card(10, 'c');
			shoe.add(card);
		}
	}

	// burnCard
	private void burnCard() {
		index++;
	}

	// getNextCard
	public Card getNextCard() {
		Card card = shoe.get(index);
		index++;
		return card;
	}

	// getShoe
	public List<Card> getShoe() {
		return shoe;
	}

	// reshuffle
	public void reshuffle() {
		if (isReshuffle()) {
			Collections.shuffle(shoe, random);
			index = 0;

			// get burn card
			Card burnCard = shoe.get(0);
			burnCard();

			// if face card, burn 10, otherwise burn value.
			int burn = burnCard.getRank();
			if (burn == 0) {
				burn = 10;
			}

			for (int i = 0; i < burn; i++) {
				burnCard();
			}

			resetCount();
		}
	}

	public boolean isReshuffle() {
		return index > reshuffle;
	}

	public int getDecks() {
		return decks;
	}

	public int getIndex() {
		return index;
	}
}