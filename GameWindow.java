import java.awt.event.*; //For ActionListener
import javax.swing.*; //Using Java Swing for 
//import java.awt.*;

public class GameWindow extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    // UI for the game
    Minesweeper game;
    char gameBoard[];
    boolean mineBoard[];
    int x, y, i;
    JButton select[];
    public GameWindow(int difficulty) {
        x = 0;
        y = 0;
        setLayout(null);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game = new Minesweeper(); 
        Minesweeper.chooseDifficulty(difficulty);
        JButton select[] = new JButton[Minesweeper.dimensions * Minesweeper.dimensions]; //initialise buttons
        char gameBoard[] = new char[Minesweeper.dimensions * Minesweeper.dimensions]; //used to hold
        boolean mineBoard[] = new boolean[Minesweeper.dimensions * Minesweeper.dimensions];
        for (i = 0; i < (Minesweeper.dimensions * Minesweeper.dimensions); i++) {
            select[i] = new JButton(Integer.toString(i)); //makes it easier to get the values

            if (x == Minesweeper.dimensions - 1) {   
                select[i].addActionListener(this);
                select[i].setBounds(35 * (x + 1), 35 * (y + 1), 35, 35);
                select[i].setVisible(true);
                select[i].setEnabled(true);
                gameBoard[i] = Minesweeper.board[x][y];
                mineBoard[i] = Minesweeper.isMine[x][y];
                //System.out.println("i: " + i + " x: " + x + " y: " + y + " mine: " + mineBoard[i]);
                x = 0; y++;
                super.add(select[i]);
            } else {
                select[i].addActionListener(this);
                select[i].setBounds(35 * (x + 1), 35 * (y + 1), 35, 35);
                select[i].setVisible(true);
                select[i].setEnabled(true);
                gameBoard[i] = Minesweeper.board[x][y];
                mineBoard[i] = Minesweeper.isMine[x][y];
                //System.out.println("i: " + i + " x: " + x + " y: " + y + " mine: " + mineBoard[i]);
                super.add(select[i]);
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //initialise board, done in Minesweeper class
        //initialise mines, done in Minesweeper class
        //wont need display board - might display for testing
        //implement playGame
        
        JButton[] select = new JButton[Minesweeper.dimensions * Minesweeper.dimensions];
        for (i = 0; i < Minesweeper.dimensions * Minesweeper.dimensions; i++) {
            
            select[i] = new JButton(Integer.toString(i));
            String str = e.getActionCommand();
            if (str.equals(Integer.toString(i))) {
                System.out.println(i + " was selected");
                //gameBoard[i] is causing the error
                int value = i % Minesweeper.dimensions;
                for (int j = 0; j < Minesweeper.dimensions * Minesweeper.dimensions; j++) {
                    if (j == i) {
                        y = (j / Minesweeper.dimensions) % Minesweeper.dimensions;
                        x = value;
                        break;
                    }
                } 
                System.out.println("x: " + x + " y: " + y + " was selected");
                //Minesweeper.playGame(Minesweeper.board, Minesweeper.dimensions, Minesweeper.isMine);  
            }
            select[i].setEnabled(false);     
        }
    }
}  