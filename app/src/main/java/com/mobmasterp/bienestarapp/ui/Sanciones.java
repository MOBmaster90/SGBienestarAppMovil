package com.mobmasterp.bienestarapp.ui;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mobmasterp.bienestarapp.ClienteAPI;
import com.mobmasterp.bienestarapp.FuncionesGenerales;
import com.mobmasterp.bienestarapp.Interface.SancionesAPI;
import com.mobmasterp.bienestarapp.Modelos.AuthRequest;
import com.mobmasterp.bienestarapp.Modelos.Prestamo.PrestamoGetModel;
import com.mobmasterp.bienestarapp.Modelos.SancionesModel;
import com.mobmasterp.bienestarapp.Principal;
import com.mobmasterp.bienestarapp.R;
import com.mobmasterp.bienestarapp.RV.SancionesRV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sanciones extends Fragment {
    EditText eTfiltrar;
    RecyclerView rV;
    FloatingActionButton fabFiltro;

    AuthRequest.TokenModel usuarioInfo;
    FuncionesGenerales fg;

    SancionesAPI sancionesAPI;

    List<SancionesModel> sancionesModelList = new ArrayList<>();
    List<SancionesModel> sancionesFiltradasModelList = new ArrayList<>();

    SancionesRV sancionesRV;

    List<String> listaEstados = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fg = new FuncionesGenerales(getContext());
        usuarioInfo = new Gson().fromJson(fg.ReadSharedPreferences("USUARIO_INFO"), AuthRequest.TokenModel.class);
        View root = inflater.inflate(R.layout.fragment_sanciones, container, false);
        eTfiltrar = (EditText)root.findViewById(R.id.eTfiltrar);
        rV = (RecyclerView)root.findViewById(R.id.rV);
        fabFiltro = (FloatingActionButton)root.findViewById(R.id.fabFiltrar);

        sancionesRV = new SancionesRV(getContext(), sancionesFiltradasModelList, new SancionesRV.OnClick() {
            @Override
            public void click(SancionesModel sancionesModel, int pos) {

            }
        });
        rV.setLayoutManager(new LinearLayoutManager(getContext()));
        rV.setAdapter(sancionesRV);

        eTfiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> listaEstado = new ArrayList<>();
                listaEstado.add("Activas");
                listaEstado.add("Inactivas");
                fg.dialogSpinner(eTfiltrar, listaEstado);
            }
        });

        eTfiltrar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                listaEstados = new ArrayList<>();
                try {
                    listaEstados = Arrays.asList(eTfiltrar.getText().toString().split(", "));
                    if(listaEstados.size() > 1){listaEstados = new ArrayList<>();}
                }catch (Exception e){
                    if(!eTfiltrar.getText().toString().isEmpty()){
                        listaEstados.add(eTfiltrar.getText().toString());
                    }
                }
                ActualizarVista("","");
            }
        });

        fabFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialogo_busqueda);
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                EditText eT = (EditText)dialog.findViewById(R.id.eT);
                RadioGroup radioG = (RadioGroup)dialog.findViewById(R.id.radioG);
                Button btn = (Button) dialog.findViewById(R.id.btn);
                final String[] opc = {""};
                fg.createRadioButtons(radioG, new String[]{"Documento"});
                radioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton radioButton = (RadioButton)radioGroup.getChildAt(i-1);
                        opc[0] = radioButton.getText().toString();
                    }
                });
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!eT.getText().toString().isEmpty()){
                            if(opc[0].equals("Documento")){
                                ActualizarVista("doc", eT.getText().toString());
                            }else{
                                Toast.makeText(getContext(), "Seleccione una opcion de busqueda", Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
            }
        });

        sancionesAPI = new ClienteAPI().getClient().create(SancionesAPI.class);
        ActualizarVista("", "");
        return root;
    }

    private void ActualizarVista(String endPoint, String value){
        Call<List<SancionesModel>> callSanciones;
        if(endPoint.equals("doc")){
            callSanciones = sancionesAPI.getSancionesXusuarioXdoc(value);
        }else{
            callSanciones = sancionesAPI.getSanciones();
        }
        callSanciones.enqueue(new Callback<List<SancionesModel>>() {
            @Override
            public void onResponse(Call<List<SancionesModel>> call, Response<List<SancionesModel>> response) {
                if(response.isSuccessful()){
                    sancionesModelList.clear();
                    sancionesFiltradasModelList.clear();
                    sancionesModelList.addAll(response.body());
                    if(!listaEstados.isEmpty()){
                        for (SancionesModel sancionesModel : sancionesModelList) {
                            if(sancionesModel.isEstado() && listaEstados.get(0).equals("Activas")){
                                sancionesFiltradasModelList.add(sancionesModel);
                            }else if(!sancionesModel.isEstado() && listaEstados.get(0).equals("Inactivas")){
                                sancionesFiltradasModelList.add(sancionesModel);
                            }
                        }
                    }else{
                        sancionesFiltradasModelList.addAll(sancionesModelList);
                    }
                    Collections.reverse(sancionesFiltradasModelList);
                    sancionesRV.notifyDataSetChanged();
                }else{
                    Log.i("PRUEBA", "ERROR ::: " + response.errorBody().source());
                }
            }
            @Override
            public void onFailure(Call<List<SancionesModel>> call, Throwable t) {
                Log.i("PRUEBA", "ERROR : " + t.getMessage());
            }
        });
    }
}