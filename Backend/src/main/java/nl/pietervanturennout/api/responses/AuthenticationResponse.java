package nl.pietervanturennout.api.responses;

import nl.pietervanturennout.api.responses.management.ResponseObject;

public class AuthenticationResponse extends BaseResponse{

    public static ResponseObject generateMap(String authToken, String refreshToken){
        if (authToken == null || refreshToken == null) return null;
        ResponseObject response = new ResponseObject();

        response.put("token", authToken);
        response.put("refreshToken", refreshToken);

        return response;
    }

    public AuthenticationResponse(String token, String refreshToken) { data = generateMap(token, refreshToken); }
}
