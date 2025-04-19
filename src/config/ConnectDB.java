package config;

import java.sql.*;

public class ConnectDB {
    private static final String URL = "jdbc:mysql://localhost:3306/quanlykhachsan";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            // Nếu connection đã bị đóng hoặc là null, tạo kết nối mới
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("MySQL connection successful!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            e.printStackTrace();
            connection = null; // Đặt lại connection thành null nếu kết nối thất bại
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("MySQL connection closed successfully!");
                connection = null; // Đặt lại connection thành null sau khi đóng
            } catch (SQLException e) {
                System.out.println("Lỗi khi đóng kết nối: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}