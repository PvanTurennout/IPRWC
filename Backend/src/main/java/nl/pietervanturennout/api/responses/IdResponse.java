package nl.pietervanturennout.api.responses;

import nl.pietervanturennout.api.responses.management.ResponseObject;

public class IdResponse extends BaseResponse{

    public static ResponseObject generateMap(int id, String whatId){
        if (id == 0) return null;
        ResponseObject response = new ResponseObject();

        response.put(whatId + "Id", id);

        return response;
    }

    public IdResponse(int id, String whatId) { data = generateMap(id, whatId); status = 201; }
}
