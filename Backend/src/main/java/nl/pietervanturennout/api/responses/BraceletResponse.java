package nl.pietervanturennout.api.responses;

import nl.pietervanturennout.api.responses.management.ResponseObject;
import nl.pietervanturennout.model.Bracelet;

public class BraceletResponse extends BaseResponse{

    public static ResponseObject generateMap(Bracelet bracelet) {
        if (bracelet == null || bracelet.getBraceletId() == 0)
            return null;

        ResponseObject response = new ResponseObject();

        response.put("braceletId", bracelet.getBraceletId());
        response.put("length", bracelet.getLength());
        response.put("material", bracelet.getMaterial());
        response.put("style", bracelet.getStyle());
        response.put("color", bracelet.getColor());

        return response;
    }

    public BraceletResponse(Bracelet bracelet) { data = generateMap(bracelet); }
}
