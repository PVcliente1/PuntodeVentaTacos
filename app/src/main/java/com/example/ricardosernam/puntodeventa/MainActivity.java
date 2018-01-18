package com.example.ricardosernam.puntodeventa;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private android.support.v4.app.FragmentManager manejador = getSupportFragmentManager();  //manejador que permite hacer el cambio de ventanas
    //private FragmentManager fm= getFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       manejador.beginTransaction().replace(R.id.Principal, new Ventas()).commit(); ///cambio de fragment

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    ///////////////////////método que maneja el item seleccionado
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //android.support.v4.app.FragmentManager manejador = getSupportFragmentManager();  //manejador que permite hacer el cambio de ventanas
        TextView fragabierto= findViewById(R.id.Fragabierto);  ///textview que va en la app bar e indica que item esta abierto
        int id = item.getItemId();
        fragabierto.setText(item.getTitle());
       // manejador.beginTransaction().replace(R.id.Principal, new item.() ).commit(); ///cambio de fragments

        if (id == R.id.Ventas) {
            manejador.beginTransaction().replace(R.id.Principal, new Ventas()).commit(); ///cambio de fragments
        } else if (id == R.id.Compras) {
            manejador.beginTransaction().replace(R.id.Principal, new Compras()).commit();
        } else if (id == R.id.Productos) {
            manejador.beginTransaction().replace(R.id.Principal, new Productos()).commit();
        } else if (id == R.id.Vendedores) {
            manejador.beginTransaction().replace(R.id.Principal, new Vendedores()).commit();
        } else if (id == R.id.Provedores) {
            manejador.beginTransaction().replace(R.id.Principal, new Provedores()).commit();
        } else if (id == R.id.Clientes) {
            manejador.beginTransaction().replace(R.id.Principal, new Clientes()).commit();
        } else if (id == R.id.Reportes) {
            manejador.beginTransaction().replace(R.id.Principal, new Reportes()).commit();
        } else if (id == R.id.Inventario) {
            manejador.beginTransaction().replace(R.id.Principal, new Inventario()).commit();
        } else if (id == R.id.Personalizar) {
            manejador.beginTransaction().replace(R.id.Principal, new Personalizar()).commit();
        } else if (id == R.id.Configurar) {
            manejador.beginTransaction().replace(R.id.Principal, new Configurar()).commit();
        } else if (id == R.id.Contáctanos) {
            manejador.beginTransaction().replace(R.id.Principal, new Contactanos()).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
