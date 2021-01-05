package org.kutsuki.bond.shoe;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CountShoe extends AbstractShoe {
	private BigDecimal count;
	private BigDecimal numCards;

	public CountShoe(int decks) {
		super(decks);
		this.count = BigDecimal.ZERO;
		this.numCards = new BigDecimal(decks * 52);
	}

	public CountShoe(int decks, int... c) {
		super(decks, c);
		this.count = BigDecimal.ZERO;
		this.numCards = new BigDecimal(decks * 52);
	}

	@Override
	public Card getNextCard() {
		Card card = super.getNextCard();
		count(card);
		return card;
	}

	public void count(Card card) {
		switch (card.getActualRank()) {
		case 2:
		case 3:
		case 14:
			count = count.add(BigDecimal.ONE);
			break;
		case 4:
			count = count.add(BigDecimal.ONE).add(BigDecimal.ONE);
			break;
		case 5:
		case 7:
		case 8:
			count = count.subtract(BigDecimal.ONE);
			break;
		case 6:
			count = count.subtract(BigDecimal.ONE).subtract(BigDecimal.ONE);
			break;
		default:
			break;
		}
	}

	@Override
	public int getCount() {
		return count
				.divide(numCards.divide(new BigDecimal(getIndex()), 0, RoundingMode.HALF_UP), 0, RoundingMode.HALF_UP)
				.intValue();
	}

	@Override
	public void resetCount() {
		this.count = BigDecimal.ZERO;
	}
}
