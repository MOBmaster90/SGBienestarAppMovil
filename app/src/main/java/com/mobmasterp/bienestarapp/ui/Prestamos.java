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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.mobmasterp.bienestarapp.ClienteAPI;
import com.mobmasterp.bienestarapp.Interface.PrestamoAPI;
import com.mobmasterp.bienestarapp.Modelos.Prestamo.PrestamoFinalizarModel;
import com.mobmasterp.bienestarapp.Modelos.Prestamo.PrestamoGetModel;
import com.mobmasterp.bienestarapp.R;
import com.mobmasterp.bienestarapp.StringValues;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Prestamos extends Fragment {

    FloatingActionButton fabQR;
    StringValues stringValues = new StringValues();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_prestamos, container, false);
        fabQR = (FloatingActionButton)root.findViewById(R.id.fabQR);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
        }

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

    private final ActivityResultLauncher<ScanOptions> QRLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_LONG).show();
                } else {
                    PrestamoAPI prestamoAPI = new ClienteAPI().getClient().create(PrestamoAPI.class);
                    Call<PrestamoGetModel> prestamoGetModelCall = prestamoAPI.getPrestamo(result.getContents());
                    prestamoGetModelCall.enqueue(new Callback<PrestamoGetModel>() {
                        @Override
                        public void onResponse(Call<PrestamoGetModel> call, Response<PrestamoGetModel> response) {
                            if(response.isSuccessful()){
                                if(response.body().getEstado().get_id().equals(stringValues.ID_ESTADO_PRESTAMO_PENDIENTE)){
                                    AprobarPrestamo(prestamoAPI, response.body().get_id());
                                }else if(response.body().getEstado().get_id().equals(stringValues.ID_ESTADO_PRESTAMO_APROBADO)){
                                    FinalizarPrestamo(prestamoAPI, response.body().get_id());
                                }
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

    private void AprobarPrestamo(PrestamoAPI prestamoAPI, String idPrestamo){
        Call call = prestamoAPI.aprobarPrestamo(idPrestamo);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Prestamo Aprobado", Toast.LENGTH_SHORT).show();
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

    private void FinalizarPrestamo(PrestamoAPI prestamoAPI, String idPrestamo){
        PrestamoFinalizarModel finalizarModel = new PrestamoFinalizarModel();
        finalizarModel.setId(idPrestamo);
        finalizarModel.setEstado(stringValues.ID_ESTADO_PRESTAMO_COMPLETADO);
        Call call = prestamoAPI.finalizarPrestamo(finalizarModel);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){

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
}