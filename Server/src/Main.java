import javax.swing.*;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Main {
    private JPanel panel;

    public static void main(String[] args){
        Server server = new Server(1050);
        server.begin();
        server.closeConnection();
    }
}
