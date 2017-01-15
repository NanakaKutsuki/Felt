package org.kutsuki.felt;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;

public class Felt {
	private static final BigDecimal SIX = new BigDecimal(6);
	private static final BigDecimal FIVE = new BigDecimal(5);
	private static final BigDecimal THREE = new BigDecimal(3);
	private static final BigDecimal TWO = new BigDecimal(2);
	private static final int BET = 1;
	private static final int ODDS = 100;
	private static final int TRIALS = 1000000;
	private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance();

	private BigDecimal bet;
	private BigDecimal comeBankroll;
	private BigDecimal dontComeBankroll;
	private BigDecimal dontPassBankroll;
	private BigDecimal odds;
	private BigDecimal passBankroll;
	private Dices dices;
	private Set<Integer> comeSet;

	public Felt(Dices dices) {
		this.dices = dices;

		this.comeSet = new HashSet<Integer>();
		this.comeBankroll = BigDecimal.ZERO;
		this.dontComeBankroll = BigDecimal.ZERO;
		this.dontPassBankroll = BigDecimal.ZERO;
		this.passBankroll = BigDecimal.ZERO;

		setBet(BET);
		setOdds(ODDS);
	}

	public void run() {
		int i = 0;
		while (i < TRIALS) {
			if (felt()) {
				i++;
			}
		}

		System.out.println(getPassBankroll());
		System.out.println(getComeBankroll());
		System.out.println(getDontPassBankroll());
		System.out.println(getDontComeBankroll());
	}

	public boolean felt() {
		boolean done = false;

		// make bet
		passBankroll = passBankroll.subtract(bet);
		dontPassBankroll = dontPassBankroll.subtract(bet);

		int roll = dices.roll();
		if (roll == 7) {
			// resolve come
			for (int come : comeSet) {
				// come bet loses, come odds are returned
				comeBankroll = comeBankroll.add(bet.multiply(odds));

				// don't come wins
				dontComeBankroll = dontComeBankroll.add(bet);
				dontComeBankroll = dontComeBankroll.add(bet);
				dontComeBankroll = dontComeBankroll.add(bet.multiply(odds));

				if (come == 6 || come == 8) {
					dontComeBankroll = dontComeBankroll.add(fiveToSix(bet));
				} else if (come == 5 || come == 9) {
					dontComeBankroll = dontComeBankroll.add(twoToThree(bet));
				} else if (come == 4 || come == 10) {
					dontComeBankroll = dontComeBankroll.add(oneToTwo(bet));
				}
			}
			comeSet.clear();

			// pass wins, don't pass loses
			passBankroll = passBankroll.add(bet);
			passBankroll = passBankroll.add(bet);
		} else if (roll == 11) {
			// pass wins, don't pass loses
			passBankroll = passBankroll.add(bet);
			passBankroll = passBankroll.add(bet);
		} else if (roll == 2 || roll == 3) {
			// pass loses, don't pass wins
			dontPassBankroll = dontPassBankroll.add(bet);
			dontPassBankroll = dontPassBankroll.add(bet);
		} else if (roll == 12) {
			// pass loses, don't pass pushes
			dontPassBankroll = dontPassBankroll.add(bet);
		} else {
			// set point
			int point = roll;

			// take down come bet, don't come loses
			if (comeSet.contains(point)) {
				comeBankroll = comeBankroll.add(bet);
				comeBankroll = comeBankroll.add(bet.multiply(odds));
				comeSet.remove(point);
			}

			// lay max odds
			passBankroll = passBankroll.subtract(bet.multiply(odds));
			dontPassBankroll = dontPassBankroll.subtract(bet.multiply(odds));
			makeComeBets();

			roll = dices.roll();
			while (roll != point && roll != 7) {
				if (roll == 6 || roll == 8) {
					if (comeSet.contains(roll)) {
						comeBankroll = comeBankroll.add(bet);
						comeBankroll = comeBankroll.add(bet.multiply(odds));
						comeBankroll = comeBankroll.add(bet);
						comeBankroll = comeBankroll.add(sixToFive(bet));
					} else {
						comeSet.add(roll);
					}

					layMaxComeOdds();
					makeComeBets();
				} else if (roll == 5 || roll == 9) {
					if (comeSet.contains(roll)) {
						comeBankroll = comeBankroll.add(bet);
						comeBankroll = comeBankroll.add(bet.multiply(odds));
						comeBankroll = comeBankroll.add(bet);
						comeBankroll = comeBankroll.add(threeToTwo(bet));
					} else {
						comeSet.add(roll);
					}

					layMaxComeOdds();
					makeComeBets();
				} else if (roll == 4 || roll == 10) {
					if (comeSet.contains(roll)) {
						comeBankroll = comeBankroll.add(bet);
						comeBankroll = comeBankroll.add(bet.multiply(odds));
						comeBankroll = comeBankroll.add(bet);
						comeBankroll = comeBankroll.add(twoToOne(bet));
					} else {
						comeSet.add(roll);
					}

					layMaxComeOdds();
					makeComeBets();
				} else if (roll == 11) {
					comeBankroll = comeBankroll.add(bet);
					dontComeBankroll = dontComeBankroll.subtract(bet);
				} else if (roll == 2 || roll == 3) {
					dontComeBankroll = dontComeBankroll.add(bet);
					comeBankroll = comeBankroll.subtract(bet);
				} else if (roll == 12) {
					comeBankroll = comeBankroll.subtract(bet);
				}

				roll = dices.roll();
			}

			if (roll == point) {
				comeSet.add(roll);
				layMaxComeOdds();

				passBankroll = passBankroll.add(bet);
				passBankroll = passBankroll.add(bet);

				if (point == 6 || point == 8) {
					passBankroll = passBankroll.add(bet.multiply(odds));
					passBankroll = passBankroll.add(sixToFive(bet));
				} else if (point == 5 || point == 9) {
					passBankroll = passBankroll.add(bet.multiply(odds));
					passBankroll = passBankroll.add(threeToTwo(bet));
				} else if (point == 4 || point == 10) {
					passBankroll = passBankroll.add(bet.multiply(odds));
					passBankroll = passBankroll.add(twoToOne(bet));
				}
			} else if (roll == 7) {
				// resolve come
				comeBankroll = comeBankroll.add(bet);
				comeBankroll = comeBankroll.add(bet);

				for (int come : comeSet) {
					dontComeBankroll = dontComeBankroll.add(bet);
					dontComeBankroll = dontComeBankroll.add(bet);
					dontComeBankroll = dontComeBankroll.add(bet.multiply(odds));

					if (come == 6 || come == 8) {
						dontComeBankroll = dontComeBankroll.add(fiveToSix(bet));
					} else if (come == 5 || come == 9) {
						dontComeBankroll = dontComeBankroll.add(twoToThree(bet));
					} else if (come == 4 || come == 10) {
						dontComeBankroll = dontComeBankroll.add(oneToTwo(bet));
					}
				}
				comeSet.clear();

				dontPassBankroll = dontPassBankroll.add(bet);
				dontPassBankroll = dontPassBankroll.add(bet);

				if (point == 6 || point == 8) {
					dontPassBankroll = dontPassBankroll.add(bet.multiply(odds));
					dontPassBankroll = dontPassBankroll.add(fiveToSix(bet));
				} else if (point == 5 || point == 9) {
					dontPassBankroll = dontPassBankroll.add(bet.multiply(odds));
					dontPassBankroll = dontPassBankroll.add(twoToThree(bet));
				} else if (point == 4 || point == 10) {
					dontPassBankroll = dontPassBankroll.add(bet.multiply(odds));
					dontPassBankroll = dontPassBankroll.add(oneToTwo(bet));
				}

				done = true;
			} else {
				throw new IllegalStateException(roll + " ended unexpectedly!");
			}
		}

		return done;
	}

