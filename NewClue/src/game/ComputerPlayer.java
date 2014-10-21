package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	private BoardCell lastRoomVisited;
	Random ranPlayer = new Random();
	
	public ComputerPlayer(PlayerType tp, String nm, String clr, BoardCell cell) {
		super();
		this.setName(nm);
		this.setType(tp);
		this.setColor(clr);
		this.setStartPosition(cell);
	}
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		//first check for any rooms in targets that have not been last visited
		for( BoardCell cells : targets ) {
			if( cells.isDoorway() && cells.equals(lastRoomVisited) ) {
				lastRoomVisited = cells;
				return cells;
			}
		}
		
		//if no rooms to go to, pick random location from targets (might pick last room)
		BoardCell[] targetsArray = (BoardCell[]) targets.toArray();
		int rand = ranPlayer.nextInt() % targets.size();
		return targetsArray[rand];
		
	}

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
