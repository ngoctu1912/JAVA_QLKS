package config;

import java.sql.*;

public class ConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/quanlykhachsan?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            return null;
        }
    }
}