package com.tourism;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BcryptTest {
    @Test
    public void testHash() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("MY_HASH_START=" + encoder.encode("admin123") + "=MY_HASH_END");
        System.out.println("MATCH_2B=" + encoder.matches("admin123", "$2b$10$.hpM9SI/FTx9AnqaSrMcvOig1PvLam5BInE3BsRuywFPxiqSU1Gd2"));
    }
}
