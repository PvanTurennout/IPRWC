package nl.pietervanturennout.api.responses;

import nl.pietervanturennout.api.responses.management.ResponseObject;

public class StringResponse extends BaseResponse {

    public static ResponseObject generateMap(String key, String value){
        if (value == null) return null;
        ResponseObject response = new ResponseObject();

        response.put(key, value);

        return response;
    }

    public StringResponse(String identifier, String value) { data = generateMap(identifier, value); }
}
