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

public class HttpGet {

    private final HttpRequesterBuilder requestBuilder = new HttpRequesterBuilder();
    private String method;

    public HttpGet(){
        method = "GET";
    }

    @NonNull
    public HttpGet setUrl(@NonNull String url) {
        requestBuilder.setUrl(url);
        return this;
    }

    @NonNull
    public HttpGet setParams(@NonNull Map<String, String> params) {
        requestBuilder.setParams(params);
        return this;
    }

    @NonNull
    public HttpGet setHeaders(@NonNull Map<String, String> headers) {
        requestBuilder.setHeaders(headers);
        return this;
    }

    @NonNull
    public HttpGet setTimeout(int timeout) {
        requestBuilder.setTimeout(timeout);
        return this;
    }

    @NonNull
    public HttpGet setOnBeforeSending(@NonNull Consumer<HttpURLConnection> onBeforeSending) {
        requestBuilder.setOnBeforeSending(onBeforeSending);
        return this;
    }

    @NonNull
    public HttpGet setOnTimeout(@NonNull Runnable onTimeout) {
        requestBuilder.setOnTimeout(onTimeout);
        return this;
    }

    @NonNull
    public HttpGet setOnException(@NonNull Consumer<Exception> onException) {
        requestBuilder.setOnException(onException);
        return this;
    }

    @NonNull
    public HttpGet setOnProgress(@NonNull Consumer<Float> onProgress) {
        requestBuilder.setOnProgress(onProgress);
        return this;
    }

    @NonNull
    public HttpGet setOnResponded(@NonNull Consumer<Response> onResponded) {
        requestBuilder.setOnResponded(onResponded);
        return this;
    }

    @NonNull
    public HttpGet setOnFinish(@NonNull Consumer<Response> onFinish) {
        requestBuilder.setOnFinish(onFinish);
        return this;
    }

    @NonNull
    public HttpGet addAuthorization(@NonNull String token) {
        requestBuilder.addAuthorization(token);
        return this;
    }

    @NonNull
    public HttpGet addParam(@NonNull String key, @NonNull String value) {
        requestBuilder.addParam(key, value);
        return this;
    }

    @NonNull
    public HttpGet addHeaders(@NonNull String key, @NonNull String value) {
        requestBuilder.addHeaders(key, value);
        return this;
    }

    public void asyncExecute() {
        requestBuilder.setMethod(method).asyncExecute();
    }

    public void asyncExecute(Consumer<Response> onFinish) {
        requestBuilder.setMethod(method).setOnFinish(onFinish).asyncExecute();
    }

    public Response execute(){
        return requestBuilder.setMethod(method).execute();
    }
}
