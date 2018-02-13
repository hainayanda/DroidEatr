package nayanda.droid.eatr.builder;

import android.support.annotation.NonNull;

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
    public void asyncExecute(@NonNull ProgressDigester<Response> responseProgressDigester) {
        asyncExecutor("GET", null, responseProgressDigester);
    }

    @Override
    public <O> void asyncExecute(@NonNull ProgressDigester<RestResponse<O>> restResponseProgressDigester, @NonNull Class<O> withModelClass) {
        asyncExecutor(withModelClass, "GET", null, restResponseProgressDigester);
    }

    @Override
    public void asyncExecute(@NonNull final Digester<Response> responseDigester) {
        asyncExecutor("GET", null, responseDigester);
    }

    @Override
    public <O> void asyncExecute(@NonNull final Digester<RestResponse<O>> restResponseDigester, @NonNull final Class<O> withModelClass) {
        asyncExecutor(withModelClass, "GET", null, restResponseDigester);
    }

    @Override
    public void asyncExecute(@NonNull final Finisher<Response> responseFinisher) {
        asyncExecutor("GET", null, responseFinisher);
    }

    @Override
    public <O> void asyncExecute(@NonNull final Finisher<RestResponse<O>> restResponseFinisher, @NonNull final Class<O> withModelClass) {
        asyncExecutor(withModelClass, "GET", null, restResponseFinisher);
    }

    @NonNull
    @Override
    public Response execute() {
        return executor("GET");
    }

    @NonNull
    @Override
    public <O> RestResponse<O> execute(@NonNull Class<O> withModelClass) {
        return executor(withModelClass, "GET");
    }
}
