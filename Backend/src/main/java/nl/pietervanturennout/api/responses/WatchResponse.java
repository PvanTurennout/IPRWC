package nl.pietervanturennout.api.responses;

import nl.pietervanturennout.api.responses.management.ResponseObject;
import nl.pietervanturennout.model.Watch;

public class WatchResponse extends BaseResponse{

    public static ResponseObject generateMap(Watch watch, boolean bracelet) {
        if (watch == null || watch.getWatchId() == 0)
            return null;

        ResponseObject response = new ResponseObject();

        response.put("watchId", watch.getWatchId());

        if (bracelet)
            response.put("watchBracelet", new BraceletResponse(watch.getWatchBracelet()).getData());

        response.put("material", watch.getMaterial());
        response.put("size", watch.getSize());
        response.put("colorPointer", watch.getColorPointer());
        response.put("colorDial", watch.getColorDial());

        return response;
    }

    public WatchResponse(Watch watch, boolean includeBracelet) { data = generateMap(watch, includeBracelet); }

    public WatchResponse(Watch watch) { this(watch, true); }
}
