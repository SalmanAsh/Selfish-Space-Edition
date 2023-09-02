package selfish;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import selfish.deck.Card;
import selfish.deck.Oxygen;
/**
 * A class handles the astronaut logic and imlements serializations.
 * @author Salman Ashraf
 * @version 1
 */
public class Astronaut  implements Serializable{
    private GameEngine game;
    private List<Card> actions;
    private List<Oxygen> oxygens;
    private String name;
    private Collection<Card> track;
    private static final long serialVersionUID = 1L;
    /**
     * Constructor for the Astronaut class.
     * @param name The name of the Astronaut.
     * @param game The main game engine.
     */
    public Astronaut(String name, GameEngine game){
        this.name=name;
        this.game=game;
        this.actions = new ArrayList<>();
        this.oxygens = new ArrayList<>();
        this.track = new ArrayList<>();
    }
    /**
     * Getter method for the dead Astronaut.
     * @return The name of the dead Astronaut.
     */
    public String toString(){
        if (isAlive() == false){
            return name + " (is dead)";
        }
        return name;
    }
    /**
     * Method to check if the Astonaut is alive.
     * @return The boolean value of the check.
     */
    public boolean isAlive(){
        if(oxygenRemaining()==0){
            return false;
        }
        return true;
    }
    /**
     * Method to add a card to the hand.
     * @param card to added.
     */
    public void addToHand(Card card){
        if (card instanceof Oxygen){
            this.oxygens.add((Oxygen) card);
        }
        else{
            this.actions.add(card);
        }
    }
    /**
     * Method to add the Game card on track.
     * @param card to be added on the track.
     */
    public void addToTrack(Card card){
        this.track.add(card);
    }
    /**
     * Method to discard oxygen to breathe.
     * @return The number of oxygen remaining. 
     */
    public int breathe(){
        int foundSingle = 0;
        Oxygen singleCard = null;
        int foundDouble = 0;
        Oxygen doubleCard = null;
        for (Oxygen card: this.oxygens){
            if(card.getValue()==1){
                foundSingle += 1;
                singleCard = card;
                break;
            }
        }
        for (Oxygen card: this.oxygens){
            if(card.getValue()==2){
                foundDouble += 1;
                doubleCard = card;
                break;
            }
        }
        if(foundSingle==1){
            this.oxygens.remove(singleCard);
            this.game.discardCard(singleCard);
        }
        if(foundDouble == 1 && foundSingle==0){
            Oxygen[] twoCards = this.game.splitOxygen(doubleCard);
            this.oxygens.remove(doubleCard);
            this.oxygens.add(twoCards[0]);
            this.game.discardCard(twoCards[1]);
        }
        if(oxygenRemaining()==0){
                this.game.killPlayer(this);
                return 0;
            }
        int count = oxygenRemaining();
        return count;
    }
    /**
     *Method to Distace from the ship to Astronaut.
     * @return Distace from the ship to Astronaut.
     */
    public int distanceFromShip(){
        int dis = 6;
        if (this.track.size()==0){
            return 6;
        }

        return dis - this.track.size();
    }
    /**
     * Method to get all action cards in hand.
     * @return List of cards in hand.
     */
    public List<Card> getActions(){
        Collections.sort(this.actions);
        return this.actions;
    }
    /**
     * Method to update the action cards
     * @param newActions to be updated with.
     */
    public void updateActions(List<Card> newActions){
        this.actions= newActions;
    }
    /**
     * Method that returns the card in the hand of player.
     * @return The list of cards in hand.
     */
    public List<Card> getHand(){
        List<Card> allCards = new ArrayList<>();
        if(this.isAlive()==false){
            return allCards;
        }
        for (Oxygen card: this.oxygens){
            allCards.add(card);
        }
        for (Card card: this.actions){
            allCards.add(card);
        }
        Collections.sort(allCards);
        return allCards;
    }
    /**
     * Method that return the actions cards in sorted order if asked.
     * @param enumerated Enumerated or not.
     * @param excludedShields Include shield or not.
     * @return The requested list of cards.
     */
    public String getActionsStr(boolean enumerated, boolean excludedShields){
        String values = "";
        if (!enumerated & !excludedShields){
            List<Card> actionCards = getActions();
            int size = actionCards.size();
            int endOfList = actionCards.size()-1;
            if (size >0){
                for (Card aCard: actionCards){
                    String card = aCard.toString();
                    int count = hasCard(card);
                    int pos = actionCards.indexOf(aCard);
                    if(!values.contains(card)){
                        if (count>0){
                            if(pos == endOfList){
                                if(count == 1) {
                                    values = values.concat(" " + card);
                                }else{
                                    values = values.concat(count + "x " +card);
                                }
                                
                            }else{
                                if(count == 1){
                                    values = values.concat(" " + card + ",");
                                }else{
                                    values = values.concat(" " + count + "x " +card + ",");
                                }
                            }
                        }
                    }
                }
                if(values.charAt(values.length()-1)==','){
                    values = values.substring(0, values.length()-1);

                }
                if(values.charAt(values.length()-2)==','){
                    values = values.substring(0, values.length()-2);

                }
                if(values.charAt(0)==' '){
                    values = values.substring(1, values.length());
                }
            }
            return values;
        }
        if(enumerated && !excludedShields){
            List<Card> actionCards = getActions();
            int size = actionCards.size();
            int endOfList = actionCards.size()-1;
            if (size >0){
                int numOfCards = 0;
                for (Card aCard: actionCards){
                    String card = aCard.toString();
                    int pos = actionCards.indexOf(aCard);
                    String substring1 = card;
                    if(!values.contains(substring1)){
                        numOfCards += 1;
                        if(pos == endOfList){
                            char letter = (char) ('A' + numOfCards-1);
                            String l = Character.toString(letter);
                            values = values.concat("[" + l + "] " + card);
                        }else{
                            char letter = (char) ('A' + numOfCards-1);
                            String l = Character.toString(letter);
                            values = values.concat("[" + l + "] " + card + ", ");
                        }
                    }
                }
                if(values.charAt(values.length()-1)==','){
                    values = values.substring(0, values.length()-1);

                }
                if(values.charAt(values.length()-2)==','){
                    values = values.substring(0, values.length()-2);

                }
                if(values.charAt(0)==' '){
                    values = values.substring(1, values.length());
                }
            }
            return values;
        }

        if(enumerated && excludedShields){
            List<Card> actionCards = getActions();
            int size = actionCards.size();
            int endOfList = actionCards.size()-1;
            if (size >0){
                int numOfCards = 0;
                for (Card aCard: actionCards){
                    String card = aCard.toString();
                    if(!card.equals("Shield")){
                        int pos = actionCards.indexOf(aCard);
                        String substring1 = card;
                        if(!values.contains(substring1)){
                            numOfCards += 1;
                            if(pos == endOfList){
                                char letter = (char) ('A' + numOfCards-1);
                                String l = Character.toString(letter);
                                values = values.concat("[" + l + "] " + card);
                            }else{
                                char letter = (char) ('A' + numOfCards-1);
                                String l = Character.toString(letter);
                                values = values.concat("[" + l + "] " + card + ", ");
                            }
                        }
                    }
                    
                }
                if(values.charAt(values.length()-1)==','){
                    values = values.substring(0, values.length()-1);

                }
                if(values.charAt(values.length()-2)==','){
                    values = values.substring(0, values.length()-2);

                }
                if(values.charAt(0)==' '){
                    values = values.substring(1, values.length());
                }
            }
            return values;
        }

        if (!enumerated & excludedShields){
            List<Card> actionCards = getActions();
            int size = actionCards.size();
            int endOfList = actionCards.size()-1;
            if (size >0){
                for (Card aCard: actionCards){
                    String card = aCard.toString();
                    int count = hasCard(card);
                    int pos = actionCards.indexOf(aCard);
                    if(!values.contains(card) && !card.equals("Shield")){
                        if (count>0){
                            if(pos == endOfList){
                                if(count == 1) {
                                    values = values.concat(" " + card);
                                }else{
                                    values = values.concat(count + "x " +card);
                                }
                                
                            }else{
                                if(count == 1){
                                    values = values.concat(" " + card + ",");
                                }else{
                                    values = values.concat(" " + count + "x " +card + ",");
                                }
                            }
                        }
                    }
                }
                if(values.charAt(values.length()-1)==','){
                    values = values.substring(0, values.length()-1);

                }
                if(values.charAt(values.length()-2)==','){
                    values = values.substring(0, values.length()-2);

                }
                if(values.charAt(0)==' '){
                    values = values.substring(1, values.length());
                }
            }
            return values;
        }


        return values;
    }
    /**
     * Method that return the card in players hand in string form.
     * @return String of cards.
     */
    public String getHandStr(){
        String values = "";
        int countOxyTwo = 0;
        String oxy = "";
        for (Oxygen c: this.oxygens){
            if(c.getValue()==2){
                countOxyTwo +=1;
                if (countOxyTwo==1){
                    oxy = "Oxygen(2), ";
                }else if(countOxyTwo>1){
                    oxy = Integer.toString(countOxyTwo) + "x Oxygen(2), ";
                }    
            }
        }
        
        int countOxyOne = 0;
        String oxyOne = "";
        for (Oxygen c: this.oxygens){
            if(c.getValue()==1){
                countOxyOne +=1;
                if (countOxyOne==1){
                    oxyOne = "Oxygen(1)";
                }
                else{
                    oxyOne = Integer.toString(countOxyOne) + "x Oxygen(1)";
                }
                
            }
        }
        if(countOxyOne>0 && countOxyTwo>0){
            values = values.concat(oxy);
            values = values.concat(oxyOne + ";");
        }
        else if(countOxyTwo>0 && countOxyOne==0){
            oxy = oxy.substring(0, oxy.length()-1);
            oxy = oxy.substring(0, oxy.length()-1);
            values = values.concat(oxy + ";");
        }else if(countOxyTwo==0 && countOxyOne>0){
            values = values.concat(oxyOne + ";");
        }
        
        List<Card> actionCards = getActions();
        int size = actionCards.size();
        int endOfList = actionCards.size()-1;
        if (size >0){
            for (Card aCard: actionCards){
                String card = aCard.toString();
                int count = hasCard(card);
                int pos = actionCards.indexOf(aCard);
                if (count>0){
                    if(pos == endOfList){
                        if(count == 1) {
                            values = values.concat(" " + card);
                        }else{
                            values = values.concat(count + "x " +card);
                        }
                        
                    }else{
                        if(count == 1){
                            values = values.concat(" " + card + ",");
                        }else{
                            values = values.concat(count + "x " +card + ",");
                        }
                    }
                }
    
            }
        }
    
        return values;
    }
    /**
     * Method that return the track of the game.
     * @return The track as a collection.
     */
    public Collection<Card> getTrack(){
        return this.track;
    }
    /**
     * Method to check a card in Astronauts hand. 
     * @param card To be checked.
     */
    public void hack(Card card){
        boolean foundAction = false;
        Card actionCardFound = null;
        for (Card cardIn: this.actions){
            if (cardIn == card){
                foundAction = true;
                actionCardFound = card;
                break;
            }
        }
        if(foundAction){
            this.actions.remove(actionCardFound);
        }

        boolean foundOxygen = false;
        Oxygen oxygenCardFound = null;
        for (Card cardIn: this.oxygens){
            if (cardIn == card){
                foundOxygen = true;
                oxygenCardFound = (Oxygen) cardIn;
                break;
            }
        }
        if(foundOxygen){
            this.oxygens.remove(oxygenCardFound);
            if(oxygenRemaining()==0){
                this.game.killPlayer(this);
            }
        }
        if(!foundAction && !foundOxygen){
            throw new IllegalArgumentException("Card not found");
        }
    }
    /**
     * Method that removes a specific card from player hand.
     * @param card name to be removed 
     * @return THe card removed.
     */
    public Card hack(String card){
        boolean foundAction = false;
        Card actionCardFound = null;
        for (Card cardIn: this.actions){
            if (cardIn.toString().equals(card)){
                foundAction = true;
                actionCardFound = cardIn;
            }
        }
        if(foundAction==true){
            this.actions.remove(actionCardFound);
            return actionCardFound;
        }
        
        boolean foundOxygen = false;
        Oxygen oxygenCardFound = null;
        for (Card cardIn: this.oxygens){
            if (cardIn.toString().equals(card)){
                foundOxygen = true;
                oxygenCardFound = (Oxygen) cardIn;
            }
        }
        if(foundOxygen==true){
            this.oxygens.remove(oxygenCardFound);
            if(oxygenRemaining()==0){
                this.game.killPlayer(this);
            }
            return oxygenCardFound;
        }
        throw new IllegalArgumentException("Card not found");

    }
    /**
     * Method to check if an Astronaut has a certain card.
     * @param card That has been checked.
     * @return Quantity of the card in hand.
     */
    public int hasCard(String card){
        int count = 0;
        List<Card> cardsInHand = getHand();
        for (Card c: cardsInHand){
            String str = c.toString();
            if(str.equals(card)){
                count += 1;
            }
        }
        return count;
    }
    /**
     * Method to check if a Solar Flare card is just behing,
     * @return The result of check.
     */
    public boolean hasMeltedEyeballs(){
        if(((List<Card>) this.track).get(0).toString().equals("Solar flare")){
            return true;
        }
        if(this.track.size()==6){
            if(((List<Card>) this.track).get(5).toString().equals("Solar flare")){
                return true;
            }
        }
        for (int i=1; i<this.track.size(); i++){
            if(((List<Card>) this.track).get(i-1).toString().equals("Solar flare")){
                return true;
            }
        }
        return false;
    }
    /**
     * Method to check if an Astronaut has reached the ship.
     * @return THe boolean value accordingly.
     */
    public boolean hasWon(){
        boolean won = false;
        if (distanceFromShip()==0 && this.oxygens.size()>0){
            won = true;
        }
        return won;
    }
    /**
     * Method that draws the top card from Astronauts hand.
     * @return The card at the top.
     * @throws IllegalArgumentException to check if track is empty.
     */
    public Card laserBlast() throws IllegalArgumentException{
        Card card;
        if(this.track.size()==0){
            throw new IllegalArgumentException("Track empty");  
        }
        card = ((List<Card>) this.track).get(this.track.size()-1);
        this.track.remove(card);
        return card;
    }
    /**
     * Method that check the remaining oxygen.
     * @return The remaining oxygen.
     */
    public int oxygenRemaining(){
        int count = 0;
        for (Oxygen card: this.oxygens){
            if(card.getValue()==1){
                count+=1;
            }else if(card.getValue()==2){
                count+=2;
            }
        }
        return count;
    }
    /**
     * Method that return the Astronaut behind.
     * @return The card for the move.
     */
    public Card peekAtTrack(){
        if(this.track.size()==0){
            return null;
        }
        return ((List<Card>) this.track).get(this.track.size()-1);
    }
    /**
     * Method that extracts a single oxygen cards.
     * @return The drawn oxygen cards.
     */
    public Oxygen siphon(){
        List<Card> allCards = new ArrayList<>();
        allCards = getHand();
        boolean oxygenFound = false;
        Oxygen single = null;
        for (Card card: allCards){
            if(card instanceof Oxygen){
                if(((Oxygen)card).getValue()==1){
                    oxygenFound=true;
                    single = (Oxygen) card;
                    this.oxygens.remove(single);
                    if(oxygenRemaining()==0){
                        this.game.killPlayer(this);
                    }
                    return single;
                }
            }
        }
        
        Oxygen[] twoOxy = new Oxygen[2];
        Oxygen dbl = null;
        single = null;
        for (Card card: allCards){
            if(card instanceof Oxygen){
                if(((Oxygen)card).getValue()==2){
                    oxygenFound=true;
                    dbl = (Oxygen) card;
                    twoOxy = this.game.splitOxygen(dbl);
                    single = twoOxy[1];
                    this.game.discardCard(dbl);
                    this.oxygens.remove(dbl);
                    this.oxygens.add(twoOxy[1]);
                    if(oxygenRemaining()==0){
                        this.game.killPlayer(this);
                    }
                    return single;  
                }
            }
        }
        if(oxygenRemaining()==0){
            this.game.killPlayer(this);
        }
        return null;
    }
    /**
     * Method that an Astronaut to steal a card from another.
     * @return The stolen card.
     */
    public Card steal(){
        List<Card> allCards = new ArrayList<>();
        allCards = getHand();
        Random random = new Random();
        int randomNum = random.nextInt(allCards.size());
        Card randomCard = allCards.get(randomNum);
        if(randomCard instanceof Oxygen){
            this.oxygens.remove(randomCard);
            if(oxygenRemaining()==0){
                this.game.killPlayer(this);
            }
            return randomCard;
        }
        this.actions.remove(randomCard);
        return randomCard;
    }
    /**
     * Method to update track for Astronaut
     * @param newTrack To be swapped with.
     */
    public void updateTrack(Collection<Card> newTrack){
        this.track = newTrack;
    }
    /**
     * Method that swaps the path of two Astronauts.
     * @param swapee The astronaut to be swapped.
     */
    public void swapTrack(Astronaut swapee){
        Collection<Card> newTrack = new ArrayList<>();
        newTrack.addAll(this.track);
        this.track = swapee.getTrack();
        swapee.updateTrack(newTrack);
        
    }
    
}
