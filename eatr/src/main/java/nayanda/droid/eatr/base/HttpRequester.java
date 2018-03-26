package nayanda.droid.eatr.base;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

import nayanda.droid.eatr.digester.Consumer;
import nayanda.droid.eatr.digester.Response;

/**
 * Created by nayanda on 26/03/18.
 */

public class HttpRequester extends AsyncTask<Void, Float, Response> {

    private final Consumer<HttpURLConnection> onBeforeSending;
    private final Runnable onBeforeStart;
    private final Runnable onTimeout;
    private final Consumer<Exception> onException;
    private final Consumer<Float> onProgress;
    private final Consumer<Response> onResponded;
    private final Consumer<Response> onFinish;

    private final String url;
    private final Map<String, String> params;
    private final Map<String, String> headers;
    private final String body;
    private int timeout;
    private String method;

    HttpRequester(String url, String method, Map<String, String> params, Map<String, String> headers, String body,
                  int timeout, Runnable onBeforeStart, Consumer<HttpURLConnection> onBeforeSending,
                  Runnable onTimeout, Consumer<Exception> onException,
                  Consumer<Float> onProgress, Consumer<Response> onResponded,
                  Consumer<Response> onFinish) {
        this.url = url;
        this.body = body;
        this.params = params;
        this.headers = headers;
        this.timeout = timeout;
        this.method = method;
        if(onBeforeSending == null) this.onBeforeSending = new Consumer<HttpURLConnection>() {
            @Override
            public void onConsume(HttpURLConnection param) {
            }
        };
        else this.onBeforeSending = onBeforeSending;
        if(onBeforeStart == null)this.onBeforeStart = new Runnable() {
            @Override
            public void run() {
            }
        };
        else this.onBeforeStart = onBeforeStart;
        if(onTimeout == null) this.onTimeout = new Runnable() {
            @Override
            public void run() {
            }
        };
        else this.onTimeout = onTimeout;
        if(onException == null) this.onException = new Consumer<Exception>() {
            @Override
            public void onConsume(Exception param) {
            }
        };
        else this.onException = onException;
        if(onProgress == null) this.onProgress = new Consumer<Float>() {
            @Override
            public void onConsume(Float param) {
            }
        };
        else this.onProgress = onProgress;
        if(onResponded == null) this.onResponded = new Consumer<Response>() {
            @Override
            public void onConsume(Response param) {
            }
        };
        else this.onResponded = onResponded;
        if(onFinish == null) this.onFinish = new Consumer<Response>() {
            @Override
            public void onConsume(Response response) {
            }
        };
        else this.onFinish = onFinish;
    }

    @NonNull
    public static String buildUrlWithParam(@NonNull String url, Map<String, String> params) throws UnsupportedEncodingException {
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

    public static void addHeaders(@NonNull HttpURLConnection connection, @NonNull Map<String, String> headers) {
        if (headers.size() == 0) return;
        Set<Map.Entry<String, String>> entries = headers.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            connection.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    public static void addBody(@NonNull HttpURLConnection connection, String body) throws IOException {
        if (body == null) return;
        connection.setRequestProperty("Content-Length", (Integer.valueOf(body.length())).toString());
        connection.getOutputStream().write(body.getBytes(Charset.forName("UTF8")));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        publishProgress(0.0f);
        try {
            onBeforeStart.run();
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
        publishProgress(0.25f);
    }

    @Override
    protected Response doInBackground(Void[] _) {
        try {
            publishProgress(0.5f);
            HttpURLConnection connection = initRequest();
            if (body != null) {
                if (!body.equals("")) addBody(connection, body);
            }
            onBeforeSending.onConsume(connection);
            Response response = HttpURLConnectionHelper.execute(connection);
            publishProgress(1f);
            return response;
        }
        catch (Exception e){
            try {
                if (e instanceof SocketTimeoutException)
                    onTimeout.run();
                else onException.onConsume(e);
                publishProgress(1f);
            } catch (Exception er) {
                Log.e("ERROR", er.toString());
            }
            return new Response(null, -1, false, e);
        }
    }

    @Override
    protected void onProgressUpdate(Float... values) {
        onProgress.onConsume(values[0]);
    }

    @Override
    protected void onPostExecute(Response response) {
        try {
            onResponded.onConsume(response);
            onFinish.onConsume(response);
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }
    }

    @NonNull
    private HttpURLConnection initRequest() throws IOException {
        String fullUrl = buildUrlWithParam(url, params);
        URL urlObj = new URL(fullUrl);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        addHeaders(connection, headers);
        connection.setRequestMethod(method);
        return connection;
    }
}
