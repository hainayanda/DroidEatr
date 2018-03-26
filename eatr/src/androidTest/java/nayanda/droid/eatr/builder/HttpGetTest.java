package nayanda.droid.eatr.builder;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.HttpURLConnection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import nayanda.droid.eatr.digester.Consumer;
import nayanda.droid.eatr.digester.Response;
import nayanda.droid.eatr.model.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by nayanda on 13/02/18.
 */

@RunWith(AndroidJUnit4.class)
public class HttpGetTest {

    @Test
    public void sync() throws Exception {
        Response response = HttpRequestBuilder.httpGet()
                .setUrl("http://staging-webservice.catch.co.id/user/get")
                .addHeaders("API-KEY", "default").addParam("username", "nayanda")
                .execute();
        assertFalse(response.hadException());
        assertTrue(response.isSuccess());
        assertTrue(response.getParsedBody(User.class) != null);
        User user = response.getParsedBody(User.class);
        assertEquals(user.getUserName(), "nayanda");
    }

    @Test
    public void async() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Response[] reservedResponse = new Response[2];
        final boolean[] isRunBeforeSending = {false};
        final boolean[] isTimeout = {false};
        final boolean[] isException = {false};
        final int[] progressCount = {0};
        final float[] finalProgress = new float[1];
        HttpRequestBuilder.httpGet()
                .setUrl("http://staging-webservice.catch.co.id/user/get")
                .addHeaders("API-KEY", "default").addParam("username", "nayanda")
                .setOnTimeout(new Runnable() {
            @Override
            public void run() {
                isTimeout[0] = true;
                countDownLatch.countDown();
            }
        }).setOnBeforeSending(new Consumer<HttpURLConnection>() {
            @Override
            public void onConsume(HttpURLConnection param) {
                isRunBeforeSending[0] = true;
            }
        }).setOnException(new Consumer<Exception>() {
            @Override
            public void onConsume(Exception param) {
                isException[0] = true;
                countDownLatch.countDown();
            }
        }).setOnProgress(new Consumer<Float>() {
            @Override
            public void onConsume(Float param) {
                finalProgress[0] = param;
                progressCount[0]++;
            }
        }).setOnResponded(new Consumer<Response>() {
            @Override
            public void onConsume(Response param) {
                reservedResponse[0] = param;
                countDownLatch.countDown();
            }
        }).setOnFinish(new Consumer<Response>() {
            @Override
            public void onConsume(Response param) {
                reservedResponse[1] = param;
                countDownLatch.countDown();
            }
        }).execute();
        countDownLatch.await(10000, TimeUnit.MILLISECONDS);
        assertTrue(isRunBeforeSending[0]);
        assertTrue(progressCount[0] >= 2);
        assertTrue(Math.abs(finalProgress[0] - 1f) <= 0.01f);
        assertFalse(isTimeout[0]);
        assertFalse(isException[0]);
        assertNotNull(reservedResponse[0]);
        assertNotNull(reservedResponse[1]);
        assertEquals(reservedResponse[0], reservedResponse[1]);
        assertFalse(reservedResponse[0].hadException());
        assertTrue(reservedResponse[0].isSuccess());
        assertTrue(reservedResponse[0].getParsedBody(User.class) != null);
        User user = (User) reservedResponse[0].getParsedBody(User.class);
        assertEquals(user.getUserName(), "nayanda");
    }

}
