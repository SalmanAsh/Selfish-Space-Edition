package selfish.deck;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import selfish.GameException;
/**
 * An abstract class that implements serialization and handles the deck logic
 * @author Salman Ashraf
 * @version 1
 */
public abstract class Deck implements Serializable{
    private Collection<Card> cards;
    private static final long serialVersionUID = 1L;
    /**
     * Constructor of class Deck
     */
    protected Deck(){
        this.cards = new ArrayList<>();
    }
    /**
     * Method that creates a List of cards from a givem path.
     * @param path The location of a file.
     * @return The list of all cards with exact quantity. 
     * @throws GameException To check if anything goes wrong with file handing.
     */
    protected static List<Card> loadCards(String path) throws GameException{
        try {
            List<Card> allCards = new ArrayList<Card>();
            File cardsPath = new File(path);
            Scanner cardsFile = new Scanner(cardsPath);
            cardsFile.nextLine();
            while (cardsFile.hasNextLine()) {
                String line = cardsFile.nextLine();
                Card[] sameCards = stringToCards(line);
                for (Card sameCard: sameCards){
                    allCards.add(sameCard);
                }
            }
        cardsFile.close();
        return allCards;
        } catch (IOException e) {
            throw new GameException("File does not exist.", e);
        }
    }
    /**
     * Method that creates an array of card of same type.
     * @param str The line containing name, description and quantity.
     * @return  An array of Card objects.
     */
    protected static Card[] stringToCards(String str){
        String[] line = str.split(";");
        String name = line[0];
        String description = line[1];
        description = description.trim();
        String value = line[2];
        value = value.trim();
        int quantity = Integer.parseInt(value);
        if (quantity == 0){
            Card[] sameCards = new Card[0];
            return sameCards;
        }
        Card[] sameCards = new Card[quantity];
        for (int i=0; i<quantity; i++){
            sameCards[i] = new Card(name, description);
        }
        return sameCards;
    }
    /**
     * Method to shuffle the deck.
     * @param random Random value used to generate collection.
     */
    public void shuffle(Random random){
        Collections.shuffle((List<Card>) this.cards, random);
    }
    /**
     * Method that adds a card in a deck.
     * @param card Single card
     * @return quantity of total cards.
     */
    public int add(Card card){
        int count = 0;
        this.cards.add(card);
        count+=this.cards.size();
        return count;
    }
    /**
     * Method that add a list to the deck
     * @param cards The list of cards to be added.
     * @return Size of the deck.
     */
    protected int add(List<Card> cards){
        int count = 0;
        for (Card card: cards){
            this.cards.add(card);
        }
        count += this.cards.size();
        return count;
    }
    /**
     * Method to draw a card from the deck.
     * @return card drawn from top of the deck.
     * @throws IllegalStateException for any illegal draws.
     */
    public Card draw() throws IllegalStateException{
        Card card = null;
        if (this.cards.size()!=0){
            card = ((ArrayList<Card>) this.cards).get(this.cards.size()-1);
            ((ArrayList<Card>) this.cards).remove(this.cards.size()-1);
            return card;
        }
        throw new IllegalStateException("Deck is empty.");
    }
    /**
     * Method to remove the card of the deck.
     * @param card Removed card.
     */
    public void remove(Card card){
        if(this.cards.contains(card)){
            this.cards.remove(card);
        }
    }
    /**
     * Method to get the size of the deck.
     * @return The size as an int.
     */
    public int size(){
        return this.cards.size();
    }
    /**
     * Method that return the cards attribute
     * @return The card collection.
     */
    public Collection<Card> getCards(){
        return this.cards;
    }
    /**
     * Method to update the deck.
     * @param newCards The new deck.
     */
    public void updateCards(Collection<Card> newCards){
        this.cards = newCards;
    }
    

}
