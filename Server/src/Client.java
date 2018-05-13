import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class Client extends Thread{
    private Player player;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    LinkedList<Card> deck;
    private Card card;

    public Client() {
    }

    public Client(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.deck = createDeck();
    }

    private static LinkedList<Card> createDeck(){
        LinkedList<Card> deck = new LinkedList<>();
        for (int i = 2; i <=14; ++i){
            deck.add(new Card("Diamonds", i));
            deck.add(new Card("Clubs", i));
            deck.add(new Card("Hearts", i));
            deck.add(new Card("Spades", i));
        }
        return deck;
    }

    public String playerInfo() {
        return (player.getPlayerName() + " (" + player.getNumberOfCredits() + ")");
    }

    public void run() {
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            System.out.print("New player connected, and his name is ");
            player = (Player)in.readObject();
            System.out.println(playerInfo());
            card = new Card(null, 0);
            int i, bet;
            while (card.getValue() != -1){
                i = bet = 0;
                out.writeObject(card);
            }

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Connection to client failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
