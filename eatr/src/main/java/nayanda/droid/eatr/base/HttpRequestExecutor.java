package nayanda.droid.eatr.base;

import android.support.annotation.NonNull;

import nayanda.droid.eatr.digester.Consumer;
import nayanda.droid.eatr.digester.Response;

/**
 * Created by nayanda on 15/02/18.
 */

public interface HttpRequestExecutor extends HttpRequestBasicExecutor<HttpRequestExecutor> {

    @NonNull
    Consumer<Response> getOnResponded();

    @NonNull
    HttpRequestExecutor setOnResponded(@NonNull Consumer<Response> onResponded);

    @NonNull
    Consumer<Response> getOnFinished();

    @NonNull
    HttpRequestExecutor setOnFinished(@NonNull Consumer<Response> onFinish);
}
