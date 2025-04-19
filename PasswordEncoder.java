import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {
    // Mã hóa mật khẩu
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Xác minh mật khẩu
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public static void main(String[] args) {
        // Ví dụ sử dụng
        String password = "12345678";
        
        // Mã hóa
        String hashedPassword = hashPassword(password);
        System.out.println("Hashed Password: " + hashedPassword);
        
        // Xác minh
        boolean isMatch = verifyPassword(password, hashedPassword);
        System.out.println("Password Match: " + isMatch);
        
        // Xác minh với mật khẩu sai
        boolean isWrongMatch = verifyPassword("WrongPassword", hashedPassword);
        System.out.println("Wrong Password Match: " + isWrongMatch);
    }
}
