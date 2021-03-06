package com.bukakado.bukakado.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bukakado.bukakado.activity.DownloadActivity;
import com.bukakado.bukakado.R;
import com.bukakado.bukakado.model.User;
import com.bukakado.bukakado.model.request.NewRelationRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by Hidayat on 13/05/2017.
 */

public class UseListAdapter extends RecyclerView.Adapter<UseListAdapter.ViewHolder> {
    private List<User> mDataset;
    private static final int USER_ID_TAG = 1;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView userName, userCountry, userGender;
        public ImageView userPhoto;
        public Button requestBtn,viewUserAddressButton;
        private DatabaseReference relationDbRef = FirebaseDatabase.getInstance().getReference().child("relation");
        private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        public ViewHolder(View v) {
            super(v);
            mView = v;
            userName = (TextView)mView.findViewById(R.id.txtUserName);
            userCountry = (TextView)mView.findViewById(R.id.txtUserCountry);
            userGender = (TextView)mView.findViewById(R.id.txtUserSex);
            userPhoto = (ImageView)mView.findViewById(R.id.imgUserPhoto);
            requestBtn = (Button)mView.findViewById(R.id.requestBtn);
            viewUserAddressButton = (Button) mView.findViewById(R.id.viewUserAddressButton);
            viewUserAddressButton.setVisibility(View.GONE);
            requestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    relationDbRef.push().setValue(new NewRelationRequest(currentUser.getUid(), userName.getTag().toString()));
                    requestBtn.setClickable(false);
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
        holder.userName.setTag(mDataset.get(position).getUserId());
        holder.userGender.setText(mDataset.get(position).getSex());
        holder.userCountry.setText(mDataset.get(position).getCountry());
        new DownloadActivity(holder.userPhoto).execute(mDataset.get(position).getPhotoUrl());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}