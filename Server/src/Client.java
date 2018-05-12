import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class Client extends Thread{
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private int clientId;
    private String clientName;
    private int numberOfCredits;
    LinkedList<Card> deck;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(int numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public Client(String name, int numberOfCredits) {
        this.clientName = name;
        this.numberOfCredits = numberOfCredits;
    }

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

    public void run() {
        try {
            System.out.println("Hello i'm new!");
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

//wymiana danych z klientem

            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("connection to client failed");
            e.printStackTrace();
        }
    }
}
