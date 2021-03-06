import java.sql.*;
//import com.mysql.jdbc.Driver;

public class MySQLConnection { //used to establish MySQL Server database

    public MySQLConnection() {

    }

    // java -cp ".;mysql-connector-java-8.0.15.jar" Minesweeper
    // javac Minesweeper.java GameWindow.java DifficultyWindow.java LoginWindow.java RegisterWindow.java MySQLConnection.java

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/minesweeper", "root", "root");
            //System.out.println("Connected to database");
        } catch (Exception e) {
            System.out.println("MySQLConnection Error: " + e);
        }

        return con;
    }
}