package com.example.ricardosernam.puntodeventa;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class Compras extends Fragment implements ZXingScannerView.ResultHandler{
private ZXingScannerView escanerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compras, container, false);
    }


    public void BtnEscanearQR(View view){
        escanerView=new ZXingScannerView(getContext());
        escanerView.setResultHandler(this);
        escanerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        escanerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Resultado del escaner");
        builder.setMessage("Resultado " + result.getText() + "\n" + "Formato "+result.getBarcodeFormat());
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
        escanerView.resumeCameraPreview(this);



    }
}
