package nayanda.droid.eatr.digester;

import java.net.HttpURLConnection;

/**
 * Created by nayanda on 10/02/18.
 * complete digester for HttpRequest
 *
 * @param <T> Response object
 */
public interface Digester<T extends Response> {
    /**
     * Will run right before estabilishing the connection
     *
     * @param connection
     */
    void onBeforeSending(HttpURLConnection connection);

    /**
     * will ONLY run when connection got a response
     *
     * @param response
     */
    void onResponded(T response);

    /**
     * will ONLY run when connection is timeout
     */
    void onTimeout();
}
