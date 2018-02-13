package nayanda.droid.eatr.base;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by nayanda on 08/02/18.
 */

public interface HttpRequestWithBody<T extends HttpRequestWithBody> extends HttpRequest<T> {
    @NonNull
    T addBody(@NonNull String body);

    @NonNull
    <O> T addJsonBody(@NonNull O obj);

    @NonNull
    T addFormUrlEncoded(@NonNull Map<String, String> form) throws UnsupportedEncodingException;
}
