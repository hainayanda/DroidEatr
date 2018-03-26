package nayanda.droid.eatr.digester;

import com.google.gson.Gson;

import java.net.SocketTimeoutException;

/**
 * Created by nayanda on 07/02/18.
 */
public class Response {
    private final String rawBody;
    private final int statusCode;
    private final boolean isSuccess;
    private Exception exception;
    private boolean isParsedAlready = false;
    private Object parsed;

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

    public <T> T getParsedBody(Class<T> tClass) {
        if (isParsedAlready) {
            if (parsed != null) return (T) parsed;
            else return null;
        }
        try {
            Gson gson = new Gson();
            return gson.fromJson(getRawBody(), tClass);
        } catch (Exception e) {
            return null;
        }
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
