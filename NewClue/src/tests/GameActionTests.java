package tests;

import static org.junit.Assert.*;
import game.BadConfigFormatException;
import game.Board;
import game.Card;
import game.ClueGame;
import game.Player;

import java.util.ArrayList;
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
		ClueGame game = new ClueGame("layout.csv","legend.txt","cards.txt","players.txt");
		game.loadConfigFiles();
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
	// Assume the suggestion is made by player 1.  Suggestion is Miss Scarlett in the classroom with the knife.
	@Test 
	public void testDisprove() {
		ClueGame game = new ClueGame("layout.csv","legend.txt","cards.txt","players.txt");
		game.loadConfigFiles();
		Set<Card> sgstn = game.createSuggestion();
		game.checkSuggestion(sgstn);
		
		// Next two players have none of the suggested cards.  Third player has two cards, "Miss Scarlett" and "classroom."
		
		
	}
	
	//Tests making a suggestion
	@Test 
	public void testMakeSugg() {
		fail("Not yet implemented");
	}
}
