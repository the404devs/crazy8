package crazy8;
import crazy8.*;

public class Pile extends Deck {
	//Pile is a child of Deck, and a grandchild of ArrayList.
	//Pile needs some special stuff Deck doesn't in order to work for us

	private static final long serialVersionUID = 12345678910L;
	//This thing ain't that imporant, its just that without this i get some stupid warning every time i try to build this class

	/***************************************
	****Instance Variables of class Pile****
	***************************************/	
	private Card topCard = null;//The card at the top of the pile

	/****************************
	**Constructors of class Pile*
	****************************/
	Pile(){
		super();//Initialize all iherited stuff from class Deck
		this.clear();//Empty out all the cards, because Deck starts with 52 but we want the pile to be empty
	}

	/****************************
	****Methods of class Deck****
	****************************/

	//Get the card at the top of the pile
	Card getTopCard(){
		return this.getCard(0);
	}

	//Add a card to a specific spot
	//This overrides the addCard of Deck
	void addCard(int index, Card next){
		this.add(index, next);
	}
}
