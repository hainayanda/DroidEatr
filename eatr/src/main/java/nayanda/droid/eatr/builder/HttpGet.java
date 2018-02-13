package nayanda.droid.eatr.builder;

import nayanda.droid.eatr.base.BaseHttpRequest;
import nayanda.droid.eatr.digester.Digester;
import nayanda.droid.eatr.digester.Finisher;
import nayanda.droid.eatr.digester.ProgressDigester;
import nayanda.droid.eatr.digester.Response;
import nayanda.droid.eatr.digester.RestResponse;

/**
 * Created by nayanda on 08/02/18.
 */

class HttpGet extends BaseHttpRequest<HttpGet> {

    @Override
    public void asyncExecute(ProgressDigester<Response> responseProgressDigester) {
        asyncExecutor("GET", null, responseProgressDigester);
    }

    @Override
    public <O> void asyncExecute(ProgressDigester<RestResponse<O>> restResponseProgressDigester, Class<O> withModelClass) {
        asyncExecutor(withModelClass, "GET", null, restResponseProgressDigester);
    }

    @Override
    public void asyncExecute(final Digester<Response> responseDigester) {
        asyncExecutor("GET", null, responseDigester);
    }

    @Override
    public <O> void asyncExecute(final Digester<RestResponse<O>> restResponseDigester, final Class<O> withModelClass) {
        asyncExecutor(withModelClass, "GET", null, restResponseDigester);
    }

    @Override
    public void asyncExecute(final Finisher<Response> responseFinisher) {
        asyncExecutor("GET", null, responseFinisher);
    }

    @Override
    public <O> void asyncExecute(final Finisher<RestResponse<O>> restResponseFinisher, final Class<O> withModelClass) {
        asyncExecutor(withModelClass, "GET", null, restResponseFinisher);
    }

    @Override
    public Response execute() {
        return executor("GET");
    }

    @Override
    public <O> RestResponse<O> execute(Class<O> withModelClass) {
        return executor(withModelClass, "GET");
    }
}
