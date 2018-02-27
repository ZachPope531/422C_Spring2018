package gofish_assn;

import java.util.ArrayList;

public class Player {
	
	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Card> book = new ArrayList<Card>();
	private String name;

	/**
	 * Creates a player for the game
	 * @param name	The name of the character
	 */
	public Player(String name) {
		this.name = name;
	}

	/**
	 * Adds a card to the player's hand
	 * @param c	The card to be added
	 */
	public void addCardToHand(Card c) {
		hand.add(c);
	}

	/**
	 * Removes a card from the players hand
	 * @param c	The card to be removed
	 * @return	The removed card
	 */
	public Card removeCardFromHand(Card c) {
		Card retCard = null;
		for(Card cardToCheck : hand){
			if(cardToCheck.getRank() == c.getRank()){
				retCard = cardToCheck;
				break;
			}
		}
		hand.remove(retCard);
		return retCard;
	}

	/**
	 * Private variable name getter
	 * @return	The player's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Lists the cards in the player's hand
	 * @return	A string of all the cards in hand
	 */
	public String handToString() {
		String s = "";
		for(Card c : hand){
			s += " " + c.toString();
		}

		return s;
	}

	/**
	 * Lists the cards in the player's book
	 * @return	A string of all the cards in the book
	 */
	public String bookToString() {
		String s = new String();
		for(Card c : book){
			s.concat(" " + c.toString());
		}
		
		return s;
	}

	/**
	 * Private variable hand size getter
	 * @return	An integer of the size of the player's hand
	 */
	public int getHandSize() {
		return hand.size();
	}

	/**
	 * Private variable book size getter
	 * @return An integer of the size of the player's book
	 */
	public int getBookSize() {
		return book.size();
	}
	
	
	//Here are som ideas for additional functionality that you may want/need
	//OPTIONAL
    // comment out if you decide to not use it
    //this function will check a players hand for a pair. 
    //If a pair is found, it moves the cards to the book and returns true

	/**
	 * Checks the player's hand for a potential pair
	 * If a pair exists, the cards are removed from the player's
	 * hand and added to the book
	 * @return	A boolean resulting in true if a pair was found and false otherwise
	 */
    public boolean checkHandForBook(){
		//This will run in O(n^2) but, if run every time someone gets a new card,
		//the max cards someone could have is 25 so it'll run over 625 iterations
		for(int i = 0; i < hand.size()-1; i++){
			for(int j = i + 1; j < hand.size(); j++){
				if(hand.get(i).getRank() == hand.get(j).getRank()){
					book.add(hand.get(i));
					book.add(hand.get(j));
					hand.remove(j);
					hand.remove(i);
					return true;
				}
			}
		}
		return false;
    }

    //OPTIONAL
    // comment out if you decide to not use it    
    //Does the player have a card with the same rank as c in her hand?

	/**
	 * Checks if the player has a card of the same rank
	 * @param c	The card to compare to
	 * @return	A boolean that's true if the player has a card of the same rank, false otherwise
	 */
    public boolean rankInHand(Card c) {
		for(Card compareCard : hand){
			if(compareCard.getRank() == c.getRank()) return true;
		}
		return false;
	}
    
    //uses some strategy to choose one card from the player's
    //hand so they can say "Do you have a 4?"

	/**
	 * Chooses a random card from the player's hand
	 * @return	A card randomly chosen from their hand
	 */
    public Card chooseCardFromHand() {
	    double i = (Math.random()*(hand.size()));
    	return hand.get((int)i);
    }
    
    //Does the player have the card c in her hand?
	/*
    public boolean cardInHand(Card c) {
    	return false; //stubbed
    }
    */
    

    //OPTIONAL
    // comment out if you decide to not use it    
    //Does the player have a card with the same rank as c in her hand?
    //e.g. will return true if the player has a 7d and the parameter is 7c
    /*
    public boolean sameRankInHand(Card c) {
		return rankInHand(c);
    }
    */

}
