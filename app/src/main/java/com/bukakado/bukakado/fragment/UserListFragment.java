package com.bukakado.bukakado.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.bukakado.bukakado.R;
import com.bukakado.bukakado.adapter.UseListAdapter;
import com.bukakado.bukakado.model.MatchedUser;
import com.bukakado.bukakado.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private DatabaseReference matchedDbRef = FirebaseDatabase.getInstance().getReference("matched");
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private List<User> myDataset = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new UseListAdapter(myDataset);

        userDbRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                if (!user.getUserId().equals(firebaseUser.getUid())){
                    myDataset.add(user);
                    mAdapter.notifyDataSetChanged();
                }
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
        attachMatchedUserListener("user1");
        attachMatchedUserListener("user2");

        mRecyclerView.setAdapter(mAdapter);
    }

    public void attachMatchedUserListener(final String user) {
        matchedDbRef.orderByChild(user).equalTo(firebaseUser.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MatchedUser matchedUser = dataSnapshot.getValue(MatchedUser.class);
                int idx = -1;
                for (int i = 0; i < myDataset.size(); i++) {
                    if (user.equals("user1")) {
                        if (matchedUser.getUser2().equals(myDataset.get(i).getUserId())) {
                            idx = i;
                            break;
                        }
                    } else {
                        if (matchedUser.getUser1().equals(myDataset.get(i).getUserId())) {
                            idx = i;
                            break;
                        }
                    }
                }
                if (idx > -1) {
                    myDataset.remove(idx);
                    mAdapter.notifyDataSetChanged();
                }
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
    }
}
