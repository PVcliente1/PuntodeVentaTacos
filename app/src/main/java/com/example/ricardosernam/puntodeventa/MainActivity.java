package com.example.ricardosernam.puntodeventa;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ricardosernam.puntodeventa.Contactanos.Contactanos;
import com.example.ricardosernam.puntodeventa.Inventario.Inventario;
import com.example.ricardosernam.puntodeventa.Terminos.Terminos;
import com.example.ricardosernam.puntodeventa.Ventas.Ventas;
import com.example.ricardosernam.puntodeventa.sync.SyncAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private MenuItem importar, exportar;
    private Toolbar toolbar;
    private android.support.v4.app.FragmentManager manejador = getSupportFragmentManager();  //manejador que permite hacer el cambio de ventanas
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppBarLayout bar=findViewById(R.id.APLappBar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manejador.beginTransaction().replace(R.id.LOprincipal, new Ventas()).commit(); ///cambio de fragment

        manejador.beginTransaction().replace(R.id.LOprincipal, new Ventas()).commit(); ///cambio de fragment

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);//editamos el navigationheader
        View hView = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        importar=menu.findItem(R.id.importar);
        exportar=menu.findItem(R.id.exportar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.importar) {
            importar.setEnabled(false);
            exportar.setEnabled(true);
            if(!(importar.isEnabled())){ ///esta desabilitado
                SyncAdapter.sincronizarAhora(getApplicationContext(), false);

            }
            return true;
        }
        else if (id == R.id.exportar) {
            importar.setEnabled(true);
            exportar.setEnabled(false);
            /*if(!exportar.isEnabled()){ ///esta desabilitad
                Toast.makeText(getApplicationContext(), "Importa el actual inventario para poder exportar", Toast.LENGTH_LONG).show();
            }*/
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
        if (id == R.id.Ventas) {
            importar.setVisible(false);
            exportar.setVisible(false);
            manejador.beginTransaction().replace(R.id.LOprincipal, new Ventas()).commit(); ///cambio de fragments
        }  else if (id == R.id.Inventario) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Inventario()).commit();
            importar.setVisible(true);
            exportar.setVisible(true);
        } else if (id == R.id.Contáctanos) {
            importar.setVisible(false);
            exportar.setVisible(false);
            manejador.beginTransaction().replace(R.id.LOprincipal, new Contactanos()).commit();
        } else if (id == R.id.Terminos) {
            importar.setVisible(false);
            exportar.setVisible(false);
            manejador.beginTransaction().replace(R.id.LOprincipal, new Terminos()).commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
