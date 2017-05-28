package com.bukakado.bukakado.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bukakado.bukakado.R;
import com.bukakado.bukakado.activity.DownloadActivity;
import com.bukakado.bukakado.model.response.wishlist.Product;

import java.util.List;

/**
 * Created by Jessica Casanova Lim on 5/21/2017.
 */

public class MyWishlistAdapter  extends RecyclerView.Adapter<MyWishlistAdapter.ViewHolder> {
    List<Product> mDataset;

    public MyWishlistAdapter(List<Product> wishlistResponses) {
        mDataset = wishlistResponses;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName;
        public ImageView productImage;

        public View mView;
        public ViewHolder(View v) {
            super(v);
            mView = v;
            txtProductName = (TextView) v.findViewById(R.id.txtProductName);
            productImage = (ImageView) v.findViewById(R.id.productImage);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_wishlist, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtProductName.setText(mDataset.get(position).getName());
        new DownloadActivity(holder.productImage).execute(mDataset.get(position).getImages().get(0));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
