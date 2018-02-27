package gofish_assn;

import java.util.ArrayList;

import gofish_assn.Card.Suits;
import java.util.Random;

public class Deck {
	private ArrayList<Card> deck = new ArrayList<Card> ();
	final int NUM_CARDS = 52;  //for this kind of deck

	/**
	 * Creates a new, sorted deck
	 */
	public Deck() {
		for(Suits tempSuit : Suits.values()){
			for(int i = Card.LOW_RANK; i <= Card.TOP_RANK; i++){
				deck.add(new Card(i, tempSuit));
			}
		}
		
	}

	/**
	 * Shuffles the deck by choosing a random card and placing it at the top
	 */
	public void shuffle() {
		Random rand = new Random(0x12345678);
		int swapNum;
		for(int i = 0; i < deck.size()*10; i++){
			swapNum = rand.nextInt(NUM_CARDS);
			Card tempCard = deck.remove(swapNum);
			deck.add(tempCard);
		}
	}

	/**
	 * Prints out the deck in readable format
	 */
	public void printDeck() {
		int counter = 0;
		for(Card c : deck){
			System.out.print(c.toString() + " ");
			if(counter >= 12){
				System.out.println();
				counter = 0;
			} else {
				counter++;
			}
		}
		System.out.println();
	}

	/**
	 * Removes a card from the deck if it's not empty
	 * @return A card from the deck or a card with rank 0
	 */
	public Card dealCard() {
		
		if(!deck.isEmpty()){
			return deck.remove(0);
		} else {
			System.out.println("Can't deal card, deck is empty");
			return new Card(0, Suits.club);
		}
		
	}
	

}
