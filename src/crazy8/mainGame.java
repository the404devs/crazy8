/*Crazy 8s in Java
By Owen, Kevin and Jatin
May 29th, 2018
Using OOP to create a card game. oops.*/

package crazy8;
import crazy8.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

public class mainGame {
	private static final Deck d = new Deck();//The game's deck. Players draw from here.
    private static final Pile p = new Pile();//The game's pile. Players put cards here.
	private static Scanner scan = new Scanner(System.in);//Scanner for user input
    private static int players = 0;//Number of human players in current game, used to determine how many AI to make
    private static Player p1 = null;//Objects for each of the 4 players.
    private static Player p2 = null;
    private static Player p3 = null;
    private static Player p4 = null;    
    private static int pickUp = 0;//Used to determine how many cards the next player should pick up
    private static Random r = new Random();//Random object, for some random reason                           ha ha ha
    private static Boolean gameOver = false;//Flag for whether or not the game is done
    private static Boolean skipTurn = false;//Flag for whether or not the next player misses their turn.
    private static Boolean wildPlayed = false;//Flag for whether or not somebody played a wild.
    private static String wildSuit = null;//Used to store the new suit after a wild is played.
    private static Clip clip = null;//To hold whatever audio file we use

    /*When the deck is empty, we take all of the cards in the pile
    except the top one, and put them back in the deck, then shuffle them.
    This function needs a boolean so it knows if the deck is empty*/
    private static void returnPileToDeck(Boolean empty){
        if (empty==true){//If it's empty
            for (int x = p.size()-1; x>=1; x--) {//Loop through each card in pile, except top one
                d.addCard(p.play(x));//Add it to deck
            }
            d.shuffle();//shuffle new deck
        }
    }

    /*Used to take user input and turn it into an int.
    All of our menu options are numbered, so it makes sense to do this.*/
    private static int userInputInt(){
        int x = 0;//Make an int
        try{
            x = Integer.parseInt(scan.nextLine());  //Turn user input into int          
        }
        catch(NumberFormatException e){
            System.out.println("Whoops, that wasn't right!");//If it can't be turned into int, error message
            delay();//delay for readability
        }
        return x;//return int-ified input
    }

