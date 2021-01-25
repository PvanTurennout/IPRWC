package nl.pietervanturennout.resource;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AuthenticationResourceTest {
    private AuthenticationResource authResource;

    private void freshAuthenticationResource(){
        this.authResource = new AuthenticationResource();
    }

    @Test
    public void test_getJwtString_returnsJWT(){
        freshAuthenticationResource();

//        assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2UifQ.1KP0SsvENi7Uz1oQc07aXTL7kpQG5jBNIybqr60AlD4", authResource.getJwtString());
    }
}
