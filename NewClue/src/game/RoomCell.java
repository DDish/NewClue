package game;

public class RoomCell extends BoardCell{
	private char roomInitial;
	private boolean doorway=false;
	public enum DoorDirection {
	    UP,DOWN,LEFT,RIGHT,NONE
	}
	DoorDirection doorDirection;
	public RoomCell(){
		super(0,0);
		doorDirection = DoorDirection.NONE;
		roomInitial=' ';
	}
	public RoomCell(int row,int col,char initial){
		super(row,col);
		doorDirection = DoorDirection.NONE;
		roomInitial=initial;
	}
	
	public RoomCell(int row,int col,char initial,char direction) throws BadConfigFormatException{
		super(row,col);
		this.roomInitial=initial;
		switch(direction){
		case 'U':
			doorDirection = DoorDirection.UP;
			doorway=true;
			break;
		case 'D':
			doorDirection = DoorDirection.DOWN;
			doorway=true;
			break;
		case 'L':
			doorDirection = DoorDirection.LEFT;
			doorway=true;
			break;
		case 'R':
			doorDirection = DoorDirection.RIGHT;
			doorway=true;
			break;
		/*default:
			throw new BadConfigFormatException("Only U,D,L,R are acceptable directions");*/
		}
	}
	
	@Override
	public boolean isRoom(){
		return true;
	}
	@Override
	public boolean isDoorway(){
		return doorway;
	}
	public char getInitial() {
		return roomInitial;
	}
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
}
