package nayanda.droid.eatr.digester;

import android.support.annotation.NonNull;

import java.net.HttpURLConnection;

/**
 * Created by nayanda on 10/02/18.
 * complete digester for HttpRequest
 *
 * @param <T> Response object
 */
public interface Digester<T extends Response> {
    /**
     * Will run right before establishing the connection
     *
     * @param connection
     */
    void onBeforeSending(@NonNull HttpURLConnection connection);

    /**
     * will ONLY run when connection got a response
     *
     * @param response
     */
    void onResponded(@NonNull T response);

    /**
     * will ONLY run when connection is timeout
     */
    void onTimeout();

    /**
     * will ONLY run when there is an unhandled exception
     */
    void onException(@NonNull Exception exception);
}
