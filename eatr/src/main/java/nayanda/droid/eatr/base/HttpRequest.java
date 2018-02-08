package nayanda.droid.eatr.base;

import java.util.Map;

import nayanda.droid.eatr.digester.Finisher;
import nayanda.droid.eatr.digester.Response;
import nayanda.droid.eatr.digester.RestResponse;

/**
 * Created by nayanda on 07/02/18.
 */

public interface HttpRequest<T extends HttpRequest> {
    T setUrl(String url);

    T setParams(Map<String, String> params);

    T setHeaders(Map<String, String> headers);

    T addHeaders(String key, String value);

    T addAuthorization(String token);

    T setTimeout(int timeout);

    void asyncExecute(Finisher<Response> finisher);

    <O> void asyncExecute(Finisher<RestResponse<O>> finisher, Class<O> withModelClass);

    Response execute();

    <O> RestResponse<O> execute(Class<O> withModelClass);
}
