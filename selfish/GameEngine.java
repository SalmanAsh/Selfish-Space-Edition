package selfish;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import selfish.deck.Card;
import selfish.deck.Deck;
import selfish.deck.GameDeck;
import selfish.deck.Oxygen;
import selfish.deck.SpaceDeck;
/**
 * A class handles the main game logic and imlements serializations.
 * @author Salman Ashraf
 * @version 25
 */
public class GameEngine implements Serializable{
    private Collection<Astronaut> activePlayers;
    private List<Astronaut> corpses;
    private Astronaut currentPlayer;
    private boolean hasStarted;
    private Random random;
    private GameDeck gameDeck;
    private GameDeck gameDiscard;
    private SpaceDeck spaceDeck;
    private SpaceDeck spaceDiscard;
    private static final long serialVersionUID = 1L;
    /**
     * Empty Constructor for GameEngine
     */
    private GameEngine(){};
    /**
     * Constructor for class GameEngine
     * @param seed Random number used to shuffle.
     * @param gameDeck Action cards deck.
     * @param spaceDeck Space cards deck.
     * @throws GameException to manage file hanging.
     */
    public GameEngine(long seed, String gameDeck, String spaceDeck) throws GameException{
        this.random = new Random(seed);
        this.gameDeck = new GameDeck(gameDeck);
        this.gameDeck.shuffle(this.random);
        this.gameDiscard = new GameDeck();
        this.spaceDeck = new SpaceDeck(spaceDeck);
        this.spaceDeck.shuffle(this.random);
        this.spaceDiscard = new SpaceDeck();
        this.activePlayers = new ArrayList<>();
        this.corpses = new ArrayList<>();
        this.currentPlayer = null;
    }
    /**
     * Getter for game deck
     * @return Deck of action cards.
     */
    public GameDeck getGameDeck(){
        return this.gameDeck;
    }
    /**
     * Getter for discarded game cards.
     * @return Discarded action cards.
     */
    public GameDeck getGameDiscard(){
        return this.gameDiscard;
    }
    /**
     * Getter for space deck.
     * @return Space card deck.
     */
    public SpaceDeck getSpaceDeck(){
        return this.spaceDeck;
    }
    /**
     * Getter for discarded space cards.
     * @return Discarded space cards.
     */
    public SpaceDeck getSpaceDiscard(){
        return this.spaceDiscard;
    }
    /**
     * Method to add cards to GameDiscard.
     * @param card to be added.
     */
    public void discardCard(Card card){
        this.gameDiscard.add(card);
    }
    /**
     * Method to load the state of the game throught serialization.
     * @param path Th file where the state stream is stored.
     * @return The object GameEngine of current state.
     * @throws GameException To check for file handing.
     */
    public static GameEngine loadState(String path) throws GameException{
        GameEngine game;
        try (
            FileInputStream gameFile = new FileInputStream(path);
            ObjectInputStream gameStream = new ObjectInputStream(gameFile);){
            game = (GameEngine) gameStream.readObject();
            gameStream.close();
            gameFile.close();
            return game;
        } catch (IOException e) {
            throw new GameException("There was a problem with the file.", e);
        }catch (ClassNotFoundException e){
            throw new GameException("Unknown Class", e);
        }
    }
    /**
     * Method to save current state of the GameEngine in a file through serialization.
     * @param path The file where the state will be saved.
     * @throws GameException To check for file handling.
     */
    public void saveState(String path) throws GameException{
        try {
            FileOutputStream gameFile = new FileOutputStream(path);
            ObjectOutputStream gameStream = new ObjectOutputStream(gameFile);
            gameStream.writeObject(this);
            gameStream.close();
            gameFile.close();
        } catch (IOException e) {
            String msg = "File Error!";
            throw new GameException(msg, e);
        }
    }
    /**
     * Method to add players to the game.
     * @param player Name of the player.
     * @return The number of the players in the game.
     * @throws IllegalStateException for any illegal moves.
     */
    public int addPlayer(String player) throws IllegalStateException{
        if(!(this.activePlayers.size() >= 5) && !this.hasStarted){
            int count;
            Astronaut p = new Astronaut(player, this);
            this.activePlayers.add(p);
            count = this.activePlayers.size();
            return count;
        }
        throw new IllegalStateException("Can't add player.");
    }
    /**`                                                                        
     * Getter for all players in the game at the start.
     * @return The list of all players who were playing when the game started.
     */
    public List<Astronaut> getAllPlayers(){
        List<Astronaut> allPlayersTemp = new ArrayList<>();
        List<Astronaut> allPlayers = new ArrayList<>();
        for(Astronaut p: this.activePlayers){
            allPlayers.add(p);
        }
        for (Astronaut c: this.corpses){
            allPlayers.add(c);
        }
        if (this.currentPlayer != null){
            allPlayers.add(this.currentPlayer);
        }
        if(this.currentPlayer == null){
            allPlayersTemp.addAll(this.corpses);
        }
        for (Astronaut p:allPlayers){
            if(!allPlayersTemp.contains(p)){
                allPlayersTemp.add(p);
            }
        }
        return allPlayersTemp;
    }
    /**
     * Getter for the full player count.
     * @return The total number of player that player the curren game.
     */
    public int getFullPlayerCount(){
        List<Astronaut> allPlayers = new ArrayList<>();
        List<Astronaut> allPlayersTemp = new ArrayList<>();
        allPlayers = getAllPlayers();
        for (Astronaut p: allPlayers){
            if(!allPlayersTemp.contains(p)){
                allPlayersTemp.add(p);
            }
        }
        return allPlayersTemp.size();
    }
    /**
     * Method that sets up a new game.
     * @throws IllegalStateException for any illegal moves.
     */
    public void startGame() throws IllegalStateException{
        if (this.activePlayers.size()>5 || getFullPlayerCount()<2 || this.hasStarted==true){
            throw new IllegalStateException("Cannot start game!");
        }
        for (Astronaut player: this.activePlayers){
            for (int i=0; i<1;i++){
                Oxygen dblOxigen = this.gameDeck.drawOxygen(2);
                player.addToHand(dblOxigen);
            }
        }
        for (Astronaut player: this.activePlayers){
            for (int i=0; i<4; i++){
                Oxygen sngOxygen = this.gameDeck.drawOxygen(1);
                player.addToHand(sngOxygen);
            }
        }
        for (int i=0; i<4; i++){
            for(Astronaut player: this.activePlayers){
                Card card = this.gameDeck.draw();
                player.addToHand(card);
            }
        }
        this.hasStarted = true;
        
    }
    /**
     * Method that updates the currentPlayer and activePlayer.
     * @return Players in queue.
     */
    public int endTurn(){
        if (this.currentPlayer.oxygenRemaining()>0){
            this.activePlayers.add(this.currentPlayer);
            this.currentPlayer = null;
        }else{
            this.corpses.add(this.currentPlayer);
            this.currentPlayer = null;
        }
        return this.activePlayers.size();
    }
    /**
     * Method to check if the game has ended.
     * @return The boolean value accodingly.
     */
    public boolean gameOver(){
        boolean over = false;
        if(this.activePlayers.size()==0 && getWinner()==null){
            over = true;
        }
        if(getWinner() != null){
            over = true;
        }
        return over;
    }
    /**
     * Method to get the player taking the current turn.
     * @return The Astonaut taking the turn.
     */
    public Astronaut getCurrentPlayer(){
        return this.currentPlayer;
    }
    /**
     * Method to find who reached the ship.
     * @return The winner Astronaut.
     */
    public Astronaut getWinner(){
        for (Astronaut player: this.activePlayers){
            if (player.distanceFromShip()==0){
                return player;
            }
        }
        return null;
    }
    /**
     * Method to declare a player dead. 
     * @param corpse The list of dead Astronauts.
     */
    public void killPlayer(Astronaut corpse){
        if(this.activePlayers.contains(corpse)){
            this.activePlayers.remove(corpse);
        }
        this.corpses.add(corpse);
        List<Card> cardLeft = new ArrayList<>();
        cardLeft.addAll(corpse.getActions());
        for (Card c: cardLeft){
            corpse.hack(c);
            discardCard(c);
        }
    }
    /**
     * Method to merge game and space deck.
     * @param deck1 Deck number one.
     * @param deck2 Deck number two.
     */
    public void mergeDecks(Deck deck1, Deck deck2){
        while (deck2.size() != 0){
            deck1.add(deck2.draw());
        }
    }
    /**
     * Method that start the turn of the player in active players
     * @throws IllegalStateException if any illegal move.
     */
    public void startTurn() throws IllegalStateException{
        if(this.hasStarted && this.currentPlayer == null && (getWinner() == null) && this.activePlayers.size() !=0){
            this.currentPlayer = ((List<Astronaut>) this.activePlayers).get(0);
            ((List<Astronaut>) this.activePlayers).remove(0);
        }
        else{
            throw new IllegalStateException("Cannot start turn.");
        }
    }
    /**
     * Method to split the double oxygen into two.
     * @param dbl The double oxygen. 
     * @return List of Oxygens.
     * @throws IllegalStateException For oxygens not found.
     * @throws IllegalArgumentException for illegal input value.
     */
    public Oxygen[] splitOxygen(Oxygen dbl) throws IllegalStateException, IllegalArgumentException{
        if (dbl.getValue() == 1){
            throw new IllegalArgumentException("Can't split Oxygen(1)");
        }
        Oxygen[] theSplitedOxygens = new Oxygen[2];
        Oxygen oneOxy = null;
        Oxygen twoOxy = null;
        Collection<Card> gamecards = this.gameDeck.getCards();
        Collection<Card> cardPile = this.gameDiscard.getCards();    
        int foundDeck = 0;
        int foundPile = 0;
        for(Card card: cardPile){
            if(card instanceof Oxygen){
                if(((Oxygen) card).getValue()==1){
                    foundPile += 1;
                }
            }
            if(foundPile==2){
                theSplitedOxygens = this.gameDiscard.splitOxygen(dbl);
                return theSplitedOxygens;
            }
        }
        for(Card card: gamecards){
            if(card instanceof Oxygen){
                if(((Oxygen) card).getValue()==1){
                    foundDeck += 1;
                }
            }
            if (foundDeck ==2){
                theSplitedOxygens = this.gameDeck.splitOxygen(dbl);
                return theSplitedOxygens;
            }
        }
        if(foundDeck==1 && foundPile==1){
            mergeDecks(this.gameDeck, this.gameDiscard);
            oneOxy = this.gameDeck.drawOxygen(1);
            twoOxy = this.gameDeck.drawOxygen(1);
            this.gameDeck.remove(oneOxy);
            this.gameDeck.remove(twoOxy);
            this.gameDeck.add(dbl);
            return theSplitedOxygens;
        }
        if(this.gameDiscard.size()==0 && foundDeck==2){
            theSplitedOxygens = this.gameDeck.splitOxygen(dbl);
            return theSplitedOxygens;
        }
        throw new IllegalStateException("Cannot Split Oxygen");
    }
    /**
     * 
     * Method to make an Astonaut move in the space.
     * @param traveller The moving Astronaut.
     * @return The card used to move the Astronaut.
     * @throws IllegalStateException if no oxygens left.
     */
    public Card travel(Astronaut traveller) throws IllegalStateException{
        if (traveller.oxygenRemaining()>1 ){
            Card drawnCard = this.spaceDeck.draw();
            if(!drawnCard.toString().equals("Gravitational anomaly")){
                traveller.addToTrack(drawnCard);
            }
            traveller.breathe();
            traveller.breathe();
            return drawnCard;
        }
        throw new IllegalStateException("No more oxygens left");
    }


}