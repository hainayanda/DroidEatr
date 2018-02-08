package nayanda.droid.eatr.digester;

/**
 * Created by nayanda on 07/02/18.
 */
public interface Finisher<T extends Response> {
    void onResponded(T response);

    void onTimeout();
}
