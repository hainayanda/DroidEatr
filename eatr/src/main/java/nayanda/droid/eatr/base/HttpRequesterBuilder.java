package nayanda.droid.eatr.base;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import nayanda.droid.eatr.digester.Consumer;
import nayanda.droid.eatr.digester.Response;

public class HttpRequesterBuilder {
    private String url;
    private Map<String, String> params;
    private Map<String, String> headers;
    private String body;
    private int timeout = 20000;
    private Consumer<HttpURLConnection> onBeforeSending;
    private Runnable onTimeout;
    private Consumer<Exception> onException;
    private Consumer<Float> onProgress;
    private Consumer<Response> onResponded;
    private Consumer<Response> onFinish;
    private String method;
    private Runnable onBeforeStart;

    @NonNull
    public HttpRequesterBuilder setUrl(@NonNull String url) {
        this.url = url;
        return this;
    }

    @NonNull
    public HttpRequesterBuilder setMethod(@NonNull String method) {
        this.method = method;
        return this;
    }

    @NonNull
    public HttpRequesterBuilder setParams(@NonNull Map<String, String> params) {
        this.params = params;
        return this;
    }

    @NonNull
    public HttpRequesterBuilder setHeaders(@NonNull Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    @NonNull
    public HttpRequesterBuilder setBody(@NonNull String body) {
        this.body = body;
        return this;
    }

    @NonNull
    public HttpRequesterBuilder setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    @NonNull
    public HttpRequesterBuilder setOnBeforeSending(@NonNull Consumer<HttpURLConnection> onBeforeSending) {
        this.onBeforeSending = onBeforeSending;
        return this;
    }

    @NonNull
    public HttpRequesterBuilder setOnTimeout(@NonNull Runnable onTimeout) {
        this.onTimeout = onTimeout;
        return this;
    }

    @NonNull
    public HttpRequesterBuilder setOnBeforeStart(@NonNull Runnable onBeforeStart) {
        this.onBeforeStart = onBeforeStart;
        return this;
    }

    @NonNull
    public HttpRequesterBuilder setOnException(@NonNull Consumer<Exception> onException) {
        this.onException = onException;
        return this;
    }

    @NonNull
    public HttpRequesterBuilder setOnProgress(@NonNull Consumer<Float> onProgress) {
        this.onProgress = onProgress;
        return this;
    }

    @NonNull
    public HttpRequesterBuilder setOnResponded(@NonNull Consumer<Response> onResponded) {
        this.onResponded = onResponded;
        return this;
    }

    @NonNull
    public HttpRequesterBuilder setOnFinish(@NonNull Consumer<Response> onFinish) {
        this.onFinish = onFinish;
        return this;
    }

    @NonNull
    public HttpRequesterBuilder addAuthorization(@NonNull String token) {
        if (headers == null) headers = new HashMap<>();
        headers.put("Authorization", "bearer " + token);
        return this;
    }

    @NonNull
    public HttpRequesterBuilder addParam(@NonNull String key, @NonNull String value) {
        if (params == null) params = new HashMap<>();
        params.put(key, value);
        return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public HttpRequesterBuilder addHeaders(@NonNull String key, @NonNull String value) {
        if (headers == null) headers = new HashMap<>();
        headers.put(key, value);
        return this;
    }

    @NonNull
    public <T> HttpRequesterBuilder setJsonBody(@NonNull T obj) {
        Gson gson = new Gson();
        this.body = gson.toJson(obj);
        addHeaders("Content-Type", "application/json");
        return this;
    }

    public void asyncExecute() {
        new HttpRequester(url, method, params, headers, body, timeout, onBeforeStart, onBeforeSending, onTimeout,
                onException, onProgress, onResponded, onFinish)
        .execute();
    }

    public Response execute(){
        final Response[] response = new Response[1];
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new HttpRequester(url, method, params, headers, body, timeout, onBeforeStart, onBeforeSending, onTimeout,
                onException, onProgress, onResponded, new Consumer<Response>() {
            @Override
            public void onConsume(Response param) {
                response[0] = param;
                countDownLatch.countDown();
            }
        }).execute();
        try {
            countDownLatch.await((long) timeout, TimeUnit.MILLISECONDS);
            return response[0];
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}