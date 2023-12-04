package com.mobmasterp.bienestarapp.Interface;

import com.mobmasterp.bienestarapp.Modelos.ImplementosModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ImplementosAPI {

    @GET("implementos")
    Call<List<ImplementosModel>> getImplementos();
}
