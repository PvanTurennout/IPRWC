package nl.pietervanturennout.api.responses;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.NewCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseResponse {

    protected final Map<String, Object> headers = new HashMap<>();
    protected final List<NewCookie> cookies = new ArrayList<>();
    protected int status = 200;
    protected Object data = null;
    protected CacheControl cacheControl = null;

    public final Object getData() {
        return data;
    }

    public final int getStatus() {
        return status;
    }

    public final Map<String, Object> getHeaders() {
        return headers;
    }

    public final List<NewCookie> getCookies() {
        return cookies;
    }

    public final CacheControl getCacheControl() {
        return cacheControl;
    }
}
