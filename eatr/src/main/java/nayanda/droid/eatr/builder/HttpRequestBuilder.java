package nayanda.droid.eatr.builder;

import nayanda.droid.eatr.base.HttpRequest;
import nayanda.droid.eatr.base.HttpRequestWithBody;

/**
 * Created by nayanda on 07/02/18.
 */

public class HttpRequestBuilder {

    public static HttpRequestWithBody httpPost() {
        return new HttpPost();
    }

    public static HttpRequest httpGet() {
        return new HttpGet();
    }

    public static HttpRequestWithBody httpPut() {
        return new HttpPut();
    }

}
