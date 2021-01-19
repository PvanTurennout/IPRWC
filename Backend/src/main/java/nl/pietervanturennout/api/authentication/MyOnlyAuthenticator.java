package nl.pietervanturennout.api.authentication;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import nl.pietervanturennout.exceptions.UnAuthorizedException;
import nl.pietervanturennout.utils.JwtUtil;

import java.util.Optional;

public class MyOnlyAuthenticator implements Authenticator<String, AuthenticateObject> {
    @Override
    public Optional<AuthenticateObject> authenticate(String token) throws AuthenticationException {
        try {
            if (JwtUtil.getInstance().verifyJwt(token, false)) {
                return Optional.of(new AuthenticateObject(token));
            }
        } catch (UnAuthorizedException e) {
            throw new AuthenticationException(e);
        }

        return Optional.empty();
    }
}
