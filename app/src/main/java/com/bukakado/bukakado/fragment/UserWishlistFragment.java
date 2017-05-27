package com.bukakado.bukakado.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import com.bukakado.bukakado.R;
import com.bukakado.bukakado.adapter.MyWishlistAdapter;
import com.bukakado.bukakado.helper.RestClient;
import com.bukakado.bukakado.interfaces.BukaLapakClient;
import com.bukakado.bukakado.model.response.wishlist.Product;
import com.bukakado.bukakado.model.response.wishlist.WishlistResponse;

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.recycler_view, null))
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserWishlistFragment.this.getDialog().cancel();
                    }
                });
        Dialog dialog = builder.create();
        return dialog;
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

        Dialog dialog = getDialog();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BukaLapakClient restClient = RestClient.createService(BukaLapakClient.class, "wfwfw");
                Call<WishlistResponse> wishlistResponseCall = restClient.getWishlist(getArguments().getString("bukalapakUsername"));
                wishlistResponseCall.enqueue(new Callback<WishlistResponse>() {
                    @Override
                    public void onResponse(Call<WishlistResponse> call, Response<WishlistResponse> response) {
                        mDataSet.addAll(response.body().getProducts());
                    }

                    @Override
                    public void onFailure(Call<WishlistResponse> call, Throwable t) {

                    }
                });
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
