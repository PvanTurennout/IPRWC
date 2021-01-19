package nl.pietervanturennout.api.authentication;

import nl.pietervanturennout.exceptions.UnAuthorizedException;
import nl.pietervanturennout.utils.JwtUtil;

import java.security.Principal;

public class AuthenticateObject implements Principal {
    private String name;
    private String token;
    private int accountId;

    public AuthenticateObject(){}

    public AuthenticateObject(String token){
        this.token = token;
        this.accountId = JwtUtil.getInstance().getAccountIdFromJwt(token);
    }

    @Override
    public String getName() {
        return this.name;
    }
    public String getToken(){
        return this.token;
    }
    public int getAccountId() { return this.accountId; }

    public boolean isAuthenticated() throws UnAuthorizedException {
        return JwtUtil.getInstance().verifyJwt(this.token, false);
    }
}
