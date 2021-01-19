package nl.pietervanturennout.api.authorization;

import io.dropwizard.auth.Authorizer;
import nl.pietervanturennout.api.authentication.AuthenticateObject;
import nl.pietervanturennout.controller.AuthorizationController;
import nl.pietervanturennout.dao.AuthorizationDAO;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.utils.JwtUtil;

public class MyOnlyAuthorizer implements Authorizer<AuthenticateObject> {
    @Override
    public boolean authorize(AuthenticateObject principal, String role) {
        try {
            return AuthorizationController.getInstance().doesGroupHaveRole(role, JwtUtil.getInstance().getGroupFromJwt(principal.getToken()));
        } catch (OperationFailedException e){
            e.printStackTrace();
            return false;
        }
    }
}
