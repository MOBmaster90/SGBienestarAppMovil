package com.mobmasterp.bienestarapp.Interface;

import com.mobmasterp.bienestarapp.Modelos.Rol;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RolAPI {
    @GET("rol")
    Call<List<Rol>> getRols();
}
