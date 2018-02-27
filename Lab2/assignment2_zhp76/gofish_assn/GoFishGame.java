package gofish_assn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class GoFishGame {

	/**
	 * Simulates a game of Go Fish
	 */
	public GoFishGame() {
		File results = new File("GoFish_results.txt");
		try {
			PrintWriter writer = new PrintWriter(results);

			//Initialize the game
			Deck deck = new Deck();
			deck.shuffle();
			
			//Placeholder for the winner of the game
			Player winner = null;

			Player player1 = new Player("Ainz Ooal Gown");
			Player player2 = new Player("Sebas Tian");

			writer.println(player1.getName() + " has joined the game.");
			writer.println(player2.getName() + " has joined the game.");
			writer.println();


			//Deal the first 7 cards to each player
			for(int i = 0; i < 7; i++){
				player1.addCardToHand(deck.dealCard());
				player2.addCardToHand(deck.dealCard());
			}

			writer.println("\nThe players check for books in their hands...");
			while(player1.checkHandForBook());
			while(player2.checkHandForBook());
			//Grammar check, use "pair" for one, "pairs" for any other number
			String pairOrPairs = null;
			pairOrPairs = player1.getBookSize()/2 != 1 ? "pairs" : "pair";
			writer.println(player1.getName() + " books " + player1.getBookSize()/2 + " " + pairOrPairs + ".");
			pairOrPairs = player2.getBookSize()/2 != 1 ? "pairs" : "pair";
			writer.println(player2.getName() + " books " + player2.getBookSize()/2 + " " + pairOrPairs + ".");
			writer.println();

			//Play the game until all 26 books are formed
			while(player1.getBookSize() + player2.getBookSize() < 52){
				turn(player1, player2, deck, writer);
				writer.println();
				turn(player2, player1, deck, writer);
				writer.println();
			}

			//Check for who won or if it was a tie
			winner = checkForWinner(player1, player2);
			if(winner.getName() == "tie"){
				writer.println("It's a tie!");
			} else {
				writer.println(winner.getName() + " wins with " + winner.getBookSize()/2 + " pairs!");
			}

			writer.close();
		}catch(FileNotFoundException | NullPointerException e){
			e.printStackTrace();
		}
	}

	/**
	 * Determines who has won the game, or if there has been a tie
	 * @param player1
	 * @param player2
	 * @return	Returns the winning player if there is one, or a "tie" flag
	 */
	private Player checkForWinner(Player player1, Player player2){
		if(player1.getBookSize() > player2.getBookSize()){
			return player1;
		} else if(player2.getBookSize() > player1.getBookSize()){
			return player2;
		} else if(player1.getBookSize() == player2.getBookSize()){
			return new Player("tie");
		} else {
			return null;
		}
	}

	/**
	 * Emulates a turn of Go Fish
	 * @param player1	The player who's asking for a card
	 * @param player2	The player who's being asked for a card
	 * @param deck		The deck being used
	 * @param writer	PrintWriter writer
	 */
	private void turn(Player player1, Player player2, Deck deck, PrintWriter writer){
		//Placeholder for a card being drawn from the deck
		Card cardDrawn = new Card();
		boolean isThereABook;
		if(player1.getHandSize() == 0){
			try {
				cardDrawn = deck.dealCard();
			} catch(NullPointerException e){
				//e.printStackTrace();
			}
			writer.println(player1.getName() + " draws a " + cardDrawn.toString());
			player1.addCardToHand(cardDrawn);
			isThereABook = player1.checkHandForBook();
			if(isThereABook){
				writer.println(player1.getName() + " books the " + cardDrawn.toString());
			}
			return;
		}
		//Choose a random card in hand to ask for
		Card cardToAsk = player1.chooseCardFromHand();
		writer.println(player1.getName() + " asks - Do you have a " + cardToAsk.getRank());

		if(player2.rankInHand(cardToAsk)){
			writer.println(player2.getName() + " says - Yes. I have a " + cardToAsk.getRank());
			player1.addCardToHand(player2.removeCardFromHand(cardToAsk));
			isThereABook = player1.checkHandForBook();
			if(isThereABook){
				writer.println(player1.getName() + " books the " + cardToAsk.toString());
			}
		} else {
			try {
				cardDrawn = deck.dealCard();
			} catch(NullPointerException e){
				//e.printStackTrace();
			}
			if(cardDrawn.getRank() != 0) {
				writer.println(player2.getName() + " says - Go fish.");
				writer.println(player1.getName() + " draws a " + cardDrawn.toString());
				player1.addCardToHand(cardDrawn);
				isThereABook = player1.checkHandForBook();
				if(isThereABook){
					writer.println(player1.getName() + " books the " + cardDrawn.toString());
				}
			} else {
				writer.println(player2.getName() + " says - No. (The deck is empty)");
			}
		}
	}

}
