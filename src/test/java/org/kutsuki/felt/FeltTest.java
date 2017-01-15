package org.kutsuki.felt;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.NumberFormat;

import org.junit.Test;

public class FeltTest {
	private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance();
	private static final int BET = 1;
	private static final int ODDS = 100;

	@Test
	public void testComeoutRolls() {
		Dices dices = null;

		// 2 pass loses, dont wins
		dices = new Dices(2);
		testFelt(dices, "-1", "1");

		// 3 pass loses, dont wins
		dices = new Dices(3);
		testFelt(dices, "-1", "1");

		// 7 pass wins, dont loses
		dices = new Dices(7);
		testFelt(dices, "1", "-1");

		// 11 pass wins, dont loses
		dices = new Dices(11);
		testFelt(dices, "1", "-1");

		// 12 pass loses, dont push
		dices = new Dices(12);
		testFelt(dices, "-1", "0");
	}

	@Test
	public void testPointRolls() {
		Dices dices = null;

		// 4 pass wins, dont loses
		dices = new Dices(4, 4);
		testFelt(dices, "201", "-101");

		// 5 pass wins, dont loses
		dices = new Dices(5, 5);
		testFelt(dices, "151", "-101");

		// 6 pass wins, dont loses
		dices = new Dices(6, 6);
		testFelt(dices, "121", "-101");

		// 8 pass wins, dont loses
		dices = new Dices(8, 8);
		testFelt(dices, "121", "-101");

		// 9 pass wins, dont loses
		dices = new Dices(9, 9);
		testFelt(dices, "151", "-101");

		// 10 pass wins, dont loses
		dices = new Dices(10, 10);
		testFelt(dices, "201", "-101");

		// 4 pass loses, dont wins
		dices = new Dices(4, 7);
		testFelt(dices, "-101", "51");

		// 5 pass loses, dont wins
		dices = new Dices(5, 7);
		testFelt(dices, "-101", "67.67");

		// 6 pass loses, dont wins
		dices = new Dices(6, 7);
		testFelt(dices, "-101", "84.33");

		// 8 pass loses, dont wins
		dices = new Dices(8, 7);
		testFelt(dices, "-101", "84.33");

		// 9pass loses, dont wins
		dices = new Dices(9, 7);
		testFelt(dices, "-101", "67.67");

		// 10 pass loses, dont wins
		dices = new Dices(10, 7);
		testFelt(dices, "-101", "51");
	}

	@Test
	public void testComeRolls() {
		Dices dices = null;

		dices = new Dices(6, 4, 4, 6);
		testFelt(dices, false, "121", "-101", "-1", "-303");

		dices = new Dices(6, 5, 5, 6);
		testFelt(dices, false, "121", "-101", "-51", "-303");

		dices = new Dices(6, 8, 8, 6);
		testFelt(dices, false, "121", "-101", "-81", "-303");

		dices = new Dices(6, 4, 7);
		testFelt(dices, true, "-101", "84.33", "-100", "50");

		dices = new Dices(6, 5, 7);
		testFelt(dices, true, "-101", "84.33", "-100", "66.67");

		dices = new Dices(6, 8, 7);
		testFelt(dices, true, "-101", "84.33", "-100", "83.33");

		dices = new Dices(6, 4, 4, 6, 8, 7);
		testFelt(dices, true, "20", "-16.67", "0", "33.33");

		dices = new Dices(6, 5, 5, 6, 8, 7);
		testFelt(dices, true, "20", "-16.67", "-50", "50");

		dices = new Dices(6, 8, 8, 6, 8, 7);
		testFelt(dices, true, "20", "-16.67", "21", "-118.67");
	}

	private void testFelt(Dices dices, String expectedPass, String expectedDontPass) {
		testFelt(dices, false, expectedPass, expectedDontPass, null, null);
	}

	private void testFelt(Dices dices, boolean run, String expectedPass, String expectedDontPass, String expectedCome,
			String expectedDontCome) {
		String expected = null;

		Felt felt = new Felt(dices);
		felt.setBet(BET);
		felt.setOdds(ODDS);

		boolean done = felt.felt();
		while (run && !done) {
			done = felt.felt();
		}

		expected = CURRENCY.format(new BigDecimal(expectedPass));
		assertEquals("Wrong pass bankroll", expected, felt.getPassBankroll());

		expected = CURRENCY.format(new BigDecimal(expectedDontPass));
		assertEquals("Wrong don't pass bankroll", expected, felt.getDontPassBankroll());

		if (expectedCome != null) {
			expected = CURRENCY.format(new BigDecimal(expectedCome));
			assertEquals("Wrong come bankroll", expected, felt.getComeBankroll());
		}

		if (expectedDontCome != null) {
			expected = CURRENCY.format(new BigDecimal(expectedDontCome));
			assertEquals("Wrong don't come bankroll", expected, felt.getDontComeBankroll());
		}
	}
}
