package nayanda.droid.eatr.base;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import nayanda.droid.eatr.digester.Consumer;
import nayanda.droid.eatr.digester.Response;

/**
 * Created by nayanda on 08/02/18.
 */

public class HttpURLConnectionHelper {

    private static boolean isSuccess(int responseCode) {
        return (responseCode >= 200 && responseCode < 300);
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
        connection.disconnect();
        return response;
    }
}
