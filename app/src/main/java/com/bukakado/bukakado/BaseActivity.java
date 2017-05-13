package com.bukakado.bukakado;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.support.annotation.VisibleForTesting;

/**
 * Created by edy.h on 5/10/2017.
 */

public class BaseActivity extends AppCompatActivity {
    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public static class UserListActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_user_list);
        }
    }
}
