package org.kutsuki.bond.shoe;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WizardShoe extends AbstractShoe {
	private int count;
	private BigDecimal numCards;

	public WizardShoe(int decks) {
		super(decks);
		this.count = 0;
		this.numCards = new BigDecimal(decks * 52);
	}

	public WizardShoe(int decks, int... c) {
		super(decks, c);
		this.count = 0;
		this.numCards = new BigDecimal(decks * 52);
	}

	@Override
	public Card getNextCard() {
		Card card = super.getNextCard();
		count(card);
		return card;
	}

	public void count(Card card) {
		switch (card.getRank()) {
		case 0:
			count -= 178;
			break;
		case 1:
			count -= 448;
			break;
		case 2:
			count -= 543;
			break;
		case 3:
			count -= 672;
			break;
		case 4:
			count -= 1195;
			break;
		case 5:
			count += 841;
			break;
		case 6:
			count += 1128;
			break;
		case 7:
			count += 817;
			break;
		case 8:
			count += 533;
			break;
		case 9:
			count += 249;
			break;
		default:
			break;
		}
	}

	@Override
	public int getCount() {
		BigDecimal c = new BigDecimal(count);
		BigDecimal index = new BigDecimal(getIndex());
		c = c.divide(numCards.divide(index, 0, RoundingMode.HALF_UP), 0, RoundingMode.HALF_UP);

		int result = 0;

		if (c.intValue() >= 123508) {
			result = 1;
		}

		return result;
	}

	@Override
	public void resetCount() {
		this.count = 0;
	}
}
