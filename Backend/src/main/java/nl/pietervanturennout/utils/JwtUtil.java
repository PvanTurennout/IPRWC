package nl.pietervanturennout.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import nl.pietervanturennout.IprwcBackendConfiguration;
import nl.pietervanturennout.IprwcBackendMain;
import nl.pietervanturennout.exceptions.InvalidDataException;
import nl.pietervanturennout.exceptions.UnAuthorizedException;
import nl.pietervanturennout.utils.types.Group;
import org.joda.time.DateTime;

import nl.pietervanturennout.model.Account;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


public class JwtUtil {

    private static final JwtUtil instance;
    static{ instance = new JwtUtil(); }
    public static JwtUtil getInstance() { return instance; }

    private static String key;
    private static String refreshKey;
    private static int tokenValidityTime;

    public JwtUtil() {
        IprwcBackendConfiguration serverConfig = IprwcBackendMain.getServerConfiguration();
        key = serverConfig.getSecret();
        refreshKey = serverConfig.getRefreshSecret();
        tokenValidityTime = serverConfig.getTokenValidityTime();
    }

    public static void setKey(String key) {
        JwtUtil.key = key;
    }

    public String buildJwt(Account subject) throws InvalidDataException {
        String token;
        try {
            token = JWT.create()
                    .withExpiresAt(new DateTime().plusMinutes(tokenValidityTime).toDate())
                    .withClaim("accountId", subject.getAccountId())
                    .withClaim("group", subject.getGroup().getGroupId())
                    .withArrayClaim("wishlist", subject.getWishList().toArray(new Integer[0]))
                    .sign(Algorithm.HMAC512(key));
        } catch (JWTCreationException e ){
            throw new InvalidDataException("JWT couldn't be constructed", e);
        }

        return token;
    }

    public String buildRefreshToken(Account subject) throws InvalidDataException{
        String token;
        try {
            token = JWT.create()
                    .withClaim("accountId", subject.getAccountId())
                    .sign(Algorithm.HMAC512(refreshKey));
        } catch (JWTCreationException e ){
            throw new InvalidDataException("JWT couldn't be constructed", e);
        }

        return token;
    }

    public boolean verifyJwt(String token, boolean refresh) throws UnAuthorizedException{
        String signature = refresh ? refreshKey : key;
        Algorithm algorithm = Algorithm.HMAC512(signature);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try{
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (JWTVerificationException e){
            throw new UnAuthorizedException(e);
        }
    }

    public DecodedJWT deconstructJwt(String token){
        try{
            return JWT.decode(token);
        } catch (JWTDecodeException e){
            e.printStackTrace();
        }
        return null;
    }

    public int getAccountIdFromJwt(String token){
        return deconstructJwt(token).getClaim("accountId").asInt();
    }

    public Group getGroupFromJwt(String token) {
        int groupId = deconstructJwt(token).getClaim("group").asInt();
        switch (groupId){
            case 1:
                return Group.ADMIN;
            case 2:
                return Group.SELLER;
            default:
                return Group.CUSTOMER;
        }
    }
}
