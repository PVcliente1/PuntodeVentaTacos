package com.example.ricardosernam.puntodeventa;

import android.app.Fragment;
import android.content.ContentValues;
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
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.Sincronizar.Sincronizar;
import com.example.ricardosernam.puntodeventa.Inventario.Inventario;
import com.example.ricardosernam.puntodeventa.Ventas.Ventas;
import com.example.ricardosernam.puntodeventa.sync.SyncAdapter;
import com.example.ricardosernam.puntodeventa.utils.Constantes;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment myFragment;
    private MenuItem exportar;
    private Toolbar toolbar;
    private ContentValues values;

    private android.support.v4.app.FragmentManager manejador = getSupportFragmentManager();  //manejador que permite hacer el cambio de ventanas
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppBarLayout bar=findViewById(R.id.APLappBar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            Toast.makeText(getApplicationContext(), "Sin cambios Activity" , LENGTH_LONG).show();
            manejador.beginTransaction().replace(R.id.LOprincipal, new Ventas()).commit(); ///cambio de fragment
        }
        else if (savedInstanceState != null) {
            Toast.makeText(getApplicationContext(), "Cambios Activity" , LENGTH_LONG).show();

            //myFragment = getFragmentManager().getFragment(savedInstanceState,"Sincronizar");
        }

        values=new ContentValues();
        ////////////////////////////////////////7
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);//editamos el navigationheader
        View hView = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);
    }

    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getFragmentManager().putFragment(outState,"Sincronizar", myFragment);

        //outState.putString("NUMERO", String.valueOf(ip.getText()));
        //Save the fragment's state here
    }

    /*@Override
    public void onRestoreInstanceState(Bundle inState){
        myFragment = getFragmentManager().getFragment(inState,"sincronizar");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "sincronizar", myFragment);
        //Save the fragment's state here
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        //importar=menu.findItem(R.id.importar);
        exportar=menu.findItem(R.id.exportar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exportar) {
                ///////////////////////inventario//////////////////////////
                //SyncAdapter.sincronizarAhora(getApplicationContext(), true, Constantes.INSERT_URL_VENTA);
                SyncAdapter.sincronizarAhora(getApplicationContext(), true, Constantes.UPDATE_URL_INVENTARIO_DETALLE);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isTagInBackStack(String tag){
        //Log.i(TAG, "isTagInBackStack() Start");
        int x;
        boolean toReturn = false;
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        //Log.i(TAG, "backStackCount = " + backStackCount);
        for (x = 0; x < backStackCount; x++){
            //Log.i(TAG, "Iter = " + x +" "+ getSupportFragmentManager().getBackStackEntryAt(x).getName());
            if (tag.equals(getSupportFragmentManager().getBackStackEntryAt(x).getName())){
                toReturn = true;
            }
        }
        //Log.i(TAG, "isTagInBackStack() End, toReturn = " + toReturn);
        return toReturn;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    ///////////////////////método que maneja el item seleccionado
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //android.support.v4.app.FragmentManager manejador = getSupportFragmentManager();  //manejador que permite hacer el cambio de ventanas


        /*android.support.v4.app.Fragment Ventas= getSupportFragmentManager().findFragmentByTag("Ventas");
        android.support.v4.app.Fragment Inventario= getSupportFragmentManager().findFragmentByTag("Inventario");
        android.support.v4.app.Fragment Sincronizar= getSupportFragmentManager().findFragmentByTag("Sincronizar");*/

        TextView fragAbierto = findViewById(R.id.TVFragabierto);  ///textview que va en la app bar e indica que item esta abierto
        int id = item.getItemId();
        fragAbierto.setText(item.getTitle());
        android.support.v4.app.FragmentTransaction transaction=manejador.beginTransaction();

        if (id == R.id.Ventas) {
            if(isTagInBackStack("Ventas")) {
                //transaction.show(getSupportFragmentManager().findFragmentByTag("Ventas")).commit();
                transaction.replace(R.id.LOprincipal, getSupportFragmentManager().findFragmentByTag("Ventas") ).addToBackStack("Ventas").commit();
                //transaction.hide(getSupportFragmentManager().findFragmentByTag("Ventas")).commit();
            }
            else{
                Toast.makeText(getApplicationContext(), "Crea otro", LENGTH_LONG).show();
                transaction.replace(R.id.LOprincipal, new Ventas(), "Ventas").addToBackStack("Ventas").commit();
            }
            exportar.setVisible(false);
        }
        else if (id == R.id.Inventario) {

            if(isTagInBackStack("Inventario")) {
                //transaction.show(getSupportFragmentManager().findFragmentByTag("Inventario")).commit();
                transaction.replace(R.id.LOprincipal, getSupportFragmentManager().findFragmentByTag("Inventario")).addToBackStack("Inventario").commit();
                //transaction.hide(getSupportFragmentManager().findFragmentByTag("Inventario")).commit();
                }
            else{
                Toast.makeText(getApplicationContext(), "Crea otro", LENGTH_LONG).show();
                transaction.replace(R.id.LOprincipal, new Inventario(), "Inventario").addToBackStack("Inventario").commit();
            }
            //manejador.beginTransaction().replace(R.id.LOprincipal, new Inventario(), "Inventario").addToBackStack("Inventario").commit();
            exportar.setVisible(true);
        }
        else if (id == R.id.Sincronizar) {
            if(isTagInBackStack("Sincronizar")) {
                //transaction.show(getSupportFragmentManager().findFragmentByTag("Sincronizar")).commit();
                transaction.replace(R.id.LOprincipal, getSupportFragmentManager().findFragmentByTag("Sincronizar")).addToBackStack("Sincronizar").commit();
                //transaction.hide(getSupportFragmentManager().findFragmentByTag("Sincronizar")).commit();
                }
            else{
                Toast.makeText(getApplicationContext(), "Crea otro", LENGTH_LONG).show();
                transaction.replace(R.id.LOprincipal, new Sincronizar(), "Sincronizar").addToBackStack("Sincronizar").commit();
            }
            exportar.setVisible(false);
            //manejador.beginTransaction().replace(R.id.LOprincipal, new Sincronizar(), "Sincronizar").addToBackStack("Sincronizar").commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
