package selfish;
/**
 * This class is excuted when a file handing error occurs.
 * @author Salman Ashraf
 * @version 1
 */
public class GameException extends Exception{
    /**
     * Constructor for the GameException class. 
     * @param msg The error message, 
     * @param e The thrown cause. 
     */
    public GameException(String msg, Throwable e){
        super(msg, e);
    }
}
