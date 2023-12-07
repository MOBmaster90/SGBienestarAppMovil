package com.mobmasterp.bienestarapp.ui;

import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mobmasterp.bienestarapp.ClienteAPI;
import com.mobmasterp.bienestarapp.FuncionesGenerales;
import com.mobmasterp.bienestarapp.Interface.ImplementosAPI;
import com.mobmasterp.bienestarapp.Interface.PrestamoAPI;
import com.mobmasterp.bienestarapp.Modelos.AuthRequest;
import com.mobmasterp.bienestarapp.Modelos.ImplementosModel;
import com.mobmasterp.bienestarapp.Modelos.PrestamoModel;
import com.mobmasterp.bienestarapp.Principal;
import com.mobmasterp.bienestarapp.R;
import com.mobmasterp.bienestarapp.RV.ImplementosRV;
import com.mobmasterp.bienestarapp.StringValues;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Prestamo extends Fragment {

    RecyclerView rVImplementosPrestamo;
    LinearLayout lLQR;
    TextView txtIdPrestamo;
    ImageView imgQR;

    FloatingActionButton fab, fabEnviar;
    ImplementosRV implementosPrestamoRV;
    ImplementosRV implementosRV;

    AuthRequest.TokenModel usuarioInfo;
    FuncionesGenerales fg;

    List<ImplementosModel> implementosPrestamoLista = new ArrayList<>();
    List<ImplementosModel> implementosLista = new ArrayList<>();
    List<ImplementosModel> implementosListaFiltrada = new ArrayList<>();

    List<ImplementosModel> implementosListaRV = new ArrayList<>();
    List<String> nombreImplementosLista = new ArrayList<>();

    StringValues stringValues = new StringValues();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fg = new FuncionesGenerales(getContext());
        usuarioInfo = new Gson().fromJson(fg.ReadSharedPreferences("USUARIO_INFO"), AuthRequest.TokenModel.class);
        View root = inflater.inflate(R.layout.fragment_prestamo, container, false);
        rVImplementosPrestamo = (RecyclerView)root.findViewById(R.id.rVImplementosPrestamo);
        lLQR = (LinearLayout)root.findViewById(R.id.lLQR);
        txtIdPrestamo = (TextView)root.findViewById(R.id.txtIdPrestamo);
        imgQR = (ImageView)root.findViewById(R.id.imgQR);
        fab = (FloatingActionButton)root.findViewById(R.id.fab);
        fabEnviar = (FloatingActionButton)root.findViewById(R.id.fabEnviar);

        implementosPrestamoRV = new ImplementosRV(getContext(), implementosPrestamoLista, new ImplementosRV.onItemClick() {
            @Override
            public void onClick(ImplementosModel im, int cantidad) {

            }
        }, new ImplementosRV.OnItemLongClick() {
            @Override
            public void onLongClick(ImplementosModel im, int pos) {
                fg.alertDialog("Quitar Implemento", "¿Esta seguro de quitar el implemento seleccionado del prestamo?", new FuncionesGenerales.OnClickAlertDialog() {
                    @Override
                    public void click(DialogInterface dialog, int which, boolean confirmar) {
                        if(confirmar){
                            implementosPrestamoLista.remove(pos);
                            implementosPrestamoRV.notifyDataSetChanged();
                        }
                    }
                });
            }
        }, usuarioInfo.getPrivilegio());
        rVImplementosPrestamo.setAdapter(implementosPrestamoRV);
        rVImplementosPrestamo.setLayoutManager(new LinearLayoutManager(getContext()));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lLQR.setVisibility(View.INVISIBLE);
                rVImplementosPrestamo.setVisibility(View.VISIBLE);
                if(usuarioInfo.getPrivilegio() == 3){
                    if(implementosPrestamoRV.getItemCount() < 1){
                        addPrestamo();
                    }else{
                        Toast.makeText(getContext(), "No puede prestar mas de un (1) implemento", Toast.LENGTH_LONG).show();
                    }
                }else{
                    addPrestamo();
                }
            }
        });

        fabEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrestamoAPI prestamoAPI = new ClienteAPI().getClient().create(PrestamoAPI.class);
                PrestamoModel prestamoModel = new PrestamoModel();
                List<String> imp = new ArrayList<>();
                List<Integer> cant_imp = new ArrayList<>();
                for (ImplementosModel implementosModel : implementosPrestamoLista) {
                    imp.add(implementosModel.get_id());
                    cant_imp.add(implementosModel.getCantidad_seleccionada() + 1);
                }
                prestamoModel.setImplementos(imp);
                prestamoModel.setCantidad_implementos(cant_imp);
                prestamoModel.setUsuario(usuarioInfo.getId());
                prestamoModel.setEstado(stringValues.ID_ESTADO_PRESTAMO_PENDIENTE);
                ZonedDateTime fechaHoraActual = ZonedDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(stringValues.FORMATO_FECHA);
                prestamoModel.setFecha_inicio(fechaHoraActual.format(formatter));
                prestamoModel.setFecha_fin(fechaHoraActual.format(formatter));

                Call<String> call = prestamoAPI.crearPrestamo(prestamoModel);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()){
                            Log.i("PRUEBA", "Successfull: " + response.body());
                            lLQR.setVisibility(View.VISIBLE);
                            rVImplementosPrestamo.setVisibility(View.INVISIBLE);
                            implementosPrestamoLista.clear();
                            implementosPrestamoRV.notifyDataSetChanged();
                            txtIdPrestamo.setText(response.body().toString());
                            fg.generarCodigoQR(response.body().toString(), imgQR);
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
        });
        return root;
    }

    private void addPrestamo(){
        Dialog root = new Dialog(getContext());
        root.setContentView(R.layout.dialog_add_prestamo);
        Window window = root.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        AutoCompleteTextView eTBusqueda = (AutoCompleteTextView)root.findViewById(R.id.eTBusqueda);
        RecyclerView rVImplementos = (RecyclerView)root.findViewById(R.id.rVImplementos);

        implementosRV = new ImplementosRV(getContext(), implementosListaRV, new ImplementosRV.onItemClick() {
            @Override
            public void onClick(ImplementosModel im, int cantidad) {
                im.setCantidad_seleccionada(cantidad);
                implementosPrestamoLista.add(im);
                implementosPrestamoRV.notifyDataSetChanged();
                root.dismiss();
            }
        }, new ImplementosRV.OnItemLongClick() {
            @Override
            public void onLongClick(ImplementosModel im, int pos) {

            }
        }, usuarioInfo.getPrivilegio());
        rVImplementos.setAdapter(implementosRV);
        rVImplementos.setLayoutManager(new LinearLayoutManager(getContext()));

        ImplementosAPI implementosAPI = new ClienteAPI().getClient().create(ImplementosAPI.class);
        Call<List<ImplementosModel>> listImplementos = implementosAPI.getImplementos();
        listImplementos.enqueue(new Callback<List<ImplementosModel>>() {
            @Override
            public void onResponse(Call<List<ImplementosModel>> call, Response<List<ImplementosModel>> response) {
                if(response.isSuccessful()){
                    nombreImplementosLista.clear();
                    implementosListaFiltrada.clear();
                    implementosListaRV.clear();
                    implementosLista = response.body();
                    for (ImplementosModel implementosModel : implementosLista) {
                        for(int i=0; i<implementosModel.getEstado().size(); i++){
                            if(implementosModel.getEstado().get(i).isApto()){
                                nombreImplementosLista.add(implementosModel.getNombre());
                                implementosListaFiltrada.add(implementosModel);
                                break;
                            }
                        }
                    }
                    implementosListaRV.addAll(implementosListaFiltrada);
                    eTBusqueda.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, nombreImplementosLista));
                    implementosRV.notifyDataSetChanged();
                }else{
                    Log.i("PRUEBA", "ERROR ::: " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<List<ImplementosModel>> call, Throwable t) {
                Log.i("PRUEBA", "ERROR : " + t.getMessage());
            }
        });


        eTBusqueda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i=0; i<nombreImplementosLista.size(); i++) {
                    if(nombreImplementosLista.get(i).equals(eTBusqueda.getText().toString())){
                        implementosListaRV.clear();
                        implementosListaRV.add(implementosListaFiltrada.get(i));
                        break;
                    }
                }
                implementosRV.notifyDataSetChanged();
            }
        });

        eTBusqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(eTBusqueda.getText().toString().isEmpty()){
                    implementosListaRV.clear();
                    implementosListaRV.addAll(implementosListaFiltrada);
                    implementosRV.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        root.show();
    }
}