package com.bukakado.bukakado.activity;

import android.support.design.widget.NavigationView;
import android.view.View;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bukakado.bukakado.R;
import com.bukakado.bukakado.fragment.MyWishlistFragment;
import com.bukakado.bukakado.fragment.MatchedUserListFragment;
import com.bukakado.bukakado.fragment.SignInBukaKadoFragment;
import com.bukakado.bukakado.fragment.UserListFragment;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private  void setFacebookProfile(NavigationView navigationView)
    {
        final View view  = navigationView.getHeaderView(0);

        //facebook profile
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback()
        {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                TextView userName = (TextView) view.findViewById(R.id.userName);
                TextView userEmail = (TextView) view.findViewById(R.id.userEmail);
                ImageView userImage = (ImageView) view.findViewById(R.id.userProfile);

                if(object!=null)
                {
                    try {
                        String profilePicture = "https://graph.facebook.com/me/picture?width=150&height=150&access_token="+AccessToken.getCurrentAccessToken().getToken();
                        userName.setText(object.getString("name").toString());
                        //userEmail.setText(object.getString("email").toString());
                        new DownloadActivity(userImage).execute(profilePicture);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setMessage(e.getMessage()).show();
                    }
                }
            }
        });

        Bundle parameter = new Bundle();
        parameter.putString("fields","id,name,email,picture");
        request.setParameters(parameter);
        request.executeAsync();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setFacebookProfile(navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        View mainContainer = findViewById(R.id.fragment_container);

        if (id == R.id.nav_camera) {
            UserListFragment userListFragment = new UserListFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, userListFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_gallery) {
            SignInBukaKadoFragment signInBukaKadoFragment = new SignInBukaKadoFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, signInBukaKadoFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_slideshow) {
            MyWishlistFragment myWishlistFragment = new MyWishlistFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, myWishlistFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_manage) {
            MatchedUserListFragment matchedUserListFragment = new MatchedUserListFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, matchedUserListFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
