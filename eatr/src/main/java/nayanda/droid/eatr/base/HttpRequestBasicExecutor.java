package nayanda.droid.eatr.base;

import android.support.annotation.NonNull;

import java.net.HttpURLConnection;

import nayanda.droid.eatr.digester.Consumer;

/**
 * Created by nayanda on 15/02/18.
 */

public interface HttpRequestBasicExecutor<T extends HttpRequestBasicExecutor<T>> {

    @NonNull
    Consumer<HttpURLConnection> getOnBeforeSending();

    @NonNull
    T setOnBeforeSending(@NonNull Consumer<HttpURLConnection> onBeforeSending);

    @NonNull
    Runnable getOnTimeout();

    @NonNull
    T setOnTimeout(@NonNull Runnable onTimeout);

    @NonNull
    Consumer<Exception> getOnException();

    @NonNull
    T setOnException(@NonNull Consumer<Exception> onException);

    @NonNull
    Consumer<Float> getOnProgress();

    @NonNull
    T setOnProgress(@NonNull Consumer<Float> onProgress);

    void execute();

}
