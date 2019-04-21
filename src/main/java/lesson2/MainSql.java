package lesson2;

import java.sql.*;

public class MainSql {
    private static Connection connection;
    private static Statement stmt;

    public static void main(String[] args) {
        try {
            connect();
            stmt.executeUpdate("DROP TABLE IF EXISTS Employe");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Employe " +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Name TEXT, " +
                    "Salary INTEGER)");

            stmt.executeUpdate("INSERT INTO Employe (Name, Salary) VALUES ('Robert1', 500)");
            stmt.executeUpdate("INSERT INTO Employe (Name, Salary) VALUES ('Robert2', 600)");
            stmt.executeUpdate("INSERT INTO Employe (Name, Salary) VALUES ('Robert3', 700)");
            stmt.executeUpdate("INSERT INTO Employe (Name, Salary) VALUES ('Robert4', 400)");
            stmt.executeUpdate("UPDATE Employe  SET Salary = 800 where Name = 'Robert1'");

            stmt.executeUpdate("DELETE FROM Employe WHERE Name = 'Robert2'");

            ResultSet rs = stmt.executeQuery("SELECT * FROM Employe");
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.print(rsmd.getColumnName(i) + "    ");
            }

            System.out.println();
            while (rs.next()){
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    System.out.print(rs.getString(i) + "    ");
                }
                System.out.println();
            }
            disconnect();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void connect() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:java3_DB.db");
        stmt = connection.createStatement();
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


