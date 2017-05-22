package com.bukakado.bukakado.interfaces;

import com.bukakado.bukakado.model.response.BukalapakLoginResponse;
import com.bukakado.bukakado.model.response.userProfile.UserProfileResponse;
import com.bukakado.bukakado.model.response.wishlist.WishlistResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Jessica Casanova Lim on 5/22/2017.
 */

public interface BukaKadoInterface {
    @POST("v2/authenticate.json")
    Call<BukalapakLoginResponse> getAccessToken();

    @GET("v2/favorites/{name}.json")
    Call<WishlistResponse> getWishlist(@Path("name") String name);

    @GET("v2/users/{id}/profile.json")
    Call<UserProfileResponse> getUserProfile(@Path("id") String id);
}
