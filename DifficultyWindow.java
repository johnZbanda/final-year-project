import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.util.logging.*;

public class DifficultyWindow extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    JLabel title;
    JButton beginner, intermediate, advanced, login, register, custom, scoreBoard;
    int difficulty;
    static int userID = 0;
    String customDimension;
    String customMine;
    //Create a Scoreboard, possibly a theme button
    public DifficultyWindow() {
        setLayout(null);

        super.setTitle("MINESWEEPER");
        super.setSize(500, 220);
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);

        title = new JLabel("MINESWEEPER");
        beginner = new JButton("Beginner");
        intermediate = new JButton("Intermediate");
        advanced = new JButton("Advanced");
        login = new JButton("Login");
        register = new JButton("Register");
        custom = new JButton("Custom");
        scoreBoard = new JButton("Scoreboard");

        beginner.addActionListener(this);
        intermediate.addActionListener(this);
        advanced.addActionListener(this);
        login.addActionListener(this);
        register.addActionListener(this);
        custom.addActionListener(this);
        scoreBoard.addActionListener(this);

        title.setFont(new Font("Arial", Font.BOLD, 20));
        beginner.setFont(new Font("Arial", Font.BOLD, 12));
        intermediate.setFont(new Font("Arial", Font.BOLD, 12));
        advanced.setFont(new Font("Arial", Font.BOLD, 12));
        login.setFont(new Font("Arial", Font.BOLD, 12));
        register.setFont(new Font("Arial", Font.BOLD, 12));
        custom.setFont(new Font("Arial", Font.BOLD, 12));
        scoreBoard.setFont(new Font("Arial", Font.BOLD, 12));

        title.setForeground(Color.RED);
        beginner.setForeground(Color.BLACK);
        intermediate.setForeground(Color.BLACK);
        advanced.setForeground(Color.BLACK);
        login.setForeground(Color.BLACK);
        register.setForeground(Color.BLACK);
        custom.setForeground(Color.BLACK);
        scoreBoard.setForeground(Color.BLACK);

        beginner.setBackground(Color.GREEN);
        intermediate.setBackground(Color.ORANGE);
        advanced.setBackground(Color.RED);
        login.setBackground(Color.LIGHT_GRAY);
        register.setBackground(Color.LIGHT_GRAY);
        custom.setBackground(Color.CYAN);
        scoreBoard.setBackground(Color.YELLOW);

        title.setBounds(168, 10, 200, 40);
        beginner.setBounds(40, 70, 100, 40);
        intermediate.setBounds(180, 70, 120, 40);
        advanced.setBounds(340, 70, 100, 40);
        login.setBounds(40, 15, 100, 30);
        register.setBounds(340, 15, 100, 30);
        custom.setBounds(100, 130, 120, 40);
        scoreBoard.setBounds(260, 130, 120, 40);

        super.add(title);
        super.add(beginner);
        super.add(intermediate);
        super.add(advanced);
        super.add(login);
        super.add(register);
        super.add(custom);
        super.add(scoreBoard);
    }

    public void actionPerformed(ActionEvent e) { //separate the clicks
        String str = e.getActionCommand();
        if (str.equals("Beginner")) { 
            Minesweeper.difficulty = 1;
            Minesweeper.chooseDifficulty();   
            //gameWindow = new GameWindow();            
        } else if (str.equals("Intermediate")) {
            Minesweeper.difficulty = 2;
            Minesweeper.chooseDifficulty();   
        } else if (str.equals("Advanced")) {
            Minesweeper.difficulty = 3;
            Minesweeper.chooseDifficulty();   
        } else if (str.equals("Login")) {
            //new window to login, once logged in, they should go straight into the game as that user
            new LoginWindow();
        } else if (str.equals("Register")) {
            new RegisterWindow();
            //new window to register, once registered, they should go straight into the database and their information will be added to the oit
        } else if (str.equals("Custom")) {
            Minesweeper.difficulty = 4;
            JFrame f = new JFrame("Custom Dimensions");
            //Create a new window which sets the custom board
            customDimension = JOptionPane.showInputDialog(f, "Enter Dimensions (Between 9 and 25): ");
            customMine = JOptionPane.showInputDialog(f, "Enter Mines (Between 10 and 250): ");
            Minesweeper.dimensions = Integer.parseInt(customDimension);
            Minesweeper.mines = Integer.parseInt(customMine);
            //if cancelled, set to 0. - use try and catch - NumberFormatException
            if (Minesweeper.mines > (Minesweeper.dimensions * Minesweeper.dimensions) - Minesweeper.dimensions) {
                JOptionPane.showMessageDialog(null, "Too many mines");
            } else if (Minesweeper.dimensions < 9 || Minesweeper.dimensions > 24) {
                JOptionPane.showMessageDialog(null, "Too little/many dimensions");
            } else if (Minesweeper.mines < 10 || Minesweeper.mines > 250) {
                JOptionPane.showMessageDialog(null, "Too little/many mines");
            } else {
                Minesweeper.chooseDifficulty(); 
            }   
        } else if (str.equals("Scoreboard")) {
            getScoreBoard();
        }
    }

    public void getScoreBoard() {
        PreparedStatement ps;
        ResultSet rs;
        String uName[] = new String[100];
        double bestTime[] = new double[100];
        int count = 0;

        String query = "SELECT `username`, `bestTime` FROM `users` WHERE `bestTime` NOT IN (0) ORDER BY `bestTime`";

        try {
            ps = MySQLConnection.getConnection().prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()) {
                uName[count] = rs.getString("username");
                bestTime[count] = rs.getDouble("bestTime");
                count++;
            }

            String result = "";
            for (int i = 0; i < count; i++) {
                result = result + (i + 1) + ". " + uName[i] + " - Time: " + bestTime[i] + "\n";
            }
            JOptionPane.showMessageDialog(null, result, "Scoreboard - Best Time", 2);
        } catch (SQLException ex) {
            Logger.getLogger(DifficultyWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}