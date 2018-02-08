package nayanda.droid.eatr;

/**
 * Created by nayanda on 07/02/18.
 */

public class HttpRequestBuilder {

    public HttpRequestWithBody httpPost() {
        return new HttpPost();
    }

    public HttpRequest httpGet() {
        return new HttpGet();
    }

    public HttpRequestWithBody httpPut() {
        return new HttpPut();
    }

}
