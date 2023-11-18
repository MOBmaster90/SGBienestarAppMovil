package com.mobmasterp.bienestarapp.Interface;

import com.mobmasterp.bienestarapp.Modelos.DominioSena;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DominioSenaAPI {
    @GET("dominio-sena")
    Call<List<DominioSena>> getDominios();
}
