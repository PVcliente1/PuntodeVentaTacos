package com.example.ricardosernam.puntodeventa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Vendedores extends Fragment {
    ArrayList<listaVendedores> items;

    //recycler
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vendedores, container, false);

        items = new ArrayList<>();

        items.add(new listaVendedores("Juan"));

        recycler = view.findViewById(R.id.RVvendedores);
        lManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(lManager);

        adapter = new vendedoresAdapter(items);

        recycler.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }
}
