
package selfish.deck;
/**
 * This class represents Oxygen card, which comes from Deck.
 * Its inheriting fom the Deck class.
 * @author Salman Ashraf
 * @version 1
 */
public class Oxygen extends Card implements Comparable<Card>{
    /**
     * The quantity of the card type.
     */
    private int value;
    /**
     * The serial number.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for the Oxygen class.
     * @param value The value of the Oxygen card.
     */
    public Oxygen(int value){
        super("Oxygen", "Use need this to breathe and travel. If you don't breathe you die.");
        this.value = value;
    }

    /**
     * Getter method for the value of the card.
     * @return The value of the card.
     */
    public int getValue(){
        return value;
    }
    /**Getter method for the name and value of the card.
     * @return The name and the value.
     */
    public String toString(){
        return "Oxygen" + "(" + value +")";
    }

    @Override
    /**
     * Interface method to compare the cards.
     * @param card The card to be compared To.
     * @return The interger value of the result.
     */
    public int compareTo(Card card) {
        if(card instanceof Oxygen){
            card = (Oxygen) card;
            int cardValue = ((Oxygen) card).getValue();
            return Integer.compare(this.value, cardValue);
        }
        return super.compareTo(card);   
    }
}
