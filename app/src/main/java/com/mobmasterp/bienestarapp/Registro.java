package com.mobmasterp.bienestarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.mobmasterp.bienestarapp.Interface.DominioSenaAPI;
import com.mobmasterp.bienestarapp.Interface.EpsAPI;
import com.mobmasterp.bienestarapp.Interface.FichaAPI;
import com.mobmasterp.bienestarapp.Interface.RolAPI;
import com.mobmasterp.bienestarapp.Interface.UsuarioAPI;
import com.mobmasterp.bienestarapp.Modelos.DominioSena;
import com.mobmasterp.bienestarapp.Modelos.EPS;
import com.mobmasterp.bienestarapp.Modelos.Ficha;
import com.mobmasterp.bienestarapp.Modelos.RegistroPost;
import com.mobmasterp.bienestarapp.Modelos.Rol;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Registro extends AppCompatActivity {
    EditText eTtipoDoc, eTgenero, eTrh, eTeps, eTficha, eTrol, eTprograma;
    Button btnConfirmar;

    List<EPS> listaEPS = new ArrayList<>();
    List<Rol> listaRol = new ArrayList<>();
    List<Ficha> listaFichas = new ArrayList<>();
    List<String> nombreEPS = new ArrayList<>();
    List<String> codigoFichas = new ArrayList<>();
    List<String> nombreDominios = new ArrayList<>();
    List<String> nombreRol = new ArrayList<>();



    RegistroPost registroPost = new RegistroPost();

    boolean validado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        eTtipoDoc = (EditText)findViewById(R.id.eTtipoDoc);
        eTrh = (EditText)findViewById(R.id.eTrh);
        eTgenero = (EditText)findViewById(R.id.eTgenero);
        eTeps = (EditText)findViewById(R.id.eTeps);
        eTficha = (EditText)findViewById(R.id.eTficha);
        eTprograma = (EditText)findViewById(R.id.eTprograma);
        eTrol = (EditText)findViewById(R.id.eTrol);

        btnConfirmar = (Button)findViewById(R.id.btnConfirmar);

        eTtipoDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOptionSpinner(new String[]{"T.I.","C.C.","C.E.","P.A."}, eTtipoDoc);
            }
        });

        eTgenero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOptionSpinner(new String[]{"Masculino","Femenino","Otro"}, eTgenero);
            }
        });

        eTrh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOptionSpinner(new String[]{"A+","A-","AB+","AB-","B+", "B-", "O+", "O-"}, eTrh);
            }
        });

        Retrofit clienteAPI = new ClienteAPI().getClient();


        EpsAPI epsAPI = clienteAPI.create(EpsAPI.class);
        Call<List<EPS>> callEps = epsAPI.getEPS();
        callEps.enqueue(new Callback<List<EPS>>() {
            @Override
            public void onResponse(Call<List<EPS>> call, Response<List<EPS>> response) {
                if(response.isSuccessful()){
                    listaEPS = response.body();
                    for (EPS listaEP : listaEPS) {
                        nombreEPS.add(listaEP.getNombre());
                    }
                }else{
                    Log.i("PRUEBA", "ERROR ::: " + response.errorBody().source());
                }
            }

            @Override
            public void onFailure(Call<List<EPS>> call, Throwable t) {
                Log.i("PRUEBA", "Failure ::: " + t.getMessage());
            }
        });



        DominioSenaAPI dominioSenaAPI = clienteAPI.create(DominioSenaAPI.class);
        Call<List<DominioSena>> callDominio = dominioSenaAPI.getDominios();
        callDominio.enqueue(new Callback<List<DominioSena>>() {
            @Override
            public void onResponse(Call<List<DominioSena>> call, Response<List<DominioSena>> response) {
                if(response.isSuccessful()){
                    for (DominioSena dominioSena : response.body()) {
                        nombreDominios.add(dominioSena.getNombre());
                    }
                }else{
                    Log.i("PRUEBA", "ERROR ::: " + response.errorBody().source());
                }
            }

            @Override
            public void onFailure(Call<List<DominioSena>> call, Throwable t) {
                Log.i("PRUEBA", "Failure ::: " + t.getMessage());
            }
        });

        RolAPI rolAPI = clienteAPI.create(RolAPI.class);
        Call<List<Rol>> callRol = rolAPI.getRols();
        callRol.enqueue(new Callback<List<Rol>>() {
            @Override
            public void onResponse(Call<List<Rol>> call, Response<List<Rol>> response) {
                if(response.isSuccessful()){
                    listaRol = response.body();
                    for (Rol rol : response.body()) {
                        nombreRol.add(rol.getNombre());
                    }
                }else{
                    Log.i("PRUEBA", "ERROR ::: " + response.errorBody().source());
                }
            }

            @Override
            public void onFailure(Call<List<Rol>> call, Throwable t) {
                Log.i("PRUEBA", "Failure ::: " + t.getMessage());
            }
        });


        FichaAPI fichaAPI = clienteAPI.create(FichaAPI.class);
        Call<List<Ficha>> callFicha = fichaAPI.getFichas();
        callFicha.enqueue(new Callback<List<Ficha>>() {
            @Override
            public void onResponse(Call<List<Ficha>> call, Response<List<Ficha>> response) {
                if(response.isSuccessful()){
                    listaFichas = response.body();
                    for (Ficha listaFicha : listaFichas) {
                        codigoFichas.add(listaFicha.getCodigo());
                        //Log.i("PRUEBA", listaFicha.getCodigo());
                    }
                }else{
                    Log.i("PRUEBA", "ERROR ::: " + response.errorBody().source());
                }
            }

            @Override
            public void onFailure(Call<List<Ficha>> call, Throwable t) {
                Log.i("PRUEBA", "Failure ::: " + t.getMessage());
            }
        });

        eTeps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOptionSpinner(nombreEPS.toArray(new String[nombreEPS.size()]), eTeps);
            }
        });

        eTrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogOptionSpinner(nombreRol.toArray(new String[nombreRol.size()]), eTrol);
            }
        });

        eTficha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object[] objects = dialogFindText(codigoFichas, "Numero de Ficha");
                AutoCompleteTextView aC = (AutoCompleteTextView) objects[1];
                aC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        eTficha.setText(aC.getText().toString());
                        eTprograma.setText(listaFichas.get(codigoFichas.indexOf(aC.getText().toString())).getPrograma().getNombre());
                        Dialog dialog = (Dialog) objects[0];
                        dialog.dismiss();
                    }
                });
                //dialogOptionSpinner(codigoFichas.toArray(new String[codigoFichas.size()]), eTtipoDoc);
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registroPost.setNombres("Android 1");
                registroPost.setApellidos("Android 2");
                registroPost.setTipo_doc("C.C.");
                registroPost.setN_doc("1234567890");
                registroPost.setTelefono("3111111111");
                registroPost.setCorreo_inst("android@soysena.edu.co");
                registroPost.setContrasena("android123");
                registroPost.setCorreo_pers("android@mail.com");
                registroPost.setNacimiento("17/04/2023");
                registroPost.setRol("64e4ac0ce4067fe165a9a5a5");
                registroPost.setDireccion("ciudad 1");
                registroPost.setRh("A+");
                registroPost.setGenero("Masculino");
                registroPost.setEps("64e4ac0ce4067fe165a9a5a6");
                registroPost.setPps(true);
                registroPost.setFicha("64eb625121b885e04a01433f");
                UsuarioAPI usuarioAPI = clienteAPI.create(UsuarioAPI.class);
                Call<JsonElement> callUsuario = usuarioAPI.setUsuario(registroPost);
                callUsuario.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        if(response.isSuccessful()){

                            Log.i("PRUEBA", response.body().getAsJsonArray().get(1).toString());
                        }else{
                            Log.i("PRUEBA", "ERROR ::: " + response.errorBody().source());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        try {
                            JSONObject jsonObject = new JSONObject(t.getMessage().replace("[text=", ""). replace("]", ""));
                            Log.i("PRUEBA", jsonObject.toString());
                        } catch (JSONException e) {
                            Log.i("PRUEBA", e.getMessage().toString());
                        }
                        Log.i("PRUEBA", "Failure ::: " + t.getMessage());
                    }
                });
            }
        });



    }

    private String validacion(EditText eT){
        if(eT.getText().toString().isEmpty()){
            eT.setError("Campo Obligatorio");
            return "";
        }
        return eT.getText().toString();
    }

    private Object[] dialogFindText(List<String> data, String hint){
        Dialog dialog = new Dialog(Registro.this);
        dialog.setContentView(R.layout.dialog_find_edittext);
        AutoCompleteTextView aC = (AutoCompleteTextView)dialog.findViewById(R.id.aC);
        aC.setHint(hint);
        aC.setAdapter(new ArrayAdapter<String>(Registro.this, android.R.layout.simple_list_item_1, data));
        dialog.show();
        return new Object[]{dialog, aC};
    }

    private void dialogOptionSpinner(String[] options, EditText eT){
        Dialog dialog = new Dialog(Registro.this);
        dialog.setContentView(R.layout.dialog_option_spinner);
        ListView listView = (ListView)dialog.findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<>(Registro.this, android.R.layout.simple_list_item_1, options));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                eT.setText(listView.getItemAtPosition(i).toString());
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}