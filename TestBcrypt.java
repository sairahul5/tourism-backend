import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBcrypt {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = "$2a$10$LFCgjAqSFZ0nfa49vKzGK.z0fh3EVPsttdjcao7v/Hlu10S3n4t0K";
        String[] passwords = {"admin", "admin123", "password", "123456", "root", "Admin123", "Admin@123"};
        for(String p : passwords) {
            if(encoder.matches(p, hash)) {
                System.out.println("Match found: " + p);
                return;
            }
        }
        System.out.println("No match found");
    }
}
