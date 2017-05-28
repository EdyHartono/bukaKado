package com.bukakado.bukakado.adapter;

import android.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.TintTypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bukakado.bukakado.R;
import com.bukakado.bukakado.activity.DownloadActivity;
import com.bukakado.bukakado.constant.HTTTPStatus;
import com.bukakado.bukakado.fragment.UserWishlistFragment;
import com.bukakado.bukakado.helper.RestClient;
import com.bukakado.bukakado.interfaces.BukaLapakClient;
import com.bukakado.bukakado.model.User;
import com.bukakado.bukakado.model.request.NewRelationRequest;
import com.bukakado.bukakado.model.response.BukalapakLoginResponse;
import com.bukakado.bukakado.model.response.userAddress.UserAddressResponse;
import com.bukakado.bukakado.model.response.userProfile.UserProfileResponse;
import com.bukakado.bukakado.model.response.wishlist.Product;
import com.bukakado.bukakado.model.response.wishlist.WishlistResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by edy.h on 5/20/2017.
 */

public class MatchedUserListAdpater extends RecyclerView.Adapter<MatchedUserListAdpater.ViewHolder> {
    private List<User> mDataset;
    private static final int USER_ID_TAG = 1;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ViewHolder holder);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public TextView userName, userCountry, userGender;
        public ImageView userPhoto;
        public Button requestBtn,viewUserAddressButton;
        private DatabaseReference relationDbRef = FirebaseDatabase.getInstance().getReference().child("relation");
        private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userUID;
        DatabaseReference bukalapakUserTokenRef;
        DatabaseReference bukalapakUserProfileRef;

        public ViewHolder(View v) {
            super(v);
            mView = v;
            userName = (TextView)mView.findViewById(R.id.txtUserName);
            userCountry = (TextView)mView.findViewById(R.id.txtUserCountry);
            userGender = (TextView)mView.findViewById(R.id.txtUserSex);
            userPhoto = (ImageView)mView.findViewById(R.id.imgUserPhoto);
            requestBtn = (Button)mView.findViewById(R.id.requestBtn);
            viewUserAddressButton = (Button) mView.findViewById(R.id.viewUserAddressButton);
            requestBtn.setText("View Wishlist");
            requestBtn.getLayoutParams().width=200;
            viewUserAddressButton.setVisibility(View.VISIBLE);

            requestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(ViewHolder.this);
                }
            });

            viewUserAddressButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    userUID = userName.getTag().toString();
                    bukalapakUserTokenRef = database.getReference("users").child(userUID).child("bukalapakUserToken");
                    bukalapakUserProfileRef = database.getReference("users").child(userUID).child("bukalapakUserId");
                    bukalapakUserTokenRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String userToken = dataSnapshot.getValue(String.class);
                            bukalapakUserProfileRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String userId = String.valueOf(dataSnapshot.getValue());
                                    BukaLapakClient bukaLapakClient  = RestClient.createService(BukaLapakClient.class,userId,userToken);
                                    Call<UserAddressResponse> userAddressResponseCall = bukaLapakClient.getUserAddress();
                                    userAddressResponseCall.enqueue(new Callback<UserAddressResponse>() {
                                        @Override
                                        public void onResponse(Call<UserAddressResponse> call, Response<UserAddressResponse> response) {
                                            int statusCode=response.code();
                                            UserAddressResponse userAddressResponse = response.body();
                                            if(statusCode == HTTTPStatus.OK)
                                            {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                                                builder.setMessage
                                                        ("Address     : "+userAddressResponse.getUser_addresses().get(0).getAddress_attributes().getAddress()
                                                        +"\nArea      : "+userAddressResponse.getUser_addresses().get(0).getAddress_attributes().getArea()
                                                        +"\nCity      : "+userAddressResponse.getUser_addresses().get(0).getAddress_attributes().getCity()
                                                        +"\nProvince  : "+userAddressResponse.getUser_addresses().get(0).getAddress_attributes().getProvince()
                                                        +"\nPost Code : "+userAddressResponse.getUser_addresses().get(0).getAddress_attributes().getPost_code()
                                                        +"\nPhone     : "+userAddressResponse.getUser_addresses().get(0).getPhone());
                                                AlertDialog alertDialog = builder.show();
                                            }
                                            else
                                            {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                                                builder.setMessage("error : "+userAddressResponse.getMessage());
                                                AlertDialog alertDialog = builder.show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<UserAddressResponse> call, Throwable t) {

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
                }
            });
        }
    }

    public MatchedUserListAdpater(List<User> mDataset, OnItemClickListener listener) {
        this.mDataset = mDataset;
        this.listener = listener;
    }

    @Override
    public MatchedUserListAdpater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_user_list, parent, false);

        MatchedUserListAdpater.ViewHolder vh = new MatchedUserListAdpater.ViewHolder(v);
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
