import java.awt.*;
import java.awt.event.*; //For ActionListener
import javax.swing.*; //Using Java Swing for 
import java.sql.*;
import java.util.logging.*;

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
    JButton stats;
    JButton playAgain;
    JButton instructions;
    JLabel timer;
    JLabel flagsLeft;
    private final long start;
    double time = 0.0;
    
    public GameWindow() {
        x = 0;
        y = 0;
        start = System.currentTimeMillis();
        setLayout(null);
        super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        select = new JButton[Minesweeper.dimensions * Minesweeper.dimensions]; //initialise buttons
        gameBoard = new char[Minesweeper.dimensions * Minesweeper.dimensions]; //used to value of the board
        mineBoard = new boolean[Minesweeper.dimensions * Minesweeper.dimensions]; //used to hold value of the mines
        bFlag = new JButton("Flag - On");
        quit = new JButton("Quit");
        timer = new JLabel("Timer: " + formatTime(time));
        flagsLeft = new JLabel ("Flags Left: " + Minesweeper.mines);
        playAgain = new JButton("Play Again");
        stats = new JButton("Stats");
        instructions = new JButton("Instructions");
        Minesweeper.flag = 'x';
        for (i = 0; i < (Minesweeper.dimensions * Minesweeper.dimensions); i++) {
            select[i] = new JButton(Integer.toString(i)); //makes it easier to get the values
            if (x == Minesweeper.dimensions - 1) {   
                select[i].setBounds(50 * (x + 1), 28 * (y + 1), 50, 28);
                gameBoard[i] = Minesweeper.board[x][y];
                mineBoard[i] = Minesweeper.isMine[x][y];
                x = 0; y++;            
            } else {            
                select[i].setBounds(50 * (x + 1), 28 * (y + 1), 50, 28);             
                gameBoard[i] = Minesweeper.board[x][y];
                mineBoard[i] = Minesweeper.isMine[x][y];
                x++;
            }
            select[i].addActionListener(this);
            select[i].setVisible(true);
            select[i].setFont(new Font("Arial", Font.BOLD, 9));
            select[i].setForeground(Color.DARK_GRAY);
            select[i].setBackground(Color.DARK_GRAY);
            super.add(select[i]);
        }
        bFlag.addActionListener(this);
        quit.addActionListener(this);
        playAgain.addActionListener(this);
        stats.addActionListener(this);
        instructions.addActionListener(this);

        bFlag.setFont(new Font("Arial", Font.BOLD, 10));
        quit.setFont(new Font("Arial", Font.BOLD, 10));
        timer.setFont(new Font("Arial", Font.BOLD, 10));
        flagsLeft.setFont(new Font("Arial", Font.BOLD, 10));
        playAgain.setFont(new Font("Arial", Font.BOLD, 10));
        stats.setFont(new Font("Arial", Font.BOLD, 10));
        instructions.setFont(new Font("Arial", Font.BOLD, 9));

        bFlag.setBackground(Color.BLACK);
        quit.setBackground(Color.BLACK);
        playAgain.setBackground(Color.BLACK);
        stats.setBackground(Color.BLACK);
        instructions.setBackground(Color.BLACK);    

        bFlag.setForeground(Color.WHITE);
        quit.setForeground(Color.WHITE);
        playAgain.setForeground(Color.WHITE);
        stats.setForeground(Color.WHITE);
        timer.setForeground(Color.BLACK);
        flagsLeft.setForeground(Color.BLACK);
        instructions.setForeground(Color.WHITE);
        super.getContentPane().setBackground(Color.LIGHT_GRAY);
        
        switch (Minesweeper.difficulty) {
            case 1: 
                bFlag.setBounds(510, 30, 90, 30); //57.7
                quit.setBounds(510, 70, 90, 30);
                timer.setBounds(510, 105, 90, 30);
                flagsLeft.setBounds(510, 125, 90, 30);
                playAgain.setBounds(510, 160, 90, 30);
                stats.setBounds(510, 200, 90, 30);
                instructions.setBounds(510, 240, 90, 30);
                super.setSize(650,350); //72.2, 38.8
                super.setTitle("Minesweeper - Beginner");        
                break;
            case 2:
                bFlag.setBounds(860, 30, 90, 30); //55.625
                quit.setBounds(860, 70, 90, 30);
                timer.setBounds(860, 105, 90, 30);
                flagsLeft.setBounds(860, 125, 90, 30);
                playAgain.setBounds(860, 160, 90, 30);
                stats.setBounds(860, 200, 90, 30);
                instructions.setBounds(860, 240, 90, 30);
                super.setSize(1000,550); //62.5, 34.375
                super.setTitle("Minesweeper - Intermediate");
                break;
            case 3:
                bFlag.setBounds(1260, 30, 90, 30); //52.5
                quit.setBounds(1260, 70, 90, 30);
                timer.setBounds(1260, 105, 90, 30);
                flagsLeft.setBounds(1260, 125, 90, 30);
                playAgain.setBounds(1260, 160, 90, 30);
                stats.setBounds(1260, 200, 90, 30);
                instructions.setBounds(1260, 240, 90, 30);
                super.setSize(1720,980); //71.6, 40.8
                super.setTitle("Minesweeper - Advanced");
                break;
            case 4:
                bFlag.setBounds(1260, 30, 90, 30); //52.5
                quit.setBounds(1260, 70, 90, 30);
                timer.setBounds(1260, 105, 90, 30);
                flagsLeft.setBounds(1260, 125, 90, 30);
                playAgain.setBounds(1260, 160, 90, 30);
                stats.setBounds(1260, 200, 90, 30);
                instructions.setBounds(1260, 240, 90, 30);
                super.setSize(1720,980); //71.6, 40.8
                super.setSize(Minesweeper.dimensions * 72,Minesweeper.dimensions * 37);
                super.setTitle("Minesweeper - Custom");
                break;
        }
        bFlag.setEnabled(true);
        quit.setEnabled(true);
        playAgain.setEnabled(true);
        stats.setEnabled(true);
        instructions.setEnabled(true);
        super.add(quit);
        super.add(bFlag);
        super.add(timer);
        super.add(flagsLeft);
        super.add(playAgain);
        super.add(stats);
        super.add(instructions);
        super.setResizable(true);
        super.setVisible(true);
    }
    
    public double elaspedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

    @Override
    public void actionPerformed(ActionEvent e) { //activates whena  button has been clicked
        boolean gameWon = false;
        boolean gameLost = false;
        for (int i = 0; i < (Minesweeper.dimensions * Minesweeper.dimensions); i++) {
            System.out.println(i + ": Pressed");
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
                        gameLost = true;
                        loseUI();
                    } else if (Minesweeper.checkGameWon(Minesweeper.board, Minesweeper.dimensions, Minesweeper.isMine)) {
                        gameWon = true;
                        updateUI();
                    } else {
                        updateUI();    
                    }                 
                }
                calcCoordinates(i); //used for displaying the figure when it says you'v el

                Minesweeper.row = y;
                Minesweeper.column = x;
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
            } else if (e.getActionCommand().equals("Play Again")) {
                //System.out.println("Playing Again");
                dispose();
                Minesweeper.chooseDifficulty();
                break;
            } else if (e.getActionCommand().equals("Stats")) { 
                if (DifficultyWindow.userID > 0) { //if user has logged in
                    PreparedStatement ps;
                    ResultSet rs;
                    String query = "SELECT * FROM `users` WHERE `idUsers` = ?";
                    try {
                        ps = MySQLConnection.getConnection().prepareStatement(query);
                        ps.setInt(1, DifficultyWindow.userID);

                        rs = ps.executeQuery();
                        while (rs.next()) {
                            String username = rs.getString("username");
                            int gamesPlayed = rs.getInt("gamesPlayed");
                            double percentageOfWins = rs.getDouble("percentageOfWins");
                            double bestTime = rs.getDouble("bestTime");
                            String bestOutput = formatTime(bestTime);
                            double averageTime = rs.getDouble("averageTime");
                            String averageOutput = formatTime(averageTime);
                            double totalTime = rs.getDouble("totalTime");
                            String totalOutput = formatTime(totalTime);
                            JOptionPane.showMessageDialog(null, "Username: " + username + "\nGames Played: " + gamesPlayed 
                            + "\nPercentage of Wins: " + percentageOfWins + "% \nBest Time: " + bestOutput + "\nAverage Time: " + 
                            averageOutput + "\nTotal Time: " + totalOutput, username + " statistics", 2);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else { //if user has not
                    JOptionPane.showMessageDialog(null, "You are not logged in");
                }
                break;
            } else if (e.getActionCommand().equals("Instructions")) { //implemented instructions button on game window as well
                DifficultyWindow.readInstructions();
                break;
            }
        }

        flagsLeft.setText("Flags Left: " + (Minesweeper.mines - Minesweeper.flagTotal));

        time = this.elaspedTime(); //timer works
        String timeString = formatTime(time);
        timer.setText("Timer: " + timeString);
        //updates after selectig a button
        if (gameWon || gameLost) {
            //db = database value
            if (DifficultyWindow.userID > 0) {
                int dbWin = 0;
                if (gameWon) {
                    dbWin = 1;
                }
                PreparedStatement ps;
                String query = "INSERT INTO `game` (`userID`, `difficulty`, `timeTaken`, `win`) VALUES (?,?,?,?)";
                //adding into game table
                try {
                    ps = MySQLConnection.getConnection().prepareStatement(query);
                    ps.setInt(1, DifficultyWindow.userID);
                    ps.setInt(2, Minesweeper.difficulty);
                    ps.setDouble(3, time);
                    ps.setInt(4, dbWin);
                    if (ps.executeUpdate() > 0) {
                        displayWinOrLossMessage(gameWon, gameLost);
                        JOptionPane.showMessageDialog(null, "Game saved into database - GAME TABLE", "GAME Table Updated", 2);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                //updating the user table           
                //a bit awkward to refactor 
                int gamesPlayed = getGamesPlayed();
                double percentageWin = getPercentageWin(gamesPlayed, dbWin);
                double bestTime = getBestTime(dbWin);
                double averageTime = getAverageTime();
                double totalTime = getTotalTime();
                //queryies needed to assign update values
                query = "UPDATE `users` SET `gamesPlayed` = ?, `percentageOfWins` = ?, `bestTime` = ?, `averageTime` = ?, `totalTime` = ? WHERE `idUsers` = ?;";
                try { 
                    ps = MySQLConnection.getConnection().prepareStatement(query);
                    ps.setInt(1, gamesPlayed);
                    ps.setDouble(2, percentageWin);
                    ps.setDouble(3, bestTime);
                    ps.setDouble(4, averageTime);
                    ps.setDouble(5, totalTime);
                    ps.setInt(6, DifficultyWindow.userID);
                    if (ps.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "User table updated", "USER Table Updated", 2);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                displayWinOrLossMessage(gameWon, gameLost);
            }
        }
    }

    public int getGamesPlayed() { //works
        int gamesPlayed = 0;
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT COUNT(0) AS `gamesPlayed` FROM `game` WHERE `userID` = ?";
        try {
            ps = MySQLConnection.getConnection().prepareStatement(query);
            ps.setInt(1, DifficultyWindow.userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                gamesPlayed = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gamesPlayed;
    }

    public double getPercentageWin(int gamesPlayed, int dbWin) {
        int wonGames = 0;
        double percentageWin = 0.00;
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT COUNT(0) FROM `game` WHERE `userID` = ? AND `win` = ?";
        try {
            ps = MySQLConnection.getConnection().prepareStatement(query);
            ps.setInt(1, DifficultyWindow.userID);
            ps.setInt(2, dbWin);
            rs = ps.executeQuery();
            if (rs.next()) {
                wonGames = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        percentageWin = (wonGames * 100.00 / gamesPlayed);
        return percentageWin;
    }

    public double getBestTime(int dbWin) {
        double bestTime = 0;
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT MIN(`timeTaken`) AS `bestTime` FROM `game` WHERE `userID` = ? AND win = ?";
        try {
            ps = MySQLConnection.getConnection().prepareStatement(query);
            ps.setInt(1, DifficultyWindow.userID);
            ps.setInt(2, dbWin);
            rs = ps.executeQuery();
            if (rs.next()) {
                bestTime = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bestTime;
    }

    public double getAverageTime() {
        double averageTime = 0;
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT AVG(`timeTaken`) AS `averagetime` FROM `game` WHERE userID = ?";
        try {
            ps = MySQLConnection.getConnection().prepareStatement(query);
            ps.setInt(1, DifficultyWindow.userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                averageTime = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return averageTime;
    }

    public double getTotalTime() {
        double totalTime = 0;
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT SUM(`timeTaken`) AS `totalTime` FROM `game` WHERE `userID` = ?";
        try {
            ps = MySQLConnection.getConnection().prepareStatement(query);
            ps.setInt(1, DifficultyWindow.userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                totalTime = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalTime;
    }

    public void displayWinOrLossMessage(boolean gameWon, boolean gameLost) {
        if (gameWon) {
            JOptionPane.showMessageDialog(null, "CONGRATULATIONS, YOU WON!! Time = " + time, "YOU WIN!", 2);
        }
        if (gameLost) {
            JOptionPane.showMessageDialog(null, "TOO BAD, YOU LOST!! Time = " + time + " Mine found at: " + Minesweeper.column + "," + Minesweeper.row, "YOU LOSE", 2);
        }
    }

    public void updateUI() { //updates the ui after button has been clicked
        for (int i = 0; i < Minesweeper.dimensions * Minesweeper.dimensions; i++) {
            calcCoordinates(i);
            Minesweeper.row = y;
            Minesweeper.column = x;
            gameBoard[i] = Minesweeper.board[Minesweeper.row][Minesweeper.column];
            
            if (Minesweeper.checkGameWon(Minesweeper.board, Minesweeper.dimensions, Minesweeper.isMine)) {
                Minesweeper.playGame(Minesweeper.board, Minesweeper.dimensions, Minesweeper.isMine); //inefficient but works
                if (gameBoard[i] == 'f'){
                    select[i].setText("F");
                    select[i].setForeground(Color.BLUE);
                    select[i].setBackground(Color.BLUE);
                } else { //technically only need to put flags where they are
                    select[i].setText(String.valueOf(gameBoard[i]));
                }
                select[i].setEnabled(false);
                bFlag.setEnabled(false);
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
            if (gameBoard[i] == '-') { //do nothing to squares that have been unselected
                select[i].setText("-");
            } else if (gameBoard[i] == 'w') { //incorrect flags
                select[i].setText("W");
                select[i].setBackground(Color.ORANGE);
            } else if (gameBoard[i] == 'f') { //correct flag placements
                select[i].setText("F");
                select[i].setBackground(Color.BLUE);
            } else if (gameBoard[i] == 'X') { //positions of mines
                select[i].setBackground(Color.RED);
                select[i].setText("X");
            }
            select[i].setEnabled(false);
        }
        bFlag.setEnabled(false);
    }

    public void updateFlagUI(int i) { //updates ui when flag has been selected
        calcCoordinates(i);
        Minesweeper.row = y;
        Minesweeper.column = x;
        gameBoard[i] = Minesweeper.board[Minesweeper.row][Minesweeper.column];
        
        //playGame will be before this statement
        if (gameBoard[i] == '-') {
            select[i].setForeground(Color.DARK_GRAY);
            select[i].setBackground(Color.DARK_GRAY);
            select[i].setText(Integer.toString(i));
        } else if (gameBoard[i] == 'f') {
            select[i].setForeground(Color.YELLOW);
            select[i].setBackground(Color.YELLOW);
            select[i].setText(Integer.toString(i) + " f");
        }
    }

    public void calcCoordinates(int i) { //calculates the coordinates
        int value = i % Minesweeper.dimensions;
        for (int j = 0; j < Minesweeper.dimensions * Minesweeper.dimensions; j++) {
            if (j == i) {
                y = (j / Minesweeper.dimensions) % Minesweeper.dimensions;
                x = value;
            }
        }
    }

    public String formatTime(double time) { //formats time to hours, minutes and seconds
        int timeFormatted = (int) time;
        int hours = timeFormatted / 3600;
        int minutes = (timeFormatted - hours * 3600) / 60;
        int seconds = (timeFormatted - hours * 3600) - minutes * 60;
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return timeString;
    }
}  