package nayanda.droid.eatr.base;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

import nayanda.droid.eatr.digester.ProgressDigester;
import nayanda.droid.eatr.digester.Response;
import nayanda.droid.eatr.digester.RestResponse;

/**
 * Created by nayanda on 08/02/18.
 */

class HttpURLConnectionHelper {

    @org.jetbrains.annotations.Contract(pure = true)
    private static boolean isSuccess(int responseCode) {
        return (responseCode >= 200 && responseCode < 300);
    }

    static void addHeaders(@NonNull HttpURLConnection connection, @NonNull Map<String, String> headers) {
        if (headers.size() == 0) return;
        Set<Map.Entry<String, String>> entries = headers.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            connection.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    static void addBody(@NonNull HttpURLConnection connection, String body) throws IOException {
        if (body == null) return;
        connection.setRequestProperty("Content-Length", (Integer.valueOf(body.length())).toString());
        connection.getOutputStream().write(body.getBytes(Charset.forName("UTF8")));
    }

    @NonNull
    private static String bufferExtractor(@NonNull BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            builder = builder.append(line).append("\n");
            line = reader.readLine();
        }
        if (builder.length() > 0) builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    @NonNull
    static Response execute(@NonNull HttpURLConnection connection, @NonNull ProgressDigester progressDigester) throws IOException {
        int statusCode;
        Response response;
        statusCode = connection.getResponseCode();
        progressDigester.onProgress(0.7f);
        if (!isSuccess(statusCode) || connection.getInputStream() == null)
            response = new Response(null, statusCode, false);
        else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            progressDigester.onProgress(0.8f);
            String extracted = bufferExtractor(reader);
            response = new Response(extracted, statusCode, isSuccess(statusCode));
        }
        progressDigester.onProgress(0.9f);
        return response;
    }

    @NonNull
    static <T> RestResponse<T> execute(@NonNull HttpURLConnection connection, @NonNull Class<T> tClass, @NonNull ProgressDigester progressDigester) throws IOException {
        int statusCode;
        RestResponse<T> response;
        statusCode = connection.getResponseCode();
        progressDigester.onProgress(0.7f);
        if (!isSuccess(statusCode) || connection.getInputStream() == null)
            response = new RestResponse<>(null, null, statusCode, false);
        else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String extracted = bufferExtractor(reader);
            progressDigester.onProgress(0.8f);
            if (extracted.equals(""))
                response = new RestResponse<>(extracted, null, statusCode, isSuccess(statusCode));
            else {
                Gson gson = new Gson();
                T obj = gson.fromJson(extracted, tClass);
                response = new RestResponse<>(extracted, obj, statusCode, isSuccess(statusCode));
            }
        }
        progressDigester.onProgress(0.9f);
        return response;
    }

    @NonNull
    static Response execute(@NonNull HttpURLConnection connection) throws IOException {
        int statusCode;
        Response response;
        statusCode = connection.getResponseCode();
        if (!isSuccess(statusCode) || connection.getInputStream() == null)
            response = new Response(null, statusCode, false);
        else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String extracted = bufferExtractor(reader);
            response = new Response(extracted, statusCode, isSuccess(statusCode));
        }
        return response;
    }

    @NonNull
    static <T> RestResponse<T> execute(@NonNull HttpURLConnection connection, @NonNull Class<T> tClass) throws IOException {
        int statusCode;
        RestResponse<T> response;
        statusCode = connection.getResponseCode();
        if (!isSuccess(statusCode) || connection.getInputStream() == null)
            response = new RestResponse<>(null, null, statusCode, false);
        else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String extracted = bufferExtractor(reader);
            if (extracted.equals(""))
                response = new RestResponse<>(extracted, null, statusCode, isSuccess(statusCode));
            else {
                Gson gson = new Gson();
                T obj = gson.fromJson(extracted, tClass);
                response = new RestResponse<>(extracted, obj, statusCode, isSuccess(statusCode));
            }
        }
        return response;
    }
}
