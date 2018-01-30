/*
    Activity del scanner de codigo de barras utilizando la libreria 'zxing'
    Fuentes:
    https://demonuts.com/2017/03/16/scan-barcode-and-qrcode-using-zxing-android-studio/
 */

package com.example.ricardosernam.puntodeventa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Escanner extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    MainActivity main = new MainActivity();

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    //manejar resultado
    public void handleResult(Result rawResult) {
        String resultado;
        resultado = rawResult.getText().toString().trim();

        Toast.makeText(this, resultado.trim(), Toast.LENGTH_SHORT).show();//mostrar en un mensaje emerjente

        //regresar resultado
        Intent intent = new Intent();
        intent.putExtra("BARCODE", resultado);
        setResult(RESULT_OK, intent);
        finish();

        // si quieres seguir escaneando:
        //mScannerView.resumeCameraPreview(this);
    }
}