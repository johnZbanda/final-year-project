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
    JButton quit;
    private final long start;
    double time = 0.0;
    
    public GameWindow() {
        x = 0;
        y = 0;
        start = System.currentTimeMillis();
        setLayout(null);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        select = new JButton[Minesweeper.dimensions * Minesweeper.dimensions]; //initialise buttons
        gameBoard = new char[Minesweeper.dimensions * Minesweeper.dimensions]; //used to hold
        //mineBoard = new boolean[Minesweeper.dimensions * Minesweeper.dimensions];
        //super.getContentPane().setBackground(Color.YELLOW);
        bFlag = new JButton("Flag - On");
        quit = new JButton("Quit");
        Minesweeper.flag = 'x';
        for (i = 0; i < (Minesweeper.dimensions * Minesweeper.dimensions); i++) {
            select[i] = new JButton(Integer.toString(i)); //makes it easier to get the values

            if (x == Minesweeper.dimensions - 1) {   
                select[i].addActionListener(this);
                select[i].setBounds(50 * (x + 1), 28 * (y + 1), 50, 28);
                select[i].setVisible(true);
                select[i].setText(Integer.toString(i));
                select[i].setFont(new Font("Arial", Font.PLAIN, 9));
                select[i].setForeground(Color.BLACK);
                gameBoard[i] = Minesweeper.board[x][y];
                //mineBoard[i] = Minesweeper.isMine[x][y];
                //System.out.println("i: " + i + " x: " + x + " y: " + y + " mine: " + mineBoard[i]);
                x = 0; y++;
                super.add(select[i]);
            } else {
                select[i].addActionListener(this);
                select[i].setBounds(50 * (x + 1), 28 * (y + 1), 50, 28);
                select[i].setVisible(true);
                select[i].setText(Integer.toString(i));
                select[i].setFont(new Font("Arial", Font.PLAIN, 9));
                select[i].setForeground(Color.BLACK);
                gameBoard[i] = Minesweeper.board[x][y];
                //mineBoard[i] = Minesweeper.isMine[x][y];
                //System.out.println("i: " + i + " x: " + x + " y: " + y + " mine: " + mineBoard[i]);
                super.add(select[i]);
                x++;
            }
            // System.out.println("Button Value: "+select[i]);
        }
        bFlag.addActionListener(this);
        quit.addActionListener(this);
        bFlag.setFont(new Font("Arial", Font.PLAIN, 10));
        quit.setFont(new Font("Arial", Font.PLAIN, 10));
        switch (Minesweeper.difficulty) {
            case 1:
                bFlag.setBounds(520, 60, 90, 30);
                quit.setBounds(520, 120, 90, 30);
                super.setSize(650,350);
                super.setTitle("Minesweeper - Beginner");
                break;
            case 2:
                bFlag.setBounds(890, 60, 90, 30);
                quit.setBounds(890, 120, 90, 30);
                super.setSize(1050,550);
                super.setTitle("Minesweeper - Intermediate");
                break;
            case 3:
                bFlag.setBounds(1260, 60, 90, 30);
                quit.setBounds(1260, 120, 90, 30);
                super.setSize(1720,980);
                super.setTitle("Minesweeper - Advanced");
                break;
        }
        bFlag.setEnabled(true);
        quit.setEnabled(true);
        super.add(quit);
        super.add(bFlag);
        super.setResizable(true);
        super.setVisible(true);
    }
    
    public double elaspedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }
    //In Action Performed: You will need to implement
    /*
        - Stats button
        - Timer
        - Adding the timer and the difficulty when they've played a game
        - Update the users table after a game has been won or lost
    */

    @Override
    public void actionPerformed(ActionEvent e) {
        //wont need display board - might display for testing
        //implement playGame 
        time = this.elaspedTime(); //timer works
        System.out.println("Time: " + time);
        for (int i = 0; i < (Minesweeper.dimensions * Minesweeper.dimensions); i++) {
            System.out.println(i + ": Pressed");
            //seprate if statments
            calcCoordinates(i);

            Minesweeper.row = y;
            Minesweeper.column = x;
            if (e.getActionCommand().equals(Integer.toString(i))) { //issue, it does not go into if statement
                //issue is here. Does not check if flag is equal to f
                System.out.println(i + " was selected");

                System.out.println("x: " + Minesweeper.column + " y: " + Minesweeper.row + " was selected");
                Minesweeper.playGame(Minesweeper.board, Minesweeper.dimensions, Minesweeper.isMine);

                System.out.println("Checking Flag: " + Minesweeper.flag);
                if (Minesweeper.flag == 'f') { //doesnt go through here after it is done the first time
                    System.out.println("Update Flag UI");
                    updateFlagUI(i);
                } else {    
                    if (Minesweeper.checkGameLost(Minesweeper.isMine, Minesweeper.row, Minesweeper.column)) {
                        System.out.println("Check");
                        loseUI();
                    } else {
                        updateUI();    
                    }                 
                    changeNumberColour(i);
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
            } else if (e.getActionCommand().equals(Integer.toString(i) + " f")) {
                System.out.println("Check - Integer to String + F");
                Minesweeper.playGame(Minesweeper.board, Minesweeper.dimensions, Minesweeper.isMine);
                //updateUI();
                updateFlagUI(i);
                break;
            } else if (e.getActionCommand().equals("Quit")) {
                dispose();
                break;
            }
        }
    }

    public void updateUI() {
        for (int i = 0; i < Minesweeper.dimensions * Minesweeper.dimensions; i++) {
            calcCoordinates(i);
            Minesweeper.row = y;
            Minesweeper.column = x;
            gameBoard[i] = Minesweeper.board[Minesweeper.row][Minesweeper.column];
            
            if (Minesweeper.checkGameWon(Minesweeper.board, Minesweeper.dimensions, Minesweeper.isMine)) {
                Minesweeper.playGame(Minesweeper.board, Minesweeper.dimensions, Minesweeper.isMine); //inefficient but works
                if (gameBoard[i] == 'f'){
                    select[i].setText("f");
                } else {
                    select[i].setText(String.valueOf(gameBoard[i]));
                }
                select[i].setEnabled(false);
            } else {
                if (gameBoard[i] == '-') {
                    select[i].setText(Integer.toString(i));
                    select[i].setEnabled(true);
                } else if (gameBoard[i] == 'f'){
                    select[i].setText(String.valueOf(Integer.toString(i) + " f"));
                    select[i].setEnabled(true); 
                } else {
                    select[i].setText(String.valueOf(gameBoard[i]));
                    select[i].setEnabled(false); 
                }    
            }
            
        }
    }

    public void loseUI() {
        for (int i = 0; i < Minesweeper.dimensions * Minesweeper.dimensions; i++) {
            calcCoordinates(i);
            Minesweeper.row = y;
            Minesweeper.column = x;
            gameBoard[i] = Minesweeper.board[Minesweeper.row][Minesweeper.column];
            if (gameBoard[i] == '-') {
                select[i].setText("-");
            } else if (gameBoard[i] == 'w') {
                select[i].setText("W");
            } else if (gameBoard[i] == 'f') {
                select[i].setText("f");
            } else if (gameBoard[i] == 'X') {
                select[i].setText("X");
            }
            select[i].setEnabled(false);
        }
    }

    public void updateFlagUI(int i) {
        calcCoordinates(i);
        Minesweeper.row = y;
        Minesweeper.column = x;
        gameBoard[i] = Minesweeper.board[Minesweeper.row][Minesweeper.column];

        //playGame will be before this statement
        if (gameBoard[i] == '-') {
            select[i].setText(Integer.toString(i));
        } else if (gameBoard[i] == 'f') {
            select[i].setText(Integer.toString(i) + " f");
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

    public void changeNumberColour(int i) {
        gameBoard[i] = Minesweeper.board[Minesweeper.row][Minesweeper.column];
        switch (gameBoard[i]) {
            case '1':
                select[i].getRootPane().setForeground(Color.RED);
                break;
            case '2':
                select[i].setForeground(Color.BLUE);
                break;
        }
    }
}  