package org.kutsuki.bond.shoe;

import java.math.BigDecimal;

public class Count {
	private int count, gamesPlayed;
	private BigDecimal bet, lose, win;

	// default constructor
	public Count(int count) {
		this.bet = BigDecimal.ZERO;
		this.count = count;
		this.gamesPlayed = 0;
		this.lose = BigDecimal.ZERO;
		this.win = BigDecimal.ZERO;
	}

	// addBet
	public void addBet(BigDecimal bet) {
		this.bet = this.bet.add(bet);
	}

	// addLoss
	public void addLoss(BigDecimal start, BigDecimal end) {
		lose = lose.add(start.subtract(end));

	}

	// addWin
	public void addWin(BigDecimal start, BigDecimal end) {
		win = win.add(end.subtract(start));
	}

	// addGamesPlayed

	public void addGamesPlayed(int gamesPlayed) {
		this.gamesPlayed += gamesPlayed;
	}

	public int getCount() {
		return count;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public BigDecimal getBet() {
		return bet;
	}

	public BigDecimal getLose() {
		return lose;
	}

	public BigDecimal getWin() {
		return win;
	}
}
