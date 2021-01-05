package org.kutsuki.bond;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomAdaptor;
import org.kutsuki.bond.shoe.AbstractShoe;
import org.kutsuki.bond.shoe.BasicShoe;
import org.kutsuki.bond.shoe.BetType;
import org.kutsuki.bond.shoe.Count;
import org.kutsuki.bond.shoe.Hand;

public class Bond {
	private static final BigDecimal COMMISSION = new BigDecimal("0.05");
	private static final BigDecimal MULTIPLE = new BigDecimal("2");

	public static BigDecimal BET = BigDecimal.ONE;
	public static BigDecimal START = BigDecimal.ZERO;
	public static final long TRIALS = 10000000;

	private BetType betType;
	private BigDecimal bankroll, bet, commission, bankerBankroll, playerBankroll;
	private boolean tie;
	private Map<Integer, Count> countMap;
	private Random random;

	// default constructor
	public Bond() {
		this.bankerBankroll = START;
		this.bankroll = START;
		this.betType = BetType.NONE;
		this.bet = BigDecimal.ZERO;
		this.countMap = new HashMap<Integer, Count>();
		this.commission = BigDecimal.ZERO;
		this.playerBankroll = START;
		this.random = RandomAdaptor.createAdaptor(new MersenneTwister());
		this.tie = false;
	}

	// run
	public void run() {
		Hand playerHand = new Hand();
		Hand bankerHand = new Hand();
		AbstractShoe shoe = new BasicShoe();

		for (int i = 0; i < TRIALS; i++) {
			if (i % (TRIALS * 0.1) == 0 && i != 0) {
				System.out.println(i + " completed!");
			}

			int startingCount = shoe.getCount();
			BigDecimal startingBankroll = getBankroll();

			setBet(BetType.BANKER);
			distributeCards(playerHand, bankerHand, shoe);
			playerAction(playerHand, bankerHand, shoe);
			bankerAction(playerHand, bankerHand, shoe);
			payout(playerHand, bankerHand);
			addCount(startingCount, bet, startingBankroll, getBankroll());
		}

		BigDecimal grandWin = BigDecimal.ZERO;
		BigDecimal grandLoss = BigDecimal.ZERO;
		BigDecimal grandBet = BigDecimal.ZERO;
		List<Integer> countList = new ArrayList<Integer>(countMap.keySet());
		Collections.sort(countList);
		NumberFormat CURRENCY = NumberFormat.getCurrencyInstance();
		NumberFormat FRACTION = NumberFormat.getNumberInstance();
		System.out.println("Count, Total Won, Total Bet, Total Games Played, Expected Value");
		for (int count : countList) {
			Count countResult = countMap.get(count);
			BigDecimal total = countResult.getWin().subtract(countResult.getLose());
			System.out.println(count + ", " + CURRENCY.format(total) + ", " + CURRENCY.format(countResult.getBet())
					+ ", " + countResult.getGamesPlayed() + ", "
					+ FRACTION.format(total.divide(countResult.getBet(), 4, RoundingMode.HALF_UP)));

			grandWin = grandWin.add(countResult.getWin());
			grandLoss = grandLoss.add(countResult.getLose());
			grandBet = grandBet.add(countResult.getBet());

		}

		System.out.println("Player Bankroll: " + playerBankroll);
		System.out.println("Banker Bankroll: " + bankerBankroll);
		System.out.println("Bankroll: " + bankroll);
		System.out.println("Commission: " + commission);

	}

	// getBankroll
	public BigDecimal getBankroll() {
		return bankroll;
	}

	// resetBankroll
	public void resetBankroll() {
		bankroll = BigDecimal.ZERO;
	}

	// setBet
	public void setBet(BetType betType) {
		this.betType = betType;

		if (this.betType != BetType.NONE) {
			bet = BET;
			bankroll = bankroll.subtract(bet);
			bankerBankroll = bankerBankroll.subtract(bet);
			playerBankroll = playerBankroll.subtract(bet);

		}
	}

