package game;

public class Card {
	@Override
	public String toString() {
		return "Card [name=" + name + "]";
	}

	public enum Type {
		PERSON, WEAPON, ROOM
	}
	private String name;
	private Type type;
	
	public Card (Type tp, String nm) {
		type = tp;
		name = nm;
	}
	public Boolean equals(Card card) {
		if (card instanceof Card) {
			return (type.equals(card.getType()) && name.equals(card.getName()));
		}
		else return false;
	}
	
	public String getName() {
		return name;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setName(String nm) {
		name = nm;
	}
	
	public void setType(Type tp) {
		type = tp;
	}
}
