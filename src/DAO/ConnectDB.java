package DAO;

import java.sql.*;

public class ConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/quanlykhachsan";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("MySQL connection successful!");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("MySQL connection closed successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}