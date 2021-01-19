package nl.pietervanturennout.api.responses.management;

import nl.pietervanturennout.api.responses.BaseResponse;
import nl.pietervanturennout.utils.Headers;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import java.util.List;
import java.util.Map;

public class BaseResponseFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        Object entity = responseContext.getEntity();

        if (entity instanceof BaseResponse) {
            BaseResponse response = (BaseResponse) entity;

            responseContext.setStatus(response.getStatus());
            putHeaders(responseContext.getHeaders(), response.getHeaders());

            // dont use responseContext.getCookies(),
            // this will throw a UnsupportedOperationException when cookies are added
            // this seems to be a bug in Jersey/Dropwizard
            putCookies(responseContext.getHeaders(), response.getCookies());

            if (response.getCacheControl() != null)
                responseContext.getHeaders().add("Cache-Control", response.getCacheControl().toString());

            responseContext.setEntity(response.getData());
        }
    }

    private void putHeaders(MultivaluedMap<String, Object> headers, Map<String, Object> newHeaders) {
        for (Map.Entry<String, Object> entry: newHeaders.entrySet()) {
            if (Headers.isSingleValueHeader(entry.getKey())){
                headers.putSingle(entry.getKey(), entry.getValue());
            }
            else {
                headers.add(entry.getKey(), entry.getValue());
            }
        }
    }

    private void putCookies(MultivaluedMap<String, Object> headers, List<NewCookie> newCookies) {
        for (NewCookie cookie: newCookies) {
            headers.add("Set-Cookie", cookie);
        }
    }
}
