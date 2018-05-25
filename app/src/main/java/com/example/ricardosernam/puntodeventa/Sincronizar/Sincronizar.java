package com.example.ricardosernam.puntodeventa.Sincronizar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ricardosernam.puntodeventa.R;
import com.example.ricardosernam.puntodeventa.sync.SyncAdapter;
import com.example.ricardosernam.puntodeventa.utils.Constantes;

public class Sincronizar extends Fragment {
    public static EditText ip;
    public Button establecer, importar, exportar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sincronizar, container, false);
        ip=view.findViewById(R.id.ETip);
        importar=view.findViewById(R.id.BtnImportar);
        exportar=view.findViewById(R.id.BtnExportar);

        SyncAdapter.inicializarSyncAdapter(getContext(), Constantes.GET_URL_INVENTARIO);

        establecer=view.findViewById(R.id.BtnEstablecer);
        establecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(establecer.getText().equals("Establecer")){
                    establecer.setText("Modificar");
                    ip.setEnabled(false);
                }
                else{
                    establecer.setText("Establecer");
                    ip.setEnabled(true);
                }
            }
        });
        importar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SyncAdapter.sincronizarAhora(getContext(), false);

            }
        });
        exportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SyncAdapter.sincronizarAhora(getContext(), false);
            }
        });
        return view;
    }
}
