package e.nayanda.eatr;

import e.nayanda.eatr.model.Finisher;
import e.nayanda.eatr.model.Response;
import e.nayanda.eatr.model.RestFinisher;
import e.nayanda.eatr.model.RestResponse;

/**
 * Created by nayanda on 08/02/18.
 */

class HttpPut extends BaseHttpRequestWithBody<HttpPost> {

    @Override
    public void asyncExecute(final Finisher finisher) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                finisher.onFinished(executor("PUT", body));
            }
        });
        thread.run();
    }

    @Override
    public <O> void asyncExecute(RestFinisher<O> finisher, Class<O> withModelClass) {
        UnsupportedOperationException exception = new UnsupportedOperationException("PUT doesn't have response body");
        finisher.onFinished(
                new RestResponse<O>(null, -1, false, exception));
        throw exception;
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