	// distributeCards
	public void distributeCards(Hand playerHand, Hand bankerHand, AbstractShoe shoe) {
		// clear table
		playerHand.clear();
		bankerHand.clear();

		// check for reshuffle
		shoe.reshuffle();

		playerHand.addCard(shoe.getNextCard());
		bankerHand.addCard(shoe.getNextCard());
		playerHand.addCard(shoe.getNextCard());
		bankerHand.addCard(shoe.getNextCard());
	}

	// playerAction
	public void playerAction(Hand playerHand, Hand bankerHand, AbstractShoe shoe) {
		if (!playerHand.isOverride() && !bankerHand.isOverride() && playerHand.getValue() <= 5) {
			playerHand.addCard(shoe.getNextCard());
		}
	}

	// bankerAction
	public void bankerAction(Hand playerHand, Hand bankerHand, AbstractShoe shoe) {
		if (!playerHand.isOverride() && !bankerHand.isOverride()) {
			if (!playerHand.hasThirdCard() && bankerHand.getValue() <= 5) {
				bankerHand.addCard(shoe.getNextCard());
			} else if (playerHand.hasThirdCard()) {
				if (bankerHand.getValue() == 6
						&& (playerHand.getThirdCardRank() == 6 || playerHand.getThirdCardRank() == 7)) {
					bankerHand.addCard(shoe.getNextCard());
				} else if (bankerHand.getValue() == 5 && playerHand.getThirdCardRank() >= 4
						&& playerHand.getThirdCardRank() <= 7) {
					bankerHand.addCard(shoe.getNextCard());
				} else if (bankerHand.getValue() == 4 && playerHand.getThirdCardRank() >= 2
						&& playerHand.getThirdCardRank() <= 7) {
					bankerHand.addCard(shoe.getNextCard());
				} else if (bankerHand.getValue() == 3 && playerHand.getThirdCardRank() != 8) {
					bankerHand.addCard(shoe.getNextCard());
				} else if (bankerHand.getValue() <= 2) {
					bankerHand.addCard(shoe.getNextCard());
				}
			}
		}
	}

	// payout
	public BetType payout(Hand playerHand, Hand bankerHand) {
		BetType result = null;

		if (playerHand.getValue() > bankerHand.getValue()) {
			// player wins, 1-1
			if (betType == BetType.PLAYER) {
				bankroll = bankroll.add(bet);
				bankroll = bankroll.add(bet);
			}

			playerBankroll = playerBankroll.add(bet);
			playerBankroll = playerBankroll.add(bet);
		} else if (playerHand.getValue() < bankerHand.getValue()) {
			// banker wins, 1-1
			if (betType == BetType.BANKER) {
				bankroll = bankroll.add(bet);

				// 5% commission
				bankroll = bankroll.add(bet);
				bankroll = bankroll.subtract(bet.multiply(COMMISSION));
				commission = commission.add(bet.multiply(COMMISSION));
			}

			bankerBankroll = bankerBankroll.add(bet);
			bankerBankroll = bankerBankroll.add(bet);
			bankerBankroll = bankerBankroll.subtract(bet.multiply(COMMISSION));
		} else {
			if (tie) {
				bankroll = bankroll.add(bet.multiply(BigDecimal.valueOf(8)));
				bankroll = bankroll.add(bet);
			}

			if (betType == BetType.PLAYER || betType == BetType.BANKER) {
				bankroll = bankroll.add(bet);
				bankerBankroll = bankerBankroll.add(bet);
				playerBankroll = playerBankroll.add(bet);
			}
		}

		return result;
	}

	public void setTie(boolean tie) {
		this.tie = tie;

		if (tie) {
			this.bankroll = bankroll.subtract(bet);
		}
	}

	// addCount

	public void addCount(int count, BigDecimal bet, BigDecimal start, BigDecimal end) {
		if (!countMap.containsKey(count)) {
			countMap.put(count, new Count(count));
		}

		Count countResult = countMap.get(count);

		if (end.compareTo(start) == 1) {
			countResult.addWin(start, end);
		} else if (end.compareTo(start) == -1) {
			countResult.addLoss(start, end);
		}

		countResult.addBet(bet);
		countResult.addGamesPlayed(1);
	}

	// main
	public static void main(String[] args) {
		Bond b = new Bond();
		b.run();
	}
}
