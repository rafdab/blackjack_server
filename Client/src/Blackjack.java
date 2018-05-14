import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JLabel remainingCredits;
    private Card card;
    private int bet, credits, srvP, clP;
    private Connection connection;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Blackjack");
        frame.setContentPane(new Blackjack().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Blackjack() {
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                hitOnClick();
            }
        });

        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                standOnClick();
            }
        });

    }

    public Blackjack(Connection connection) {
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                hitOnClick();
            }
        });

        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                standOnClick();
            }
        });

        this.connection = connection;
        card = new Card();
        JFrame frame = new JFrame("Blackjack");
        frame.setContentPane(new Blackjack().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        credits = 100;
        startRound();
    }

    public void startRound(){
        bet = credits + 1;
        playersHand.setText("");
        dealersHand.setText("");
        playersPoints.setText("0");
        dealersPoints.setText("0");
        remainingCredits.setText(Integer.toString(credits));
      //  while (true) {
            card = connection.getCard();
            if (card.getValue() == 0) {
                while (bet > credits && bet <-1) bet = Integer.parseInt(JOptionPane.showInputDialog(panel, "How much credits do you want to bet?", null));
                connection.sendCard(card);
            }
            srvP = clP = 0;

            card = connection.getCard();
            srvP += cardValue();
            dealersPoints.setText(Integer.toString(srvP));
            dealersHand.setText(card.printCard() + "\n");

            card = connection.getCard();
            clP += cardValue();
            playersHand.setText(card.printCard() + "\n");
            card = connection.getCard();
            clP += cardValue();
            playersHand.setText(playersHand.getText() + card.printCard() + "\n");
            playersPoints.setText(Integer.toString(clP));
       // }
    }

    public void hitOnClick(){
        card = new Card();
        card.setValue(1);
        connection.sendCard(card);
        card = connection.getCard();
        clP += cardValue();
        playersHand.setText(playersHand.getText() + card.printCard() + "\n");
        playersPoints.setText(Integer.toString(clP));
    }

    public  void standOnClick(){
        card.setValue(0);
        connection.sendCard(card);
        dealersTurn();
    }

    public void dealersTurn(){
        card = connection.getCard();
        while (card.getValue() != 0){
            srvP += cardValue();
            dealersPoints.setText(Integer.toString(srvP));
            dealersHand.setText(dealersHand.getText() + card.printCard() + "\n");
            card = connection.getCard();
        }
    }

    public int cardValue(){
        if (card.getValue() > 11) return 10;
        return card.getValue();
    }

}
