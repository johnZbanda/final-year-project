import java.sql.*;

public class MySQLConnection {

    public MySQLConnection() {

    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Minesweeper", "root", "root");
            //Database name = Minesweeper
            /*
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from minesweeper.users"); //will fetch everything from users table
            while (rs.next()) { //int, string, string, string, int, int, int, time, time, time
                //userId, username, password, email, gamesPlayed, gamesWon, percentOfWins, bestTime, aveTime, totalTime
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3));
                con.close();
            }
            */
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return con;
    }
}