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
import com.bukakado.bukakado.adapter.SignInBukaKadoAdapter;
import com.bukakado.bukakado.adapter.UseListAdapter;
import com.bukakado.bukakado.interfaces.AdapterCallback;

import java.util.ArrayList;

/**
 * Created by Jessica Casanova Lim on 5/18/2017.
 */
//https://developer.android.com/samples/RecyclerView/src/com.example.android.recyclerview/RecyclerViewFragment.html
public class SignInBukaKadoFragment extends Fragment implements AdapterCallback{
    private RecyclerView mRecyclerView;
    private SignInBukaKadoAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new SignInBukaKadoAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onMethodCallback() {

    }
}