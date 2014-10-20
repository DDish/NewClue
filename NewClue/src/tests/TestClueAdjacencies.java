package tests;

import static org.junit.Assert.*;
import game.Board;
import game.BoardCell;
import game.ClueGame;

import java.util.LinkedList;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestClueAdjacencies {
	private static Board board;
	LinkedList<BoardCell> testList;
	@Before
	public void setUp() {
		ClueGame game = new ClueGame("layout.csv","legend.txt","cards.txt","players.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		board.calcAdjacencies();

	}
	//Green on diragram
	@Test
	public void testAdjacencyWalkways()
	{
		testList = board.getAdjList(16, 16);
		Assert.assertTrue(testList.contains(board.getCellAt(16, 17)));
		Assert.assertTrue(testList.contains(board.getCellAt(17, 16)));
		Assert.assertTrue(testList.contains(board.getCellAt(15, 16)));
		Assert.assertTrue(testList.contains(board.getCellAt(16, 15)));

		//edge of board beside room
		testList = board.getAdjList(21, 4);
		Assert.assertTrue(testList.contains(board.getCellAt(21, 5)));
		Assert.assertTrue(testList.contains(board.getCellAt(20, 4)));

		//edge of board beside room
		testList = board.getAdjList(17, 21);
		Assert.assertTrue(testList.contains(board.getCellAt(16, 21)));
		Assert.assertTrue(testList.contains(board.getCellAt(17, 20)));

		//edge of board beside room
		testList = board.getAdjList(4, 22);
		Assert.assertTrue(testList.contains(board.getCellAt(4, 21)));
		Assert.assertTrue(testList.contains(board.getCellAt(5, 22)));

		//edge of board beside room
		testList = board.getAdjList(0, 6);
		Assert.assertTrue(testList.contains(board.getCellAt(1,6)));
		Assert.assertTrue(testList.contains(board.getCellAt(0, 5)));
	}
	//Pink on digram
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(6, 6, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(6, 7)));
		// Take two steps
		board.calcTargets(6, 6, 2);
		targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(6, 8)));
		Assert.assertTrue(targets.contains(board.getCellAt(5, 7)));
		Assert.assertTrue(targets.contains(board.getCellAt(7, 7)));
	}
	//Purple on diagram
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction DOWN
		testList = board.getAdjList(4, 12);
		Assert.assertTrue(testList.contains(board.getCellAt(4, 13)));
		Assert.assertTrue(testList.contains(board.getCellAt(4, 11)));
		Assert.assertTrue(testList.contains(board.getCellAt(3, 12)));
		Assert.assertTrue(testList.contains(board.getCellAt(5, 12)));
		Assert.assertEquals(4, testList.size());

		// Test beside a door direction UP
		testList = board.getAdjList(6, 20);
		Assert.assertTrue(testList.contains(board.getCellAt(6, 19)));
		Assert.assertTrue(testList.contains(board.getCellAt(6, 21)));
		Assert.assertTrue(testList.contains(board.getCellAt(7, 20)));
		Assert.assertTrue(testList.contains(board.getCellAt(5, 20)));
		Assert.assertEquals(4, testList.size());

		// Test beside a door direction LEFT
		testList = board.getAdjList(10, 16);
		Assert.assertTrue(testList.contains(board.getCellAt(10, 17)));
		Assert.assertTrue(testList.contains(board.getCellAt(10, 15)));
		Assert.assertTrue(testList.contains(board.getCellAt(11, 16)));
		Assert.assertTrue(testList.contains(board.getCellAt(9, 16)));
		Assert.assertEquals(4, testList.size());

		// Test beside a door direction RIGHT
		testList = board.getAdjList(15, 6);
		Assert.assertTrue(testList.contains(board.getCellAt(15, 7)));
		Assert.assertTrue(testList.contains(board.getCellAt(15, 5)));
		Assert.assertTrue(testList.contains(board.getCellAt(16, 6)));
		Assert.assertTrue(testList.contains(board.getCellAt(14, 6)));
		Assert.assertEquals(4, testList.size());
	}
	//white on diagram
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(5, 0, 1);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(5, 1)));
		Assert.assertTrue(targets.contains(board.getCellAt(4, 0)));	
				
	}
	
	@Test
	public void testTargetsTwoStep() {
		board.calcTargets(5, 0, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(5, 2)));
		Assert.assertTrue(targets.contains(board.getCellAt(4, 1)));	
				
	}
	@Test
	public void testTargetsThreeStep() {
		board.calcTargets(5, 0, 3);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(4, 0)));
		Assert.assertTrue(targets.contains(board.getCellAt(5, 1)));	
		Assert.assertTrue(targets.contains(board.getCellAt(4, 2)));	
		Assert.assertTrue(targets.contains(board.getCellAt(5, 3)));	
				
	}
	
	@Test
	public void testTargetsFourStep() {
		board.calcTargets(5, 0, 4);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(5, 4)));
		Assert.assertTrue(targets.contains(board.getCellAt(5, 2)));	
		Assert.assertTrue(targets.contains(board.getCellAt(4, 3)));	
		Assert.assertTrue(targets.contains(board.getCellAt(4, 1)));	
				
	}
	//orange on diagram
	@Test 
	public void testTargetsIntoRoom()
	{
		board.calcTargets(6, 8, 2);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(6, 6)));
		
	}
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(6, 9, 3);
		Set<BoardCell> targets= board.getTargets();
		Assert.assertTrue(targets.contains(board.getCellAt(6, 6)));
	}
	
}
