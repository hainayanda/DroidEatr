package e.nayanda.eatr.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

import e.nayanda.eatr.model.Response;
import e.nayanda.eatr.model.RestResponse;

/**
 * Created by nayanda on 08/02/18.
 */

public class HttpURLConnectionHelper {

    public static boolean isSuccess(HttpURLConnection connection) throws IOException {
        int responseCode = connection.getResponseCode();
        return (responseCode >= 200 && responseCode < 300);
    }

    private static boolean isSuccess(int responseCode){
        return (responseCode >= 200 && responseCode < 300);
    }

    public static void addHeaders(HttpURLConnection connection, Map<String, String> headers){
        if(headers == null) return;
        if(headers.size() == 0) return;
        Set<Map.Entry<String, String>> entries = headers.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            connection.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    public static void addBody(HttpURLConnection connection, String body) throws IOException {
        if(body == null) return;
        connection.setRequestProperty("Content-Length", (Integer.valueOf(body.length())).toString());
        connection.getOutputStream().write(body.getBytes(Charset.forName("UTF8")));
    }

    private static String bufferExtractor(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();
        String line = reader.readLine();
        while (line != null){
            builder = builder.append(line).append("\n");
            line = reader.readLine();
        }
        if(builder.length() > 0) builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    public static Response execute(HttpURLConnection connection) {
        int statusCode = -1;
        Response response;
        try {
            statusCode = connection.getResponseCode();
            if(!isSuccess(connection) || connection.getInputStream() == null)
                response = new Response(null, statusCode, false);
            else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String extracted = bufferExtractor(reader);
                response = new Response(extracted, statusCode, isSuccess(statusCode));
            }
        } catch (IOException exception) {
            response = new Response(null, statusCode, false, exception);
        }
        return response;
    }

    public static <T> RestResponse<T> execute(HttpURLConnection connection, Class<T> tClass){
        int statusCode = -1;
        RestResponse<T> response;
        try {
            statusCode = connection.getResponseCode();
            if(!isSuccess(connection) || connection.getInputStream() == null)
                response = new RestResponse<>(null, statusCode, false);
            else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String extracted = bufferExtractor(reader);
                if(extracted.equals(""))
                    response = new RestResponse<>(null, statusCode, isSuccess(statusCode));
                else {
                    Gson gson = new Gson();
                    T obj = gson.fromJson(extracted, tClass);
                    response = new RestResponse<>(obj, statusCode, isSuccess(statusCode));
                }
            }
        } catch (IOException exception) {
            response = new RestResponse<>(null, statusCode, false, exception);
        }
        return response;
    }
}
