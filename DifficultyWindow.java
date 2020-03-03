import java.awt.event.*;
import javax.swing.*;

public class DifficultyWindow extends JFrame implements ActionListener{

    private static final long serialVersionUID = 1L;
    Minesweeper game;
    JLabel title;
    JButton beginner;
    JButton intermediate;
    JButton advanced;
    JButton login;
    JButton register;
    GameWindow gameWindow;
    int difficulty;
    public DifficultyWindow() {
        setLayout(null);

        super.setTitle("Minesweeper");
        super.setSize(500, 200);
        super.setResizable(false);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);

        title = new JLabel("Minesweeper");
        beginner = new JButton("Beginner");
        intermediate = new JButton("Intermediate");
        advanced = new JButton("Advanced");
        login = new JButton("Login");
        register = new JButton("Register");

        beginner.addActionListener(this);
        intermediate.addActionListener(this);
        advanced.addActionListener(this);
        login.addActionListener(this);
        register.addActionListener(this);

        title.setBounds(200, 10, 80, 40);
        beginner.setBounds(40, 85, 100, 40);
        intermediate.setBounds(180, 85, 120, 40);
        advanced.setBounds(340, 85, 100, 40);
        login.setBounds(40, 15, 100, 30);
        register.setBounds(340, 15, 100, 30);

        super.add(title);
        super.add(beginner);
        super.add(intermediate);
        super.add(advanced);
        super.add(login);
        super.add(register);

    }

    public void actionPerformed(ActionEvent e) { //separate the clicks
        String str = e.getActionCommand();
        if (str.equals("Beginner")) {
            beginner.setText("Selected");
            beginner.setEnabled(false);
            gameWindow = new GameWindow();            
        } else if (str.equals("Intermediate")) {
            gameWindow = new GameWindow();
        } else if (str.equals("Advanced")) {
            gameWindow = new GameWindow();
        } else if (str.equals("Login")) {
            //new window to login, once logged in, they should go straight into the game as that user
        } else if (str.equals("Register")) {
            //new window to register, once registered, they should go straight into the database and their information will be added to the oit
        }

    }
}