	public BigDecimal sixToFive(BigDecimal bd) {
		return bd.multiply(odds).multiply(SIX).divide(FIVE, 2, RoundingMode.HALF_UP);
	}

	public BigDecimal threeToTwo(BigDecimal bd) {
		return bd.multiply(odds).multiply(THREE).divide(TWO, 2, RoundingMode.HALF_UP);
	}

	public BigDecimal twoToOne(BigDecimal bd) {
		return bd.multiply(odds).multiply(TWO);
	}

	public BigDecimal fiveToSix(BigDecimal bd) {
		return bd.multiply(odds).multiply(FIVE).divide(SIX, 2, RoundingMode.HALF_UP);
	}

	public BigDecimal twoToThree(BigDecimal bd) {
		return bd.multiply(odds).multiply(TWO).divide(THREE, 2, RoundingMode.HALF_UP);
	}

	public BigDecimal oneToTwo(BigDecimal bd) {
		return bd.multiply(odds).divide(TWO, 2, RoundingMode.HALF_UP);
	}

	public String getComeBankroll() {
		return CURRENCY.format(comeBankroll);
	}

	public String getDontComeBankroll() {
		return CURRENCY.format(dontComeBankroll);
	}

	public String getPassBankroll() {
		return CURRENCY.format(passBankroll);
	}

	public String getDontPassBankroll() {
		return CURRENCY.format(dontPassBankroll);
	}

	public void setBet(int bet) {
		this.bet = new BigDecimal(bet);
	}

	public void setOdds(int odds) {
		this.odds = new BigDecimal(odds);
	}

	private void makeComeBets() {
		comeBankroll = comeBankroll.subtract(bet);
		dontComeBankroll = dontComeBankroll.subtract(bet);
	}

	private void layMaxComeOdds() {
		comeBankroll = comeBankroll.subtract(bet.multiply(odds));
		dontComeBankroll = dontComeBankroll.subtract(bet.multiply(odds));
	}

	public static void main(String[] args) {
		Felt felt = new Felt(new Dices());
		felt.run();
	}
}
