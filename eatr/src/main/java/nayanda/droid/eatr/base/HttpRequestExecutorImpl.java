package nayanda.droid.eatr.base;

import android.support.annotation.NonNull;

import java.net.HttpURLConnection;

import nayanda.droid.eatr.digester.Consumer;
import nayanda.droid.eatr.digester.Digester;
import nayanda.droid.eatr.digester.Finisher;
import nayanda.droid.eatr.digester.ProgressDigester;
import nayanda.droid.eatr.digester.Response;

/**
 * Created by nayanda on 15/02/18.
 */

public class HttpRequestExecutorImpl extends BaseHttpRequestExecutor<HttpRequestExecutor> implements HttpRequestExecutor {

    private Consumer<Response> onResponded = new Consumer<Response>() {
        @Override
        public void onConsume(Response param) {
        }
    };

    private Consumer<Response> onFinished = new Consumer<Response>() {
        @Override
        public void onConsume(Response param) {
        }
    };

    public HttpRequestExecutorImpl(BaseHttpRequest httpRequest) {
        super(httpRequest);
    }

    public HttpRequestExecutorImpl(BaseHttpRequest httpRequest, final Finisher<Response> finisher) {
        this(httpRequest);
        this.setOnFinished(new Consumer<Response>() {
            @Override
            public void onConsume(Response param) {
                finisher.onFinished(param);
            }
        });
    }

    public HttpRequestExecutorImpl(BaseHttpRequest httpRequest, final Digester<Response> digester) {
        this(httpRequest);
        this.setOnResponded(new Consumer<Response>() {
            @Override
            public void onConsume(Response param) {
                digester.onResponded(param);
            }
        }).setOnException(new Consumer<Exception>() {
            @Override
            public void onConsume(Exception param) {
                digester.onException(param);
            }
        }).setOnBeforeSending(new Consumer<HttpURLConnection>() {
            @Override
            public void onConsume(HttpURLConnection param) {
                digester.onBeforeSending(param);
            }
        }).setOnTimeout(new Runnable() {
            @Override
            public void run() {
                digester.onTimeout();
            }
        });
    }

    public HttpRequestExecutorImpl(BaseHttpRequest httpRequest, final ProgressDigester<Response> progressDigester) {
        this(httpRequest, (Digester<Response>) progressDigester);
        this.setOnProgress(new Consumer<Float>() {
            @Override
            public void onConsume(Float param) {
                progressDigester.onProgress(param);
            }
        });
    }

    public BaseHttpRequest getHttpRequest() {
        return httpRequest;
    }

    @NonNull
    @Override
    public Consumer<Response> getOnResponded() {
        return onResponded;
    }

    @NonNull
    @Override
    public Consumer<Response> getOnFinished() {
        return onFinished;
    }

    @NonNull
    @Override
    public HttpRequestExecutor setOnResponded(@NonNull Consumer<Response> onResponded) {
        this.onResponded = onResponded;
        return this;
    }

    @NonNull
    @Override
    public HttpRequestExecutor setOnFinished(@NonNull Consumer<Response> onFinished) {
        this.onFinished = onFinished;
        return this;
    }

    @Override
    public void execute() {
        if (httpRequest instanceof HttpRequestWithBody)
            httpRequest.asyncExecutor(httpRequest.method, ((BaseHttpRequestWithBody) httpRequest).getBody(), this);
        httpRequest.asyncExecutor(httpRequest.method, null, this);
    }
}
