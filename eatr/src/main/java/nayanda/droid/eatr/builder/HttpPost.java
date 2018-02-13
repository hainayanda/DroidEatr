package nayanda.droid.eatr.builder;

import nayanda.droid.eatr.base.BaseHttpRequestWithBody;
import nayanda.droid.eatr.digester.Digester;
import nayanda.droid.eatr.digester.Finisher;
import nayanda.droid.eatr.digester.Response;
import nayanda.droid.eatr.digester.RestResponse;

/**
 * Created by nayanda on 08/02/18.
 */

class HttpPost extends BaseHttpRequestWithBody<HttpPost> {

    @Override
    public void asyncExecute(final Digester<Response> responseDigester) {
        asyncExecutor("POST", body, responseDigester);
    }

    @Override
    public <O> void asyncExecute(final Digester<RestResponse<O>> restResponseDigester, final Class<O> withModelClass) {

        asyncExecutor(withModelClass, "POST", body, restResponseDigester);
    }

    @Override
    public void asyncExecute(final Finisher<Response> responseFinisher) {
        asyncExecutor("POST", body, responseFinisher);
    }

    @Override
    public <O> void asyncExecute(final Finisher<RestResponse<O>> restResponseFinisher, final Class<O> withModelClass) {

        asyncExecutor(withModelClass, "POST", body, restResponseFinisher);
    }

    @Override
    public Response execute() {
        return executor("POST", body);
    }

    @Override
    public <O> RestResponse<O> execute(Class<O> withModelClass) {
        return executor(withModelClass, "POST", body);
    }
}
