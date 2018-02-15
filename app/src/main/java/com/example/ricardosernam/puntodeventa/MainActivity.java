package com.example.ricardosernam.puntodeventa;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import com.example.ricardosernam.puntodeventa.Benvenida.Bienvenida;

import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, interfaz_historial {

    private android.support.v4.app.FragmentManager manejador = getSupportFragmentManager();  //manejador que permite hacer el cambio de ventanas
    private ArrayList<Historial_ventas_class> itemsHistorial = new ArrayList<>();   ///array para productos seleccionados
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //CoordinatorLayout chingon=findViewById(R.id.chingon);
        AppBarLayout bar=findViewById(R.id.APLappBar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /////comprobamos si es la primera vez que se abre
        if(appGetFirstTimeRun()==0 ){
            //Toast.makeText(getApplicationContext(), "Es la primera vez", Toast.LENGTH_LONG).show();
            //manejador.beginTransaction().replace(R.id.CLcontenedorTotal, new Bienvenida()).commit(); ///cambio de fragment
            //bar.setVisibility(View.INVISIBLE);
        }
        else if(appGetFirstTimeRun()==1){
            //Toast.makeText(getApplicationContext(), "Ya se habia abierto", Toast.LENGTH_LONG).show();
            //manejador.beginTransaction().replace(R.id.LOprincipal, new Ventas()).commit(); ///cambio de fragment
            manejador.beginTransaction().replace(R.id.CLcontenedorTotal, new Bienvenida()).commit(); ///cambio de fragment
            bar.setVisibility(View.INVISIBLE);
        }
        else if(appGetFirstTimeRun()==2){
            Toast.makeText(getApplicationContext(), "Es una catualizacion", Toast.LENGTH_LONG).show();
        }
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
        TextView fragAbierto = findViewById(R.id.TVFragabierto);  ///textview que va en la app bar e indica que item esta abierto
        int id = item.getItemId();
        fragAbierto.setText(item.getTitle());
        // manejador.beginTransaction().replace(R.id.Principal, new item.() ).commit(); ///cambio de fragments

        if (id == R.id.Ventas) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Ventas()).commit(); ///cambio de fragments
        } else if (id == R.id.Compras) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Compras()).commit();
        } else if (id == R.id.Productos) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Productos()).commit();
        } else if (id == R.id.Miembros) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Vendedores()).commit();
        } else if (id == R.id.Provedores) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Provedores()).commit();
        } else if (id == R.id.Clientes) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Clientes()).commit();
        } else if (id == R.id.Descuentos) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Descuentos()).commit();
        } else if (id == R.id.Reportes) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Reportes()).commit();
        } else if (id == R.id.Inventario) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Inventario()).commit();
        } else if (id == R.id.Personalizar) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Personalizar()).commit();
        } else if (id == R.id.Configurar) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Configurar()).commit();
        } else if (id == R.id.Contáctanos) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Contactanos()).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    ////metodo de la interface (debo puentearla forzosamente con el activity que las contiene)
    public void mandarHistorial(String tipo, String pagar) {  ////metodo de la interface (debo puentearla forzosamente con el activity que las contiene)
        itemsHistorial.add(new Historial_ventas_class(tipo, pagar));  ///tipo viene de fragment_cobrar
        manejador.beginTransaction().replace(R.id.LOprincipal, new Ventas(itemsHistorial)).commit(); ///cambio de fragment (Le envio a ventas el array que ira a Historial)

    }
    private int appGetFirstTimeRun() {
        //Check if App Start First Time
        SharedPreferences appPreferences = getSharedPreferences("MyAPP", 0);
        int appCurrentBuildVersion = BuildConfig.VERSION_CODE;
        int appLastBuildVersion = appPreferences.getInt("app_first_time", 0);

        if (appLastBuildVersion == appCurrentBuildVersion ) {
            return 1; //ya has iniciado la appp alguna vez

        } else {
            appPreferences.edit().putInt("app_first_time", appCurrentBuildVersion).apply();
            if (appLastBuildVersion == 0) {
                return 0; //es la primera vez
            } else {
                return 2; //es una versión nueva
            }
        }
    }
}
