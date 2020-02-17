import java.awt.event.*; //For ActionListener
import javax.swing.*; //Using Java Spring for 

public class GameWindow extends JFrame implements ActionListener{
    //UI for the game
    Minesweeper game;
    static int newDimensions;
    public GameWindow(int difficulty) {
        int x = 1;
        int y = 1;
        setLayout(null);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new Minesweeper(); //needed
        game.chooseDifficulty(difficulty);
        newDimensions = game.dimensions;
        JPanel panel = new JPanel();
        JButton button[] = new JButton[newDimensions * newDimensions]; //initialise buttons

        for (int i = 0; i < (newDimensions * newDimensions); i++) {
            button[i] = new JButton(); //NullPointerException
            if (x == newDimensions) {
                button[i].addActionListener(this);
                button[i].setBounds(35 * x, 35 * y, 30, 30);
                button[i].setVisible(true);
                super.add(button[i]);
                x = 1;
                y++;
            } else {
                button[i].addActionListener(this);
                button[i].setBounds(35 * x, 35 * y, 30, 30);
                button[i].setVisible(true);
                super.add(button[i]);
                x++;
            }
        }
        
        super.setTitle("Minesweeper");
        switch (difficulty) {
            case 1:
                super.setSize(400,500);
                break;
            case 2:
                super.setSize(650,700);
                break;
            case 3:
                super.setSize(920,980);
                break;
        }
        super.setResizable(true);
        super.setVisible(true);
    }
    

    public void actionPerformed(ActionEvent e) {
        
    }
}