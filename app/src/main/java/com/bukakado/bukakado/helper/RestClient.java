package com.bukakado.bukakado.helper;

import android.text.TextUtils;

import com.bukakado.bukakado.R;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by edy.h on 5/14/2017.
 */

public class RestClient {
    private static OkHttpClient.Builder client = new OkHttpClient.Builder();
    private static final String urlString = "https://api.bukalapak.com/";
    private  static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(urlString).addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = builder.build();

    public static <S> S createService(
            Class<S> serviceClass, String username, String password) {
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!client.interceptors().contains(interceptor)) {
                client.addInterceptor(interceptor);
                builder.client(client.build());
                retrofit = builder.build();
            }
        }
        return retrofit.create(serviceClass);
    }
}
