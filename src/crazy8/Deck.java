package crazy8;
import java.io.*;
import java.util.*;
import crazy8.*;

public class Deck extends ArrayList<Card>{
	//Decks are used in this game to hold a bunch of cards. 
	//There is a deck the players all draw cards from,
	//and decks that each player has to store their cards in.
	//Decks can be shuffled.

	private static final long serialVersionUID = 12345678910L;
	//This thing ain't that important, its just that without this i get some stupid warning every time i try to build this class

	/****************************
	**Constructors of class Deck*
	****************************/
	Deck(){
		//Default constructor, no parameters required.
		//This creates a 52 card deck with no repeats
		for (int x = 1; x <= 13; x++) {
			//This loop goes through each number from 1-13 and makes a card with that value in each suit.
			Card nextHeart = new Card(x, "heart");
			Card nextDiamond = new Card(x, "diamond");
			Card nextSpade = new Card(x, "spade");
			Card nextClub = new Card(x, "club");

			//Add the newly created cards to the deck.
			this.add(nextHeart);
			this.add(nextDiamond);
			this.add(nextSpade);
			this.add(nextClub);
		}
	}

	/****************************
	****Methods of class Deck****
	****************************/

	//To shuffle the deck using the Fisher-Yates algorithm
	void shuffle(){
		Collections.shuffle(this);
	}

	//Simply returns the card at the top of the deck.
	Card deal(){
		Card topCard = this.get(0);//Retrieve the card in 1st position
		this.remove(0);//Remove it from the deck
		return topCard;//Return it to whatever wants it.
	}

	//Get a card anywhere in the deck. used for player hands
	Card getCard(int pos){
		return this.get(pos);//return the card at the spot specified
	}

	//Get a card anywhere in the deck and remove it from the deck. also used by player hands
	Card play(int pos){
		Card play = this.get(pos);//get card from that spot, put in new variable
		this.remove(pos);//remove card from deck
		return play;//return the card
	}

	//Add a card to the deck
	void addCard(Card next){
		this.add(next);//adds card to bottom
	}

	//Check if the deck is empty
	Boolean checkEmpty(){
		return this.isEmpty();//return a bool, true = empty
	}

	//Get the number of cards in the deck
	int numberOfCards(){
		return this.size();
	}	
}