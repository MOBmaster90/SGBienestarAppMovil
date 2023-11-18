package com.mobmasterp.bienestarapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

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
}
