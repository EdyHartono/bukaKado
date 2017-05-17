package com.bukakado.bukakado.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bukakado.bukakado.DownloadActivity;
import com.bukakado.bukakado.R;
import com.bukakado.bukakado.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Hidayat on 13/05/2017.
 */

public class UseListAdapter extends RecyclerView.Adapter<UseListAdapter.ViewHolder> {
    private List<User> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView userName, userCountry, userGender;
        public ImageView userPhoto;
        public Button requestBtn;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            userName = (TextView)mView.findViewById(R.id.txtUserName);
            userCountry = (TextView)mView.findViewById(R.id.txtUserCountry);
            userGender = (TextView)mView.findViewById(R.id.txtUserSex);
            userPhoto = (ImageView)mView.findViewById(R.id.imgUserPhoto);
            requestBtn = (Button)mView.findViewById(R.id.requestBtn);

            requestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "button pressed in postion "+ getAdapterPosition(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public UseListAdapter(List<User> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public UseListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_user_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.userName.setText(mDataset.get(position).getUserName());
        holder.userGender.setText(mDataset.get(position).getSex());
        holder.userCountry.setText(mDataset.get(position).getCountry());
        new DownloadActivity(holder.userPhoto).execute(mDataset.get(position).getPhotoUrl());
//        try {
//
//            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(mDataset.get(position).getPhotoUrl())
//                            .getContent());
//            holder.userPhoto.setImageBitmap(bitmap);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}