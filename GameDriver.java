import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import selfish.Astronaut;
import selfish.GameEngine;
import selfish.GameException;
import selfish.deck.GameDeck;
import selfish.deck.SpaceDeck;

public class GameDriver {

    /**
     * A helper function to centre text in a longer String.
     * @param width The length of the return String.
     * @param s The text to centre.
     * @return A longer string with the specified text centred.
     */
    public static String centreString (int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    public GameDriver() {
    }

    public static void main(String[] args) throws GameException  {
        GameEngine game = new GameEngine(500, "./io/ActionCards.txt", "./io/SpaceCards.txt");

        try (BufferedReader header = new BufferedReader(new FileReader("./io/art.txt"))) {
            for (int i=0; i<17; i++){
                System.out.println(header.readLine());
            }
            header.close();
        } catch (FileNotFoundException e){
            System.out.println("------------Selfish - Space Edition------- ");
        } catch (IOException e) {
            System.out.println("------------Selfish - Space Edition------- ");
        }
        Console players = System.console();
        String player1_name = players.readLine("Player 1 name? ");
        game.addPlayer(player1_name);
        Astronaut player1 = new Astronaut(player1_name, game);
        String player2_name = players.readLine("Player 2 name? ");
        game.addPlayer(player2_name);
        Astronaut player2 = new Astronaut(player2_name, game);
        int playerNum = 2;
        while(true){
            if(playerNum == 5){
                break;
            }
            String addPlayer = players.readLine("Add another? [Y]es/[N]o ");
            if(addPlayer.equals("Y") || addPlayer.equals("y") ){
                if (playerNum == 2){
                    String player3_name = players.readLine("Player 3 name? ");
                    Astronaut player3 = new Astronaut(player3_name, game);
                    playerNum += 1;
                }
                else if (playerNum == 3){
                    String player4_name = players.readLine("Player 4 name? ");
                    game.addPlayer(player4_name);
                    Astronaut player4 = new Astronaut(player4_name, game);
                    playerNum += 1;
                }
                else if (playerNum == 4){
                    String player5_name = players.readLine("Player 5 name? ");
                    Astronaut player5 = new Astronaut(player5_name, game);
                    playerNum += 1;
                    break;
                }
            }else if(addPlayer.equals("N") || addPlayer.equals("n")){
                break;
            }else{
                System.out.println("INVALID INPUT!");
                System.out.println("Please try again.");
            }
        }
        System.out.println("After a dazzling (but doomed) space mission, players are floating in space and their Oxigen supplies are running low.");
        System.out.println("Only the first back to the ship will survive!");

        GameDeck gameDeck = new GameDeck("./io/ActionCards.txt");
        SpaceDeck spaceDeck = new SpaceDeck("./io/SpaceCards.txt");
        // List<Card> c = GameDeck.loadCards("../../io/ActionCards.txt");
        
        // spaceDeck.shuffle(new Random());

        // game.saveState("../../io/hello.txt");
        // game.loadState("../../io/hello.txt");
    }

}