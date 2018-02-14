package nayanda.droid.eatr.digester;

import java.net.SocketTimeoutException;

/**
 * Created by nayanda on 07/02/18.
 */
public class Response {
    private final String rawBody;
    private final int statusCode;
    private final boolean isSuccess;
    private Exception exception;

    public Response(String rawBody, int statusCode, boolean isSuccess) {
        this.rawBody = rawBody;
        this.statusCode = statusCode;
        this.isSuccess = isSuccess;
    }

    public Response(String rawBody, int statusCode, boolean isSuccess, Exception exception) {
        this(rawBody, statusCode, isSuccess);
        this.exception = exception;
    }

    public String getRawBody() {
        return rawBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean hadException() {
        return exception != null;
    }

    public boolean isTimeout() {
        return exception instanceof SocketTimeoutException;
    }

    public boolean hadBody() {
        return rawBody != null && !rawBody.equals("");
    }

    public Exception getException() {
        return exception;
    }

}
