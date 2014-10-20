package game ;

import game.Player.PlayerType;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class ClueGame {
	private Map<Character,String> rooms = new HashMap<Character,String>();
	private ArrayList<Card> weapons = new ArrayList<Card>();
	private ArrayList<Card> suspects = new ArrayList<Card>();
	private ArrayList<Card> roomCards = new ArrayList<Card>();
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Player> players  = new ArrayList<Player>();
	private Set<Card> solution = new HashSet<Card>();
	private Board board;
	private String layout;
	private String legend;
	private String cards;
	private String contenders;
	private int turn = 0;
	
	public static void main(String args[]) throws BadConfigFormatException{
		ClueGame game = new ClueGame("ClueLayoutColumns.csv", "ClueLegend.txt","cards.txt","players.txt");
		game.loadConfigFiles();
		game.initiateSolution();
		game.dealCards();
	}

	public ClueGame(String layout, String legend, String cards, String contenders){
		this.legend = legend;
		this.layout = layout;
		this.cards = cards;
		this.contenders = contenders;
	}

	public void loadConfigFiles() {
		try{
			loadRoomConfig();
		}
		catch(BadConfigFormatException e){
			System.out.println(e.getMessage());
		}	
		try {
			loadCards();
		}
		catch(BadConfigFormatException e){
			System.out.println(e.getMessage());
		}
		try{
			createPlayers();
		}
		catch(BadConfigFormatException e){
			System.out.println(e.getMessage());
		}
	}

	public void loadCards() throws BadConfigFormatException {
		try{
			FileReader reader = new FileReader(cards);
			Scanner in = new Scanner(reader);
			while(in.hasNextLine()) {
				String line = in.nextLine();
				String parts[] = line.split(",");
				if(parts.length != 2) {
					throw new BadConfigFormatException("Invalid number of arguments per line.");
				}
				if(parts[0].length() != 1) {
					throw new BadConfigFormatException("Invalid argument header.");
				}
				char initial = parts[0].charAt(0);
				String card = parts[1];
				if (initial == 'S') {
					suspects.add(new Card(Card.Type.PERSON,card));
					deck.add(new Card(Card.Type.PERSON,card));
				}
				if (initial == 'W') {
					weapons.add(new Card(Card.Type.WEAPON,card));
					deck.add(new Card(Card.Type.WEAPON,card));
				}
				if (initial == 'R') {
					roomCards.add(new Card(Card.Type.ROOM,card));
					deck.add(new Card(Card.Type.ROOM,card));
				}
				//else throw new BadConfigFormatException("Invalid argument header.");
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void loadRoomConfig() throws BadConfigFormatException {
		//load legend
				try{
					FileReader reader = new FileReader(legend);
					Scanner in = new Scanner(reader);
					while (in.hasNextLine()) {
						String line = in.nextLine();
						//split the line by commas
						String parts[] = line.split(",");
						//verify that we only have 2 items on a line
						if(parts.length != 2){
							throw new BadConfigFormatException("Invalid number of items on line");
						}
						//verify that the initial is only 1 character
						if(parts[0].length() != 1){
							throw new BadConfigFormatException("Invalid Initial");
						}
						char initial = parts[0].charAt(0);
						String room = parts[1];
						rooms.put(initial, room);
					}
					board  = new Board(this.layout,rooms);
					board.loadBoardConfig();
					board.calcAdjacencies();
				}catch(FileNotFoundException e){
					System.out.println(e.getMessage());
				} 

	}
	
	public void createPlayers() throws BadConfigFormatException {
		try{
			FileReader reader = new FileReader(contenders);
			Scanner in = new Scanner(reader);
			while(in.hasNextLine()) {
				String line = in.nextLine();
				String parts[] = line.split(",");
				if(parts.length != 5) {
					throw new BadConfigFormatException("Invalid number of arguments per line.");
				}
				if(parts[0].length() != 1) {
					throw new BadConfigFormatException("Invalid argument header.");
				}
				char initial = parts[0].charAt(0);
				String playerName = parts[1];
				String color = parts[2];
				int x = Integer.parseInt(parts[3]);
				int y = Integer.parseInt(parts[4]);
				if (initial == 'H') players.add(new HumanPlayer (PlayerType.HUMAN,playerName,color,board.getCellAt(x,y)));
				if (initial == 'C') players.add(new ComputerPlayer(PlayerType.COMPUTER,playerName,color,board.getCellAt(x,y)));
				
				//else throw new BadConfigFormatException("Invalid argument header.");
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Will be randomized for actual gameplay.  Only artificially selected for testing purposes.
	public void initiateSolution() {
		solution.add(deck.get(0));
		solution.add(deck.get(6));
		solution.add(deck.get(12));
		deck.remove(12);
		deck.remove(6);
		deck.remove(0);
	}
	
	public void dealCards() {
		Random rand = new Random();
		int index = 0;
		while(!deck.isEmpty()) {
			int random = rand.nextInt((deck.size())); 
			players.get(index%6).giveCard(deck.get(random));
			deck.remove(random);
			index++;
		}		
	}
	
	public void dealCardsContrived() {
		players.get(4).giveCard(deck.get(0));
		players.get(4).giveCard(deck.get(12));
		players.get(2).giveCard(deck.get(15));
		players.get(3).giveCard(deck.get(11));
		players.get(1).giveCard(deck.get(3));
		players.get(0).giveCard(deck.get(1));
		players.get(0).giveCard(deck.get(7));
		players.get(0).giveCard(deck.get(13));
	}
	
	public Boolean checkAccusation(Set<String> accusation){
		Set<String> solved = new HashSet<String>();
		for(Card card : solution)
			solved.add(card.getName());
		if(accusation.equals(solved))
			return true;
		return false;
	}
	
	public Set<String> createSuggestion(String person, String room, String weapon) {
		Set<String> sgstn = new HashSet<String>();
		return sgstn;
	}
	
	public ArrayList<Card> checkSuggestion(Set<String> suggestion) {
			return null;
	}
	
	// For testing purposes only.
	public ArrayList<Card> getSuspects(){
		return suspects;
	}
	public ArrayList<Card> getWeapons(){
		return weapons;
	}
	public ArrayList<Card> getRooms(){
		return roomCards;
	}
	public ArrayList<Player> getPlayers(){
		return players;
	}
	public ArrayList<Card> getDeck(){
		return deck;
	}
	public Board getBoard() {
		return board;
	}
	public Set<Card> getSolution() {
		return solution;
	}
	public void setTurn(int trn) {
		turn = trn;
	}
}
