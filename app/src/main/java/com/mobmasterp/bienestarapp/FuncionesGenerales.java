package com.mobmasterp.bienestarapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class FuncionesGenerales {
    Context context;
    public FuncionesGenerales(Context ctx) {
        this.context = ctx;
    }

    boolean stateET = false;
    public String getTextEditText(EditText eT){
        if(eT.getText().toString().equals("")){
            eT.setError("ERROR: Valor Invalido");
            stateET = false;
            return "";
        }
        stateET = true;
        return eT.getText().toString();
    }

    public boolean isStateET() {
        return stateET;
    }

    public void WriteSharedPreferences(String key, String value){
        SharedPreferences sharedPref = this.context.getSharedPreferences(key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String ReadSharedPreferences(String key){
        SharedPreferences sharedPref = this.context.getSharedPreferences(key, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    public void generarCodigoQR(String contenido, ImageView imageView) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            Bitmap bitmap = barcodeEncoder.encodeBitmap(contenido, BarcodeFormat.QR_CODE, (int)(obtenerAnchoPantalla()*0.7f), (int)(obtenerAnchoPantalla()*0.7f));
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }



    private int obtenerAnchoPantalla() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
