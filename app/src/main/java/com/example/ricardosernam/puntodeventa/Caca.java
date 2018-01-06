package com.example.ricardosernam.puntodeventa;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

/**
 * Created by Ricardo Serna M on 05/01/2018.
 */

public class Caca extends AppCompatActivity {
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prueba);

        ArrayList<Productos_venta> items = new ArrayList <>();
        items.add(new Productos_venta("Tacos"));
        items.add(new Productos_venta("Tortas"));
        items.add(new Productos_venta("Quesadillas"));
        items.add(new Productos_venta("Refrescos"));
        items.add(new Productos_venta("Cervezas"));

// Obtener el Recycler
       recycler = findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

// Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(lManager);

// Crear un nuevo adaptador
        adapter = new Productos_ventasAdapter(items);
        recycler.setAdapter(adapter);
    }
}
