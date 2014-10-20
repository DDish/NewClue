package game;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	public static final int MAX_ROWS = 100;
	public static final int MAX_COLS = 100;
	BoardCell[][] board = new BoardCell[MAX_ROWS][MAX_COLS];
	Map<Character,String> rooms;
	Map<BoardCell,LinkedList<BoardCell>> adjacencies = new HashMap<BoardCell,LinkedList<BoardCell>>();

	HashSet<BoardCell> visited;
	HashSet<BoardCell> path;
	HashSet<BoardCell> targets;

	int numRows;
	int numColumns;
	String layout;

	public Board(){
		this.layout = "";
	}

	public Board(String layout,Map<Character,String> rooms){
		this.layout = layout;
		this.rooms = rooms;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}
	public RoomCell getRoomCellAt(int i, int j) {
		return (RoomCell) board[i][j];
	}
	public void loadBoardConfig() throws BadConfigFormatException {
		try{
			FileReader reader = new FileReader(layout);
			Scanner in = new Scanner(reader);
			int currentRow=0;
			int currentCol=0;
			int lastColCount=0;
			while (in.hasNextLine()) {
				//get the next line
				String line = in.nextLine();
				//split the next line on commas
				String parts[] = line.split(",");
				for(String cell:parts){
					//check if the cell is a walkway
					if(rooms.containsKey(cell.charAt(0))){
						if(cell.charAt(0)=='W'){
							board[currentRow][currentCol]= new WalkwayCell(currentRow,currentCol);
						}
						else if(cell.length()==1){
							board[currentRow][currentCol]= new RoomCell(currentRow,currentCol,cell.charAt(0));
						}
						else if(cell.length()==2){
							board[currentRow][currentCol]= new RoomCell(currentRow,currentCol,cell.charAt(0),cell.charAt(1));
						}
						else{
							throw new BadConfigFormatException("Invalid room config");
						}
						currentCol++;
					}
					else{
						throw new BadConfigFormatException("Room not included in legend: "+cell.charAt(0));
					}
					numColumns = currentCol;
				}
				if(parts.length !=lastColCount && currentRow!=0){
					throw new BadConfigFormatException("Invalid number of columns");
				}
				else{
					lastColCount=parts.length;
				}

				currentRow++;
				currentCol=0;

				numRows=currentRow;
			}

		}catch(FileNotFoundException e){
			e.getLocalizedMessage();
		}

	}
	public RoomCell.DoorDirection checkDoorDirection(BoardCell cell){
		RoomCell tempCell = (RoomCell)cell;
		return tempCell.doorDirection;
	}

	public void calcAdjacencies() {

		//iterate through every cell
		for (int row=0; row<this.getNumRows(); row++)
			for (int col=0; col<this.getNumColumns(); col++) {
				BoardCell cell = this.getCellAt(row, col);
				//List to store adjacencies
				LinkedList<BoardCell> adBoard = new LinkedList<BoardCell>();
				
				//if the input is a doorway we need to check the direction to get the right adjacency
				if (cell.isDoorway()){
					RoomCell tempCell = (RoomCell) cell;
					switch(tempCell.doorDirection){
					case UP:
						adBoard.add(getCellAt(row-1,col));
						break;
					case DOWN:
						adBoard.add(getCellAt(row+1,col));
						break;
					case LEFT:
						adBoard.add(getCellAt(row,col-1));
						break;
					case RIGHT:
						adBoard.add(getCellAt(row,col+1));
						break;
					default:
						break;
					}
				}
				//else we don't want to inclue roomcells in our adjacency list
				else if(!cell.isRoom()){
					//make sure we don't fall off the edge of the board
					if (row-1 >= 0 ) {
						BoardCell up=getCellAt(row-1,col);
						if(up.isWalkway())
							adBoard.add(up);
						//only add a door to the list if it is facing the right direction
						else if(up.isRoom() && checkDoorDirection(up)==RoomCell.DoorDirection.DOWN)
							adBoard.add(up);
					}
					//make sure we don't fall off the edge of the board
					if (row+1 < getNumRows()) {
						BoardCell down=getCellAt(row+1,col);
						if(down.isWalkway())
							adBoard.add(down);
						//only add a door to the list if it is facing the right direction
						else if(down.isRoom() && checkDoorDirection(down)==RoomCell.DoorDirection.UP)
							adBoard.add(down);
					}
					//make sure we don't fall off the edge of the board
					if (col-1 >= 0) {
						BoardCell left=getCellAt(row,col-1);
						if(left.isWalkway())
							adBoard.add(left);
						//only add a door to the list if it is facing the right direction
						else if(left.isRoom() && checkDoorDirection(left)==RoomCell.DoorDirection.RIGHT)
							adBoard.add(left);
					}
					//make sure we don't fall off the edge of the board
					if (col+1 < getNumColumns()) {
						BoardCell right=getCellAt(row,col+1);
						if(right.isWalkway())
							adBoard.add(right);
						//only add a door to the list if it is facing the right direction
						else if(right.isRoom() && checkDoorDirection(right)==RoomCell.DoorDirection.LEFT)
							adBoard.add(right);
					}

				}
				adjacencies.put(cell, adBoard);
			}
	}

	public void findAllTargets(BoardCell cell, int numStep) {
		visited.add(cell);
		numStep--;
		if((numStep ==0)){
			visited.remove(cell);
			targets.add(cell);
			return;
		}
		for(BoardCell b:adjacencies.get(cell)){
			if(!visited.contains(b)){
				findAllTargets(b,numStep);
				//If target is a doorway we can skip right to adding it to the target list
				//Doorways don't need to use the whole roll
				if(b.isDoorway()){
					targets.add(b);
				}
			}
		}
		visited.remove(cell);
	}

	public void calcTargets(int row, int col, int roll) {
		visited = new HashSet<BoardCell>();
		path = new HashSet<BoardCell>();
		targets= new HashSet<BoardCell>();
		findAllTargets(getCellAt(row,col),roll+1);
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public LinkedList<BoardCell> getAdjList(int row, int col) {
		return adjacencies.get(getCellAt(row,col));
	}
}
