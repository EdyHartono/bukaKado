package com.bukakado.bukakado.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bukakado.bukakado.R;
import com.bukakado.bukakado.helper.RestClient;
import com.bukakado.bukakado.interfaces.LoginInterface;
import com.bukakado.bukakado.model.response.BukalapakLoginResponse;
import com.twitter.sdk.android.core.internal.network.OkHttpClientHelper;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jessica Casanova Lim on 5/13/2017.
 */

public class LoginActivity extends BaseActivity {

    private Button buttonLogin;
    private TextView txtUsername;
    private TextView txtPassword;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.sign_in_bukakado);

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = txtUsername.getText().toString();
                final String password = txtPassword.getText().toString();

                LoginInterface result = RestClient.createService(LoginInterface.class,userName,password);
                Call<BukalapakLoginResponse> responseCall=result.getAccessToken();
                responseCall.enqueue(new Callback<BukalapakLoginResponse>() {
                    @Override
                    public void onResponse(Call<BukalapakLoginResponse> call, Response<BukalapakLoginResponse> response) {
                        int statusCode = response.code();
                        BukalapakLoginResponse bukalapakLoginResponse = response.body();
                        if(bukalapakLoginResponse.getMessage()!=null)
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).setMessage(bukalapakLoginResponse.getMessage()).show();
                        }
                        else {
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).setMessage("success login"+bukalapakLoginResponse.getToken()).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BukalapakLoginResponse> call, Throwable t) {
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).setMessage("failed ").show();
                    }
                });
            }
        });
    }
}