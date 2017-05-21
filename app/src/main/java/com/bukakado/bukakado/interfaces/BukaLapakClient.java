package com.bukakado.bukakado.interfaces;

import com.bukakado.bukakado.model.response.BukalapakLoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Jessica Casanova Lim on 5/14/2017.
 */

public interface BukaLapakClient {
    @POST("v2/authenticate.json")
    Call<BukalapakLoginResponse> getAccessToken();
}
