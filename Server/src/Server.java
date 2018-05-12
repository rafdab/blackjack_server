import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Logger;

public class Server {
    private ServerSocket serverSocket;

    public Server(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.err.println("failed to start a server");
            e.printStackTrace();
        }
    }

    public void begin(){
        while (true){
            try {
                new Client(serverSocket.accept()).start();
            } catch (IOException e) {
                System.err.println("Connection failed");
                e.printStackTrace();
            }
        }
    }

    public void closeConnection(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Fail");
        }
    }
}
