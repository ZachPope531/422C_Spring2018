package gofish_assn;

public class Card {
	
	public enum Suits {club, diamond, heart, spade};

	final static int TOP_RANK = 13; //King
	final static int LOW_RANK = 1; //Ace
	
	private int rank;  //1 is an Ace
	private Suits suit;

	/**
	 * Basic constructor for Card
	 */
	public Card() {
		rank = 1;
		suit = Suits.spade;
	}

	/**
	 * Card constructor for rank and a character describing the suit
	 * @param r	The rank of the card
	 * @param s	The character describing the suit the card is in
	 */
	public Card(int r, char s) {
		if(r > TOP_RANK || r < LOW_RANK){
			System.out.println("Card.java's \"Card(int r, char s)\" took r outside of bounds");
			return;
		}
		rank = r;
		suit = toSuit(s);
	}

	/**
	 * Card constructor for rank and the suit
	 * @param r	Rank of the card
	 * @param s	Suit of the card
	 */
	public Card(int r, Suits s) {
		if(r > TOP_RANK || r < LOW_RANK){
			System.out.println("Card.java's \"Card(int r, char s)\" took r outside of bounds");
			return;
		}
		rank = r;
		suit = s;
	}

	/**
	 * Takes a character and determines what suit it describes
	 * @param c	The character of the first letter of the suit
	 * @return	A suit
	 */
	private Suits toSuit(char c) {
		switch (c){
			case 'c':
				return Suits.club;
			case 'd':
				return Suits.diamond;
			case 'h':
				suit = Suits.heart;
			case 's':
				suit = Suits.spade;
			default:
				System.out.println("Card.java's \"toSuit(char c)\" is broken");
				return null;
		}
	}

	/**
	 * Prints out the suit name
	 * @param s	The suit
	 * @return	A string with the suit name
	 */
	private String suitToString(Suits s)
	{
		return s.toString();
	}
	
	private String rankToString(int r)
	{
		switch(r){
			case 1:
				return "Ace";
			case 11:
				return "Jack";
			case 12:
				return "Queen";
			case 13:
				return "King";
			default:
				return Integer.toString(r);
		}
	}

	/**
	 * Private variable rank getter
	 * @return	The rank of the card
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * Private variable suit getter
	 * @return	The suit of the card
	 */
	public Suits getSuit() {
		return suit;
	}

	/**
	 * Converts the card into a readable format
	 * @return	A string of rank and suit
	 */
	public String toString() {
		String s = "";
		
		s = s + rankToString(getRank()) + " of " + suitToString(getSuit()) + "s";
		
		return s;
	}
}
