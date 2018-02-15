package nayanda.droid.eatr.base;

import android.support.annotation.NonNull;

import java.net.HttpURLConnection;

import nayanda.droid.eatr.digester.Consumer;

/**
 * Created by nayanda on 15/02/18.
 */

public abstract class BaseHttpRequestExecutor<T extends HttpRequestBasicExecutor<T>> implements HttpRequestBasicExecutor<T> {

    final BaseHttpRequest httpRequest;
    private Consumer<HttpURLConnection> onBeforeSending = new Consumer<HttpURLConnection>() {
        @Override
        public void onConsume(HttpURLConnection param) {
        }
    };
    private Runnable onTimeout = new Runnable() {
        @Override
        public void run() {
        }
    };
    private Consumer<Exception> onException = new Consumer<Exception>() {
        @Override
        public void onConsume(Exception param) {
        }
    };
    private Consumer<Float> onProgress = new Consumer<Float>() {
        @Override
        public void onConsume(Float param) {
        }
    };

    BaseHttpRequestExecutor(BaseHttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @NonNull
    @Override
    public Consumer<HttpURLConnection> getOnBeforeSending() {
        return onBeforeSending;
    }

    @NonNull
    @Override
    public Runnable getOnTimeout() {
        return onTimeout;
    }

    @NonNull
    @Override
    public Consumer<Exception> getOnException() {
        return onException;
    }

    @NonNull
    @Override
    public Consumer<Float> getOnProgress() {
        return onProgress;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public T setOnProgress(@NonNull Consumer<Float> onProgress) {
        this.onProgress = onProgress;
        return (T) this;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public T setOnBeforeSending(@NonNull Consumer<HttpURLConnection> onBeforeSending) {
        this.onBeforeSending = onBeforeSending;
        return (T) this;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public T setOnTimeout(@NonNull Runnable onTimeout) {
        this.onTimeout = onTimeout;
        return (T) this;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public T setOnException(@NonNull Consumer<Exception> onException) {
        this.onException = onException;
        return (T) this;
    }

}
