package com.bukakado.bukakado.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bukakado.bukakado.R;
import com.bukakado.bukakado.adapter.MatchedUserListAdpater;
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
 * Created by edy.h on 5/20/2017.
 */

public class MatchedUserListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference matchedUserDbRef = FirebaseDatabase.getInstance().getReference("matched");
    private DatabaseReference userDbRef = FirebaseDatabase.getInstance().getReference("users");
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private List<User> myDataset;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.recycler_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        myDataset = new ArrayList<>();
        mAdapter = new MatchedUserListAdpater(myDataset, new MatchedUserListAdpater.OnItemClickListener() {

            public void onItemClick(MatchedUserListAdpater.ViewHolder viewHolder) {
                DialogFragment fragment = new UserWishlistFragment();
                Bundle bundle = new Bundle();
                bundle.putString("userUUID", viewHolder.userName.getTag().toString());
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(),"");
            }
        });
        attachMatchedUserListener("user1");
        attachMatchedUserListener("user2");

        mRecyclerView.setAdapter(mAdapter);

    }

    public void attachMatchedUserListener(final String user) {
        matchedUserDbRef.orderByChild(user).equalTo(currentUser.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MatchedUser matchedUser = dataSnapshot.getValue(MatchedUser.class);
                if (user.equals("user1")) {
                    Log.i("MatchedUserFound", "Found matched user with id "+ matchedUser.getUser2());
                    attachUserDetailListener(matchedUser.getUser2());
                } else{
                    Log.i("MatchedUserFound", "Found matched user with id "+ matchedUser.getUser1());
                    attachUserDetailListener(matchedUser.getUser1());
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

    private void attachUserDetailListener(String userId) {
        userDbRef.orderByChild("userId").equalTo(userId).addChildEventListener(new ChildEventListener() {
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
    }
}
