package crazy8;
import crazy8.*;

public class Card {
	//Card is used to represent the 52 playing cards in a deck, 
	//and there are never 2 identical cards in the game

	/***************************************
	****Instance Variables of class Card****
	***************************************/
	private int value = 0;//Value of the card (1-13)
    private String suit = null;//Suit of the card
    private String face = null;//Card face (suit + value)

    /****************************
	**Constructors of class Card*
	****************************/

	//Card needs a value and suit
   	Card(int number, String type){
    	this.suit = type;//Set teh cards suit and value to the provided args
    	this.value = number;
    	String valLetter = null;//Create string objects for the value and suit icons
		String suitIcon = null;
		if (this.value == 1){//If the value is 1, it's an ace
			valLetter = "A";
		}
		else if (this.value == 11){//or a jack
			valLetter = "J";
		}
		else if (this.value == 12){//or a queen
			valLetter = "Q";
		}
		else if (this.value == 13){//or a king
			valLetter = "K";
		}
		else{//or else its a plain old number
			valLetter = this.value+"";
		}

		//Here we look at the suit and choose the appropriate symbol
		if (this.suit.equals("heart"))
		{
			suitIcon = "♥";
		}
		else if (this.suit.equals("club"))
		{
			suitIcon = "♣";
		}
		else if (this.suit.equals("diamond"))
		{
			suitIcon = "♦";
		}
		else if (this.suit.equals("spade"))
		{
			suitIcon = "♠";
		}
		//Set the face string to the symbol + the letter/number of the value
		this.face = suitIcon+valLetter+"     ";
    }

    /****************************
	****Methods of class Deck****
	****************************/

	//Get the value of the card
    int getValue(){
		return this.value;
	}

	//Get the suit of the card
	String getSuit(){		
		return this.suit;
	}

	//Get the card face
	String showFace(){
		return this.face;
	}
}
