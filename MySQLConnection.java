import java.sql.*;

public class MySQLConnection {

    public MySQLConnection() {

    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Minesweeper", "root", "root"); 
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return con;
    }
}