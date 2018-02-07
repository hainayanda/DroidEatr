package e.nayanda.eatr;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import e.nayanda.eatr.model.Response;
import e.nayanda.eatr.model.RestResponse;
import e.nayanda.eatr.utils.HttpURLConnectionHelper;

/**
 * Created by nayanda on 08/02/18.
 */

abstract class BaseHttpRequest<T extends BaseHttpRequest> implements HttpRequest<T> {

    protected String url;
    protected Map<String, String> params;
    protected Map<String, String> headers;
    protected int timeout = 10000;

    @Override
    public T setUrl(String url) {
        if(url == null) throw new NullPointerException("Url is null");
        this.url = url;
        return (T)this;
    }

    @Override
    public T setParams(Map<String, String> params) {
        if(params == null) throw new NullPointerException("Url is null");
        this.params = params;
        return (T)this;
    }

    @Override
    public T setHeaders(Map<String, String> headers) {
        if(headers == null) throw new NullPointerException("Url is null");
        this.headers = headers;
        return (T)this;
    }

    @Override
    public T addHeaders(String key, String value) {
        if(headers == null) headers = new HashMap<>();
        headers.put(key, value);
        return (T)this;
    }

    @Override
    public T addAuthorization(String token) {
        if(headers == null) headers = new HashMap<>();
        headers.put("Authorization", "bearer " + token);
        return (T)this;
    }

    @Override
    public T setTimeout(int timeout) {
        this.timeout = timeout;
        return (T)this;
    }

    protected HttpURLConnection initRequest() throws IOException {
        String fullUrl = buildUrlWithParam(url, params);
        URL urlObj = new URL(fullUrl);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setConnectTimeout(timeout);
        connection.setReadTimeout(timeout);
        HttpURLConnectionHelper.addHeaders(connection, headers);
        return connection;
    }

    private static String buildUrlWithParam(String url, Map<String, String> params) throws UnsupportedEncodingException {
        if(params == null) return url;
        if(params.size() == 0) return url;
        StringBuilder strBuilder = new StringBuilder().append(url).append("?");
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry: entries) {
            strBuilder = strBuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8")).append("=")
                    .append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
        }
        strBuilder = strBuilder.deleteCharAt(strBuilder.length() - 1);
        return strBuilder.toString();
    }

    protected Response executor(String method, String body){
        try {
            HttpURLConnection connection = initRequest();
            connection.setRequestMethod(method);
            if(body != null){
                if(!body.equals("")) HttpURLConnectionHelper.addBody(connection, body);
            }
            return HttpURLConnectionHelper.execute(connection);
        } catch (IOException exception) {
            return new Response(null, -1, false, exception);
        }
    }

    protected <O> RestResponse<O> executor(Class<O> oClass, String method, String body){
        try {
            HttpURLConnection connection = initRequest();
            connection.setRequestMethod(method);
            if(body != null){
                if(!body.equals("")) HttpURLConnectionHelper.addBody(connection, body);
            }
            return HttpURLConnectionHelper.execute(connection, oClass);
        }catch (IOException exception){
            return new RestResponse<>(null, -1, false, exception);
        }
    }

    protected Response executor(String method){
        return executor(method, null);
    }

    protected <O> RestResponse<O> executor(Class<O> oClass, String method){
        return executor(oClass, method, null);
    }

}
