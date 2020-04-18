import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.logging.*;

public class DifficultyWindow extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    JLabel title;
    JButton beginner, intermediate, advanced, register, custom, scoreBoard;
    static JButton login;
    JButton instructions;
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
        instructions = new JButton("Instructions");

        beginner.addActionListener(this);
        intermediate.addActionListener(this);
        advanced.addActionListener(this);
        login.addActionListener(this);
        register.addActionListener(this);
        custom.addActionListener(this);
        scoreBoard.addActionListener(this);
        instructions.addActionListener(this);

        title.setFont(new Font("Arial", Font.BOLD, 20));
        beginner.setFont(new Font("Arial", Font.BOLD, 11));
        intermediate.setFont(new Font("Arial", Font.BOLD, 11));
        advanced.setFont(new Font("Arial", Font.BOLD, 11));
        login.setFont(new Font("Arial", Font.BOLD, 11));
        register.setFont(new Font("Arial", Font.BOLD, 11));
        custom.setFont(new Font("Arial", Font.BOLD, 11));
        scoreBoard.setFont(new Font("Arial", Font.BOLD, 11));
        instructions.setFont(new Font("Arial", Font.BOLD, 11));

        title.setForeground(Color.RED);
        beginner.setForeground(Color.BLACK);
        intermediate.setForeground(Color.BLACK);
        advanced.setForeground(Color.BLACK);
        login.setForeground(Color.BLACK);
        register.setForeground(Color.BLACK);
        custom.setForeground(Color.BLACK);
        scoreBoard.setForeground(Color.BLACK);
        instructions.setForeground(Color.BLACK);

        beginner.setBackground(Color.GREEN);
        intermediate.setBackground(Color.ORANGE);
        advanced.setBackground(Color.RED);
        login.setBackground(Color.LIGHT_GRAY);
        register.setBackground(Color.LIGHT_GRAY);
        custom.setBackground(Color.CYAN);
        scoreBoard.setBackground(Color.YELLOW);
        instructions.setBackground(Color.PINK);

        title.setBounds(168, 10, 200, 40);
        beginner.setBounds(40, 70, 100, 40);
        intermediate.setBounds(180, 70, 120, 40);
        advanced.setBounds(340, 70, 100, 40);
        login.setBounds(40, 15, 100, 30);
        register.setBounds(340, 15, 100, 30);
        custom.setBounds(40, 130, 100, 40);
        scoreBoard.setBounds(340, 130, 100, 40);
        instructions.setBounds(180, 130, 120, 40);

        super.add(title);
        super.add(beginner);
        super.add(intermediate);
        super.add(advanced);
        super.add(login);
        super.add(register);
        super.add(custom);
        super.add(scoreBoard);
        super.add(instructions);
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
            try {
                customDimension = JOptionPane.showInputDialog(f, "Enter Dimensions (Between 9 and 25): ");
                customMine = JOptionPane.showInputDialog(f, "Enter Mines (Between 10 and 250): ");
                if (customDimension.isEmpty() || customMine.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please input a number");
                } else {
                    Minesweeper.dimensions = Integer.parseInt(customDimension);
                    Minesweeper.mines = Integer.parseInt(customMine);
                    if (Minesweeper.mines > (Minesweeper.dimensions * Minesweeper.dimensions) - Minesweeper.dimensions) {
                        JOptionPane.showMessageDialog(null, "Too many mines");
                   } else if (Minesweeper.dimensions < 9 || Minesweeper.dimensions > 24) {
                        JOptionPane.showMessageDialog(null, "Too little/many dimensions");
                    } else if (Minesweeper.mines < 10 || Minesweeper.mines > 250) {
                        JOptionPane.showMessageDialog(null, "Too little/many mines");
                    } else {
                        Minesweeper.chooseDifficulty();
                    }
                }
            } catch (Exception ex) {
                System.out.println("Something went wrong");
                JOptionPane.showMessageDialog(null, "Please input a number");
            }
        } else if (str.equals("Scoreboard")) {
            getScoreBoard();
        } else if (str.equals("Instructions")) {
            readInstructions();
        } else if (str.equals("Logout")) {
            userID = 0;
            login.setText("Login");
            JOptionPane.showMessageDialog(null, "Successfully Logged out");
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
                String bestOutput = formatTime(bestTime[i]);
                result = result + (i + 1) + ". " + uName[i] + " - Time: " + bestOutput + "\n";
            }
            JOptionPane.showMessageDialog(null, result, "Scoreboard - Best Time", 2);
        } catch (SQLException ex) {
            Logger.getLogger(DifficultyWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readInstructions() {
        try {
            File file = new File("D:\\1finalyearproject\\final-year-project\\instructions.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            String instrString = "";
            while ((st = br.readLine()) != null) {
                instrString += st + "\n";
            }
            JOptionPane.showMessageDialog(null, instrString, "Instructions for Minesweeper", 2);
        } catch (Exception ex) {
            System.out.println("Something went wrong");
        }
    }

    public String formatTime(double time) {
        int timeFormatted = (int) time;
        int hours = timeFormatted / 3600;
        int minutes = (timeFormatted - hours * 3600) / 60;
        int seconds = (timeFormatted - hours * 3600) - minutes * 60;
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return timeString;
    }
}