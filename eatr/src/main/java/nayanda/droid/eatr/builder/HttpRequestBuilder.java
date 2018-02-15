package nayanda.droid.eatr.builder;

import android.support.annotation.NonNull;

/**
 * Created by nayanda on 07/02/18.
 */

public class HttpRequestBuilder {

    @NonNull
    public static HttpPost httpPost() {
        return new HttpPost();
    }

    @NonNull
    public static HttpGet httpGet() {
        return new HttpGet();
    }

    @NonNull
    public static HttpPut httpPut() {
        return new HttpPut();
    }

}
