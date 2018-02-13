package nayanda.droid.eatr.builder;

import android.os.AsyncTask;

import nayanda.droid.eatr.base.BaseHttpRequestWithBody;
import nayanda.droid.eatr.digester.Digester;
import nayanda.droid.eatr.digester.Finisher;
import nayanda.droid.eatr.digester.Response;
import nayanda.droid.eatr.digester.RestResponse;

/**
 * Created by nayanda on 08/02/18.
 */

class HttpPut extends BaseHttpRequestWithBody<HttpPost> {

    @Override
    public void asyncExecute(final Digester<Response> responseDigester) {
        asyncExecutor("PUT", body, responseDigester);
    }

    @Override
    public <O> void asyncExecute(final Digester<RestResponse<O>> restResponseDigester, final Class<O> withModelClass) {
        throw new UnsupportedOperationException("PUT doesn't have response body");
    }

    @Override
    public void asyncExecute(final Finisher<Response> responseFinisher) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Response response = executor("PUT", body);
                responseFinisher.onFinished(response);
            }
        });
    }

    @Override
    public <O> void asyncExecute(final Finisher<RestResponse<O>> restResponseFinisher, final Class<O> withModelClass) {
        throw new UnsupportedOperationException("PUT doesn't have response body");
    }

    @Override
    public Response execute() {
        return executor("PUT", body);
    }

    @Override
    public <O> RestResponse<O> execute(Class<O> withModelClass) {
        throw new UnsupportedOperationException("PUT doesn't have response body");
    }
}
