package com.bukakado.bukakado.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bukakado.bukakado.R;
import com.bukakado.bukakado.adapter.MyWishlistAdapter;
import com.bukakado.bukakado.helper.RestClient;
import com.bukakado.bukakado.interfaces.BukaLapakClient;
import com.bukakado.bukakado.model.response.wishlist.Product;
import com.bukakado.bukakado.model.response.wishlist.WishlistResponse;
import com.google.firebase.auth.FirebaseAuth;
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
 * Created by Jessica Casanova Lim on 5/21/2017.
 */

public class MyWishlistFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MyWishlistAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference bukalapakUserTokenRef = database.getReference("users").child(userUID).child("bukalapakUserToken");
    DatabaseReference bukalapakUserProfileRef = database.getReference("users").child(userUID).child("bukalapakUserId");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        ///////////////
        final List<Product> mDataSet ;
        mDataSet = new ArrayList<>();
        mAdapter = new MyWishlistAdapter(mDataSet);


        bukalapakUserTokenRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userToken = dataSnapshot.getValue(String.class);
                final BukaLapakClient result = RestClient.createService(BukaLapakClient.class,userToken);
                Call<WishlistResponse> responseCall=result.getWishlist("jessica_lim");
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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

    }
}
