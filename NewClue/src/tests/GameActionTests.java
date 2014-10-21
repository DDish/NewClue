package tests;

import static org.junit.Assert.*;
import game.BadConfigFormatException;
import game.Board;
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
	
	//Tests selecting a target location
	@Test 
	public void testTarget() {
		fail("Not yet implemented");
	}
	
	//Tests disproving a suggestion
	// Assume the suggestion is made by player 2. 
	@Test 
	public void testDisprove() {
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
