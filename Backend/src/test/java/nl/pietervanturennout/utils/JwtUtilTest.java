package nl.pietervanturennout.utils;

import nl.pietervanturennout.model.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtUtilTest {

    @Test
    public void test_buildJwtToken() {
//        JwtUtil.setKey("HiImASecret");

//        assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhY2NvdW50SWQiOjMsImV4cCI6MTYwNDUyMTE1Nn0.h3I7pruqNvxPM9WTMVtIvEcqqV_DZUyhuFgmCu9AeVY",
//                JwtUtil.buildJwt(new Account(3, "test", "test")));
    }

    @Test
    public void jwtIdTest(){
        JwtUtil.setKey("Testing");
        int id = 34564;
//        String token = JwtUtil.getInstance().buildJwt(new Account(id, "test", "test"));

//        assertEquals(id, JwtUtil.getInstance().getAccountIdFromJwt(token));
    }


}
