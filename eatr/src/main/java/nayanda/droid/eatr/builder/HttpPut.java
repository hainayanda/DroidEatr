package nayanda.droid.eatr.builder;

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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = executor("PUT", body, responseDigester);
                asyncResponseConsumer(responseDigester, response);
            }
        });
        thread.run();
    }

    @Override
    public <O> void asyncExecute(Digester<RestResponse<O>> restResponseDigester, Class<O> withModelClass) {
        throw new UnsupportedOperationException("PUT doesn't have response body");
    }

    @Override
    public void asyncExecute(final Finisher<Response> responseFinisher) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = executor("PUT", body);
                responseFinisher.onFinished(response);
            }
        });
        thread.run();
    }

    @Override
    public <O> void asyncExecute(Finisher<RestResponse<O>> restResponseFinisher, Class<O> withModelClass) {
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
