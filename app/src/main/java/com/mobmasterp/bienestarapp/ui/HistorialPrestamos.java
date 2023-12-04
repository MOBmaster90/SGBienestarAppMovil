package com.mobmasterp.bienestarapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mobmasterp.bienestarapp.ClienteAPI;
import com.mobmasterp.bienestarapp.FuncionesGenerales;
import com.mobmasterp.bienestarapp.Interface.PrestamoAPI;
import com.mobmasterp.bienestarapp.Modelos.AuthRequest;
import com.mobmasterp.bienestarapp.Modelos.Prestamo.PrestamoFinalizarModel;
import com.mobmasterp.bienestarapp.Modelos.Prestamo.PrestamoGetModel;
import com.mobmasterp.bienestarapp.R;
import com.mobmasterp.bienestarapp.RV.PrestamosRV;
import com.mobmasterp.bienestarapp.StringValues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialPrestamos extends Fragment {

    RecyclerView rV;

    List<PrestamoGetModel> prestamoGetModels = new ArrayList<>();
    PrestamosRV prestamosRV;

    AuthRequest.TokenModel usuarioInfo;
    FuncionesGenerales fg;
    StringValues stringValues = new StringValues();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fg = new FuncionesGenerales(getContext());
        usuarioInfo = new Gson().fromJson(fg.ReadSharedPreferences("USUARIO_INFO"), AuthRequest.TokenModel.class);
        View root =  inflater.inflate(R.layout.fragment_historial_prestamos, container, false);
        rV = (RecyclerView)root.findViewById(R.id.rV);

        prestamosRV = new PrestamosRV(getContext(), prestamoGetModels, new PrestamosRV.itemOnClick() {
            @Override
            public void click(PrestamoGetModel pgm, int pos) {
                Dialog dialog = new Dialog(getContext());
                ImageView imgQR = new ImageView(getContext());
                imgQR.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                fg.generarCodigoQR(pgm.get_id(), imgQR);
                dialog.setContentView(imgQR);
                dialog.show();
                /*if(pgm.getEstado().get_id().equals(stringValues.ID_ESTADO_PRESTAMO_PENDIENTE)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Cancelar Prestamo")
                            .setMessage("¿Seguro que desea cancelar el prestamo?")
                            .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    PrestamoFinalizarModel finalizarModel = new PrestamoFinalizarModel();
                                    finalizarModel.setEstado(stringValues.ID_ESTADO_PRESTAMO_ELIMINADO);
                                    finalizarModel.setId(pgm.get_id());
                                    PrestamoAPI prestamoAPI = new ClienteAPI().getClient().create(PrestamoAPI.class);
                                    Call call = prestamoAPI.finalizarPrestamo(finalizarModel);
                                    call.enqueue(new Callback() {
                                        @Override
                                        public void onResponse(Call call, Response response) {
                                            if(response.isSuccessful()){
                                                Log.i("PRUEBA", "Finalizado");
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
                            })
                            .setNegativeButton("Regresar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }*/
            }
        });

        rV.setAdapter(prestamosRV);
        rV.setLayoutManager(new LinearLayoutManager(getContext()));

        PrestamoAPI prestamoAPI = new ClienteAPI().getClient().create(PrestamoAPI.class);
        Call<List<PrestamoGetModel>> call = prestamoAPI.getPrestamoByUsuario(usuarioInfo.getId());
        call.enqueue(new Callback<List<PrestamoGetModel>>() {
            @Override
            public void onResponse(Call<List<PrestamoGetModel>> call, Response<List<PrestamoGetModel>> response) {
                if(response.isSuccessful()){
                    prestamoGetModels.clear();
                    prestamoGetModels.addAll(response.body());
                    Collections.reverse(prestamoGetModels);
                    prestamosRV.notifyDataSetChanged();
                }else{
                    Log.i("PRUEBA", "ERROR ::: " + response.errorBody().source());
                }
            }
            @Override
            public void onFailure(Call<List<PrestamoGetModel>> call, Throwable t) {
                Log.i("PRUEBA", "ERROR : " + t.getMessage());
            }
        });
        return root;
    }
}