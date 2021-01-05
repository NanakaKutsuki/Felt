package org.kutsuki.bond.shoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class HandTest {
    @Test
    public void testHand2Cards() {
	Hand hand = new Hand();
	Card c1 = null;
	Card c2 = null;
	String expectedString = null;
	int expectedValue = 0;

	for (int i = 2; i <= 14; i++) {
	    for (int j = 2; j <= 14; j++) {
		hand.clear();
		c1 = new Card(i, 'c');
		c2 = new Card(j, 'c');
		hand.addCard(c1);
		hand.addCard(c2);

		expectedString = c1 + " " + c2;
		assertEquals("Unexpected String: " + hand, expectedString, hand.toString());

		expectedValue = getValue(i, j);
		assertEquals("Unexpected Value: " + hand, expectedValue, hand.getValue());

		assertFalse("Unexpected Third Card: " + hand, hand.hasThirdCard());
	    }
	}
    }

    @Test
    public void testHand3Cards() {
	Hand hand = new Hand();
	Card c1 = null;
	Card c2 = null;
	Card c3 = null;
	String expectedString = null;
	int expectedValue = 0;

	for (int i = 2; i <= 14; i++) {
	    for (int j = 2; j <= 14; j++) {
		for (int k = 2; k <= 14; k++) {
		    hand.clear();
		    c1 = new Card(i, 'c');
		    c2 = new Card(j, 'c');
		    c3 = new Card(k, 'c');
		    hand.addCard(c1);
		    hand.addCard(c2);
		    hand.addCard(c3);

		    expectedString = c1 + " " + c2 + " " + c3;
		    assertEquals("Unexpected String: " + hand, expectedString, hand.toString());

		    expectedValue = getValue(i, j, k);
		    assertEquals("Unexpected Value: " + hand, expectedValue, hand.getValue());

		    assertTrue("Missing Third Card: " + hand, hand.hasThirdCard());
		    assertEquals("Unexpected Third Card: " + hand, c3.getRank(), hand.getThirdCardRank());
		}
	    }
	}
    }

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
