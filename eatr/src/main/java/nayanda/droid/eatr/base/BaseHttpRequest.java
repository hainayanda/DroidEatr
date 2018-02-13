package nayanda.droid.eatr.base;

import android.os.AsyncTask;

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
import java.util.concurrent.TimeUnit;

import nayanda.droid.eatr.digester.Digester;
import nayanda.droid.eatr.digester.Finisher;
import nayanda.droid.eatr.digester.Response;
import nayanda.droid.eatr.digester.RestResponse;
import nayanda.droid.eatr.utils.HttpURLConnectionHelper;

/**
 * Created by nayanda on 08/02/18.
 */

public abstract class BaseHttpRequest<T extends BaseHttpRequest> implements HttpRequest<T> {

    private String url;
    private Map<String, String> params;
    private Map<String, String> headers;
    private int timeout = 10000;

    private static String buildUrlWithParam(String url, Map<String, String> params) throws UnsupportedEncodingException {
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

    @Override
    public T setUrl(String url) {
        if (url == null) throw new NullPointerException("Url is null");
        this.url = url;
        return (T) this;
    }

    @Override
    public T setParams(Map<String, String> params) {
        if (params == null) throw new NullPointerException("Param is null");
        this.params = params;
        return (T) this;
    }

    @Override
    public T addParam(String key, String value) {
        if (params == null) params = new HashMap<>();
        params.put(key, value);
        return (T) this;
    }

    @Override
    public T setHeaders(Map<String, String> headers) {
        if (headers == null) throw new NullPointerException("Header is null");
        this.headers = headers;
        return (T) this;
    }

    @Override
    public T addHeaders(String key, String value) {
        if (headers == null) headers = new HashMap<>();
        headers.put(key, value);
        return (T) this;
    }

    @Override
    public T addAuthorization(String token) {
        if (headers == null) headers = new HashMap<>();
        headers.put("Authorization", "bearer " + token);
        return (T) this;
    }

    @Override
    public T setTimeout(int timeout) {
        this.timeout = timeout;
        return (T) this;
    }

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
            final String method, final String body, final Digester<Response> responseDigester) {
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
            final Class<O> oClass, final String method, final String body,
            final Digester<RestResponse<O>> restResponseDigester) {
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
            final String method, final String body, final Finisher<Response> responseFinisher) {
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
            final Class<O> oClass, final String method, final String body,
            final Finisher<RestResponse<O>> restResponseFinisher) {
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

    protected Response executor(final String method, final String body) {
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
            countDownLatch.await(timeout * 2, TimeUnit.MILLISECONDS);
        } catch (InterruptedException exception) {
            response[0] = new Response(null, -1, false, exception);
        }
        return response[0];
    }

    protected <O> RestResponse<O> executor(
            final Class<O> oClass, final String method, final String body) {
        final RestResponse[] restResponse = new RestResponse[1];
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
            countDownLatch.await(timeout * 2, TimeUnit.MILLISECONDS);
        } catch (InterruptedException exception) {
            restResponse[0] = new RestResponse<O>(null, null, -1, false, exception);
        }
        return restResponse[0];
    }

    protected Response executor(String method) {
        return executor(method, null);
    }

    protected <O> RestResponse<O> executor(Class<O> oClass, String method) {
        return executor(oClass, method, null);
    }

}
