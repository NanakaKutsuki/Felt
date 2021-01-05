package org.kutsuki.bond.shoe;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private List<Card> hand;
	private int value;
	
	// default constructor
	public Hand() {
		this.hand = new ArrayList<Card>();
		this.value = 0;
	}
	
	
	// addCard
	public void addCard( Card card ) {
		hand.add(card);
		
		// calculate value
		value = 0;
		
		for( Card c : hand ) {			
			value += c.getRank();
			
			if( value > 9 ) {
				value -= 10;
			}
		}
	}
	
	
	// clear
	public void clear() {
		hand.clear();
		value = 0;
	}
	
	
	// getHand
	public List<Card> getHand() {
		return hand;
	}
	
	
	// getThirdCardRank
	public int getThirdCardRank() {
		return hand.get(2).getRank();
	}
	
	
	// getValue
	public int getValue() {
		return value;
	}
	
	
	// hasThirdCard
	public boolean hasThirdCard() {
		return hand.size() == 3;
	}
	
	
	// isOverride
	public boolean isOverride() {
		return !hasThirdCard() && value >= 8;
	}
	
	// toString
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		
		for( Card c : hand ) {
			if( !first ) {
				sb.append(' ');
			}
			
			sb.append(c);
			first = false;
		}
		
		return sb.toString();
	}
}
