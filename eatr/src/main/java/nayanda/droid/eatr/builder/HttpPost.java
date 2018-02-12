package nayanda.droid.eatr.builder;

import android.os.AsyncTask;

import java.net.SocketTimeoutException;

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
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Response response = executor("POST", body, responseDigester);
                asyncResponseConsumer(responseDigester, response);
            }
        });
    }

    @Override
    public <O> void asyncExecute(final Digester<RestResponse<O>> restResponseDigester, final Class<O> withModelClass) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RestResponse<O> response = executor(withModelClass, "POST", body, restResponseDigester);
                asyncResponseConsumer(restResponseDigester, response);
            }
        });
    }

    @Override
    public void asyncExecute(final Finisher<Response> responseFinisher) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Response response = executor("POST", body);
                responseFinisher.onFinished(response);
            }
        });
    }

    @Override
    public <O> void asyncExecute(final Finisher<RestResponse<O>> restResponseFinisher, final Class<O> withModelClass) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RestResponse<O> response = executor(withModelClass, "POST", body);
                restResponseFinisher.onFinished(response);
            }
        });
    }

    @Override
    public Response execute() {
        Response response = executor("POST", body);
        if (response.getException() instanceof SocketTimeoutException ||
                response.getException() instanceof InterruptedException) return null;
        else return response;
    }

    @Override
    public <O> RestResponse<O> execute(Class<O> withModelClass) {
        RestResponse<O> response = executor(withModelClass, "POST", body);
        if (response.getException() instanceof SocketTimeoutException ||
                response.getException() instanceof InterruptedException) return null;
        else return response;
    }
}
