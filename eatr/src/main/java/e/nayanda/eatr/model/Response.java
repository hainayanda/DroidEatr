package e.nayanda.eatr.model;

/**
 * Created by nayanda on 07/02/18.
 */

public class Response {
    private String body;
    private int statusCode;
    private boolean isSuccess;
    private Exception exception;

    public Response(String body, int statusCode, boolean isSuccess) {
        this.body = body;
        this.statusCode = statusCode;
        this.isSuccess = isSuccess;
    }

    public Response(String body, int statusCode, boolean isSuccess, Exception exception){
        this(body, statusCode, isSuccess);
        this.exception = exception;
    }

    public String getBody() {
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
