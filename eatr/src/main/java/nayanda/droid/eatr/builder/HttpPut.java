package nayanda.droid.eatr.builder;

import nayanda.droid.eatr.base.BaseHttpRequestWithBody;
import nayanda.droid.eatr.digester.Finisher;
import nayanda.droid.eatr.digester.Response;
import nayanda.droid.eatr.digester.RestResponse;

/**
 * Created by nayanda on 08/02/18.
 */

class HttpPut extends BaseHttpRequestWithBody<HttpPost> {

    @Override
    public void asyncExecute(final Finisher<Response> finisher) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = executor("PUT", body);
                asyncResponseConsumer(finisher, response);
            }
        });
        thread.run();
    }

    @Override
    public <O> void asyncExecute(Finisher<RestResponse<O>> finisher, Class<O> withModelClass) {
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
