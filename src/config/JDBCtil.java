import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCtil {
    public static void main(String[] args) {
        // Thông tin kết nối
        String url = "jdbc:mysql://localhost:3306/Face_Recognition"; // Thay "mydb" bằng tên database của bạn
        String username = "root"; // Tên người dùng MySQL
        String password = "Ngoctu280105@"; // Mật khẩu MySQL

        // Kết nối
        try {
            // Đăng ký driver (không cần từ Java 7 trở lên, nhưng để chắc chắn)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Tạo kết nối
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Kết nối thành công!");

            // Tạo statement để truy vấn
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users"; // Thay "users" bằng tên bảng của bạn
            ResultSet resultSet = statement.executeQuery(query);

            // Xử lý kết quả
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") + ", Name: " + resultSet.getString("name"));
            }

            // Đóng kết nối
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối: " + e.getMessage());
            e.printStackTrace();
        }
    }
}