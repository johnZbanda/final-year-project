import java.awt.*;
import java.awt.event.*; //For ActionListener
import javax.swing.*; //Using Java Spring for 

public class GameWindow extends JFrame implements ActionListener{
    //UI for the game
    Minesweeper game;

    public GameWindow(int difficulty) {
        setLayout(null);

        game = new Minesweeper(); //needed
        game.chooseDifficulty(difficulty);

        JPanel panel = new JPanel();
        JButton button[] = new JButton[game.dimensions * game.dimensions]; //initialise buttons
        panel.setLayout(new GridLayout(game.dimensions, game.dimensions));
        panel.setBorder(BorderFactory.createLineBorder(Color.gray, 3));
        panel.setBackground(Color.white);

        for (int i = 0; i < (game.dimensions * game.dimensions); i++) {
            button[i] = new JButton(); //NullPointerException
            panel.add(button[i]);    
        }

        super.getContentPane().add(panel);
        super.pack();
        
        super.setTitle("Minesweeper");
        super.setSize(900, 600);
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
    }
    

    public void actionPerformed(ActionEvent e) {

    }
}