import java.sql.*;
import java.util.logging.*;
import javax.swing.*;
import java.awt.event.*;

public class RegisterWindow extends JFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;

    JButton register;
    JTextField usernameTField;
    JTextField passwordTField;
    JTextField repassTField;

    public RegisterWindow() {
        setLayout(null);

        super.setTitle("Register Window");
        super.setSize(800, 200);
        super.setResizable(false);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        super.setVisible(true);

        register = new JButton("Register");
        usernameTField = new JTextField("Username: ", 10);
        passwordTField = new JTextField("Password: ", 10); //text in the box, columns
        repassTField = new JTextField("Re-Enter Password: ", 10);

        register.addActionListener(this);

        register.setBounds(200, 100, 100, 40);
        usernameTField.setBounds(70, 40, 200, 30);
        passwordTField.setBounds(200, 40, 200, 30);
        repassTField.setBounds(340, 40, 200, 30);

        super.add(register);
        super.add(usernameTField);
        super.add(passwordTField);
        super.add(repassTField);

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
            String query = "INSERT INTO `minesweeper.user` (`username`, `password`) VALUES (?,?)";

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