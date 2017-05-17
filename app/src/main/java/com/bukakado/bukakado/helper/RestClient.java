package com.bukakado.bukakado.helper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by edy.h on 5/14/2017.
 */

public class RestClient {
    private OkHttpClient.Builder client;
    private OkHttpClient httpClient;
    private final MediaType JSON =  MediaType.parse("application/json; charset=utf-8");
    private Gson gson = new Gson();

    public RestClient getBasicAuthClient(final String username, final String password) {
        client = new OkHttpClient.Builder();
        client.authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(username, password);
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        }).connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS);

        httpClient = client.build();
        return this;
    }

    private <T> T call(Request request, Class<T> typeRef) throws IOException {
        final Response response = httpClient.newCall(request).execute();
        final T result = gson.fromJson(response.body().string(), typeRef);
        return result;
    }

    public <T, D> T doPost(String uri, D data, Class<T> typeRef) throws IOException {
        String json = gson.toJson(data);
        Request request = new Request.Builder().post(RequestBody.create(JSON, json)).url(uri).build();
        return call(request, typeRef);
    }

}
