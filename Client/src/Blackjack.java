import javax.swing.*;

public class Blackjack {
    private JPanel panel;
    private JButton hitButton;
    private JButton standButton;
    private JLabel playersHand;
    private JLabel dealersHand;
    private JLabel dealersPoints;
    private JLabel playersPoints;
    private JPanel dealersPanel;
    private JPanel playersPanel;
    private Card card;
    private int bet, credits;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Blackjack");
        frame.setContentPane(new Blackjack().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Blackjack() {
    }

    public Blackjack(Connection connection) {
        JFrame frame = new JFrame("Blackjack");
        frame.setContentPane(new Blackjack().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        credits = 100;
        bet = 101;
        while (true){
            card = connection.getCard();
            if (card.getValue() == 0){
                while (bet > credits) bet = Integer.parseInt(JOptionPane.showInputDialog(panel, "How much credits are you want to bet?", null));
            }
        }
    }
}
