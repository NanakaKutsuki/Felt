package org.kutsuki.bond.shoe;

public class RedSixShoe extends AbstractShoe {
	private int count;

	public RedSixShoe(int decks) {
		super(decks);
		this.count = getDecks() * -2;
	}

	public RedSixShoe(int decks, int... c) {
		super(decks, c);
		this.count = getDecks() * -2;
	}

	@Override
	public Card getNextCard() {
		Card card = super.getNextCard();
		count(card);
		return card;
	}

	public void count(Card card) {
		if (card.getRank() == 0) {
			count--;
		} else if (card.getRank() == 5 && (card.getSuit() == 'd' || card.getSuit() == 'h')) {
			count++;
		} else if (card.getRank() >= 6) {
			count++;
		}
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public void resetCount() {
		this.count = getDecks() * -2;
	}
}
