import java.awt.event.*; //For ActionListener
import javax.swing.*; //Using Java Swing for 
//import java.awt.*;

public class GameWindow extends JFrame implements ActionListener{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // UI for the game
    Minesweeper game;
    static int newDimensions;
    char gameBoard[];
    char mineBoard[];
    public GameWindow(int difficulty) {
        int x = 0;
        int y = 0;
        setLayout(null);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new Minesweeper(); 
        Minesweeper.chooseDifficulty(difficulty);
        newDimensions = Minesweeper.dimensions;
        JButton select[] = new JButton[newDimensions * newDimensions]; //initialise buttons
        char gameBoard[] = new char[newDimensions * newDimensions]; //used to hold
        boolean mineBoard[] = new boolean[newDimensions * newDimensions];
        for (int i = 0; i < (newDimensions * newDimensions); i++) {
            select[i] = new JButton(Integer.toString(i)); //makes it easier to get the values

            if (x == newDimensions - 1) {   
                select[i].addActionListener(this);
                select[i].setBounds(35 * (x + 1), 35 * (y + 1), 35, 35);
                select[i].setVisible(true);
                gameBoard[i] = Minesweeper.board[x][y];
                mineBoard[i] = Minesweeper.isMine[x][y];
                x = 0; y++;
                super.add(select[i]);
            } else {
                select[i].addActionListener(this);
                select[i].setBounds(35 * (x + 1), 35 * (y + 1), 35, 35);
                select[i].setVisible(true);
                gameBoard[i] = Minesweeper.board[x][y];
                mineBoard[i] = Minesweeper.isMine[x][y];
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
    
    public void actionPerformed(ActionEvent e) {
        //initialise board, done in Minesweeper class
        //initialise mines, done in Minesweeper class
        //wont need display board - might display for testing
        //implement playGame
        for (int i = 0; i < Minesweeper.dimensions * Minesweeper.dimensions; i++) {
            String str = e.getActionCommand();
            if (str.equals(Integer.toString(i))) {
                System.out.println(i + " was selected");
                //link to minesweeper class    
                //use gameBoard and mineBoard to retrieve the coordinates
                Minesweeper.playGame(Minesweeper.board, Minesweeper.dimensions, Minesweeper.isMine);    
            }     
        }
    }

    public void getBoard(int i) {
        //gameBoard[i] = Minesweeper.board[x][y];
    }

    public void getMines(int i) {
        //mineBoard[i] = Minesweeper.isMine[x][y];
    }
}  