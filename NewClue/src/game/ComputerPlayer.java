package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char lastRoomVisited;
	
	public ComputerPlayer(PlayerType tp, String nm, String clr, BoardCell cell) {
		super();
		this.setName(nm);
		this.setType(tp);
		this.setColor(clr);
		this.setStartPosition(cell);
	}
	
	public void pickLocation(Set<BoardCell> targets) {}

	public Set<String> makeAccusation(String person, String room, String weapon) {
		// TODO Auto-generated method stub
		Set<String> accusation = new HashSet<String>();
		accusation.add(person);
		accusation.add(room);
		accusation.add(weapon);
		return accusation;
	}

	@Override
	public Card disproveSuggestion(Set<String> suggestion) {
		Card debunked = null;
		ArrayList<Card> hand = this.getCards();
		for(Card crd : hand) {
			for(String name : suggestion) {
				if(crd.getName().equals(name))
					debunked = crd;
			}
		}
		return debunked;
	}
	
}
