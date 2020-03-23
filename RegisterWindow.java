import java.sql.*;
import java.util.logging.*;
import javax.swing.*;
import java.awt.event.*;
//import com.mysql.jdbc.Driver;

public class RegisterWindow extends JFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;

    JButton register;
    JTextField usernameTField;
    JTextField passwordTField;
    JTextField repassTField;
    JLabel usernameLabel, passwordLabel, repassLabel;

    public RegisterWindow() {
        setLayout(null);

        super.setTitle("Register Window");
        super.setSize(400, 230);
        super.setResizable(true);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        super.setVisible(true);

        register = new JButton("Register");
        usernameTField = new JTextField();
        passwordTField = new JTextField(); //text in the box, columns
        repassTField = new JTextField();
        usernameLabel = new JLabel(" - Username");
        passwordLabel = new JLabel(" - Password");
        repassLabel = new JLabel(" - Retype Password");

        register.addActionListener(this);

        register.setBounds(140, 120, 100, 40);
        usernameTField.setBounds(30, 20, 200, 30);
        passwordTField.setBounds(30, 50, 200, 30);
        repassTField.setBounds(30, 80, 200, 30);
        usernameLabel.setBounds(250, 20, 200, 30);
        passwordLabel.setBounds(250, 50, 200, 30);
        repassLabel.setBounds(250, 80, 200, 30);

        super.add(register);
        super.add(usernameTField);
        super.add(passwordTField);
        super.add(repassTField);
        super.add(usernameLabel);
        super.add(passwordLabel);
        super.add(repassLabel);

    }
    
    public boolean checkUsername(String username) { //checks if the username has already been taken
        boolean check = false;
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM `users` WHERE username = ?";

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
        } else if (!rpWord.equals(pWord)) {
            JOptionPane.showMessageDialog(null, "Retype the Password again");
        } else if (checkUsername(uName)) {
            JOptionPane.showMessageDialog(null, "User already exists");
        } else {
            PreparedStatement ps;
            String query = "INSERT INTO `users` (`username`, `password`) VALUES (?,?)";
            //(" + uName + "," + pWord + ")"

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