package nayanda.droid.eatr.base;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import nayanda.droid.eatr.digester.Digester;
import nayanda.droid.eatr.digester.Finisher;
import nayanda.droid.eatr.digester.ProgressDigester;
import nayanda.droid.eatr.digester.Response;
import nayanda.droid.eatr.digester.RestResponse;

/**
 * Created by nayanda on 08/02/18.
 */

public abstract class BaseHttpRequest<T extends BaseHttpRequest> implements HttpRequest<T> {

    private String url;
    private Map<String, String> params;
    private Map<String, String> headers;
    private int timeout = 10000;

    @NonNull
    private static String buildUrlWithParam(@NonNull String url, Map<String, String> params) throws UnsupportedEncodingException {
        if (params == null) return url;
        if (params.size() == 0) return url;
        StringBuilder strBuilder = new StringBuilder().append(url).append("?");
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            strBuilder = strBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8")).append("=")
                    .append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
        }
        if (strBuilder.charAt(strBuilder.length() - 1) == '&')
            strBuilder = strBuilder.deleteCharAt(strBuilder.length() - 1);
        return strBuilder.toString();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public T setUrl(@NonNull String url) {
        this.url = url;
        return (T) this;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public T setParams(@NonNull Map<String, String> params) {
        this.params = params;
        return (T) this;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public T addParam(@NonNull String key, @NonNull String value) {
        if (params == null) params = new HashMap<>();
        params.put(key, value);
        return (T) this;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public T setHeaders(@NonNull Map<String, String> headers) {
        this.headers = headers;
        return (T) this;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public T addHeaders(@NonNull String key, @NonNull String value) {
        if (headers == null) headers = new HashMap<>();
        headers.put(key, value);
        return (T) this;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public T addAuthorization(@NonNull String token) {
        if (headers == null) headers = new HashMap<>();
        headers.put("Authorization", "bearer " + token);
        return (T) this;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public T setTimeout(int timeout) {
        this.timeout = timeout;
        return (T) this;
    }

    @NonNull
    private HttpURLConnection initRequest(@NonNull ProgressDigester progressDigester) throws IOException {
        String fullUrl = buildUrlWithParam(url, params);
        progressDigester.onProgress(0.1f);
        URL urlObj = new URL(fullUrl);
        progressDigester.onProgress(0.2f);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        progressDigester.onProgress(0.3f);
        connection.setConnectTimeout(timeout);
        progressDigester.onProgress(0.3f);
        connection.setReadTimeout(timeout);
        progressDigester.onProgress(0.4f);
        HttpURLConnectionHelper.addHeaders(connection, headers);
        progressDigester.onProgress(0.5f);
        return connection;
    }

    @NonNull
    private HttpURLConnection initRequest() throws IOException {
        String fullUrl = buildUrlWithParam(url, params);
        URL urlObj = new URL(fullUrl);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        HttpURLConnectionHelper.addHeaders(connection, headers);
        return connection;
    }

    protected void asyncExecutor(
            @NonNull final String method, final String body, @NonNull final ProgressDigester<Response> responseProgressDigester) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Response response;
                try {
                    HttpURLConnection connection = initRequest(responseProgressDigester);
                    connection.setRequestMethod(method);
                    responseProgressDigester.onProgress(0.6f);
                    if (body != null) {
                        if (!body.equals("")) HttpURLConnectionHelper.addBody(connection, body);
                    }
                    responseProgressDigester.onBeforeSending(connection);
                    response = HttpURLConnectionHelper.execute(connection, responseProgressDigester);
                    responseProgressDigester.onProgress(1f);
                    responseProgressDigester.onResponded(response);
                } catch (IOException exception) {
                    responseProgressDigester.onProgress(1f);
                    if (exception instanceof SocketTimeoutException)
                        responseProgressDigester.onTimeout();
                    else responseProgressDigester.onException(exception);
                }

            }
        });
    }

    protected <O> void asyncExecutor(
            @NonNull final Class<O> oClass, @NonNull final String method, final String body,
            @NonNull final ProgressDigester<RestResponse<O>> restResponseProgressDigester) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RestResponse<O> response;
                try {
                    HttpURLConnection connection = initRequest(restResponseProgressDigester);
                    connection.setRequestMethod(method);
                    restResponseProgressDigester.onProgress(0.6f);
                    if (body != null) {
                        if (!body.equals("")) HttpURLConnectionHelper.addBody(connection, body);
                    }
                    restResponseProgressDigester.onBeforeSending(connection);
                    response = HttpURLConnectionHelper.execute(connection, oClass, restResponseProgressDigester);
                    restResponseProgressDigester.onProgress(1f);
                    restResponseProgressDigester.onResponded(response);
                } catch (IOException exception) {
                    restResponseProgressDigester.onProgress(1f);
                    if (exception instanceof SocketTimeoutException)
                        restResponseProgressDigester.onTimeout();
                    else restResponseProgressDigester.onException(exception);
                }
            }
        });
    }

    protected void asyncExecutor(
            @NonNull final String method, final String body, @NonNull final Digester<Response> responseDigester) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Response response;
                try {
                    HttpURLConnection connection = initRequest();
                    connection.setRequestMethod(method);
                    if (body != null) {
                        if (!body.equals("")) HttpURLConnectionHelper.addBody(connection, body);
                    }
                    responseDigester.onBeforeSending(connection);
                    response = HttpURLConnectionHelper.execute(connection);
                    responseDigester.onResponded(response);
                } catch (IOException exception) {
                    if (exception instanceof SocketTimeoutException) responseDigester.onTimeout();
                    else responseDigester.onException(exception);
                }

            }
        });
    }

    protected <O> void asyncExecutor(
            @NonNull final Class<O> oClass, @NonNull final String method, final String body,
            @NonNull final Digester<RestResponse<O>> restResponseDigester) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RestResponse<O> response;
                try {
                    HttpURLConnection connection = initRequest();
                    connection.setRequestMethod(method);
                    if (body != null) {
                        if (!body.equals("")) HttpURLConnectionHelper.addBody(connection, body);
                    }
                    restResponseDigester.onBeforeSending(connection);
                    response = HttpURLConnectionHelper.execute(connection, oClass);
                    restResponseDigester.onResponded(response);
                } catch (IOException exception) {
                    if (exception instanceof SocketTimeoutException)
                        restResponseDigester.onTimeout();
                    else restResponseDigester.onException(exception);
                }
            }
        });
    }

    protected void asyncExecutor(
            @NonNull final String method, final String body, @NonNull final Finisher<Response> responseFinisher) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Response response;
                try {
                    HttpURLConnection connection = initRequest();
                    connection.setRequestMethod(method);
                    if (body != null) {
                        if (!body.equals("")) HttpURLConnectionHelper.addBody(connection, body);
                    }
                    response = HttpURLConnectionHelper.execute(connection);
                } catch (IOException exception) {
                    response = new Response(null, -1, false, exception);
                }
                responseFinisher.onFinished(response);
            }
        });
    }

    protected <O> void asyncExecutor(
            @NonNull final Class<O> oClass, @NonNull final String method, final String body,
            @NonNull final Finisher<RestResponse<O>> restResponseFinisher) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                RestResponse<O> response;
                try {
                    HttpURLConnection connection = initRequest();
                    connection.setRequestMethod(method);
                    if (body != null) {
                        if (!body.equals("")) HttpURLConnectionHelper.addBody(connection, body);
                    }
                    response = HttpURLConnectionHelper.execute(connection, oClass);
                } catch (IOException exception) {
                    response = new RestResponse<>(
                            null, null, -1, false, exception);
                }
                restResponseFinisher.onFinished(response);
            }
        });
    }

    @NonNull
    protected Response executor(@NonNull final String method, final String body) {
        final Response[] response = new Response[1];
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = initRequest();
                    connection.setRequestMethod(method);
                    if (body != null) {
                        if (!body.equals("")) HttpURLConnectionHelper.addBody(connection, body);
                    }
                    response[0] = HttpURLConnectionHelper.execute(connection);
                } catch (IOException exception) {
                    response[0] = new Response(null, -1, false, exception);
                }
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException exception) {
            response[0] = new Response(null, -1, false, exception);
        }
        return response[0];
    }

    @NonNull
    protected <O> RestResponse<O> executor(
            @NonNull final Class<O> oClass, @NonNull final String method, final String body) {
        final RestResponse<O>[] restResponse = new RestResponse[1];
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = initRequest();
                    connection.setRequestMethod(method);
                    if (body != null) {
                        if (!body.equals("")) HttpURLConnectionHelper.addBody(connection, body);
                    }
                    restResponse[0] = HttpURLConnectionHelper.execute(connection, oClass);
                } catch (IOException exception) {
                    restResponse[0] = new RestResponse<O>(null, null, -1, false, exception);
                }
                countDownLatch.countDown();
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException exception) {
            restResponse[0] = new RestResponse<O>(null, null, -1, false, exception);
        }
        return restResponse[0];
    }

    @NonNull
    protected Response executor(@NonNull String method) {
        return executor(method, null);
    }

    @NonNull
    protected <O> RestResponse<O> executor(Class<O> oClass, String method) {
        return executor(oClass, method, null);
    }

}
