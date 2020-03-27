import java.awt.*;
import java.awt.event.*; //For ActionListener
import javax.swing.*; //Using Java Swing for 
import java.sql.*;
import java.util.logging.*;
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
    JButton stats;
    JButton playAgain;
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
        gameBoard = new char[Minesweeper.dimensions * Minesweeper.dimensions]; //used to hold
        //mineBoard = new boolean[Minesweeper.dimensions * Minesweeper.dimensions];
        //super.getContentPane().setBackground(Color.YELLOW);
        bFlag = new JButton("Flag - On");
        quit = new JButton("Quit");
        timer = new JLabel("Timer: ");
        flagsLeft = new JLabel ("Flags Left: " + (Minesweeper.mines - Minesweeper.flagTotal));
        playAgain = new JButton("Play Again");
        stats = new JButton("Stats");
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
        playAgain.addActionListener(this);
        stats.addActionListener(this);
        bFlag.setFont(new Font("Arial", Font.PLAIN, 10));
        quit.setFont(new Font("Arial", Font.PLAIN, 10));
        timer.setFont(new Font("Arial", Font.BOLD, 10));
        flagsLeft.setFont(new Font("Arial", Font.BOLD, 10));
        playAgain.setFont(new Font("Arial", Font.BOLD, 10));
        stats.setFont(new Font("Arial", Font.BOLD, 10));
        switch (Minesweeper.difficulty) {
            case 1:
                bFlag.setBounds(520, 40, 90, 30);
                quit.setBounds(520, 80, 90, 30);
                timer.setBounds(520, 120, 90, 30);
                flagsLeft.setBounds(520, 140, 90, 30);
                playAgain.setBounds(520, 180, 90, 30);
                stats.setBounds(520, 220, 90, 30);
                super.setSize(650,350);
                super.setTitle("Minesweeper - Beginner");
                break;
            case 2:
                bFlag.setBounds(890, 40, 90, 30);
                quit.setBounds(890, 80, 90, 30);
                timer.setBounds(890, 120, 90, 30);
                flagsLeft.setBounds(890, 140, 90, 30);
                playAgain.setBounds(890, 180, 90, 30);
                stats.setBounds(890, 220, 90, 30);
                super.setSize(1050,550);
                super.setTitle("Minesweeper - Intermediate");
                break;
            case 3:
                bFlag.setBounds(1260, 40, 90, 30);
                quit.setBounds(1260, 80, 90, 30);
                timer.setBounds(1260, 120, 90, 30);
                flagsLeft.setBounds(1260, 140, 90, 30);
                playAgain.setBounds(1260, 180, 90, 30);
                stats.setBounds(1260, 220, 90, 30);
                super.setSize(1720,980);
                super.setTitle("Minesweeper - Advanced");
                break;
        }
        bFlag.setEnabled(true);
        quit.setEnabled(true);
        playAgain.setEnabled(true);
        stats.setEnabled(true);
        super.add(quit);
        super.add(bFlag);
        super.add(timer);
        super.add(flagsLeft);
        super.add(playAgain);
        super.add(stats);
        super.setResizable(true);
        super.setVisible(true);
    }
    
    public double elaspedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //wont need display board - might display for testing
        //implement playGame 
        boolean gameWon = false;
        boolean gameLost = false;
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
                if (DifficultyWindow.userID > 0) {
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
                            double averageTime = rs.getDouble("averageTime");
                            double totalTime = rs.getDouble("totalTime");
                            JOptionPane.showMessageDialog(null, "Username: " + username + "\nGames Played: " + gamesPlayed 
                            + "\nPercentage of Wins: " + percentageOfWins + "\nBest Time: " + bestTime + "\nAverage Time: " + 
                            averageTime + "\nTotal Time: " + totalTime, username + " statistics", 2);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(GameWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You are not logged in");
                }
                break;
            }
        }

        flagsLeft.setText("Flags Left: " + (Minesweeper.mines - Minesweeper.flagTotal));

        time = this.elaspedTime(); //timer works
        timer.setText("Timer: " + time);
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
                int gamesPlayed = getGamesPlayed();
                double percentageWin = getPercentageWin(gamesPlayed, dbWin);
                double bestTime = getBestTime(dbWin);
                double averageTime = getAverageTime();
                double totalTime = getTotalTime();
                query = "UPDATE `users` SET `gamesPlayed` = ?, `percentageOfWins` = ?, `bestTime` = ?, `averageTime` = ?, `totalTime` = ? WHERE `idUsers` = ?;";
                try { //table updated once and thats it for some reason?
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
}  