package nayanda.droid.eatr.base;

import android.support.annotation.NonNull;

import java.net.HttpURLConnection;

import nayanda.droid.eatr.digester.Consumer;
import nayanda.droid.eatr.digester.Digester;
import nayanda.droid.eatr.digester.Finisher;
import nayanda.droid.eatr.digester.ProgressDigester;
import nayanda.droid.eatr.digester.RestResponse;

/**
 * Created by nayanda on 15/02/18.
 */

public class HttpRequestRestExecutorImpl<T> extends BaseHttpRequestExecutor<HttpRequestRestExecutor<T>> implements HttpRequestRestExecutor<T> {

    private final Class<T> modelClass;

    private Consumer<RestResponse<T>> onResponded = new Consumer<RestResponse<T>>() {
        @Override
        public void onConsume(RestResponse<T> param) {
        }
    };

    private Consumer<RestResponse<T>> onFinished = new Consumer<RestResponse<T>>() {
        @Override
        public void onConsume(RestResponse<T> param) {
        }
    };

    public HttpRequestRestExecutorImpl(BaseHttpRequest httpRequest, final Class<T> tClass) {
        super(httpRequest);
        this.modelClass = tClass;
    }

    public HttpRequestRestExecutorImpl(BaseHttpRequest httpRequest, final Class<T> tClass, final Finisher<RestResponse<T>> finisher) {
        this(httpRequest, tClass);
        this.setOnFinished(new Consumer<RestResponse<T>>() {
            @Override
            public void onConsume(RestResponse<T> param) {
                finisher.onFinished(param);
            }
        });
    }

    public HttpRequestRestExecutorImpl(BaseHttpRequest httpRequest, final Class<T> tClass, final Digester<RestResponse<T>> digester) {
        this(httpRequest, tClass);
        this.setOnResponded(new Consumer<RestResponse<T>>() {
            @Override
            public void onConsume(RestResponse<T> param) {
                digester.onResponded(param);
            }
        }).setOnException(new Consumer<Exception>() {
            @Override
            public void onConsume(Exception param) {
                digester.onException(param);
            }
        }).setOnBeforeSending(new Consumer<HttpURLConnection>() {
            @Override
            public void onConsume(HttpURLConnection param) {
                digester.onBeforeSending(param);
            }
        }).setOnTimeout(new Runnable() {
            @Override
            public void run() {
                digester.onTimeout();
            }
        });
    }

    public HttpRequestRestExecutorImpl(BaseHttpRequest httpRequest, final Class<T> tClass, final ProgressDigester<RestResponse<T>> progressDigester) {
        this(httpRequest, tClass, (Digester<RestResponse<T>>) progressDigester);
        this.setOnProgress(new Consumer<Float>() {
            @Override
            public void onConsume(Float param) {
                progressDigester.onProgress(param);
            }
        });
    }

    @NonNull
    @Override
    public Class<T> getModelClass() {
        return modelClass;
    }

    @NonNull
    @Override
    public Consumer<RestResponse<T>> getOnResponded() {
        return onResponded;
    }

    @NonNull
    @Override
    public Consumer<RestResponse<T>> getOnFinished() {
        return onFinished;
    }

    @NonNull
    @Override
    public HttpRequestRestExecutor<T> setOnResponded(@NonNull Consumer<RestResponse<T>> onResponded) {
        this.onResponded = onResponded;
        return this;
    }

    @NonNull
    @Override
    public HttpRequestRestExecutor<T> setOnFinished(@NonNull Consumer<RestResponse<T>> onFinished) {
        this.onFinished = onFinished;
        return this;
    }

    @Override
    public void execute() {
        if (httpRequest instanceof HttpRequestWithBody)
            httpRequest.asyncExecutor(httpRequest.method, ((BaseHttpRequestWithBody) httpRequest).getBody(), this);
        httpRequest.asyncExecutor(httpRequest.method, null, this);
    }
}