    /*One call on this function is an entire turn.
    Needs a player object of whoever is going*/
	private static void turn(Player currentPlayer){
        //First, make the player pick up the required amount of cards.
        //It's usually 0, so the loop doesn't fire then.
        for (int x = pickUp; x>0; x--) {
            returnPileToDeck(d.checkEmpty());//Before we draw from the deck, make sure it's not empty and if it is reshuffle the pile
            currentPlayer.hand.addCard(d.deal());//Add one card from deck to player's hand
            System.out.println("Added one card to "+currentPlayer.showName()+"'s hand.");//message so they know what's going on
            delay();//delays are back baby
        }
        //All of the code for playing a card is in this if, so if skipturn is true it won't fire.
        if (!skipTurn) {
            /*The instanceof operator is pretty neat.
            It allows you to check if an object is an instance of a certain class.
            We use it here to determine if the current player is a normal human, or a computer-controlled player.
            The AI class is a child of the Player class, so both types of players are instances of Player, 
            but only the computer-controlled players would be instances of AI. Inheritance at its finest.*/
            if(currentPlayer instanceof AI){//If it's an AI's turn...
                delay();
                clearScreen();//Wipe the screen
                int topValue = p.getTopCard().getValue();//Get the value of the top card
                String topSuit = null;//Object for top card suit
                if (wildPlayed){//If a wild was played last turn, use the suit that was specified and not the topCards suit
                    topSuit = wildSuit;//Use the suit the player changed it to
                    System.out.println("Suit has been changed to: " + topSuit.substring(0, 1).toUpperCase() + topSuit.substring(1) + "s.");//Message saying suit was changed
                    System.out.println("");
                }
                else{
                    topSuit = p.getTopCard().getSuit();//Otherwise just get the suit from the top card
                }
                Boolean cantGo = true;//Used to make the AI pick up cards until they go.
                //Once the AI plays a card it gets set to false. If it plays a card in its niitial hand, it won't do the second phase of this bit.        
                System.out.println(currentPlayer.showName() + "'s turn!");//Messages to tell the useer who's going
                System.out.println(currentPlayer.showName() + " has " + currentPlayer.hand.numberOfCards() + " cards left.");//How many cards the player has left
                System.out.println("Top card is: " + p.getTopCard().showFace());//What's the top card?
                System.out.println(d.numberOfCards()+" cards left in deck.");//How many cards left in deck and pile.
                System.out.println(p.numberOfCards()+" cards left in pile.");
                System.out.println("===========================================================================");
                for (int x = 0; x<currentPlayer.hand.numberOfCards(); x++) {//Loop through each card in AI's hand and try to play it.
                    Card played = currentPlayer.hand.getCard(x);//Get card info
                    if(played.getValue()==topValue||played.getSuit().equals(topSuit)||played.getValue()==8){//Check if AI can play it.
                        p.addCard(0,currentPlayer.hand.play(x));//If we can play it, add it to the top of the pile, remove it form their hand
                        if(played.getValue()==2){//If it's a 2
                            delay();
                            System.out.println(currentPlayer.showName()+" played "+played.showFace());//Say what they played
                            System.out.println("Next player picks up 2 cards!");//Tell user next player picks up 2
                            pickUp = 2;//Set pickUp counter to 2
                            delay();//delay yay
                            wildPlayed = false;//Wasn't a wild
                        }
                        else if(played.getValue()==12){//If it's a 12/queen
                            delay();//delays
                            System.out.println(currentPlayer.showName()+" played "+played.showFace());//what card was that?
                            skipTurn = true;//Oh no, next person is gonna be skipped!
                            System.out.println("Next player misses their turn!");//message                          
                            delay();//so many delays, i'll never get to work on time
                            wildPlayed = false;//wasn't a wild.
                            pickUp = 0;//no cards to pick up
                        }
                        else if(played.getValue()==8){//If it's an 8
                            delay();
                            wildPlayed = true;//oh my gosh its a wild tell everybody
                            pickUp = 0;//no cards to pick up 
                            System.out.println(currentPlayer.showName()+" played "+played.showFace());//what card?
                            int suitNum = r.nextInt(4)+1;//AI picks a random suit here by generating random number
                            if (suitNum==1) {
                                wildSuit = "spade";
                            }
                            else if (suitNum==2) {
                                wildSuit = "heart";
                            }
                            else if (suitNum==3) {
                                wildSuit = "club";
                            }
                            else if (suitNum==4) {
                                wildSuit = "diamond";
                            }
                            delay();
                        }
                        else{//Else its a normal card
                            delay();
                            System.out.println(currentPlayer.showName()+" played "+played.showFace());//say what card
                            pickUp = 0;//none to pick up
                            delay();
                            wildPlayed = false;//not a wild
                        }

                        if(r.nextBoolean()){//50/50 chance this will fire.
                            AI temp = (AI)currentPlayer;//CAST currentPlayer to an AI object. Since this only fires when currentPlayer is an AI, its safe to do this
                            System.out.println(currentPlayer.showName()+" says, \"" + temp.taunt()+"\"");//AI says a bad joke
                            delay();
                            temp = null;//Remove reference to the object we made so the garbage man will take it's body away once we give him hush money
                            //what
                        }

                        if (currentPlayer.hand.checkEmpty()){//If the player's hand is empty
                            System.out.println(currentPlayer.showName()+" wins!");//Woohoo they won!
                            delay();
                            gameOver = true;//game is over, this will get us out of the game loop
                        }
                        cantGo = false;//AI played a card, no need for it to draw.
                        break;//Break out of the for loop if they played a card
                    }
                }

                while(cantGo){//Loop will go until the AI gets a card it can play
                    delay();
                    returnPileToDeck(d.checkEmpty());//Check if deck is empty
                    currentPlayer.hand.addCard(d.deal());//AI picks up a card.
                    System.out.println(currentPlayer.showName()+" couldn't go, and picked up 1 card.");//Say the AI couldn't go
                    //From here down to the line it's pretty much the same as above and the documentation up there is so much funnier so go read that.
                    //Basically AI looks at the card it just picks up and tries to play it.
                    //If it can play it we get out of this loop, and if it can't we loop again until it does.
                    Card played = currentPlayer.hand.getCard(currentPlayer.hand.numberOfCards()-1);
                    if(played.getValue()==p.getTopCard().getValue()||played.getSuit().equals(p.getTopCard().getSuit())||played.getValue()==8){
                        p.addCard(0,currentPlayer.hand.play(currentPlayer.hand.numberOfCards()-1));//actually take that card from their hand now and make it the new top card of the pile
                        if(played.getValue()==2){
                            delay();
                            System.out.println(currentPlayer.showName()+" played "+played.showFace());
                            System.out.println("Next player picks up 2 cards!");
                            pickUp = 2;
                            delay();
                            wildPlayed = false;
                        }
                        else if(played.getValue()==12){
                            delay();
                            System.out.println(currentPlayer.showName()+" played "+played.showFace());
                            skipTurn = true;
                            System.out.println("Next player misses their turn!");                           
                            delay();
                            pickUp = 0;
                            wildPlayed = false;
                        }
                        else if(played.getValue()==8){
                            delay();
                            pickUp = 0;
                            wildPlayed = true;
                            System.out.println(currentPlayer.showName()+" played "+played.showFace());
                            int suitNum = r.nextInt(4)+1;
                            if (suitNum==1) {
                                wildSuit = "spade";
                            }
                            else if (suitNum==2) {
                                wildSuit = "heart";
                            }
                            else if (suitNum==3) {
                                wildSuit = "club";
                            }
                            else if (suitNum==4) {
                                wildSuit = "diamond";
                            }
                            delay();
                        }
                        else{
                            delay();
                            System.out.println(currentPlayer.showName()+" played "+played.showFace());
                            pickUp = 0;
                            delay();
                            wildPlayed = false;
                        }

                        if(r.nextBoolean()){
                            AI temp = (AI)currentPlayer;
                            System.out.println(currentPlayer.showName()+" says, \"" + temp.taunt()+"\"");
                            delay();
                            temp = null;
                        }

                        if (currentPlayer.hand.checkEmpty()){
                            System.out.println(currentPlayer.showName()+" wins!");
                            delay();
                            gameOver = true;
                        }
                        cantGo = false;
                    }
                }            
            } 
            /**********************************************************************************************************************/       
            else{//If it's an actual human's turn
                while(1==1){//inf loop showing player their options               
                    clearScreen();//bye text
                    int topValue = p.getTopCard().getValue();//get the top card's value
                    String topSuit = null;//Object we use in a moment
                    if (wildPlayed){//If the last card was a wild
                        topSuit = wildSuit;//Use the suit the last player said was the new one
                        System.out.println("Suit has been changed to: " + topSuit.substring(0, 1).toUpperCase() + topSuit.substring(1) + "s.");//message
                        System.out.println("");                        
                    }
                    else{
                        topSuit = p.getTopCard().getSuit();//else just get the suit like normal
                    }

                    /*Say who's turn it is 
                    how many cards they have
                    what the top card is
                    how many cards left in deck and pile
                    show the players options*/
                    System.out.println(currentPlayer.showName() + "'s turn!");
                    System.out.println(currentPlayer.showName() + " has " + currentPlayer.hand.numberOfCards() + " cards left.");
                    System.out.println("Top card is: " + p.getTopCard().showFace());
                    System.out.println(d.numberOfCards()+" cards left in deck.");
                    System.out.println(p.numberOfCards()+" cards left in pile.");
                    System.out.println("===========================================================================");
                    System.out.println("1. Show Hand");
                    System.out.println("2. Play Card");
                    System.out.println("3. Draw Card");
                    System.out.println("4. Order Cards by Value");
                    System.out.println("5. Order Cards by Suit");

                    int selection = userInputInt();//Get their input

                    if (selection==1) {//If they want to see their cards
                        clearScreen();
                        currentPlayer.showHand();//show it
                        System.out.println("Press enter to return.");//tell them how to get back
                        scan.nextLine();//wait for enter press
                    }
                    else if (selection==2){//If they want ot play a card
                        System.out.println("Play which card? (1-9)");//Ask which one
                        int cardNumber = userInputInt() - 1;//get the number value f the card, -1 cause arrays start at 0

                        //This is the same series of checks to determine whether a card can be played, and behavoir for special cards like 2s, 8s and Qs
                        Card played = currentPlayer.hand.getCard(cardNumber);//retrieve the info of the card they want to play
                        if(played.getValue()==topValue||played.getSuit().equals(topSuit)||played.getValue()==8){
                            p.addCard(0,currentPlayer.hand.play(cardNumber));//actually take that card from their hand now and make it the new top card of the pile
                            if(played.getValue()==2){
                                System.out.println("Next player picks up 2 cards!");
                                pickUp = 2;
                                delay();
                                wildPlayed = false;
                            }
                            else if(played.getValue()==12){
                                System.out.println(currentPlayer.showName()+" played "+played.showFace());
                                skipTurn = true;
                                System.out.println("Next player misses their turn!");                           
                                delay();
                                pickUp = 0;
                                wildPlayed = false;
                            }
                            else if(played.getValue()==8){
                                pickUp = 0;
                                wildPlayed = true;
                                System.out.println(currentPlayer.showName()+" played "+played.showFace());
                                System.out.println("Select the new suit.");
                                System.out.println("1. Spades");
                                System.out.println("2. Hearts");
                                System.out.println("3. Clubs");
                                System.out.println("4. Diamonds");
                                int suitNum = userInputInt();
                                if (suitNum==1) {
                                    wildSuit = "spade";
                                }
                                else if (suitNum==2) {
                                    wildSuit = "heart";
                                }
                                else if (suitNum==3) {
                                    wildSuit = "club";
                                }
                                else if (suitNum==4) {
                                    wildSuit = "diamond";
                                }
                                delay();
                            }
                            else{
                                System.out.println(currentPlayer.showName()+" played "+played.showFace());
                                pickUp = 0;
                                wildPlayed = false;
                                delay();
                            }

                            if (currentPlayer.hand.checkEmpty()){
                                System.out.println("");
                                System.out.println(currentPlayer.showName()+" wins!");
                                delay();
                                gameOver = true;
                            }
                            break;
                        }
                        else{//Else if the card cant be played
                            System.out.println("You can't play that card!");//tell em off
                            delay();
                        }
                    }
                    else if(selection==3){//If they need to draw a card
                        returnPileToDeck(d.checkEmpty());//Make sure deck has cards
                        currentPlayer.hand.addCard(d.deal());//Gvie em a card
                        System.out.println("Added one card to "+currentPlayer.showName()+"'s hand."); //Say they got a card
                        delay();               
                    }
                    else if(selection==4){//If they want to sort by value
                        currentPlayer.sortByValue();//We sort by value
                        System.out.println("Sorted cards by value.");
                        delay();
                    }
                    else if(selection==5){//If they want to sort by suit
                        currentPlayer.sortBySuit();//We suit by sort
                        System.out.println("Sorted cards by suit.");
                        delay();
                    }
                }
                
            } 
        }
        else{//Else if skipTurn was true, somebody missed their turn
            System.out.println(currentPlayer.showName() + " missed their turn!");//so say who
            System.out.println("");
            skipTurn = false;//no more skips
            pickUp = 0;//no cards to pick up
        }       
    }

