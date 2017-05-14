package com.bukakado.bukakado.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bukakado.bukakado.R;
import com.bukakado.bukakado.adapter.UseListAdapter;
import com.bukakado.bukakado.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<User> myDataset;
        myDataset = new ArrayList<>();
        String imgUrl = "https://pbs.twimg.com/profile_images/721959333282467840/UjU1ts0_.jpg";
        myDataset.add(new User("1","Hidayat Hendrakusuma",imgUrl,"","Indonesia","Male"));
        myDataset.add(new User("1","Erwin",imgUrl,"","Indonesia","Male"));
        myDataset.add(new User("1","Edy Hartono",imgUrl,"","Indonesia","Male"));

        mAdapter = new UseListAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

    }
}
