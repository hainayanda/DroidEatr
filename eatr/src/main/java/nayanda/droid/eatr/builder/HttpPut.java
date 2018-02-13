package nayanda.droid.eatr.builder;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import nayanda.droid.eatr.base.BaseHttpRequestWithBody;
import nayanda.droid.eatr.digester.Digester;
import nayanda.droid.eatr.digester.Finisher;
import nayanda.droid.eatr.digester.ProgressDigester;
import nayanda.droid.eatr.digester.Response;
import nayanda.droid.eatr.digester.RestResponse;

/**
 * Created by nayanda on 08/02/18.
 */

class HttpPut extends BaseHttpRequestWithBody<HttpPost> {

    @Override
    public void asyncExecute(@NonNull ProgressDigester<Response> responseProgressDigester) {
        asyncExecutor("PUT", body, responseProgressDigester);
    }

    @Override
    public <O> void asyncExecute(@NonNull ProgressDigester<RestResponse<O>> restResponseProgressDigester, @NonNull Class<O> withModelClass) {
        throw new UnsupportedOperationException("PUT doesn't have response body");
    }

    @Override
    public void asyncExecute(@NonNull final Digester<Response> responseDigester) {
        asyncExecutor("PUT", body, responseDigester);
    }

    @Override
    public <O> void asyncExecute(@NonNull final Digester<RestResponse<O>> restResponseDigester, @NonNull final Class<O> withModelClass) {
        throw new UnsupportedOperationException("PUT doesn't have response body");
    }

    @Override
    public void asyncExecute(@NonNull final Finisher<Response> responseFinisher) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Response response = executor("PUT", body);
                responseFinisher.onFinished(response);
            }
        });
    }

    @Override
    public <O> void asyncExecute(@NonNull final Finisher<RestResponse<O>> restResponseFinisher, @NonNull final Class<O> withModelClass) {
        throw new UnsupportedOperationException("PUT doesn't have response body");
    }

    @NonNull
    @Override
    public Response execute() {
        return executor("PUT", body);
    }

    @NonNull
    @Override
    public <O> RestResponse<O> execute(@NonNull Class<O> withModelClass) {
        throw new UnsupportedOperationException("PUT doesn't have response body");
    }
}
