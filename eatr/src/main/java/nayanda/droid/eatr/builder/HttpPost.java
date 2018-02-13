package nayanda.droid.eatr.builder;

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

class HttpPost extends BaseHttpRequestWithBody<HttpPost> {

    @Override
    public void asyncExecute(@NonNull ProgressDigester<Response> responseProgressDigester) {
        asyncExecutor("POST", body, responseProgressDigester);
    }

    @Override
    public <O> void asyncExecute(@NonNull ProgressDigester<RestResponse<O>> restResponseProgressDigester, @NonNull Class<O> withModelClass) {
        asyncExecutor(withModelClass, "POST", body, restResponseProgressDigester);
    }

    @Override
    public void asyncExecute(@NonNull final Digester<Response> responseDigester) {
        asyncExecutor("POST", body, responseDigester);
    }

    @Override
    public <O> void asyncExecute(@NonNull final Digester<RestResponse<O>> restResponseDigester, @NonNull final Class<O> withModelClass) {
        asyncExecutor(withModelClass, "POST", body, restResponseDigester);
    }

    @Override
    public void asyncExecute(@NonNull final Finisher<Response> responseFinisher) {
        asyncExecutor("POST", body, responseFinisher);
    }

    @Override
    public <O> void asyncExecute(@NonNull final Finisher<RestResponse<O>> restResponseFinisher, @NonNull final Class<O> withModelClass) {

        asyncExecutor(withModelClass, "POST", body, restResponseFinisher);
    }

    @NonNull
    @Override
    public Response execute() {
        return executor("POST", body);
    }

    @NonNull
    @Override
    public <O> RestResponse<O> execute(@NonNull Class<O> withModelClass) {
        return executor(withModelClass, "POST", body);
    }
}
