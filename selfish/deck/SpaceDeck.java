package selfish.deck;
import java.util.ArrayList;
import java.util.List;
import selfish.GameException;
/**
 * A class that inherits from deck and handles space deck logic.
 * @author Salman Ashraf
 * @version 1
 */
public class SpaceDeck extends Deck {
    /**
     * Constant for a Space card.
     */
    public static final String ASTEROID_FIELD = "Asteroid field";
     /**
     * Constant for a Space card.
     */
    public static final String BLANK_SPACE = "Blank space";
     /**
     * Constant for a Space card.
     */
    public static final String COSMIC_RADIATION = "Cosmic radiation";
     /**
     * Constant for a Space card.
     */
    public static final String GRAVITATIONAL_ANOMALY = "Gravitational anomaly";
     /**
     * Constant for a Space card.
     */
    public static final String HYPERSPACE = "Hyperspace";
     /**
     * Constant for a Space card.
     */
    public static final String METEOROID = "Meteroid";
     /**
     * Constant for a Space card.
     */
    public static final String MYSTERIOUS_NEBULA = "Mysterious nebula";
     /**
     * Constant for a Space card.
     */
    public static final String SOLAR_FLARE = "Solar flare";
     /**
     * Constant for a Space card.
     */
    public static final String USEFUL_JUNK = "Useful junk";
     /**
     * Constant for a Space card.
     */
    public static final String WORMHOLE = "Wormhole";
    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Empty Constructor for class SpaceDeck.
     */
    public SpaceDeck(){
        super();
    }
    /**
     * String constructor for class SpaceDeck constrctor.
     * @param path The location of the SpaceDeck.txt file
     * @throws GameException To check any Exception for file handing.
     */
    public SpaceDeck(String path) throws GameException{
        super();
        List<Card> spaceDeck = new ArrayList<>();
        int spaceDeckSize = add(Deck.loadCards(path));
    }
}
