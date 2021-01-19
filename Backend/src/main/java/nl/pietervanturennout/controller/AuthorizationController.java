package nl.pietervanturennout.controller;

import nl.pietervanturennout.dao.AuthorizationDAO;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.utils.types.Group;

public class AuthorizationController {

    private static final AuthorizationController instance;
    static{ instance = new AuthorizationController(); }
    public static AuthorizationController getInstance() { return instance; }

    private final AuthorizationDAO authorizationDAO;

    private AuthorizationController() { this.authorizationDAO = new AuthorizationDAO(); }

    public boolean doesGroupHaveRole(String role, Group group) throws OperationFailedException {
        return authorizationDAO.roleCheck(role, group);
    }
}
