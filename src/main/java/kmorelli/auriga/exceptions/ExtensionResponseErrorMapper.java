package kmorelli.auriga.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

public class ExtensionResponseErrorMapper implements ResponseExceptionMapper<ExtensionException> {

    @Override
    public ExtensionException toThrowable(Response response) {
        final StatusType statusInfo = response.getStatusInfo();
        return new ExtensionException(statusInfo.getStatusCode(), statusInfo.getReasonPhrase());
    }
    
}
