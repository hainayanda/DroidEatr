package nayanda.droid.eatr.builder;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.Map;

import nayanda.droid.eatr.base.HttpRequesterBuilder;
import nayanda.droid.eatr.digester.Consumer;
import nayanda.droid.eatr.digester.Response;

/**
 * Created by nayanda on 08/02/18.
 */

public class HttpPost {

    private final HttpRequesterBuilder requestBuilder = new HttpRequesterBuilder();
    private String method;

    public HttpPost(){
        method = "POST";
    }

    @NonNull
    public HttpPost setUrl(@NonNull String url) {
        requestBuilder.setUrl(url);
        return this;
    }

    @NonNull
    public HttpPost setParams(@NonNull Map<String, String> params) {
        requestBuilder.setParams(params);
        return this;
    }

    @NonNull
    public HttpPost setHeaders(@NonNull Map<String, String> headers) {
        requestBuilder.setHeaders(headers);
        return this;
    }

    @NonNull
    public HttpPost setTimeout(int timeout) {
        requestBuilder.setTimeout(timeout);
        return this;
    }

    @NonNull
    public HttpPost setOnBeforeSending(@NonNull Consumer<HttpURLConnection> onBeforeSending) {
        requestBuilder.setOnBeforeSending(onBeforeSending);
        return this;
    }

    @NonNull
    public HttpPost setOnTimeout(@NonNull Runnable onTimeout) {
        requestBuilder.setOnTimeout(onTimeout);
        return this;
    }

    @NonNull
    public HttpPost setOnException(@NonNull Consumer<Exception> onException) {
        requestBuilder.setOnException(onException);
        return this;
    }

    @NonNull
    public HttpPost setOnProgress(@NonNull Consumer<Float> onProgress) {
        requestBuilder.setOnProgress(onProgress);
        return this;
    }

    @NonNull
    public HttpPost setOnResponded(@NonNull Consumer<Response> onResponded) {
        requestBuilder.setOnResponded(onResponded);
        return this;
    }

    @NonNull
    public HttpPost setOnFinish(@NonNull Consumer<Response> onFinish) {
        requestBuilder.setOnFinish(onFinish);
        return this;
    }

    @NonNull
    public HttpPost addAuthorization(@NonNull String token) {
        requestBuilder.addAuthorization(token);
        return this;
    }

    @NonNull
    public HttpPost addParam(@NonNull String key, @NonNull String value) {
        requestBuilder.addParam(key, value);
        return this;
    }

    @NonNull
    public HttpPost addHeaders(@NonNull String key, @NonNull String value) {
        requestBuilder.addHeaders(key, value);
        return this;
    }

    @NonNull
    public <T> HttpPost addJsonBody(@NonNull T obj) {
        requestBuilder.addJsonBody(obj);
        return this;
    }

    @NonNull
    public HttpPost addFormUrlEncoded(@NonNull Map<String, String> form) throws UnsupportedEncodingException {
        requestBuilder.addFormUrlEncoded(form);
        return this;
    }

    @NonNull
    public HttpPost addBody(@NonNull String body) {
        requestBuilder.addBody(body);
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
