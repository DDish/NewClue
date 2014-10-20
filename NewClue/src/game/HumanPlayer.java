package game;

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
	public Card disproveSuggestion(String person, String room, String weapon) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> makeAccusation(String person, String room, String weapon) {
		// TODO Auto-generated method stub
		return null;
	}

}
