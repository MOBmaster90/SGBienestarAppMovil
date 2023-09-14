package com.mobmasterp.bienestarapp.Interface;

import com.mobmasterp.bienestarapp.Modelos.EPS;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EpsAPI {
    @GET("eps")
    Call<List<EPS>> getEPS();
}
