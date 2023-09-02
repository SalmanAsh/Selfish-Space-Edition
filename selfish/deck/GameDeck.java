package selfish.deck;
import java.util.ArrayList;
import java.util.List;
import selfish.GameException;
import java.util.Collection;
/**
 * A class that inherits from deck and handles action deck logic.
 * @author Salman Ashraf
 * @version 1
 */
public class GameDeck extends Deck{
    /**
     * Constant for an Action card.
     */
    public static final String HACK_SUIT = "Hack suit";
    /**
     * Constant for an Action card.
     */
    public static final String HOLE_IN_SUIT = "Hole in suit";
    /**
     * Constant for an Action card.
     */
    public static final String LASER_BLAST = "Laser blast";
    /**
     * Constant for an Action card.
     */
    public static final String OXYGEN = "Oxygen";
    /**
     * Constant for an Action card.
     */
    public static final String OXYGEN_1 = "Oxygen(1)";
    /**
     * Constant for an Action card.
     */
    public static final String OXYGEN_2 = "Oxygen(2)";
    /**
     * Constant for an Action card.
     */
    public static final String OXYGEN_SIPHON = "Oxygen siphon";
    /**
     * Constant for an Action card.
     */
    public static final String ROCKET_BOOSTER = "Rocket booster";
    /**
     * Constant for an Action card.
     */
    public static final String SHIELD = "Shield";
    /**
     * Constant for an Action card.
     */
    public static final String TETHER = "Tether";
    /**
     * Constant for an Action card.
     */
    public static final String TRACTOR_BEAM = "Tractor beam";
    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Empty Constructor for class GameDeck.
     */
    public GameDeck(){
        super();
    }
    /**
     * String constructor for class GameDeck constrctor.
     * @param path The location of the GameDeck.txt file
     * @throws GameException To check any Exception for file handing.
     */
    public GameDeck(String path) throws GameException{
        List<Card> gameDeck = new ArrayList<>();
        int gameDeckSize = add(Deck.loadCards(path));
        int singleOxygens = 38;
        int doubleOxygens = 10;
        Collection<Card> cards = getCards();
        for (int i=0; i<doubleOxygens; i++){
            cards.add(new Oxygen(2));
        }
        for (int i=0; i<singleOxygens; i++){
            cards.add(new Oxygen(1));
        }
        
        
    }
    /**
     * Method to draw the oxygen in space.
     * @param value The value of the oxygen used.
     * @return The Oxygen card used.
     * @throws IllegalStateException If card not found.
     */
    public Oxygen drawOxygen(int value) throws IllegalStateException{
        Collection<Card> cards = getCards();
        Oxygen oxy = null;
        Oxygen c = null;
        for (Card card: cards){
            if (card instanceof Oxygen){
                oxy = (Oxygen) card;
                if (oxy.getValue() == value){
                    c = oxy;
                    cards.remove(c);
                    updateCards(cards);
                    return c;
                }
            }
        }
        throw new IllegalStateException("No such card in deck.");
    }
    /**
     * Method to split the oxygen.
     * @param dbl The double oxygen card.
     * @return The array on oxygen used.
     * @throws IllegalStateException for any illegal splits.
     * @throws IllegalArgumentException If spliting wrong oxygen card.
     */
    public Oxygen[] splitOxygen(Oxygen dbl) throws IllegalStateException, IllegalArgumentException{
        Oxygen[] theSplitedOxygens = new Oxygen[2];
        Oxygen oneOxy = null;
        Oxygen twoOxy = null;
        Collection<Card> cards = getCards();
        try {
            if (dbl.getValue() == 1){
                throw new IllegalArgumentException("Can't split Oxygen(1)");
            }
            try{
                oneOxy = drawOxygen(1);
            }catch(IllegalStateException e){
                throw new IllegalStateException("The deck does not have any oxygen(1) cards.");
            }
            try{
                twoOxy = drawOxygen(1);
            }catch(IllegalStateException e){
                throw new IllegalStateException("The deck does not have any more oxygen(1) cards.");
            }
            theSplitedOxygens[0] = oneOxy;
            theSplitedOxygens[1] = twoOxy;
            cards.add(dbl);
            updateCards(cards);
            return theSplitedOxygens;

        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error");
        }
    }
    
}
