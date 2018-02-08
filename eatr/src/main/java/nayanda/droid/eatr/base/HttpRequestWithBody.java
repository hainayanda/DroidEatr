package nayanda.droid.eatr.base;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by nayanda on 08/02/18.
 */

public interface HttpRequestWithBody<T extends HttpRequestWithBody> extends HttpRequest<T> {
    T addBody(String body);

    <O> T addJsonBody(O obj);

    T addFormUrlEncoded(Map<String, String> form) throws UnsupportedEncodingException;
}
