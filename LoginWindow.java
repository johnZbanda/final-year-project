import java.sql.*;
import java.util.logging.*;
import javax.swing.*;
import java.awt.event.*;

public class LoginWindow extends JFrame implements ActionListener{
    //used to login users
    private static final long serialVersionUID = 1L;

    JButton login;
    JTextField usernameTField;
    JTextField passwordTField;
    JLabel usernameLabel, passwordLabel;
    DifficultyWindow difficultyWindow;
    int userID = 0;

    public LoginWindow() {
        setLayout(null);

        super.setTitle("Login Window");
        super.setSize(400, 230);
        super.setResizable(true);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        super.setVisible(true);

        login = new JButton("Login");
        usernameTField = new JTextField();
        passwordTField = new JTextField(); //text in the box, columns
        usernameLabel = new JLabel(" - Username");
        passwordLabel = new JLabel(" - Password");

        login.addActionListener(this);

        login.setBounds(140, 120, 100, 40);
        usernameTField.setBounds(30, 20, 200, 30);
        passwordTField.setBounds(30, 50, 200, 30);
        usernameLabel.setBounds(250, 20, 200, 30);
        passwordLabel.setBounds(250, 50, 200, 30);

        super.add(login);
        super.add(usernameTField);
        super.add(passwordTField);
        super.add(usernameLabel);
        super.add(passwordLabel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PreparedStatement ps;
        ResultSet rs;
        String uName = usernameTField.getText();
        String pWord = passwordTField.getText();
        //String getIDquery = "SELECT `idUsers` FROM `users` WHERE `username` = ? AND `password` = ?";
        String query = "SELECT * FROM `users` WHERE `username` = ? AND `password` = ?";
        //will need to get the ID from that user
        try {
            ps = MySQLConnection.getConnection().prepareStatement(query);
            ps.setString(1, uName);
            ps.setString(2, pWord);

            rs = ps.executeQuery();

            if (rs.next()) { 
                DifficultyWindow.userID = rs.getInt(1);
                System.out.println("Login - User ID: " + DifficultyWindow.userID);
                JOptionPane.showMessageDialog(null, "Welcome " + uName + "!", "Login Succeeded", 2);
                //get the ID value here. Then send it to the difficulty and game windows.
                //Users might not be able to log out.
                DifficultyWindow.login.setText("Logout");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Username or Password", "Login Failed", 2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}