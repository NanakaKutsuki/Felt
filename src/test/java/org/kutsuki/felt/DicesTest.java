package org.kutsuki.felt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class DicesTest {
	@Test
	public void testDices() {
		Dices dices = new Dices();

		for (int expected = 1; expected <= 13; expected++) {
			int actual = 0;
			int i = 0;
			int retries = 10000;
			while (actual != expected && i < retries) {
				actual = dices.roll();
				i++;
			}

			if (expected >= 2 && expected <= 12) {
				assertEquals("Unable to roll!", expected, actual);
			} else {
				assertNotEquals("Roll unexpected!", expected, actual);
				assertEquals("Tries unused on: " + expected, retries, i);
			}
		}
	}

	@Test
	public void testSetDice() {
		Dices dices = new Dices(4, 7, 5, 4);
		assertEquals("Roll Unexpected!", 4, dices.roll());
		assertEquals("Roll Unexpected!", 7, dices.roll());
		assertEquals("Roll Unexpected!", 5, dices.roll());
		assertEquals("Roll Unexpected!", 4, dices.roll());

		Dices dices2 = new Dices(4, 4, 4);
		for (int i = 0; i < 10; i++) {
			assertEquals("Roll Unexpected!", 4, dices2.roll());
		}
	}
}
