package org.kutsuki.bond.shoe;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CardTest {
    @Test
    public void testCard() {
	Card card = null;
	char club = 'c';
	int expectedRank = 0;
	String expectedString = null;

	for (expectedRank = 2; expectedRank <= 9; expectedRank++) {
	    expectedString = "" + expectedRank + club;
	    card = new Card(expectedRank, club);
	    assertEquals("Unexpected Rank", expectedRank, card.getRank());
	    assertEquals("Unexpected Card: ", expectedString, card.toString());
	}

	expectedRank = 0;
	expectedString = "T" + club;
	card = new Card(10, club);
	assertEquals("Unexpected Rank", expectedRank, card.getRank());
	assertEquals("Unexpected Card: ", expectedString, card.toString());

	expectedRank = 0;
	expectedString = "J" + club;
	card = new Card(11, club);
	assertEquals("Unexpected Rank", expectedRank, card.getRank());
	assertEquals("Unexpected Card: ", expectedString, card.toString());

	expectedRank = 0;
	expectedString = "Q" + club;
	card = new Card(12, club);
	assertEquals("Unexpected Rank", expectedRank, card.getRank());
	assertEquals("Unexpected Card: ", expectedString, card.toString());

	expectedRank = 0;
	expectedString = "K" + club;
	card = new Card(13, club);
	assertEquals("Unexpected Rank", expectedRank, card.getRank());
	assertEquals("Unexpected Card: ", expectedString, card.toString());

	expectedRank = 1;
	expectedString = "A" + club;
	card = new Card(14, club);
	assertEquals("Unexpected Rank", expectedRank, card.getRank());
	assertEquals("Unexpected Card: ", expectedString, card.toString());
    }
}
