package com.mobmasterp.bienestarapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.mobmasterp.bienestarapp.ClienteAPI;
import com.mobmasterp.bienestarapp.FuncionesGenerales;
import com.mobmasterp.bienestarapp.Interface.PrestamoAPI;
import com.mobmasterp.bienestarapp.Modelos.Prestamo.PrestamoFinalizarModel;
import com.mobmasterp.bienestarapp.Modelos.Prestamo.PrestamoGetModel;
import com.mobmasterp.bienestarapp.R;
import com.mobmasterp.bienestarapp.RV.AprobarPrestamosRV;
import com.mobmasterp.bienestarapp.StringValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Prestamos extends Fragment {

    EditText eTfiltrar;
    RecyclerView rV;

    FloatingActionButton fabQR;
    StringValues stringValues = new StringValues();

    List<PrestamoGetModel> prestamoGetModels = new ArrayList<>();
    AprobarPrestamosRV aprobarPrestamosRV;

    PrestamoAPI prestamoAPI = new ClienteAPI().getClient().create(PrestamoAPI.class);

    FuncionesGenerales fg;

    List<String> ListaEstadoPrestamos = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_prestamos, container, false);
        eTfiltrar = (EditText)root.findViewById(R.id.eTfiltrar);
        rV = (RecyclerView)root.findViewById(R.id.rV);
        fabQR = (FloatingActionButton)root.findViewById(R.id.fabQR);

        fg = new FuncionesGenerales(getContext());

        ActualizarVista();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
        }

        aprobarPrestamosRV = new AprobarPrestamosRV(getContext(), prestamoGetModels, new AprobarPrestamosRV.onClick() {
            @Override
            public void click(PrestamoGetModel pgm, int pos, int accion) {
                switch (accion){
                    case 1:
                        FinalizarPrestamo(pgm.get_id(), stringValues.ID_ESTADO_PRESTAMO_RECHAZADO);
                        break;
                    case 2:
                        FinalizarPrestamo(pgm.get_id(), stringValues.ID_ESTADO_PRESTAMO_COMPLETADO);
                        break;
                    case 3:
                        AprobarPrestamo(pgm.get_id());
                        break;
                }
            }
        });
        rV.setLayoutManager(new LinearLayoutManager(getContext()));
        rV.setAdapter(aprobarPrestamosRV);

        eTfiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListaEstadoPrestamos.clear();
                // Falta permitir seleccionar varias opciones de filtro

                for (PrestamoGetModel prestamoGetModel : prestamoGetModels) {
                    if(!ListaEstadoPrestamos.contains(prestamoGetModel.getEstado().getNombre())){
                        ListaEstadoPrestamos.add(prestamoGetModel.getEstado().getNombre());
                    }
                }
                Collections.sort(ListaEstadoPrestamos);
                fg.dialogSpinner(eTfiltrar, ListaEstadoPrestamos);
            }
        });

        fabQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanOptions scanOptions = new ScanOptions();
                scanOptions.setOrientationLocked(true);
                scanOptions.setBeepEnabled(true);
                QRLauncher.launch(new ScanOptions());
            }
        });
        return root;
    }

    private void ActualizarVista(){
        Call<List<PrestamoGetModel>> callPrestamos = prestamoAPI.getPrestamos();
        callPrestamos.enqueue(new Callback<List<PrestamoGetModel>>() {
            @Override
            public void onResponse(Call<List<PrestamoGetModel>> call, Response<List<PrestamoGetModel>> response) {
                if(response.isSuccessful()){
                    prestamoGetModels.clear();
                    prestamoGetModels.addAll(response.body());
                    Collections.reverse(prestamoGetModels);
                    aprobarPrestamosRV.notifyDataSetChanged();
                }else{
                    Log.i("PRUEBA", "ERROR ::: " + response.errorBody().source());
                }
            }
            @Override
            public void onFailure(Call<List<PrestamoGetModel>> call, Throwable t) {
                Log.i("PRUEBA", "ERROR : " + t.getMessage());
            }
        });
    }

    private final ActivityResultLauncher<ScanOptions> QRLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_LONG).show();
                } else {

                    Call<PrestamoGetModel> prestamoGetModelCall = prestamoAPI.getPrestamo(result.getContents());
                    prestamoGetModelCall.enqueue(new Callback<PrestamoGetModel>() {
                        @Override
                        public void onResponse(Call<PrestamoGetModel> call, Response<PrestamoGetModel> response) {
                            if(response.isSuccessful()){
                                if(response.body().getEstado().get_id().equals(stringValues.ID_ESTADO_PRESTAMO_PENDIENTE)){
                                    AprobarPrestamo(response.body().get_id());
                                }else if(response.body().getEstado().get_id().equals(stringValues.ID_ESTADO_PRESTAMO_APROBADO)){
                                    FinalizarPrestamo(response.body().get_id(), stringValues.ID_ESTADO_PRESTAMO_COMPLETADO);
                                }
                                ActualizarVista();
                            }else{
                                Log.i("PRUEBA", "ERROR ::: " + response.errorBody().source());
                            }
                        }
                        @Override
                        public void onFailure(Call<PrestamoGetModel> call, Throwable t) {
                            Log.i("PRUEBA", "ERROR : " + t.getMessage());
                        }
                    });
                }
            });

    private void AprobarPrestamo(String idPrestamo){
        Call call = prestamoAPI.aprobarPrestamo(idPrestamo);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Prestamo Aprobado", Toast.LENGTH_SHORT).show();
                    ActualizarVista();
                }else{
                    Log.i("PRUEBA", "ERROR ::: " + response.errorBody().source());
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.i("PRUEBA", "ERROR : " + t.getMessage());
            }
        });
    }

    private void FinalizarPrestamo(String idPrestamo, String idEstado){
        PrestamoFinalizarModel finalizarModel = new PrestamoFinalizarModel();
        finalizarModel.setId(idPrestamo);
        finalizarModel.setEstado(idEstado);
        Call call = prestamoAPI.finalizarPrestamo(finalizarModel);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){

                }else{
                    Log.i("PRUEBA", "ERROR ::: " + response.errorBody().source());
                }
                ActualizarVista();
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.i("PRUEBA", "ERROR : " + t.getMessage());
            }
        });
    }
}