package org.kutsuki.bond.shoe;

public class BasicShoe extends AbstractShoe {
	public BasicShoe() {
		super(8);
	}

	public BasicShoe(int... c) {
		super(8, c);
	}

	@Override
	public int getCount() {
		return -100;
	}

	@Override
	public void resetCount() {
		// do nothing
	}
}
