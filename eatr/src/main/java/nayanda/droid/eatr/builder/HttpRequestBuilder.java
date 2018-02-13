package nayanda.droid.eatr.builder;

import android.support.annotation.NonNull;

import nayanda.droid.eatr.base.HttpRequest;
import nayanda.droid.eatr.base.HttpRequestWithBody;

/**
 * Created by nayanda on 07/02/18.
 */

public class HttpRequestBuilder {

    @NonNull
    public static HttpRequestWithBody httpPost() {
        return new HttpPost();
    }

    @NonNull
    public static HttpRequest httpGet() {
        return new HttpGet();
    }

    @NonNull
    public static HttpRequestWithBody httpPut() {
        return new HttpPut();
    }

}
