package org.kutsuki.bond.shoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.kutsuki.bond.Bond;

public class BondTest {
	private Bond bond = new Bond();

	@Test
	public void testPlayerAction() {
		Hand bankerHand = new Hand();
		Hand playerHand = new Hand();
		BasicShoe shoe = null;

		// stand due to player 8
		shoe = new BasicShoe(6, 3, 2, 2);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 8, 5, null, null);

		shoe = new BasicShoe(8, 3);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 8, 3, null, null);

		// stand due to banker 9
		shoe = new BasicShoe(3, 7, 2, 2);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 5, 9, null, null);

		shoe = new BasicShoe(3, 9);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 3, 9, null, null);

		// stand >5
		shoe = new BasicShoe(5, 3, 2, 2);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 7, 5, null, null);

		shoe = new BasicShoe(7, 3);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 7, 3, null, null);

		// hit <=5
		shoe = new BasicShoe(2, 3, 2, 2, 5);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 9, 5, 5, null);

		shoe = new BasicShoe(2, 3);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 2, 3, 0, null);
	}

	@Test
	public void testBankerAction() {
		Hand bankerHand = new Hand();
		Hand playerHand = new Hand();
		BasicShoe shoe = null;

		// stand due to player 9
		shoe = new BasicShoe(6, 3, 3, 2);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		bond.bankerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 9, 5, null, null);

		shoe = new BasicShoe(9, 3);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		bond.bankerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 9, 3, null, null);

		// stand due to banker 8
		shoe = new BasicShoe(3, 6, 2, 2);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		bond.bankerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 5, 8, null, null);

		shoe = new BasicShoe(3, 8);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		bond.bankerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 3, 8, null, null);

		// no player third card stand
		shoe = new BasicShoe(5, 3, 2, 3);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		bond.bankerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 7, 6, null, null);

		shoe = new BasicShoe(7, 6);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		bond.bankerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 7, 6, null, null);

		// no player third card hit
		shoe = new BasicShoe(2, 3, 5, 2, 4);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		bond.bankerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 7, 9, null, 4);

		shoe = new BasicShoe(7, 3, 10, 2);
		bond.distributeCards(playerHand, bankerHand, shoe);
		bond.playerAction(playerHand, bankerHand, shoe);
		bond.bankerAction(playerHand, bankerHand, shoe);
		checkHands(playerHand, bankerHand, shoe, 7, 5, null, 0);

		// banker 7, 2-14 stand
		checkBankerAction(7, 2, 14, false);

		// banker 6, 2-5 stand
		checkBankerAction(6, 2, 5, false);

		// banker 6, 6-7 hit
		checkBankerAction(6, 6, 7, true);

		// banker 6, 8-A stand
		checkBankerAction(6, 8, 14, false);

		// banker 5, 2-3 stand
		checkBankerAction(5, 2, 3, false);

		// banker 5, 4-7 hit
		checkBankerAction(5, 4, 7, true);

		// banker 5, 8-A stand
		checkBankerAction(5, 8, 14, false);

		// banker 4, 2-7 hit
		checkBankerAction(4, 2, 7, true);

		// banker 4, 8-A stand
		checkBankerAction(4, 8, 14, false);

		// banker 3, 2-7 hit
		checkBankerAction(3, 2, 7, true);

		// banker 3, 9-14 stand
		checkBankerAction(3, 8, 8, false);

		// banker 3, 9-14 hit
		checkBankerAction(3, 9, 14, true);

		// banker 2, 2-14 hit
		checkBankerAction(2, 2, 14, true);

		// banker 1, 2-14 hit
		checkBankerAction(14, 2, 14, true);

		// banker 0, 2-14 hit
		checkBankerAction(10, 2, 14, true);
	}

	@Test
	public void testPayout() {
		// betting player
		checkPayout(BigDecimal.valueOf(100), BetType.PLAYER, false);

		// betting banker
		checkPayout(BigDecimal.valueOf(100), BetType.BANKER, false);

		// betting tie
		checkPayout(BigDecimal.valueOf(100), BetType.NONE, true);
	}

	// checkBankerAction
	private void checkBankerAction(int bankerValue, int playerThirdCardStart, int playerThirdCardEnd, boolean hit) {
		Hand bankerHand = new Hand();
		Hand playerHand = new Hand();
		BasicShoe shoe = null;
		int expectedPlayerValue = 0;
		int expectedBankerValue = 0;
		int playerThirdCard = 0;

		for (int i = playerThirdCardStart; i <= playerThirdCardEnd; i++) {
			shoe = new BasicShoe(10, bankerValue, 10, 10, i);
			bond.distributeCards(playerHand, bankerHand, shoe);
			bond.playerAction(playerHand, bankerHand, shoe);
			bond.bankerAction(playerHand, bankerHand, shoe);

			expectedPlayerValue = getValue(10, 10, i);
			expectedBankerValue = getValue(bankerValue, 10);
			playerThirdCard = getValue(i);

			if (hit) {
				checkHands(playerHand, bankerHand, shoe, expectedPlayerValue, expectedBankerValue, playerThirdCard, 0);
			} else {
				checkHands(playerHand, bankerHand, shoe, expectedPlayerValue, expectedBankerValue, playerThirdCard,
						null);
			}
		}
	}

	// checkHands
	private void checkHands(Hand playerHand, Hand bankerHand, BasicShoe shoe, int playerValue, int bankerValue,
			Integer playerThirdCard, Integer bankerThirdCard) {
		assertEquals("Wrong Player Value: " + playerHand, playerValue, playerHand.getValue());
		assertEquals("Wrong Banker Value: " + bankerHand, bankerValue, bankerHand.getValue());

		if (playerThirdCard == null) {
			assertFalse("No Player Third Card: " + playerHand, playerHand.hasThirdCard());

			if (playerHand.getValue() >= 8) {
				assertTrue("Player should be unplayable: " + playerHand, playerHand.isOverride());
			} else {
				assertFalse("Player should be playable: " + playerHand, playerHand.isOverride());
			}
		} else {
			assertTrue("Player is missing Third Card: " + playerHand, playerHand.hasThirdCard());
			assertEquals("Player Third Card Rank: " + playerHand, playerThirdCard.intValue(),
					playerHand.getThirdCardRank());
			assertFalse("Player should not be playable: " + playerHand, playerHand.isOverride());
		}

		if (bankerThirdCard == null) {
			assertFalse("No Banker Third Card: " + bankerHand, bankerHand.hasThirdCard());

			if (bankerHand.getValue() >= 8) {
				assertTrue("Banker should be unplayable: " + bankerHand, bankerHand.isOverride());
			} else {
				assertFalse("Banker should be playable: " + bankerHand, bankerHand.isOverride());
			}
		} else {
			assertTrue("Banker is missing Third Card: " + bankerHand, bankerHand.hasThirdCard());
			assertEquals("Banker Third Card Rank: " + bankerHand, bankerThirdCard.intValue(),
					bankerHand.getThirdCardRank());
			assertFalse("Banker should be not playable: " + bankerHand, bankerHand.isOverride());
		}
	}

	// checkPayout
	private void checkPayout(BigDecimal bet, BetType betType, boolean tie) {
		Hand bankerHand = new Hand();
		Hand playerHand = new Hand();
		int expectedPlayerValue = 0;
		int expectedBankerValue = 0;
		Integer playerThirdCard = null;
		Integer bankerThirdCard = null;
		BasicShoe shoe = null;

		for (int i = 2; i <= 14; i++) {
			for (int j = 2; j <= 14; j++) {
				for (int k = 2; k <= 14; k++) {
					for (int l = 2; l <= 14; l++) {
						shoe = new BasicShoe(i, j, k, l);
						Bond.START = BigDecimal.ZERO;
						Bond.BET = bet;
						bond.resetBankroll();
						bond.setBet(betType);
						bond.setTie(tie);
						bond.distributeCards(playerHand, bankerHand, shoe);
						bond.playerAction(playerHand, bankerHand, shoe);
						bond.bankerAction(playerHand, bankerHand, shoe);
						bond.payout(playerHand, bankerHand);

						expectedPlayerValue = getValue(i, k);
						expectedBankerValue = getValue(j, l);

						playerThirdCard = playerHand.hasThirdCard() ? 0 : null;
						bankerThirdCard = bankerHand.hasThirdCard() ? 0 : null;

						checkHands(playerHand, bankerHand, shoe, expectedPlayerValue, expectedBankerValue,
								playerThirdCard, bankerThirdCard);

						BigDecimal payout = bet.negate();
						if (expectedPlayerValue > expectedBankerValue) {
							if (betType == BetType.PLAYER) {
								payout = bet;
							}
						} else if (expectedPlayerValue < expectedBankerValue) {
							if (betType == BetType.BANKER) {
								payout = bet.multiply(BigDecimal.valueOf(0.95));
							}
						} else {
							if (betType == BetType.PLAYER || betType == BetType.BANKER) {
								payout = BigDecimal.ZERO;
							}

							if (tie) {
								payout = bet.multiply(BigDecimal.valueOf(8));
							}
						}

						assertEquals("Incorrect payout: " + playerHand + " vs " + bankerHand, payout,
								bond.getBankroll());
					}
				}
			}
		}
	}

	// getValue
	private int getValue(int... values) {
		int value = 0;

		for (int i : values) {
			if (i == 14) {
				value++;
			} else if (i < 10) {
				value += i;
			}

			if (value > 9) {
				value -= 10;
			}
		}

		return value;
	}
}
