package nayanda.droid.eatr.model;

/**
 * Created by nayanda on 07/02/18.
 */

public interface RestFinisher<T> {
    void onFinished(RestResponse<T> response);
}
