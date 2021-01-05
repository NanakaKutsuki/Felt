package org.kutsuki.bond.shoe;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShoeTest {
    private BasicShoe shoe = new BasicShoe();

    @Test
    public void testShoe() {
	int count = 0;

	// check 2-9 cards
	for (int rank = 2; rank <= 9; rank++) {
	    count = 0;

	    for (Card card : shoe.getShoe()) {
		if (card.getRank() == rank) {
		    count++;
		}
	    }

	    assertEquals("Missing Cards!", 32, count);
	}

	// check for rank 10s
	count = 0;
	for (Card card : shoe.getShoe()) {
	    if (card.getRank() == 0) {
		count++;
	    }
	}
	assertEquals("Missing Cards!", 128, count);

	// check for Aces
	count = 0;
	for (Card card : shoe.getShoe()) {
	    if (card.getRank() == 1) {
		count++;
	    }
	}
	assertEquals("Missing Cards!", 32, count);
    }

    @Test
    public void testSetupShoe() {
	AbstractShoe fakeShoe = new BasicShoe(4, 5, 6);
	assertEquals("Wrong Card", 4, fakeShoe.getNextCard().getRank());
	assertEquals("Wrong Card", 5, fakeShoe.getNextCard().getRank());
	assertEquals("Wrong Card", 6, fakeShoe.getNextCard().getRank());

	for (int i = 3; i < 8 * 52; i++) {
	    assertEquals("Wrong Card", 0, fakeShoe.getNextCard().getRank());
	}
    }

    @Test
    public void testCountShoe() {
	// RedSixShoe shoe = new RedSixShoe(8);
	CountShoe shoe = new CountShoe(8);

	for (int i = 0; i < shoe.getShoe().size(); i++) {
	    shoe.count(shoe.getShoe().get(i));
	}

	assertEquals("Wrong Count", 0, shoe.getCount());
    }
}
