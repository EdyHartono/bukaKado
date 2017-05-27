package com.bukakado.bukakado.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.bukakado.bukakado.R;
import com.bukakado.bukakado.adapter.MyWishlistAdapter;
import com.bukakado.bukakado.helper.RestClient;
import com.bukakado.bukakado.interfaces.BukaLapakClient;
import com.bukakado.bukakado.model.response.userProfile.UserProfileResponse;
import com.bukakado.bukakado.model.response.wishlist.Product;
import com.bukakado.bukakado.model.response.wishlist.WishlistResponse;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edy.h on 5/25/2017.
 */

public class UserWishlistFragment extends DialogFragment {

    private RecyclerView mRecyclerView;
    private MyWishlistAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userUID;
    DatabaseReference bukalapakUserTokenRef;
    DatabaseReference bukalapakUserProfileRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userUID = getArguments().getString("userUUID");
        bukalapakUserTokenRef = database.getReference("users").child(userUID).child("bukalapakUserToken");
        bukalapakUserProfileRef = database.getReference("users").child(userUID).child("bukalapakUserId");
        return inflater.inflate(R.layout.recycler_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //
        final List<Product> mDataSet ;
        mDataSet = new ArrayList<>();
        mAdapter = new MyWishlistAdapter(mDataSet);
        bukalapakUserTokenRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userToken = dataSnapshot.getValue(String.class);
                final BukaLapakClient result = RestClient.createService(BukaLapakClient.class, userToken);
                bukalapakUserProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userId = String.valueOf(dataSnapshot.getValue());
                        Call<UserProfileResponse> userProfileResponseCall=result.getUserProfile(userId);
                        userProfileResponseCall.enqueue(new Callback<UserProfileResponse>() {
                            @Override
                            public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                                UserProfileResponse userProfileResponse = response.body();
                                if(userProfileResponse!=null)
                                {
                                    String user_name =userProfileResponse.getUser().getUsername();
                                    Call<WishlistResponse> responseCall=result.getWishlist(user_name);
                                    responseCall.enqueue(new Callback<WishlistResponse>() {
                                        @Override
                                        public void onResponse(Call<WishlistResponse> call, Response<WishlistResponse> response) {
                                            WishlistResponse wishlistResponse = response.body();
                                            if(wishlistResponse!=null)
                                            {
                                                for (Product product: wishlistResponse.getProducts()) {
                                                    mDataSet.add(product);
                                                    mAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<WishlistResponse> call, Throwable t) {
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserWishlistFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
        */
    }
}
