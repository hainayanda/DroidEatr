package nayanda.droid.eatr.builder;

import android.support.annotation.NonNull;

import nayanda.droid.eatr.base.BaseHttpRequest;
import nayanda.droid.eatr.base.HttpRequestExecutorImpl;
import nayanda.droid.eatr.base.HttpRequestRestExecutorImpl;
import nayanda.droid.eatr.digester.Digester;
import nayanda.droid.eatr.digester.Finisher;
import nayanda.droid.eatr.digester.ProgressDigester;
import nayanda.droid.eatr.digester.Response;
import nayanda.droid.eatr.digester.RestResponse;

/**
 * Created by nayanda on 08/02/18.
 */

public class HttpGet extends BaseHttpRequest<HttpGet> {

    HttpGet() {
        super("GET");
    }

    @Override
    public void asyncExecute(@NonNull final ProgressDigester<Response> responseProgressDigester) {
        asyncExecutor(method, null, new HttpRequestExecutorImpl(this, responseProgressDigester));
    }

    @Override
    public <O> void asyncExecute(@NonNull ProgressDigester<RestResponse<O>> restResponseProgressDigester, @NonNull Class<O> withModelClass) {
        asyncExecutor(method, null, new HttpRequestRestExecutorImpl<>(this, withModelClass, restResponseProgressDigester));
    }

    @Override
    public void asyncExecute(@NonNull final Digester<Response> responseDigester) {
        asyncExecutor(method, null, new HttpRequestExecutorImpl(this, responseDigester));
    }

    @Override
    public <O> void asyncExecute(@NonNull final Digester<RestResponse<O>> restResponseDigester, @NonNull final Class<O> withModelClass) {
        asyncExecutor(method, null, new HttpRequestRestExecutorImpl<>(this, withModelClass, restResponseDigester));
    }

    @Override
    public void asyncExecute(@NonNull final Finisher<Response> responseFinisher) {
        asyncExecutor(method, null, new HttpRequestExecutorImpl(this, responseFinisher));
    }

    @Override
    public <O> void asyncExecute(@NonNull final Finisher<RestResponse<O>> restResponseFinisher, @NonNull final Class<O> withModelClass) {
        asyncExecutor(method, null, new HttpRequestRestExecutorImpl<>(this, withModelClass, restResponseFinisher));
    }

    @NonNull
    @Override
    public Response execute() {
        return executor(method);
    }

    @NonNull
    @Override
    public <O> RestResponse<O> execute(@NonNull Class<O> withModelClass) {
        return executor(withModelClass, method);
    }
}
