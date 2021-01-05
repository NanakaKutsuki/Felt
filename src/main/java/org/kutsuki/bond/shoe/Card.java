package org.kutsuki.bond.shoe;

public class Card {
	private int rank;
	private char suit;

	// Constructor
	public Card(int rank, char suit) {
		this.rank = rank;
		this.suit = suit;
	}

	// toString
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (rank == 10) {
			sb.append('T');
		} else if (rank == 11) {
			sb.append('J');
		} else if (rank == 12) {
			sb.append('Q');
		} else if (rank == 13) {
			sb.append('K');
		} else if (rank == 14) {
			sb.append('A');
		} else {
			sb.append(rank);
		}

		sb.append(suit);
		return sb.toString();
	}

	// getActualRank
	public int getActualRank() {
		return rank;
	}

	// getRank
	public int getRank() {
		int result = rank;

		if (rank >= 10 && rank < 14) {
			result = 0;
		} else if (rank == 14) {
			result = 1;
		}

		return result;
	}

	// getSuit
	public char getSuit() {
		return suit;
	}
}
