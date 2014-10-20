package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import game.BadConfigFormatException;
import game.Board;
import game.Card;
import game.ClueGame;
import game.Player;
import game.Player.PlayerType;

public class GameSetupTests {
	private static Board board;
	private static ArrayList<Card> weapons;
	private static ArrayList<Card> suspects;
	private static ArrayList<Card> rooms;
	private static ArrayList<Player> players;
	private static ArrayList<Card> deck;
	private static Set<Card> solution;
	private static final int NUM_ROOMS = 11;
	private static final int NUM_ROWS = 22;
	private static final int NUM_COLUMNS = 23;
	
	@Before
	public void setup() throws BadConfigFormatException{
		ClueGame game = new ClueGame("layout.csv", "legend.txt","cards.txt","players.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		weapons = game.getWeapons();
		suspects = game.getSuspects();
		rooms = game.getRooms();
		players = game.getPlayers();
		deck = game.getDeck();
		solution = game.getSolution();
	}
	
	// Tests number of players (loadpeople1)
	@Test
	public void testPlayerCount() {
		assertEquals(6, players.size());
		
	}
	
	// Make sure there are five computer players and one human player. (loadpeople2)
	@Test
	public void testPlayerType() {
		assertEquals(PlayerType.HUMAN,players.get(0).getPlayerType());
		assertEquals(PlayerType.COMPUTER,players.get(1).getPlayerType());
		assertEquals(PlayerType.COMPUTER,players.get(2).getPlayerType());
		assertEquals(PlayerType.COMPUTER,players.get(3).getPlayerType());
		assertEquals(PlayerType.COMPUTER,players.get(4).getPlayerType());
		assertEquals(PlayerType.COMPUTER,players.get(5).getPlayerType());
	}
	
	//Test colors are properly read (loadpeople3)
	@Test
	public void testPlayerColors() {
		assertEquals("red", players.get(0).getColor());
		assertEquals("yellow", players.get(1).getColor());
		assertEquals("white", players.get(2).getColor());
		assertEquals("black", players.get(3).getColor());
		assertEquals("blue", players.get(4).getColor());
		assertEquals("purple", players.get(5).getColor());
	}
	
	//Test starting locations (loadpeople4)
	@Test
	public void testPlayerStartLocation() {
		assertEquals(board.getCellAt(12, 0),players.get(0).getPosition());
		assertEquals(board.getCellAt(21, 6),players.get(1).getPosition());
		assertEquals(board.getCellAt(14, 22),players.get(2).getPosition());
		assertEquals(board.getCellAt(5, 22),players.get(3).getPosition());
		assertEquals(board.getCellAt(0, 13),players.get(4).getPosition());
		assertEquals(board.getCellAt(0, 5),players.get(5).getPosition());
	}
	
	// Tests number of cards (loadcards1)
	@Test
	public void testCardCount() {
		assertEquals(6, suspects.size());
		assertEquals(6, weapons.size());
		assertEquals(9, rooms.size());
	}
	
	// Tests identity of suspects (loadcards2)
	@Test
	public void testSuspects() {
		assertEquals("Miss Scarlett",suspects.get(0).getName());
		assertEquals("Colonel Mustard",suspects.get(1).getName());
		assertEquals("Mrs. White",suspects.get(2).getName());
		assertEquals("Reverend",suspects.get(3).getName());
		assertEquals("Mrs. Peacock",suspects.get(4).getName());
		assertEquals("Professor Plum",suspects.get(5).getName());
	}
	
	// Tests type of weapons (loadcards3)
	@Test
	public void testWeapons() {
		assertEquals("knife",weapons.get(0).getName());
		assertEquals("axe",weapons.get(1).getName());
		assertEquals("rope",weapons.get(2).getName());
		assertEquals("bomb",weapons.get(3).getName());
		assertEquals("poison",weapons.get(4).getName());
		assertEquals("revolver",weapons.get(5).getName());
	}
	
	// Tests room cards (loadcards4)
	public void testRooms() {
		assertEquals(8, rooms.size());
		assertEquals("Classroom",rooms.get(0).getName());
		assertEquals("Family Room",rooms.get(1).getName());
		assertEquals("Dining Room",rooms.get(2).getName());
		assertEquals("Study",rooms.get(3).getName());
		assertEquals("Office",rooms.get(4).getName());
		assertEquals("Library",rooms.get(5).getName());
		assertEquals("Guest room",rooms.get(6).getName());
		assertEquals("Kitchen",rooms.get(7).getName());
		assertEquals("Bathroom",rooms.get(8).getName());
		
	}
	
	// Tests card count of each player. 
	@Test
	public void testPlayerHands(){
		assertEquals(3,players.get(0).getCards().size());
		assertEquals(3,players.get(1).getCards().size());
		assertEquals(3,players.get(2).getCards().size());
		assertEquals(3,players.get(3).getCards().size());
		assertEquals(3,players.get(4).getCards().size());
		assertEquals(3,players.get(5).getCards().size());
	}
	
	//Tests if all cards dealt and no cards dealt twice (loaddeal1&2)
	@Test
	public void testTwoBirdsOneStone() {
		ArrayList<Card> tempDeck = new ArrayList<Card>();
		for(Card solutionCard : solution) {
			if(!tempDeck.contains(solutionCard)) tempDeck.add(solutionCard);
		}
		for( Player people : players ) {
			for( Card playerCard : people.getCards() ) {
				if(!tempDeck.contains(playerCard)) tempDeck.add(playerCard); //Won't add duplicates
		}
		}
		assertEquals(21, tempDeck.size()); //Size will be 21 if all cards dealt correctly and no duplicates, 3 for solution
	}
	
	//Tests if all players have about same number of cards (loaddeal3)
	@Test
	public void testEquality() {
		Integer[] tempArr = new Integer[players.size()];
		int minimum = players.get(0).getCards().size();
		int maximum = players.get(0).getCards().size();
		for( Player people : players ) {
			int tempInt = people.getCards().size();
			if( tempInt < minimum ) minimum = tempInt;
			if( tempInt > maximum ) maximum = tempInt;
		}
		if( maximum - minimum > 1 ) fail("Cards dealt unevenly");
	}
	
	
	/* Test that an exception is thrown for a bad cards config file
		@Test (expected = BadConfigFormatException.class)
		public void testBadCardsFormat() throws BadConfigFormatException, FileNotFoundException {
			// overloaded Board ctor takes config file name
			ClueGame game = new ClueGame("ClueLayout.csv", "ClueLegend.txt","badCards.txt","players.txt");
			game.loadRoomConfig();
			game.getBoard().loadBoardConfig();
		}*/
}
