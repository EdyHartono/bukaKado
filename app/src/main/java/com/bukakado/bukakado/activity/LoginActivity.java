package com.bukakado.bukakado.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bukakado.bukakado.R;
import com.bukakado.bukakado.constant.HTTTPStatus;
import com.bukakado.bukakado.helper.AppSession;
import com.bukakado.bukakado.helper.RestClient;
import com.bukakado.bukakado.interfaces.BukaLapakClient;
import com.bukakado.bukakado.model.response.BukalapakLoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jessica Casanova Lim on 5/13/2017.
 */

public class LoginActivity extends BaseActivity {

    private Button buttonLogin;
    private TextView txtUsername;
    private TextView txtPassword;
    private AppSession session;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.sign_in_bukakado);
        session = new AppSession(this);

        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = txtUsername.getText().toString();
                final String password = txtPassword.getText().toString();

                BukaLapakClient result = RestClient.createService(BukaLapakClient.class,userName,password);
                Call<BukalapakLoginResponse> responseCall=result.getAccessToken();
                responseCall.enqueue(new Callback<BukalapakLoginResponse>() {
                    @Override
                    public void onResponse(Call<BukalapakLoginResponse> call, Response<BukalapakLoginResponse> response) {
                        int statusCode = response.code();
                        BukalapakLoginResponse bukalapakLoginResponse = response.body();
                        if(statusCode == HTTTPStatus.OK && !TextUtils.isEmpty(bukalapakLoginResponse.getToken())) {
                            session.setBukaLapakToken(bukalapakLoginResponse.getToken());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            new AlertDialog.Builder(LoginActivity.this).setMessage(
                                    bukalapakLoginResponse.getMessage() + statusCode
                            ).show();
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
