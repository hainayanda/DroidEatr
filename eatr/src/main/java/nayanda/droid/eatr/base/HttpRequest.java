package nayanda.droid.eatr.base;

import android.support.annotation.NonNull;

import java.util.Map;

import nayanda.droid.eatr.digester.Digester;
import nayanda.droid.eatr.digester.Finisher;
import nayanda.droid.eatr.digester.ProgressDigester;
import nayanda.droid.eatr.digester.Response;
import nayanda.droid.eatr.digester.RestResponse;

/**
 * Created by nayanda on 07/02/18.
 */

public interface HttpRequest<T extends HttpRequest> {

    @NonNull
    T setUrl(@NonNull String url);

    @NonNull
    T setParams(@NonNull Map<String, String> params);

    @NonNull
    T addParam(@NonNull String key, @NonNull String value);

    @NonNull
    T setHeaders(@NonNull Map<String, String> headers);

    @NonNull
    T addHeaders(@NonNull String key, @NonNull String value);

    @NonNull
    T addAuthorization(@NonNull String token);

    @NonNull
    T setTimeout(int timeout);


    void asyncExecute(@NonNull final ProgressDigester<Response> responseProgressDigester);

    <O> void asyncExecute(@NonNull final ProgressDigester<RestResponse<O>> restResponseProgressDigester, @NonNull final Class<O> withModelClass);

    void asyncExecute(@NonNull final Digester<Response> responseDigester);

    <O> void asyncExecute(@NonNull final Digester<RestResponse<O>> restResponseDigester, @NonNull final Class<O> withModelClass);

    void asyncExecute(@NonNull final Finisher<Response> responseFinisher);

    <O> void asyncExecute(@NonNull final Finisher<RestResponse<O>> restResponseFinisher, @NonNull final Class<O> withModelClass);

    @NonNull
    Response execute();

    @NonNull
    <O> RestResponse<O> execute(@NonNull Class<O> withModelClass);

    @NonNull
    HttpRequestExecutor usingExecutor();

    @NonNull
    <O> HttpRequestRestExecutor<O> usingExecutor(@NonNull Class<O> withModelClass);

}
