package com.bukakado.bukakado.adapter;

import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bukakado.bukakado.R;
import com.bukakado.bukakado.activity.LoginActivity;
import com.bukakado.bukakado.helper.RestClient;
import com.bukakado.bukakado.interfaces.AdapterCallback;
import com.bukakado.bukakado.interfaces.LoginInterface;
import com.bukakado.bukakado.model.response.BukalapakLoginResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jessica Casanova Lim on 5/18/2017.
 */

public class SignInBukaKadoAdapter extends RecyclerView.Adapter<SignInBukaKadoAdapter.ViewHolder> {

    int size=0;
    public SignInBukaKadoAdapter()
    {
        size=1;
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

                LoginInterface result = RestClient.createService(LoginInterface.class,userName,password);
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
                            txtError.setText("success login"+bukalapakLoginResponse.getToken());
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