package nayanda.droid.eatr.base;

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

    T setUrl(String url);

    T setParams(Map<String, String> params);

    T addParam(String key, String value);

    T setHeaders(Map<String, String> headers);

    T addHeaders(String key, String value);

    T addAuthorization(String token);

    T setTimeout(int timeout);


    void asyncExecute(final ProgressDigester<Response> responseProgressDigester);

    <O> void asyncExecute(final ProgressDigester<RestResponse<O>> restResponseProgressDigester, final Class<O> withModelClass);

    void asyncExecute(final Digester<Response> responseDigester);

    <O> void asyncExecute(final Digester<RestResponse<O>> restResponseDigester, final Class<O> withModelClass);

    void asyncExecute(final Finisher<Response> responseFinisher);

    <O> void asyncExecute(final Finisher<RestResponse<O>> restResponseFinisher, final Class<O> withModelClass);

    Response execute();

    <O> RestResponse<O> execute(Class<O> withModelClass);
}
