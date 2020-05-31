package crazy8;
import java.util.*;

public class AI extends Player{
	//AI is a child of Player, and has everything that Player has.


	/***************************************
	*****Instance Variables of class AI*****
	***************************************/
	private Random r = new Random();//Random object, used when the AI taunts.

	/****************************
	***Constructors of class AI**
	****************************/
	//AI needs a name just like player, so we call the Card constructor with the provied id
    AI(String id){    	
    	super(id);
    }

    /****************************
	*****Methods of class AI*****
	****************************/

	//The AIs can taunt and make bad CS jokes.
    String taunt(){
    	//Big array of taunts and jokes and stuff
    	String[] taunts = {"You're going down!", "What do you think you're doing?", "I'm gonna win!", "You guys stink.", "Woohoo!", "First try, idiot!", "IM THE BEST!", "I put the \'crazy\' in \'crazy 8s\'", "It doesn't matter if I win or lose, I can just modify unprotected state variables because encapsulation isn't important.", "No fair! You're hacking!", "Who needs god mode when you have my skills?", "I'm so good, I didn't even know it was possible to lose.", "lol zeeshan was here", "This game sucks, the people who made it clearly can't code.", "Oh no!", "Y'all are terrible", "This is stupid. Can I leave?", "This game is driving me crazy!", "Why am I in this game?", "Hey, programmer! Give me some good cards!", "I'm sorry, which one of us is the hunk of brainless metal again?", "Waluigi's Taco Stand was a better game.", "I miss Jackblack's Blackjack, at least it was glitchy.", "Hangman 2 Classic Comeback had better memes.", "Did you know there was no code for when you lose to the final boss in ICS Sim 2017, I only found out a month ago.", "Anybody know the answers for that Mario quiz?", "Wait, I thought I was playing Connect 4.", "Go fish!", "Scrawlpad is a horrible text editor", "Icon created with Iconify, © 2018 the404.ml", "Uno!", "Hey programmer! Send him off!", "You guys are so bad, the Java garbage collector is gonna come for you after this game.", "Y'all look like you've got an empty void inside you: you can't return a win.", "while (e_coyote)", "class Brick implements Throwable { }", "Why did the functions stop calling each other? Because they had constant arguments.", "The problem with C++ is that all of your friends can see your private parts.", "How many programmers does it take to change a light bulb? None, that's a hardware problem.", "A lawyer tried to bribe the judge and got thrown an IllegalArgumentException();", "Do you ever wonder if Mr. Krnic needs a cup of Java in the morning?", "ASCII stupid question, get a stupid ANSI.", "Did you actually just play that card?", "You know you need to think in order to win, right?"};
    	int x = r.nextInt(taunts.length);//Get a random number
    	return taunts[x];//Get the taunt corresponding to that number
    }
}
