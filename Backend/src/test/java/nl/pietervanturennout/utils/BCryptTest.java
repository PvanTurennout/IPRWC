package nl.pietervanturennout.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BCryptTest {

    @Test
    public void generateHashForTestPassword(){
        BCrypt crypt = new BCrypt();
        System.out.println(crypt.hash("Admin@8080"));
        System.out.println(crypt.hash("Seller@8080"));
        System.out.println(crypt.hash("Customer@8080"));
    }
}
