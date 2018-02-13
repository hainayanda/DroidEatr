package nayanda.droid.eatr.digester;

/**
 * Created by nayanda on 07/02/18.
 */
public class RestResponse<T> extends Response {
    private final T parsedBody;

    public RestResponse(String rawBody, T parsedBody, int statusCode, boolean isSuccess) {
        super(rawBody, statusCode, isSuccess);
        this.parsedBody = parsedBody;
    }

    public RestResponse(String rawBody, T parsedBody, int statusCode, boolean isSuccess, Exception exception) {
        super(rawBody, statusCode, isSuccess, exception);
        this.parsedBody = parsedBody;
    }

    public T getParsedBody() {
        return parsedBody;
    }

}
