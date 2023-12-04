package com.mobmasterp.bienestarapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.List;

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

    public int obtenerAnchoPantalla() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public void dialogSpinner(EditText eT, List<String> list){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.spinner_layout);
        ListView listView = (ListView)dialog.findViewById(R.id.lvSp);
        listView.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                eT.setText(list.get(i));
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
