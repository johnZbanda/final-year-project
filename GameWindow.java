import java.awt.Font;
import java.awt.event.*; //For ActionListener
import javax.swing.*; //Using Java Swing for 
//import java.awt.*;

public class GameWindow extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    // UI for the game
    Minesweeper game;
    char gameBoard[];
    boolean mineBoard[];
    int x;
    int y;
    int i;
    char flag = 'z';
    JButton select[];
    JButton bFlag;
    
    public GameWindow() {
        x = 0;
        y = 0;
        setLayout(null);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        select = new JButton[Minesweeper.dimensions * Minesweeper.dimensions]; //initialise buttons
        char gameBoard[] = new char[Minesweeper.dimensions * Minesweeper.dimensions]; //used to hold
        boolean mineBoard[] = new boolean[Minesweeper.dimensions * Minesweeper.dimensions];
        bFlag = new JButton("Flag - On");
        for (i = 0; i < (Minesweeper.dimensions * Minesweeper.dimensions); i++) {
            select[i] = new JButton(Integer.toString(i)); //makes it easier to get the values

            if (x == Minesweeper.dimensions - 1) {   
                select[i].addActionListener(this);
                select[i].setBounds(60 * (x + 1), 35 * (y + 1), 60, 35);
                select[i].setVisible(true);
                select[i].setText(Integer.toString(i));
                select[i].setFont(new Font("Arial", Font.PLAIN, 10));
                gameBoard[i] = Minesweeper.board[x][y];
                mineBoard[i] = Minesweeper.isMine[x][y];
                //System.out.println("i: " + i + " x: " + x + " y: " + y + " mine: " + mineBoard[i]);
                x = 0; y++;
                super.add(select[i]);
            } else {
                select[i].addActionListener(this);
                select[i].setBounds(60 * (x + 1), 35 * (y + 1), 60, 35);
                select[i].setVisible(true);
                select[i].setText(Integer.toString(i));
                select[i].setFont(new Font("Arial", Font.PLAIN, 10));
                gameBoard[i] = Minesweeper.board[x][y];
                mineBoard[i] = Minesweeper.isMine[x][y];
                //System.out.println("i: " + i + " x: " + x + " y: " + y + " mine: " + mineBoard[i]);
                super.add(select[i]);
                x++;
            }
            // System.out.println("Button Value: "+select[i]);
        }
        bFlag.addActionListener(this);
        
        switch (Minesweeper.difficulty) {
            case 1:
                bFlag.setBounds(60, 400, 100, 30);
                super.setSize(650,500);
                super.setTitle("Minesweeper - Beginner");
                break;
            case 2:
                bFlag.setBounds(60, 600, 100, 30);
                super.setSize(1250,700);
                super.setTitle("Minesweeper - Intermediate");
                break;
            case 3:
                bFlag.setBounds(60, 900, 100, 30);
                super.setSize(1720,980);
                super.setTitle("Minesweeper - Advanced");
                break;
        }
        bFlag.setEnabled(true);
        super.add(bFlag);
        super.setResizable(true);
        super.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //wont need display board - might display for testing
        //implement playGame
        for (int i = 0; i < (Minesweeper.dimensions * Minesweeper.dimensions); i++) {
            if (e.getActionCommand().equals(Integer.toString(i))) { //issue, it does not go into if statement
                System.out.println(i + " was selected");
                int value = i % Minesweeper.dimensions;
                for (int j = 0; j < Minesweeper.dimensions * Minesweeper.dimensions; j++) {
                    if (j == i) {
                        y = (j / Minesweeper.dimensions) % Minesweeper.dimensions;
                        x = value;
                    }
                }
                if (Minesweeper.flag == 'f') {
                    select[i].setEnabled(true);
                } else {
                    select[i].setEnabled(false);
                }
                Minesweeper.row = y;
                Minesweeper.column = x;
                System.out.println("x: " + Minesweeper.row + " y: " + Minesweeper.column + " was selected");
                break;
            } else if (e.getActionCommand().equals("Flag - On")) {
                Minesweeper.flag = 'f';
                //System.out.println("Flag On " + flag);
                bFlag.setText("Flag - Off");
                break;   
            } else if (e.getActionCommand().equals("Flag - Off")) {
                Minesweeper.flag = 'x';
                //System.out.println("Flag Off " + flag);
                bFlag.setText("Flag - On");
                break;
            }
        }
        Minesweeper.playGame(Minesweeper.board, Minesweeper.dimensions, Minesweeper.isMine);
    }
}  