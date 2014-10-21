package game;

import java.util.ArrayList;
import java.util.Set;

public class HumanPlayer extends Player {

	public HumanPlayer(PlayerType tp, String nm, String clr, BoardCell cell) {
		super();
		this.setName(nm);
		this.setType(tp);
		this.setColor(clr);
		this.setStartPosition(cell);
	}

	@Override
	public Card disproveSuggestion(Set<String> suggestion) {
		Card debunked = null;
		suggestion.toString();
		ArrayList<Card> hand = this.getCards();
		for(Card crd : hand) {
			for(String name : suggestion) {
				if(crd.getName().equals(name))
					debunked = crd;
			}
		}
		return debunked;
	}

	@Override
	public Set<String> makeAccusation(String person, String room, String weapon) {
		// TODO Auto-generated method stub
		return null;
	}

}
