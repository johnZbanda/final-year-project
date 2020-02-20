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
    JButton select[];
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
        JButton select[] = new JButton[newDimensions * newDimensions]; //initialise buttons

        for (int i = 0; i < (newDimensions * newDimensions); i++) {
            select[i] = new JButton(Integer.toString(i)); //makes it easier to get the values
            char gameBoard[] = new char[newDimensions * newDimensions]; //used to hold
            boolean mineBoard[] = new boolean[newDimensions * newDimensions];
            if (x == newDimensions - 1) {   
                select[i].addActionListener(this);
                select[i].setBounds(35 * (x + 1), 35 * (y + 1), 35, 35);
                select[i].setVisible(true);
                gameBoard[i] = Minesweeper.board[x][y];
                mineBoard[i] = Minesweeper.isMine[x][y];
                System.out.println(x + "," + y + ": " + mineBoard[i]);
                x = 0;
                y++;
                super.add(select[i]);
            } else {
                select[i].addActionListener(this);
                select[i].setBounds(35 * (x + 1), 35 * (y + 1), 35, 35);
                select[i].setVisible(true);
                gameBoard[i] = Minesweeper.board[x][y];
                mineBoard[i] = Minesweeper.isMine[x][y];
                System.out.println(x + "," + y + ": " + mineBoard[i]);
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
        //wont need display board
        //implement playGame
        for (int i = 0; i < Minesweeper.dimensions * Minesweeper.dimensions; i++) {
            String str = e.getActionCommand();
            if (str.equals(Integer.toString(i))) {
                System.out.println(i + " was selected");
            }     
        }
    }
}  