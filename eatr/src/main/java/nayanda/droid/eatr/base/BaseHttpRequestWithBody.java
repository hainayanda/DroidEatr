package nayanda.droid.eatr.base;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * Created by nayanda on 08/02/18.
 */

public abstract class BaseHttpRequestWithBody<T extends BaseHttpRequestWithBody> extends BaseHttpRequest<T> implements HttpRequestWithBody<T> {

    protected String body;

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public T addBody(@NonNull String body) {
        this.body = body;
        return (T) this;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <O> T addJsonBody(@NonNull O obj) {
        Gson gson = new Gson();
        this.body = gson.toJson(obj);
        addHeaders("Content-Type", "application/json");
        return (T) this;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public T addFormUrlEncoded(@NonNull Map<String, String> form) throws UnsupportedEncodingException {
        if (form.size() == 0) return (T) this;
        StringBuilder builder = new StringBuilder();
        Set<Map.Entry<String, String>> entries = form.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = URLEncoder.encode(entry.getKey(), "UTF-8");
            String value = URLEncoder.encode(entry.getValue(), "UTF-8");
            builder = builder.append(key).append("=").append(value).append("&");
        }
        if (builder.length() > 0 && builder.charAt(builder.length() - 1) == '&')
            builder = builder.deleteCharAt(builder.length() - 1);
        addBody(builder.toString());
        addHeaders("Content-Type", "application/x-www-form-urlencoded");
        return (T) this;
    }
}
