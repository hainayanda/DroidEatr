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

import nayanda.droid.eatr.digester.Digester;
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

    protected static <O extends Response> void asyncResponseConsumer(Digester<O> oDigester, O response) {
        if (response.hadException()) {
            if (response.getException() instanceof SocketTimeoutException) {
                oDigester.onTimeout();
                return;
            }
        }
        oDigester.onResponded(response);
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

    protected Response executor(final String method, final String body, final Digester<Response> responseDigester) {
        final Response[] response = new Response[1];
        HttpTask task = new HttpTask(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = initRequest();
                    connection.setRequestMethod(method);
                    if (body != null) {
                        if (!body.equals("")) HttpURLConnectionHelper.addBody(connection, body);
                    }
                    if (responseDigester != null) {
                        responseDigester.onBeforeSending(connection);
                    }
                    response[0] = HttpURLConnectionHelper.execute(connection);
                } catch (IOException exception) {
                    response[0] = new Response(null, -1, false, exception);
                }
            }
        });
        task.execute();
        try {
            task.wait(timeout * 2);
        } catch (InterruptedException exception) {
            response[0] = new Response(null, -1, false, exception);
        }
        return response[0];
    }

    protected <O> RestResponse<O> executor(final Class<O> oClass, final String method, final String body, final Digester<RestResponse<O>> restResponseDigester) {
        final RestResponse[] restResponse = new RestResponse[1];
        HttpTask task = new HttpTask(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = initRequest();
                    connection.setRequestMethod(method);
                    if (body != null) {
                        if (!body.equals("")) HttpURLConnectionHelper.addBody(connection, body);
                    }
                    if (restResponseDigester != null)
                        restResponseDigester.onBeforeSending(connection);
                    restResponse[0] = HttpURLConnectionHelper.execute(connection, oClass);
                } catch (IOException exception) {
                    restResponse[0] = new RestResponse<O>(null, null, -1, false, exception);
                }
            }
        });
        task.execute();
        try {
            task.wait(timeout * 2);
        } catch (InterruptedException exception) {
            restResponse[0] = new RestResponse<O>(null, null, -1, false, exception);
        }
        return restResponse[0];
    }

    protected Response executor(String method) {
        return executor(method, null, null);
    }

    protected <O> RestResponse<O> executor(Class<O> oClass, String method) {
        return executor(oClass, method, null, null);
    }

    protected Response executor(String method, Digester<Response> responseDigester) {
        return executor(method, null, responseDigester);
    }

    protected <O> RestResponse<O> executor(Class<O> oClass, String method, Digester<RestResponse<O>> restResponseDigester) {
        return executor(oClass, method, null, restResponseDigester);
    }

    protected Response executor(String method, String body) {
        return executor(method, body, null);
    }

    protected <O> RestResponse<O> executor(Class<O> oClass, String method, String body) {
        return executor(oClass, method, body, null);
    }

    private static class HttpTask extends AsyncTask<Object, Integer, Object> {

        private Runnable runnable;

        HttpTask(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        protected Object doInBackground(Object... _) {
            runnable.run();
            return null;
        }
    }

}