    //Good old clearScreen
    private static void clearScreen(){
        /*This function will clear the console window.
        Only works for legit consoles, like CMD. Doesn't work when running on Sublime or Eclipse.*/
        try {
            if (System.getProperty("os.name").contains("Windows"))//Simple way to check if it's running on a Windows machine, which would mean CMD.
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();//This sends a "cls" command to CMD, which clears the screen.
            else
                Runtime.getRuntime().exec("clear");//If it's not on Windows, try clearing it a different way.
        } catch (IOException | InterruptedException ex) {
            //Can't clear the screen. 
            System.out.println("oh no the program broke. fix the clearScreen function");
        }
    }

    //Good old logoDraw
    private static void logoDraw() {
        /************************
        ****FILE IO USED HERE****
        ************************/
        /*This function is what draws the logo on the main menu*/
        clearScreen();//Blank screen to start
        try{
            FileReader fr = new FileReader("logo.txt");//Set up file reader and buffered reader, pointing to logo.txt
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();//Read the first line
            while(line!=null)//Keep going until theres nothing left in the file
            {
                System.out.println(line);//write the line
                line = br.readLine();//Get the next line
            }
        }catch(IOException e)
        {
            //Can't find the logo file, show an error
            System.out.println("oh no the program broke. fix the logoDraw function");
        }
        delay();
    }

