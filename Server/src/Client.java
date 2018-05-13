import java.io.*;
import java.net.Socket;
import java.util.Collections;
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
        deck.clear();
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

    public void startGame() throws IOException, ClassNotFoundException {
        deck = createDeck();
        Collections.shuffle(deck);
        card = new Card(null, 0);
        int bet, c, s, tmp;
        Card srvCard, c1, c2;
        while (true){
            card.setValue(0);
            out.writeObject(card);
            System.out.println("Sending card to " + playerInfo());

            card = (Card) in.readObject();
            bet = card.getValue();
            System.out.println(playerInfo() + " bet " + bet + " credits");

            if (bet == -1) break;

            srvCard = deck.pop();
            c1 = deck.pop();
            c2 = deck.pop();
            out.writeObject(srvCard);
            out.writeObject(c1);
            out.writeObject(c2);

            if ((c = clientTurn(c1, c2)) < 22){
                s = serverTurn(srvCard);
                if (s >= c) {
                    if (s > 21) bet *= 2;
                    else bet *= -1;
                }
                else bet *= 2;
            }
            else bet *= -1;
            tmp = player.getNumberOfCredits() + bet;
            player.setNumberOfCredits(tmp);
            if (player.getNumberOfCredits() <= 0) break;
        }
    }

    public int clientTurn(Card c1, Card c2) throws IOException, ClassNotFoundException {
        int points = c1.getValue() + c2.getValue();
        Card tmp = (Card) in.readObject();
        while (tmp.getValue() != 0) {
            tmp = deck.pop();
            if (tmp.getValue() > 11){
                points += 10;
            }else points += tmp.getValue();
            out.writeObject(tmp);
            tmp = (Card) in.readObject();
        }
        return points;
    }

    public int serverTurn(Card srvCard) throws IOException {
        int points = srvCard.getValue();
        Card tmp = card;
        while (points < 17) {
            tmp = deck.pop();
            if (tmp.getValue() > 11) {
                points += 10;
            } else points += tmp.getValue();
            out.writeObject(tmp);
            out.writeObject(tmp);
        }
        return points;
    }

    public void run() {
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            System.out.print("New player connected, and his name is ");
            player = (Player)in.readObject();
            System.out.println(playerInfo());
            startGame();

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
