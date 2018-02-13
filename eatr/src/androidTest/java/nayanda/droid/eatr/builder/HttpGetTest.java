package nayanda.droid.eatr.builder;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.HttpURLConnection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import nayanda.droid.eatr.digester.Digester;
import nayanda.droid.eatr.digester.Finisher;
import nayanda.droid.eatr.digester.ProgressDigester;
import nayanda.droid.eatr.digester.Response;
import nayanda.droid.eatr.digester.RestResponse;
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
    public void asyncExecute() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Response[] reservedResponse = new Response[1];
        final boolean[] isRunBeforeSending = {false};
        final boolean[] isTimeout = {false};
        final boolean[] isException = {false};
        final int[] progressCount = {0};
        final float[] finalProgress = new float[1];
        HttpRequestBuilder.httpGet().setUrl("http://staging-webservice.catch.co.id/user/get")
                .addHeaders("API-KEY", "default").addParam("username", "kryokrait")
                .asyncExecute(new ProgressDigester<Response>() {
                    @Override
                    public void onProgress(float progress) {
                        finalProgress[0] = progress;
                        progressCount[0]++;
                    }

                    @Override
                    public void onBeforeSending(HttpURLConnection connection) {
                        isRunBeforeSending[0] = true;
                    }

                    @Override
                    public void onResponded(Response response) {
                        reservedResponse[0] = response;
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onTimeout() {
                        isTimeout[0] = true;
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onException(Exception exception) {
                        isException[0] = true;
                        countDownLatch.countDown();
                    }
                });
        countDownLatch.await(20000, TimeUnit.MILLISECONDS);
        assertTrue(isRunBeforeSending[0]);
        assertTrue(progressCount[0] >= 7);
        assertTrue(Math.abs(finalProgress[0] - 1f) <= 0.01f);
        assertFalse(isTimeout[0]);
        assertFalse(isException[0]);
        assertNotNull(reservedResponse[0]);
        assertFalse(reservedResponse[0].hadException());
        assertTrue(reservedResponse[0].isSuccess());
    }

    @Test
    public void asyncExecute1() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final RestResponse[] reservedResponse = new RestResponse[1];
        final boolean[] isRunBeforeSending = {false};
        final boolean[] isTimeout = {false};
        final boolean[] isException = {false};
        final int[] progressCount = {0};
        final float[] finalProgress = new float[1];
        HttpRequestBuilder.httpGet().setUrl("http://staging-webservice.catch.co.id/user/get")
                .addHeaders("API-KEY", "default").addParam("username", "kryokrait")
                .asyncExecute(new ProgressDigester<RestResponse>() {
                    @Override
                    public void onProgress(float progress) {
                        finalProgress[0] = progress;
                        progressCount[0]++;
                    }

                    @Override
                    public void onBeforeSending(HttpURLConnection connection) {
                        isRunBeforeSending[0] = true;
                    }

                    @Override
                    public void onResponded(RestResponse response) {
                        reservedResponse[0] = response;
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onTimeout() {
                        isTimeout[0] = true;
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onException(Exception exception) {
                        isException[0] = true;
                        countDownLatch.countDown();
                    }
                }, User.class);
        countDownLatch.await(20000, TimeUnit.MILLISECONDS);
        assertTrue(isRunBeforeSending[0]);
        assertTrue(progressCount[0] >= 7);
        assertTrue(Math.abs(finalProgress[0] - 1f) <= 0.01f);
        assertFalse(isTimeout[0]);
        assertFalse(isException[0]);
        assertNotNull(reservedResponse[0]);
        assertFalse(reservedResponse[0].hadException());
        assertTrue(reservedResponse[0].isSuccess());
        assertTrue(reservedResponse[0].getParsedBody() instanceof User);
        User user = (User) reservedResponse[0].getParsedBody();
        assertEquals(user.getUserName(), "kryokrait");
    }

    @Test
    public void asyncExecute2() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Response[] reservedResponse = new Response[1];
        final boolean[] isRunBeforeSending = {false};
        final boolean[] isTimeout = {false};
        final boolean[] isException = {false};
        HttpRequestBuilder.httpGet().setUrl("http://staging-webservice.catch.co.id/user/get")
                .addHeaders("API-KEY", "default").addParam("username", "kryokrait")
                .asyncExecute(new Digester<Response>() {
                    @Override
                    public void onBeforeSending(HttpURLConnection connection) {
                        isRunBeforeSending[0] = true;
                    }

                    @Override
                    public void onResponded(Response response) {
                        reservedResponse[0] = response;
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onTimeout() {
                        isTimeout[0] = true;
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onException(Exception exception) {
                        isException[0] = true;
                        countDownLatch.countDown();
                    }
                });
        countDownLatch.await(20000, TimeUnit.MILLISECONDS);
        assertTrue(isRunBeforeSending[0]);
        assertFalse(isTimeout[0]);
        assertFalse(isException[0]);
        assertNotNull(reservedResponse[0]);
        assertFalse(reservedResponse[0].hadException());
        assertTrue(reservedResponse[0].isSuccess());
    }

    @Test
    public void asyncExecute3() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final RestResponse[] reservedResponse = new RestResponse[1];
        final boolean[] isRunBeforeSending = {false};
        final boolean[] isTimeout = {false};
        final boolean[] isException = {false};
        HttpRequestBuilder.httpGet().setUrl("http://staging-webservice.catch.co.id/user/get")
                .addHeaders("API-KEY", "default").addParam("username", "kryokrait")
                .asyncExecute(new Digester<RestResponse>() {
                    @Override
                    public void onBeforeSending(HttpURLConnection connection) {
                        isRunBeforeSending[0] = true;
                    }

                    @Override
                    public void onResponded(RestResponse response) {
                        reservedResponse[0] = response;
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onTimeout() {
                        isTimeout[0] = true;
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onException(Exception exception) {
                        isException[0] = true;
                        countDownLatch.countDown();
                    }
                }, User.class);
        countDownLatch.await(20000, TimeUnit.MILLISECONDS);
        assertTrue(isRunBeforeSending[0]);
        assertFalse(isTimeout[0]);
        assertFalse(isException[0]);
        assertNotNull(reservedResponse[0]);
        assertFalse(reservedResponse[0].hadException());
        assertTrue(reservedResponse[0].isSuccess());
        assertTrue(reservedResponse[0].getParsedBody() instanceof User);
        User user = (User) reservedResponse[0].getParsedBody();
        assertEquals(user.getUserName(), "kryokrait");
    }

    @Test
    public void asyncExecute4() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Response[] reservedResponse = new Response[1];
        HttpRequestBuilder.httpGet().setUrl("http://staging-webservice.catch.co.id/user/get")
                .addHeaders("API-KEY", "default").addParam("username", "kryokrait")
                .asyncExecute(new Finisher<Response>() {
                    @Override
                    public void onFinished(Response response) {
                        reservedResponse[0] = response;
                        countDownLatch.countDown();
                    }

                });
        countDownLatch.await(20000, TimeUnit.MILLISECONDS);
        assertNotNull(reservedResponse[0]);
        assertFalse(reservedResponse[0].hadException());
        assertTrue(reservedResponse[0].isSuccess());
    }

    @Test
    public void asyncExecute5() throws Exception {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final RestResponse<User>[] reservedResponse = new RestResponse[1];
        HttpRequestBuilder.httpGet().setUrl("http://staging-webservice.catch.co.id/user/get")
                .addHeaders("API-KEY", "default").addParam("username", "kryokrait")
                .asyncExecute(new Finisher<RestResponse<User>>() {

                    @Override
                    public void onFinished(RestResponse<User> response) {
                        reservedResponse[0] = response;
                        countDownLatch.countDown();
                    }

                }, User.class);
        countDownLatch.await(20000, TimeUnit.MILLISECONDS);
        assertNotNull(reservedResponse[0]);
        assertFalse(reservedResponse[0].hadException());
        assertTrue(reservedResponse[0].isSuccess());
        assertTrue(reservedResponse[0].getParsedBody() != null);
        User user = reservedResponse[0].getParsedBody();
        assertEquals(user.getUserName(), "kryokrait");
    }

    @Test
    public void execute() throws Exception {
        Response response = HttpRequestBuilder.httpGet()
                .setUrl("http://staging-webservice.catch.co.id/user/get")
                .addHeaders("API-KEY", "default").addParam("username", "kryokrait").execute();
        assertFalse(response.hadException());
        assertTrue(response.isSuccess());
    }

    @Test
    public void execute1() throws Exception {
        RestResponse response = HttpRequestBuilder.httpGet()
                .setUrl("http://staging-webservice.catch.co.id/user/get")
                .addHeaders("API-KEY", "default").addParam("username", "kryokrait")
                .execute(User.class);
        assertFalse(response.hadException());
        assertTrue(response.isSuccess());
        assertTrue(response.getParsedBody() instanceof User);
        User user = (User) response.getParsedBody();
        assertEquals(user.getUserName(), "kryokrait");
    }

}
