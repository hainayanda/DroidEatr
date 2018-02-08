package nayanda.droid.eatr.model;

/**
 * Created by nayanda on 07/02/18.
 */

public class RestResponse<T> {
    private Exception exception;
    private T body;
    private int statusCode;
    private boolean isSuccess;

    public RestResponse(T body, int statusCode, boolean isSuccess) {
        this.body = body;
        this.statusCode = statusCode;
        this.isSuccess = isSuccess;
    }

    public RestResponse(T body, int statusCode, boolean isSuccess, Exception exception) {
        this(body, statusCode, isSuccess);
        this.exception = exception;
    }

    public T getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
