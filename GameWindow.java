import java.awt.*;
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
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        select = new JButton[Minesweeper.dimensions * Minesweeper.dimensions]; //initialise buttons
        gameBoard = new char[Minesweeper.dimensions * Minesweeper.dimensions]; //used to hold
        mineBoard = new boolean[Minesweeper.dimensions * Minesweeper.dimensions];
        bFlag = new JButton("Flag - On");
        for (i = 0; i < (Minesweeper.dimensions * Minesweeper.dimensions); i++) {
            select[i] = new JButton(Integer.toString(i)); //makes it easier to get the values

            if (x == Minesweeper.dimensions - 1) {   
                select[i].addActionListener(this);
                select[i].setBounds(60 * (x + 1), 35 * (y + 1), 60, 35);
                select[i].setVisible(true);
                select[i].setText(Integer.toString(i));
                select[i].setFont(new Font("Arial", Font.PLAIN, 12));
                select[i].setForeground(Color.BLACK);
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
                select[i].setFont(new Font("Arial", Font.PLAIN, 12));
                select[i].setForeground(Color.BLACK);
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

                calcCoordinates(i);

                Minesweeper.row = y;
                Minesweeper.column = x;

                System.out.println("x: " + Minesweeper.column + " y: " + Minesweeper.row + " was selected");
                Minesweeper.playGame(Minesweeper.board, Minesweeper.dimensions, Minesweeper.isMine);
                System.out.println("check");
                if (Minesweeper.flag == 'f') { //doesnt go through here after it is done the first time
                    System.out.println("Update Flag UI");
                    updateFlagUI(i);
                } else {
                    updateUI();
                }
                break;

            } else if (e.getActionCommand().equals("Flag - On")) {
                Minesweeper.flag = 'f';
                System.out.println("Flag Status: " + Minesweeper.flag);
                //System.out.println("Flag On " + flag);
                bFlag.setText("Flag - Off");
                break;   
            } else if (e.getActionCommand().equals("Flag - Off")) {
                Minesweeper.flag = 'x';
                System.out.println("Flag Status: " + Minesweeper.flag);
                //System.out.println("Flag Off " + flag);
                bFlag.setText("Flag - On");
                break;
            }
        }
        /*
        if (Minesweeper.checkGameWon(Minesweeper.board, Minesweeper.dimensions, Minesweeper.isMine) || (Minesweeper.checkGameLost(Minesweeper.isMine, Minesweeper.row, Minesweeper.column)) {
            dispose();
        }
        */
    }

    public void updateUI() {
        for (int i = 0; i < Minesweeper.dimensions * Minesweeper.dimensions; i++) {
            calcCoordinates(i);
            Minesweeper.row = y;
            Minesweeper.column = x;
            gameBoard[i] = Minesweeper.board[Minesweeper.row][Minesweeper.column];
            if (gameBoard[i] == '-') {
                select[i].setText(Integer.toString(i));
                select[i].setEnabled(true);
            } else if (gameBoard[i] == 'f'){
                select[i].setText(String.valueOf("f"));
                select[i].setEnabled(true); 
            } else {
                select[i].setText(String.valueOf(gameBoard[i]));
                select[i].setEnabled(false); 
            }
            //select[i].setText(String.valueOf(gameBoard[i]));
        }
    }

    public void updateFlagUI(int i) {
        calcCoordinates(i);
        Minesweeper.row = y;
        Minesweeper.column = x;
        gameBoard[i] = Minesweeper.board[Minesweeper.row][Minesweeper.column];

        if (gameBoard[i] == '-') {
            select[i].setText("-");
        } else if (gameBoard[i] == 'f') {
            select[i].setText("f");
        }
    }

    public void calcCoordinates(int i) {
        int value = i % Minesweeper.dimensions;
        for (int j = 0; j < Minesweeper.dimensions * Minesweeper.dimensions; j++) {
            if (j == i) {
                y = (j / Minesweeper.dimensions) % Minesweeper.dimensions;
                x = value;
            }
        }
    }
}  