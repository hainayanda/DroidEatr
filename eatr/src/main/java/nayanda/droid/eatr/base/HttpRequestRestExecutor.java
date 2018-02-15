package nayanda.droid.eatr.base;

import android.support.annotation.NonNull;

import nayanda.droid.eatr.digester.Consumer;
import nayanda.droid.eatr.digester.RestResponse;

/**
 * Created by nayanda on 15/02/18.
 */

public interface HttpRequestRestExecutor<T> extends HttpRequestBasicExecutor<HttpRequestRestExecutor<T>> {

    @NonNull
    Class<T> getModelClass();

    @NonNull
    Consumer<RestResponse<T>> getOnResponded();

    @NonNull
    HttpRequestRestExecutor<T> setOnResponded(@NonNull Consumer<RestResponse<T>> onResponded);

    @NonNull
    Consumer<RestResponse<T>> getOnFinished();

    @NonNull
    HttpRequestRestExecutor<T> setOnFinished(@NonNull Consumer<RestResponse<T>> onFinish);
}
