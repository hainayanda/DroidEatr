package e.nayanda.eatr;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import nayanda.eatr.HttpRequest;

/**
 * Created by nayanda on 08/02/18.
 */

public interface HttpRequestWithBody<T extends HttpRequestWithBody> extends HttpRequest<T> {
    public T addBody(String body);
    public <O> T addJsonBody(O obj);
    public T addFormUrlEncoded(Map<String, String> form) throws UnsupportedEncodingException;
}
