package org.kutsuki.felt;

import java.math.BigDecimal;

public class Felt {
	private static final BigDecimal BET = BigDecimal.ONE;
	private static final BigDecimal ODDS = new BigDecimal(100);

	private BigDecimal dontPassBankroll;
	private BigDecimal passBankroll;
	private Dices dices;
	private int roll;

	public Felt(Dices dices) {
		this.dices = dices;
		init();
	}

	private void init() {
		this.dontPassBankroll = BigDecimal.ZERO;
		this.roll = 0;
		this.passBankroll = BigDecimal.ZERO;
	}

	public void run() {
		// make bet
		passBankroll = passBankroll.subtract(BET);
		dontPassBankroll = dontPassBankroll.subtract(BET);

		// come out roll
		roll = dices.roll();
		System.out.println(roll);

		if (roll == 7) {
			passBankroll = passBankroll.add(BET);
			passBankroll = passBankroll.add(BET);
		} else if (roll == 11) {
			passBankroll = passBankroll.add(BET);
			passBankroll = passBankroll.add(BET);
		} else if (roll == 2 || roll == 3) {
			dontPassBankroll = dontPassBankroll.add(BET);
			dontPassBankroll = dontPassBankroll.add(BET);
		} else if (roll == 12) {
			dontPassBankroll = dontPassBankroll.add(BET);
		} else {
			int point = roll;
			roll = dices.roll();

			while (roll != point && roll != 7) {
				if (roll == 6 || roll == 8) {

				} else if (roll == 5 || roll == 9) {

				} else if (roll == 4 || roll == 10) {

				} else if (roll == 11) {

				} else if (roll == 2 || roll == 3 || roll == 12) {

				}

				roll = dices.roll();
			}

			if (roll == point) {
				passBankroll = passBankroll.add(BET);
				passBankroll = passBankroll.add(BET);
			} else if (roll == 7) {
				dontPassBankroll = dontPassBankroll.add(BET);
				dontPassBankroll = dontPassBankroll.add(BET);
			} else {
				throw new IllegalStateException(roll + " ended unexpectedly!");
			}
		}

		System.out.println(roll);
		System.out.println(passBankroll);
		System.out.println(dontPassBankroll);
	}

	public static void main(String[] args) {
		Felt felt = new Felt(new Dices());
		felt.run();
	}
}
