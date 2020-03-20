import java.sql.*;
import java.util.logging.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterWindow extends JFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;

    JButton register;
    JTextField usernameTField;
    JTextField passwordTField;
    JTextField repassTField;

    public RegisterWindow() {
        JFrame f = new JFrame("Register Window");
        f.getContentPane().setLayout(new FlowLayout());
        usernameTField = new JTextField("Username: ", 10);
        passwordTField = new JTextField("Password: ", 10); //text in the box, columns
        repassTField = new JTextField("Re-Enter Password: ", 10);
        f.getContentPane().add(usernameTField);
        f.getContentPane().add(passwordTField);
        f.getContentPane().add(repassTField);

        f.pack();
        f.setVisible(true);
    }
    
    public boolean checkUsername(String username) { //checks if the username has already been taken
        boolean check = false;
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM `minesweeper.users` WHERE username = ?";

        try {
           ps = MySQLConnection.getConnection().prepareStatement(query);
           ps.setString(1, username); //why is it, 1 and username? 
           
           rs = ps.executeQuery();

           if (rs.next()) {
               check = true;
           }
        } catch (SQLException ex) {
            Logger.getLogger(RegisterWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String uName = usernameTField.getText();
        String pWord = passwordTField.getText();
        String rpWord = repassTField.getText();

        if (uName.equals("")) {
            JOptionPane.showMessageDialog(null, "Add a Username");
        } else if (pWord.equals("")) {
            JOptionPane.showMessageDialog(null, "Add a Password");
        } else if (rpWord.equals(pWord)) {
            JOptionPane.showMessageDialog(null, "Retype the Password again");
        } else {
            PreparedStatement ps;
            String query = "INSERT INTO `minesweeper.user` (`username`, `password`, `gamesPlayed`, `percentageOfWins`, `bestTime`, `averageTime`, `totalTime`) VALUES (?,?,0,0,0,0,0)";

            try {
                ps = MySQLConnection.getConnection().prepareStatement(query);
                ps.setString(1, uName);
                ps.setString(2, pWord);

                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "New User Added");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegisterWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


}