package nl.pietervanturennout.controller;

import javafx.util.Pair;
import nl.pietervanturennout.api.requests.AuthenticationRequest;
import nl.pietervanturennout.api.responses.AuthenticationResponse;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.NotFoundException;
import nl.pietervanturennout.exceptions.OperationFailedException;
import nl.pietervanturennout.exceptions.UnAuthorizedException;
import nl.pietervanturennout.utils.BCrypt;
import nl.pietervanturennout.model.Account;
import nl.pietervanturennout.utils.JwtUtil;

import javax.inject.Singleton;

@Singleton
public class AuthenticationController {

    //Singleton
    private static final AuthenticationController instance;
    static { instance = new AuthenticationController(); }
    public static AuthenticationController getInstance() { return instance; }

    public AuthenticationController() {}

    public AuthenticationResponse login(AuthenticationRequest authentication) throws UnAuthorizedException, InvalidDataException{
        try{
            Account account  = AccountController.getInstance().getAccountFromEmail(authentication.getMailAddress());
            verifyPassword(account, authentication.getPassword());

            return new AuthenticationResponse(generateJWT(account), createRefreshToken(account));
        } catch (OperationFailedException | NotFoundException e){
            throw new UnAuthorizedException(e);
        }
    }

    public String refreshJwt(String refreshToken) throws OperationFailedException, UnAuthorizedException, NotFoundException, InvalidDataException{
        if(validateRefreshToken(refreshToken)){
            Account account = AccountController.getInstance().getAccountFromId(JwtUtil.getInstance().getAccountIdFromJwt(refreshToken));
            return generateJWT(account);
        } else {
            return null;
        }
    }

    private boolean validateRefreshToken(String refreshToken) throws UnAuthorizedException {
        return JwtUtil.getInstance().verifyJwt(refreshToken, true);
    }

    private void verifyPassword(Account account, String password) throws UnAuthorizedException {
        BCrypt bCrypt = new BCrypt();
        if(!bCrypt.verifyHash(password, account.getPassword())){
            throw new UnAuthorizedException("Invalid Password!");
        }
    }

    private String generateJWT(Account account) throws InvalidDataException {
        return JwtUtil.getInstance().buildJwt(account);
    }

    private String createRefreshToken(Account account) throws OperationFailedException, InvalidDataException{
        return JwtUtil.getInstance().buildRefreshToken(account);
    }

}