    //If i had a bitcoin for every time this function was called i'd be rich
    private static void delay(){
        /*This tiny function is used to create a 2 second pause in the execution of the code. 
        Super useful to make it look like the computer is thinking,
        or just to, you know, delay things for a bit.*/     
        try{
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            
            System.out.println("oh no the program broke. fix the delay function");
        }
    }
    //then again if i had a bitcoin i'd be rich

    //CAN YOU KIDS TURN OF THAT DAMN RACKET?
    private static void stopMusic(){
        if (clip != null) {//If there is any track running...
            clip.stop();//stop it
            clip.close();//close it
            clip = null;//reset our clip object
        }
    }

    //I need to put music in every game I make
    private static void music(){//Yay music!
        String path = "msk/Casino-Super-Mario-64-DS-Music-Extended.wav";//String to hold the path to the file
        File file = new File(path);//Make a file object with the path we came up with
        try {
            stopMusic();//stop any music that's already playing
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);//Find our file
            clip = AudioSystem.getClip();//turn it into a clip object
            clip.open(inputStream);//open it
            clip.start();//start it
        } catch (Exception e) {
            stopMusic();//if something goes horribly wrong stop the music
        }
    }

	public static void main(String[] args) {
        music();//Crank up the tunes

        while(1==1){//Inf loop
            players = 0;//Reset the players to 0;
            p1 = null;//Set all the players to null
            p2 = null;//Now we can make new ones for the next game
            p3 = null;
            p4 = null;
            for (int x = p.numberOfCards()-1; x>=0; x--) {//Loop through every card in the pile, and move it to the deck
                d.addCard(p.play(x));//This is useful after a game has ended and the player is returned to the main menu.
            }
            d.shuffle();//Shuffle our deck. Probably a good idea, right?      
            logoDraw();//Draw the logo            

            //Messages for the user to read, formatted all nice and pretty
            System.out.println("===========================================================================");  
            System.out.println("                Welcome to Crazy Eights in the Java Console!               ");
            System.out.println("===========================================================================");
            System.out.println("                            ===================");
            System.out.println("                            = 1. Play         =");
            System.out.println("                            ===================");
            System.out.println("                            = 2. About        =");
            System.out.println("                            ===================");  
            
            int selection = userInputInt();//Get the user's input            
            clearScreen();//blank screen, now it's fun time        
          
            if(selection==1){//If they said play
                //Ask how many players
                System.out.println("                       How many players? (Max. 4)");              
                System.out.println("===========================================================================");

                players = userInputInt();//wait for an input 
                if(players>=0&&players<5){//If they said anything from 0-4
                    //Loop will make as many players as they said
                    //And it makes the object in whatever the y value is 
                    //y=1, new player 1
                    //y=2, new player 2
                    for (int y = 1; y<=players; y++) {
                        if (y==1) {
                            System.out.println("Enter name for P1");//Ask for a name for the player
                            p1 = new Player(scan.nextLine());//Make player with that name
                            for (int x = 0; x<8; x++) {//Give em 8 cards
                                returnPileToDeck(d.checkEmpty());
                                p1.drawCard(d.deal());
                            }        
                        } 
                        else if (y==2) {
                            System.out.println("Enter name for P2");//Same as above
                            p2 = new Player(scan.nextLine());
                            for (int x = 0; x<8; x++) {
                                returnPileToDeck(d.checkEmpty());
                                p2.drawCard(d.deal());
                            }        
                        }   
                        else if (y==3) {
                            System.out.println("Enter name for P3");
                            p3 = new Player(scan.nextLine());
                            for (int x = 0; x<8; x++) {
                                returnPileToDeck(d.checkEmpty());
                                p3.drawCard(d.deal());
                            }        
                        }  
                        else if (y==4) {
                            System.out.println("Enter name for P4");
                            p4 = new Player(scan.nextLine());
                            for (int x = 0; x<8; x++) {
                                returnPileToDeck(d.checkEmpty());
                                p4.drawCard(d.deal());
                            }        
                        }  
                    } 
                    //Here is an array of all the random names we give to CPUs.
                    //No, stop reading them. Keep it a surprise.
                    String[] names = {"Mario", "Luigi", "Peach", "Toad", "Waluigi", "pannenkoek2012", "Wario", "Bowser", "DK", "Cappy", "Conker", "Nathaniel \"Scuttlebug\" Bandy", "Nathaniel \"Paragoomba\" Bandy",  "TJ \"\"\"\"\"Henry\"\"\"\"\" Yoshi","Simpleflips", "BUP", "Mr. Krnic", "Techrax", "Iago", "Jackblack's Blackjack", "The guy you play as in ICS Sim 2017", "Owen", "Kevin", "Jatin", "the404.ml", "Vladimir Putin", "Z's hackerbot", "Hangman 2: Classic Comeback", "Fawful", "Vipneet", "William Shakespeare", "Victoria", "Bill Nye the Science Guy", "Spongebob", "Squidward", "Patrick", "Mr. Krabbs", "Plankton", "Roxanne", "Kevin's saxophone", "Sherlock Holmes", "Yoshi", "Baldi", "Banjo", "Kazooie", "Link", "Zelda", "Ganon", "yo mama"};
                    //Check if players 1, 2, 3 and 4 are not assigned to a human player, and assign them AI players.
                    if (p1==null){//If object is still empty
                        p1 = new AI(names[r.nextInt(names.length)]);//Pick a name, any name
                        System.out.println(p1.showName() + " is playing.");//Tell the user who theyre up against
                        for (int x = 0; x<8; x++) {//For loop, takes 8 cards from the deck and gives them to the new CPUs hand
                            returnPileToDeck(d.checkEmpty());
                            p1.drawCard(d.deal());
                        }
                    }
                    if (p2==null){//If object is still empty
                        p2 = new AI(names[r.nextInt(names.length)]);//Pick a name, any name
                        System.out.println(p2.showName() + " is playing.");//Tell the user who theyre up against
                        for (int x = 0; x<8; x++) {//For loop, takes 8 cards from the deck and gives them to the new CPUs hand
                            returnPileToDeck(d.checkEmpty());
                            p2.drawCard(d.deal());
                        }
                    }                    
                    if (p3==null){
                        p3 = new AI(names[r.nextInt(names.length)]);//Same as above
                        System.out.println(p3.showName() + " is playing.");
                        for (int x = 0; x<8; x++) {
                            returnPileToDeck(d.checkEmpty());
                            p3.drawCard(d.deal());
                        } 
                    }
                    if (p4==null){
                        p4 = new AI(names[r.nextInt(names.length)]);
                        System.out.println(p4.showName() + " is playing.");
                        for (int x = 0; x<8; x++) {
                            returnPileToDeck(d.checkEmpty());
                            p4.drawCard(d.deal());
                        } 
                    }
                    delay();

                    returnPileToDeck(d.checkEmpty());//make sure deck has cards. there's no reason it wouldn't but who knows right
                    p.addCard(d.deal());//Take one last card to start the pile
                    gameOver = false;//We're just getting started
                    
                    while(1==1){ //inf loop of turns for each player   
                        turn(p1);
                        if (gameOver==true) break; //Check if game is over after each turn
                        turn(p2);
                        if (gameOver==true) break; 
                        turn(p3); 
                        if (gameOver==true) break; 
                        turn(p4); 
                        if (gameOver==true) break;      
                    }
                }        
            }
            else if (selection==2) {//If they choose to learn about the game
                clearScreen();//blank screen
                //Display some useful info about the game
                System.out.println("Crazy 8s in the Java Console");
                System.out.println("===========================================================================");
                System.out.println("For Grade 12 Computer Science");
                System.out.println("By Owen and Kevin and Jatin");
                System.out.println("Version 1.0 (May 27, 2018)");
                System.out.println("===========================================================================");
                //Oh boy, a website?
                System.out.println("                            ===================");
                System.out.println("                            = 1. Back         =");
                System.out.println("                            ===================");
                System.out.println("                            = 2. Visit Website=");
                System.out.println("                            ===================");

                int aboutSelection = userInputInt();//Get their input
                try{
                    if(aboutSelection==2)//If they chose option 2
                    {
                        try{
                            //Try and send them to our website
                            java.awt.Desktop.getDesktop().browse(new java.net.URI("http://www.sdsscomputers.com/OwenGoodwin/ASSIGNMENTS/COMPLETED/Unit4Assignment/index.html"));
                        }catch(Exception e)
                        {
                            //If we can't send them to the website show them an error message telling the user exactly what went wrong..... sort of
                            System.out.println("ERROR: Code OOF");
                        }
                    }
                    //If they type anything other than 2 they get sent back to the menu
                }catch(NumberFormatException e)
                {
                    //They didn't enter a number. Come on guys
                    System.out.println("ERROR: Code WHAT_ARE_NUMBERS");
                }
            } 
        }              
    }       
}
