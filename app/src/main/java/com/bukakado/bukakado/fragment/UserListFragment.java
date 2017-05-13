package com.bukakado.bukakado.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bukakado.bukakado.R;
import com.bukakado.bukakado.adapter.UseListAdapter;
import com.bukakado.bukakado.model.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edy.h on 5/14/2017.
 */

public class UserListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference userDbRef = FirebaseDatabase.getInstance().getReference("users");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.recycler_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        final List<User> myDataset;
        myDataset = new ArrayList<>();
//        String imgUrl = "https://pbs.twimg.com/profile_images/721959333282467840/UjU1ts0_.jpg";
//        myDataset.add(new User("1","Hidayat Hendrakusuma",imgUrl,"","Indonesia","Male"));
//        myDataset.add(new User("1","Erwin",imgUrl,"","Indonesia","Male"));
//        myDataset.add(new User("1","Edy Hartono",imgUrl,"","Indonesia","Male"));
        mAdapter = new UseListAdapter(myDataset);
        userDbRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                myDataset.add(user);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }
}
