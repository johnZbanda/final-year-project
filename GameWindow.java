import java.awt.event.*; //For ActionListener
import javax.swing.*; //Using Java Spring for 
import java.awt.*;

public class GameWindow extends JFrame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // UI for the game
    Minesweeper game;
    static int newDimensions;
    public GameWindow(int difficulty) {
        int x = 0;
        int y = 0;
        setLayout(null);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new Minesweeper(); 
        Minesweeper.chooseDifficulty(difficulty);
        newDimensions = Minesweeper.dimensions;
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(newDimensions, newDimensions));
        JButton button[] = new JButton[newDimensions * newDimensions]; //initialise buttons

        for (int i = 0; i < (newDimensions * newDimensions); i++) {
            button[i] = new JButton(); //makes it easier to get the values
            if (x == newDimensions - 1) {
                button[i].addActionListener(this);
                button[i].setBounds(35 * (x + 1), 35 * (y + 1), 30, 30);
                button[i].setVisible(true);
                super.add(button[i]);
                x = 0;
                y++;
            } else {
                button[i].addActionListener(this);
                button[i].setBounds(35 * (x + 1), 35 * (y + 1), 30, 30);
                button[i].setVisible(true);
                super.add(button[i]);
                x++;
            }
        }
        
        switch (difficulty) {
            case 1:
                super.setSize(400,500);
                super.setTitle("Minesweeper - Beginner");
                break;
            case 2:
                super.setSize(650,700);
                super.setTitle("Minesweeper - Intermediate");
                break;
            case 3:
                super.setSize(920,980);
                super.setTitle("Minesweeper - Advanced");
                break;
        }
        super.setResizable(true);
        super.setVisible(true);
    }
    

    public void actionPerformed(ActionEvent e) {
        //initialise board
        //initialise mines
        //wont need display board
        //implement playGame
    
    }

}