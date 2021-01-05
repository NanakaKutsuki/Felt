package org.kutsuki.bond.shoe;

public class TamburinShoe extends AbstractShoe {
	private int count;

	public TamburinShoe(int decks) {
		super(decks);
		this.count = 14;
	}

	public TamburinShoe(int decks, int... c) {
		super(decks, c);
		this.count = 14;
	}

	@Override
	public Card getNextCard() {
		Card card = super.getNextCard();
		count(card);
		return card;
	}

	public void count(Card card) {
		if (card.getRank() == 0 || card.getRank() == 8 || card.getRank() == 9) {
			count--;
		} else if (card.getRank() == 2 || card.getRank() == 5) {
			count++;
		} else if (card.getRank() == 3 || card.getRank() == 4) {
			count = count + 2;
		}
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public void resetCount() {
		this.count = 14;
	}
}
