package nayanda.droid.eatr.base;

import java.util.Map;

import nayanda.droid.eatr.digester.Digester;
import nayanda.droid.eatr.digester.Finisher;
import nayanda.droid.eatr.digester.Response;
import nayanda.droid.eatr.digester.RestResponse;

/**
 * Created by nayanda on 07/02/18.
 */

public interface HttpRequest<T extends HttpRequest> {
    T setUrl(String url);

    T setParams(Map<String, String> params);

    T addParam(String key, String value);

    T setHeaders(Map<String, String> headers);

    T addHeaders(String key, String value);

    T addAuthorization(String token);

    T setTimeout(int timeout);

    void asyncExecute(Digester<Response> responseDigester);

    <O> void asyncExecute(Digester<RestResponse<O>> restResponseDigester, Class<O> withModelClass);

    void asyncExecute(Finisher<Response> responseFinisher);

    <O> void asyncExecute(Finisher<RestResponse<O>> restResponseFinisher, Class<O> withModelClass);

    /**
     * @return will return null if timeout
     */
    Response execute();

    /**
     *
     * @param withModelClass
     * @param <O>
     * @return will return null if timeout
     */
    <O> RestResponse<O> execute(Class<O> withModelClass);
}
