package com.bukakado.bukakado.adapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bukakado.bukakado.R;
import com.bukakado.bukakado.fragment.UserListFragment;
import com.bukakado.bukakado.helper.AppSession;
import com.bukakado.bukakado.helper.RestClient;
import com.bukakado.bukakado.interfaces.BukaLapakClient;
import com.bukakado.bukakado.model.response.BukalapakLoginResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jessica Casanova Lim on 5/18/2017.
 */

public class SignInBukaKadoAdapter extends RecyclerView.Adapter<SignInBukaKadoAdapter.ViewHolder> {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("users");
    String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    HashMap<String, Object> resultMap = new HashMap<>();
    Context parentContext;
    Activity parentActivity;

    int size=0;
    public SignInBukaKadoAdapter(Context context, Activity activity) {
        size=1;
        this.parentContext = context;
        this.parentActivity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    public TextView txtUsername, txtPassword,labelUsername,txtError;
    public Button buttonLogin;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sign_in_bukakado, parent, false);
        labelUsername = (TextView) v.findViewById(R.id.label1);
        txtError = (TextView) v.findViewById(R.id.txtErrorLogin);
        txtUsername = (TextView)v.findViewById(R.id.txtUsername);
        txtPassword = (TextView)v.findViewById(R.id.txtPassword);
        buttonLogin = (Button) v.findViewById(R.id.buttonLogin);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {

                String userName = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                final BukaLapakClient result = RestClient.createService(BukaLapakClient.class,userName,password);
                Call<BukalapakLoginResponse> responseCall=result.getAccessToken();
                responseCall.enqueue(new Callback<BukalapakLoginResponse>() {
                    @Override
                    public void onResponse(Call<BukalapakLoginResponse> call, Response<BukalapakLoginResponse> response) {
                        int statusCode = response.code();
                        BukalapakLoginResponse bukalapakLoginResponse = response.body();
                        if(bukalapakLoginResponse.getMessage()!=null)
                        {
                            txtError.setText(bukalapakLoginResponse.getMessage());
                        }
                        else {
                            resultMap.put("bukalapakUserId", bukalapakLoginResponse.getUser_id());
                            resultMap.put("bukalapakUserToken", bukalapakLoginResponse.getToken());
                            ref.child(userUID).updateChildren(resultMap);
                            AppSession appSession = new AppSession(parentContext);
                            appSession.setBukaLapakToken(bukalapakLoginResponse.getToken());
                            UserListFragment userListFragment = new UserListFragment();
                            FragmentTransaction transaction = parentActivity.getFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, userListFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<BukalapakLoginResponse> call, Throwable t) {
                        txtError.setText("failed ");
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }


}
