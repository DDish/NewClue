package tests;

import static org.junit.Assert.*;
import game.BadConfigFormatException;
import game.Board;
import game.BoardCell;
import game.Card;
import game.ClueGame;
import game.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class GameActionTests {
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
		game.initiateSolution();
		game.dealCards();
		board = game.getBoard();
		weapons = game.getWeapons();
		suspects = game.getSuspects();
		rooms = game.getRooms();
		players = game.getPlayers();
		deck = game.getDeck();
		solution = game.getSolution();
	}

	//Tests whether accusations are checked properly
	@Test 
	public void testAccusationResponse() {
		//Correct accusation.
		ClueGame game = new ClueGame("layout.csv", "legend.txt","cards.txt","players.txt");
		game.loadConfigFiles();
		game.initiateSolution();
		game.dealCards();
		Set<String> accusation = players.get(1).makeAccusation("Miss Scarlett","classroom", "knife");
		assertEquals(true,game.checkAccusation(accusation));
		//Wrong room
		accusation = players.get(1).makeAccusation("Miss Scarlett","bathroom","knife");
		assertEquals(false,game.checkAccusation(accusation));
		//Wrong weapon
		accusation = players.get(1).makeAccusation("Miss Scarlett","classroom","revolver");
		assertEquals(false,game.checkAccusation(accusation));
		//Wrong person
		accusation = players.get(1).makeAccusation("Reverend","classroom" , "knife");
		assertEquals(false,game.checkAccusation(accusation));
		//All wrong
		accusation = players.get(1).makeAccusation("Reverend","bathroom" , "revolver");
		assertEquals(false,game.checkAccusation(accusation));
	}
	
	//Tests selecting a target location for a CPU from a room to the hallway only
	@Test 
	public void testTargetCPUStartRoom1() {
		board.calcTargets(2, 2, 6);
		Set<BoardCell> targets = board.getTargets();
		BoardCell chooseCell = players.get(4).pickLocation(targets);
		
		//test targets starting from cell [2, 2] with a roll of six
		assertEquals(15, targets.size());
		assertEquals(true, targets.contains(board.getCellAt(0, 4)));
		assertEquals(true, targets.contains(board.getCellAt(0, 6)));
		assertEquals(true, targets.contains(board.getCellAt(1, 5)));
		assertEquals(true, targets.contains(board.getCellAt(2, 4)));
		assertEquals(true, targets.contains(board.getCellAt(2, 6)));
		assertEquals(true, targets.contains(board.getCellAt(3, 3)));
		assertEquals(true, targets.contains(board.getCellAt(3, 5)));
		assertEquals(true, targets.contains(board.getCellAt(3, 7)));
		assertEquals(true, targets.contains(board.getCellAt(4, 0)));
		assertEquals(true, targets.contains(board.getCellAt(4, 2)));
		assertEquals(true, targets.contains(board.getCellAt(4, 4)));
		assertEquals(true, targets.contains(board.getCellAt(4, 6)));
		assertEquals(true, targets.contains(board.getCellAt(5, 1)));
		assertEquals(true, targets.contains(board.getCellAt(5, 3)));
		assertEquals(true, targets.contains(board.getCellAt(5, 5)));
		
		//test that one of these is picked correctly
		
				assertEquals(true, targets.contains(chooseCell));
		
	}
	
	//Tests selecting a target location for a CPU from a room to  the hallway but also including a possible room
		@Test 
		public void testTargetCPUStartRoom2() {
			board.calcTargets(7, 20, 5);
			Set<BoardCell> targets = board.getTargets();
			BoardCell chooseCell = players.get(4).pickLocation(targets);
			
			//test targets starting from [7,20] with a roll of 5
			assertEquals(11, targets.size());
			assertEquals(true, targets.contains(board.getCellAt(3, 19)));
			assertEquals(true, targets.contains(board.getCellAt(4, 18)));
			assertEquals(true, targets.contains(board.getCellAt(4, 20)));
			assertEquals(true, targets.contains(board.getCellAt(4, 22)));
			assertEquals(true, targets.contains(board.getCellAt(5, 17)));
			assertEquals(true, targets.contains(board.getCellAt(5, 19)));
			assertEquals(true, targets.contains(board.getCellAt(5, 21)));
			assertEquals(true, targets.contains(board.getCellAt(6, 16)));
			assertEquals(true, targets.contains(board.getCellAt(6, 18)));
			assertEquals(true, targets.contains(board.getCellAt(6, 20)));
			assertEquals(true, targets.contains(board.getCellAt(6, 22)));
			
			//test that one of these is picked correctly
			
			assertEquals(true, targets.contains(chooseCell));
			
		}
	
	//Tests selecting a target location for a CPU from the hallway/walkway to another hallway/walkway space only
	@Test 
	public void testTargetCPUStartHall1() {
		board.calcTargets(13, 10, 4);
		Set<BoardCell> targets = board.getTargets();
		BoardCell chooseCell = players.get(4).pickLocation(targets);
		
		//test targets starting from [13,10] with a roll of 4
		assertEquals(18, targets.size());
		assertEquals(true, targets.contains(board.getCellAt(10, 9)));
		assertEquals(true, targets.contains(board.getCellAt(11, 8)));
		assertEquals(true, targets.contains(board.getCellAt(12, 7)));
		assertEquals(true, targets.contains(board.getCellAt(12, 9)));
		assertEquals(true, targets.contains(board.getCellAt(13, 6)));
		assertEquals(true, targets.contains(board.getCellAt(13, 8)));
		assertEquals(true, targets.contains(board.getCellAt(13, 12)));
		assertEquals(true, targets.contains(board.getCellAt(13, 14)));
		assertEquals(true, targets.contains(board.getCellAt(14, 7)));
		assertEquals(true, targets.contains(board.getCellAt(14, 9)));
		assertEquals(true, targets.contains(board.getCellAt(14, 11)));
		assertEquals(true, targets.contains(board.getCellAt(14, 13)));
		assertEquals(true, targets.contains(board.getCellAt(15, 8)));
		assertEquals(true, targets.contains(board.getCellAt(15, 10)));
		assertEquals(true, targets.contains(board.getCellAt(15, 12)));
		assertEquals(true, targets.contains(board.getCellAt(16, 9)));
		assertEquals(true, targets.contains(board.getCellAt(16, 11)));
		assertEquals(true, targets.contains(board.getCellAt(17, 10)));
		
		//test that one of these is picked correctly
		
		assertEquals(true, targets.contains(chooseCell));
	}
	
	//Tests selecting a target location for a CPU from the hallway/walkway to a possible room
		@Test 
		public void testTargetCPUStartHall2() {
			board.calcTargets(12, 0, 3);
			Set<BoardCell> targets = board.getTargets();
			BoardCell chooseCell = players.get(4).pickLocation(targets);
			
			//test targets starting from [12,0] with a roll of 3
			assertEquals(5, targets.size());
			assertEquals(true, targets.contains(board.getCellAt(11, 1)));
			assertEquals(true, targets.contains(board.getCellAt(11, 3)));
			assertEquals(true, targets.contains(board.getCellAt(12, 0)));
			assertEquals(true, targets.contains(board.getCellAt(12, 2)));
			assertEquals(true, targets.contains(board.getCellAt(13, 0)));
			
			//test that one of these is picked correctly
			
			assertEquals(true, targets.contains(chooseCell));
		}
	
	//Tests disproving a suggestion
	// Assume the suggestion is made by player 2. 
	@Test 
	public void testDisproveComprehensive() {
		ClueGame game = new ClueGame("layout.csv","legend.txt","cards.txt","players.txt");
		game.loadConfigFiles();
		// Deals a testable scenario.
		game.dealCardsContrived();
		// Tests that one player reveals only possible card.
		// Third player asked has only one card, "classroom."
		Set<String> sgstn = game.createSuggestion("Professor Plum","classroom","knife");
		game.setTurn(2);
		ArrayList<Card> disproval = game.checkSuggestion(sgstn);
		assertEquals(1,disproval.size());
		assertTrue(disproval.get(0).getName().equals("classroom"));
		// Tests that one player chooses randomly between 2 possible cards.
		// Next two players have none of the suggested cards.  Third player has two cards, "Miss Scarlett" and "classroom."
		sgstn = game.createSuggestion("Miss Scarlett","classroom","knife");
		game.setTurn(2);
		disproval = game.checkSuggestion(sgstn);
		assertEquals(1,disproval.size());
		assertTrue(disproval.get(0).getName().equals("Miss Scarlett") || disproval.get(0).getName().equals("classroom"));
	// Many tests ensuring that players are asked in order.
		// Tests that null is returned by all players if no one can disprove.
		sgstn = game.createSuggestion("Mrs. White","bathroom","bomb");
		game.setTurn(2);
		disproval = game.checkSuggestion(sgstn);
		assertEquals(0,disproval.size());
		// First player asked has the weapon, "study." Second player asked has the room, "revolver."
		sgstn = game.createSuggestion("Reverend","study", "revolver");
		game.setTurn(2);
		disproval = game.checkSuggestion(sgstn);
		assertEquals(1,disproval.size());
		assertTrue(disproval.get(0).getName().equals("study"));
		// Tests that all players are queried by giving last player chance to disprove.
		// Player 3 suggests, Player 2 can disprove. 
		sgstn = game.createSuggestion("Reverend","kitchen","rope");
		game.setTurn(3);
		disproval = game.checkSuggestion(sgstn);
		assertEquals(1,disproval.size());
		assertEquals("Reverend",disproval.get(0).getName());
		// Test where human player makes suggestion.
		// Next player has "Reverend."
		sgstn = game.createSuggestion("Reverend","kitchen","rope");
		game.setTurn(1);
		disproval = game.checkSuggestion(sgstn);
		assertEquals(1,disproval.size());
		assertEquals("Reverend",disproval.get(0).getName());
		// Tests that human player must reveal a card, and that it reveals only one card if it has many.
		// Human player has all three suggested cards.
		sgstn = game.createSuggestion("Colonel Mustard","family room","axe");
		game.setTurn(2);
		disproval = game.checkSuggestion(sgstn);
		assertEquals(1,disproval.size());
		assertTrue(disproval.get(0).getName().equals("Colonel Mustard") || disproval.get(0).getName().equals("axe") || disproval.get(0).getName().equals("family room"));
		// Tests that the player whose turn it is does not return a card.
		// Suggesting player has "Reverend." No one else has card to reveal.
		sgstn = game.createSuggestion("Reverend","kitchen","rope");
		game.setTurn(2);
		disproval = game.checkSuggestion(sgstn);
		assertEquals(0,disproval.size());
	}
	
	//Tests making a suggestion
	@Test 
	public void testMakeSugg() {
		fail("Not yet implemented");
	}
}
