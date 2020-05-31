package crazy8;
import crazy8.*;

public class Player{
	//Player is used to represent the player (duh)
	//It has a name, and a deck that is it's Hand (collection of cards)

	/***************************************
	***Instance Variables of class Player***
	***************************************/

	private String name = null;//Name of the player
	public Deck hand = new Deck();//Deck, representing the player's hand

	/***************************************
	******Constructors of class Player******
	***************************************/

	Player(String id){
		this.name = id;
		this.hand.clear();//By default, deck is initialized with all 52 cards. Player's hands start empty, so clear out this copy of deck
	}

	/***************************************
	*********Method of class Player*********
	***************************************/

	//To reveal all of the cards in a player's hand
	void showHand(){
		for (int x = 0; x< this.hand.size(); x++) {//Loop through each card
			System.out.println(x+1 +": "+this.hand.getCard(x).showFace());//call showFace on each card
		}
	}

	//To return the Player's name
	String showName(){
		return this.name;
	}

	//To sort the players cards by value
	void sortByValue(){
		//Good old bubble sorting
		for (int x = 1; x < this.hand.size()-1; x++)
		{
			for (int y = 0; y < this.hand.size()-x; y++)
			{
				Card card1 = this.hand.getCard(y);
				Card card2 = this.hand.getCard(y+1);
				if (card1.getValue() > card2.getValue())
				{
					Card temp = this.hand.getCard(y);
					this.hand.set(y,this.hand.getCard(y+1));
					this.hand.set(y+1,temp);
				}
			}
		}
	}

	//To sort the player's cards by suit
	void sortBySuit(){
		//Also good old bubble sorting
		for (int x = 1; x < this.hand.size()-1; x++)
		{
			for (int y = 0; y < this.hand.size()-x; y++)
			{
				Card card1 = this.hand.getCard(y);
				Card card2 = this.hand.getCard(y+1);
				if (card1.getSuit().compareTo(card2.getSuit()) < 0)
				{
					Card temp = this.hand.getCard(y);
					this.hand.set(y,this.hand.getCard(y+1));
					this.hand.set(y+1,temp);
				}
			}
		}
	}

	//To add a card to player's hand
	void drawCard(Card next){
		this.hand.addCard(next);//Add whatever card it is to the hand
	}
}
