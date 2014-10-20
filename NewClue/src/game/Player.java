package game;

import java.util.ArrayList;
import java.util.Set;

public abstract class Player {
	public enum PlayerType {
		HUMAN,COMPUTER
	}

	private String name;
	private String color;
	private PlayerType type;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private BoardCell position;
	
	public abstract Card disproveSuggestion(String person, String room, String weapon);
	public abstract Set<String> makeAccusation(String person, String room, String weapon);

	public void giveCard(Card crd) {
		cards.add(crd);
	}
	
	public void setStartPosition(BoardCell cell) {
		position = cell;
	}
	// For testing purposes only
	public PlayerType getPlayerType() {
		return type;
	}
	
	public void setName(String nm) {
		name = nm;
	}
	public void setType(PlayerType tp) {
		type = tp;
	}
	public void setColor(String clr) {
		color = clr;
	}
	
	public String getName() {
		return name;
	}
	
	public PlayerType getType() {
		return type;
	}
	public ArrayList<Card> getCards() {
		return cards;
	}
	public String getColor() {
		return color;
	}
	public BoardCell getPosition() {
		return position;
	}
}

