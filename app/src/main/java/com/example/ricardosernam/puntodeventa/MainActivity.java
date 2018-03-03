package com.example.ricardosernam.puntodeventa;

import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ricardosernam.puntodeventa.Benvenida.Personalizacion1;
import com.example.ricardosernam.puntodeventa.Benvenida.Registro_inicial;
import com.example.ricardosernam.puntodeventa.Clientes.Clientes;
import com.example.ricardosernam.puntodeventa.Compras.Compras;
import com.example.ricardosernam.puntodeventa.Configurar.Configurar;
import com.example.ricardosernam.puntodeventa.Contactanos.Contactanos;
import com.example.ricardosernam.puntodeventa.Inventario.Inventario;
import com.example.ricardosernam.puntodeventa.Miembros.Perfiles;
import com.example.ricardosernam.puntodeventa.Personalizar.Personalizar;
import com.example.ricardosernam.puntodeventa.Productos.Productos;
import com.example.ricardosernam.puntodeventa.Proveedores.Proveedores;
import com.example.ricardosernam.puntodeventa.Reportes.Reportes;
import com.example.ricardosernam.puntodeventa.Ventas.Historial_ventas_class;
import com.example.ricardosernam.puntodeventa.Ventas.Pro_ventas_class;
import com.example.ricardosernam.puntodeventa.Ventas.Ventas;
import com.example.ricardosernam.puntodeventa._____interfazes.interfaz_historial;
import com.example.ricardosernam.puntodeventa.____herramientas_app.Escanner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, interfaz_historial {
    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView logo;
    private Cursor fila;
    private SQLiteDatabase db;
    private ContentValues values;
    TextView nombreEmpresa;
    private android.support.v4.app.FragmentManager manejador = getSupportFragmentManager();  //manejador que permite hacer el cambio de ventanas
    private ArrayList<Historial_ventas_class> itemsHistorial = new ArrayList<>();   ///array para productos seleccionados
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppBarLayout bar=findViewById(R.id.APLappBar);
        tabLayout =  findViewById(R.id.TLtabla);
        logo=  findViewById(R.id.IVlogoEmpresa);
        nombreEmpresa=  findViewById(R.id.TVnombreEmpresa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BaseDeDatosLocal admin=new BaseDeDatosLocal(getApplicationContext());
        db=admin.getWritableDatabase();
        values = new ContentValues();

        fila=db.rawQuery("select nombre, logo from Datos_Empresa" ,null);

        if(fila.moveToFirst()) {
            //while (fila.moveToNext()) {
                nombreEmpresa.setText(fila.getString(0));
                logo.setImageURI(Uri.parse(fila.getString(1)));
            //itemsProductos.add(new Pro_ventas_class(fila.getString(0), fila.getString(1), fila.getString(2), fila.getString(3), fila.getString(4)));
            //}
        }

        manejador.beginTransaction().replace(R.id.LOprincipal, new Ventas()).commit(); ///cambio de fragment
        /////comprobamos si es la primera vez que se abre
        if(appGetFirstTimeRun()==0 ){
            manejador.beginTransaction().replace(R.id.CLcontenedorTotal, new Registro_inicial()).commit(); ///cambio de fragment
            bar.setVisibility(View.INVISIBLE);
        }
        else if(appGetFirstTimeRun()==1){
            manejador.beginTransaction().replace(R.id.LOprincipal, new Ventas()).commit(); ///cambio de fragment
        }
        else if(appGetFirstTimeRun()==2){
            Toast.makeText(getApplicationContext(), "Es una actualización", Toast.LENGTH_LONG).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        /*if (id == R.id.action_settings) {
            return true;
        }*/

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
            manejador.beginTransaction().replace(R.id.LOprincipal, new Ventas()).commit(); ///cambio de fragments
            tabLayout.setVisibility(View.GONE);
        } else if (id == R.id.Compras) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Compras()).commit();
            tabLayout.setVisibility(View.GONE);
        } else if (id == R.id.Productos) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Productos()).commit();
            tabLayout.setVisibility(View.GONE);
        } else if (id == R.id.Miembros) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Perfiles()).commit();
            //tabLayout.setVisibility(View.VISIBLE);
        } else if (id == R.id.Provedores) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Proveedores()).commit();
            tabLayout.setVisibility(View.GONE);
        } else if (id == R.id.Clientes) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Clientes()).commit();
            tabLayout.setVisibility(View.GONE);
        } else if (id == R.id.Reportes) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Reportes()).commit();
            tabLayout.setVisibility(View.GONE);
        } else if (id == R.id.Inventario) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Inventario()).commit();
            tabLayout.setVisibility(View.GONE);
        } else if (id == R.id.Personalizar) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Personalizar()).commit();
            tabLayout.setVisibility(View.GONE);
        } else if (id == R.id.Configurar) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Configurar()).commit();
            tabLayout.setVisibility(View.GONE);
        } else if (id == R.id.Contáctanos) {
            manejador.beginTransaction().replace(R.id.LOprincipal, new Contactanos()).commit();
            tabLayout.setVisibility(View.GONE);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    ////metodo de la interface (debo puentearla forzosamente con el activity que las contiene)
    public void mandarHistorial(String tipo, String pagar) {  ////metodo de la interface (debo puentearla forzosamente con el activity que las contiene)
        itemsHistorial.add(new Historial_ventas_class(tipo, pagar));  ///tipo viene de fragment_cobrar
        manejador.beginTransaction().replace(R.id.LOprincipal, new Ventas(itemsHistorial)).commit(); ///cambio de fragment (Le envio a ventas el array que ira a Historial)

    }
    private int appGetFirstTimeRun() {  ///comprobamos si es la primera vez que se abre la app
        //Check if App Start First Time
        SharedPreferences appPreferences = getSharedPreferences("MyAPP", 0);
        int appCurrentBuildVersion = BuildConfig.VERSION_CODE;
        int appLastBuildVersion = appPreferences.getInt("app_first_time", 0);

        if (appLastBuildVersion == appCurrentBuildVersion) {
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
