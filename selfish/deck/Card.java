package selfish.deck;

import java.io.Serializable;
/**
 *A class that implements serialization and handles the Card logic.
 * @author Salman Ashraf
 * @version 1
 */
public class Card implements Serializable, Comparable<Card>{
    private String name;
    private String description;
    private static final long serialVersionUID = 1L;

    /**
     * Construtor for the Card class.
     * @param name The name of the card.
     * @param description The description of the card.
     */
    public Card(String name, String description){
        this.name = name;
        this.description = description;
    }

    /**
     * Getter method for the description of the card.
     * @return The Description of the card.
     */
    public String getDescription(){
        return this.description;
    }

    /**Getter method for the name of the card.
     * @return The card name.
     */
    public String toString(){
        return this.name;
    }
    @Override
    /**
     * Interface method to compare the cards.
     * @param card The card to be compared To.
     * @return The value of the result.
     */
    public int compareTo(Card card) {
        return this.name.compareTo(card.name);
    
    }
}
