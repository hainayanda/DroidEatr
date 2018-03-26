package nayanda.droid.eatr.builder;

import android.support.annotation.NonNull;

import java.net.HttpURLConnection;
import java.util.Map;

import nayanda.droid.eatr.base.HttpRequesterBuilder;
import nayanda.droid.eatr.digester.Consumer;
import nayanda.droid.eatr.digester.Response;

/**
 * Created by nayanda on 08/02/18.
 */

public class HttpPut {
    private final HttpRequesterBuilder requestBuilder = new HttpRequesterBuilder();
    private String method;

    public HttpPut(){
        method = "POST";
    }

    @NonNull
    public HttpPut setUrl(@NonNull String url) {
        requestBuilder.setUrl(url);
        return this;
    }

    @NonNull
    public HttpPut setParams(@NonNull Map<String, String> params) {
        requestBuilder.setParams(params);
        return this;
    }

    @NonNull
    public HttpPut setHeaders(@NonNull Map<String, String> headers) {
        requestBuilder.setHeaders(headers);
        return this;
    }

    @NonNull
    public HttpPut setTimeout(int timeout) {
        requestBuilder.setTimeout(timeout);
        return this;
    }

    @NonNull
    public HttpPut setOnBeforeSending(@NonNull Consumer<HttpURLConnection> onBeforeSending) {
        requestBuilder.setOnBeforeSending(onBeforeSending);
        return this;
    }

    @NonNull
    public HttpPut setOnTimeout(@NonNull Runnable onTimeout) {
        requestBuilder.setOnTimeout(onTimeout);
        return this;
    }

    @NonNull
    public HttpPut setOnException(@NonNull Consumer<Exception> onException) {
        requestBuilder.setOnException(onException);
        return this;
    }

    @NonNull
    public HttpPut setOnProgress(@NonNull Consumer<Float> onProgress) {
        requestBuilder.setOnProgress(onProgress);
        return this;
    }

    @NonNull
    public HttpPut setOnResponded(@NonNull Consumer<Response> onResponded) {
        requestBuilder.setOnResponded(onResponded);
        return this;
    }

    @NonNull
    public HttpPut setOnFinish(@NonNull Consumer<Response> onFinish) {
        requestBuilder.setOnFinish(onFinish);
        return this;
    }

    @NonNull
    public HttpPut addAuthorization(@NonNull String token) {
        requestBuilder.addAuthorization(token);
        return this;
    }

    @NonNull
    public HttpPut addParam(@NonNull String key, @NonNull String value) {
        requestBuilder.addParam(key, value);
        return this;
    }

    @NonNull
    public HttpPut addHeaders(@NonNull String key, @NonNull String value) {
        requestBuilder.addHeaders(key, value);
        return this;
    }

    @NonNull
    public <T> HttpPut setJsonBody(@NonNull T obj) {
        requestBuilder.setJsonBody(obj);
        return this;
    }

    @NonNull
    public HttpPut setBody(@NonNull String body) {
        requestBuilder.setBody(body);
        return this;
    }

    public void asyncExecute() {
        requestBuilder.setMethod(method).asyncExecute();
    }

    public Response execute(){
        return requestBuilder.setMethod(method).execute();
    }
}
