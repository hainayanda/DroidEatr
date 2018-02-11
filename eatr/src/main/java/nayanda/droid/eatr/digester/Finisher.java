package nayanda.droid.eatr.digester;

/**
 * Created by nayanda on 07/02/18.
 * Basic digester interface
 * @param <T> Response object
 */
public interface Finisher<T extends Response> {
    /**
     * will run after all HttpRequest is finished, with or without response
     * if timeout happen, all of response's property will be null or false except the exception
     * it will contains SocketTimeoutException
     *
     * @param response
     */
    void onFinished(T response);
}
