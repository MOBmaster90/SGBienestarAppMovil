package com.mobmasterp.bienestarapp.Interface;

import com.mobmasterp.bienestarapp.Modelos.Ficha;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FichaAPI {
    @GET("ficha")
    Call<List<Ficha>> getFichas();
}
