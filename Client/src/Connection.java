import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    private String playerName;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Card card;

    public int bet(){
        return 0;
    }

    public Card getCard() {
        try {
            card = (Card) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); //connection
        }
        return card;
    }

    public void sendCard(Card card) {
        try {
            out.writeObject(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection(String ip, String port, String playerName) {
        this.playerName = playerName;
        try {
            socket = new Socket(ip, Integer.parseInt(port));
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Player(playerName));

        } catch (IOException e) {
            System.err.println("Unknown host");
            System.exit(-1);
        }
    }
}
