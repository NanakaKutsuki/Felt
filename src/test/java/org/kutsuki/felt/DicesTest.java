package org.kutsuki.felt;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DicesTest {
	@Test
	public void testDices() {
		Dices dices = new Dices();

		for (int expected = 1; expected <= 13; expected++) {
			int actual = 0;
			int i = 0;
			int retries = 100;
			while (actual != expected && i < retries) {
				actual = dices.roll();
				i++;
			}

			if (expected >= 2 && expected <= 12) {
				assertEquals("Unable to roll: " + expected, expected, actual);
			} else {
				assertNotEquals("Roll unexpected: " + expected, expected, actual);
				assertEquals("Tries unused on: " + expected, retries, i);
			}
		}
	}

	private void assertNotEquals(String string, int expected, int actual) {
		// TODO Auto-generated method stub

	}
}
