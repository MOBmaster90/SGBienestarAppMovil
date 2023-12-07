package com.mobmasterp.bienestarapp.Interface;

import com.mobmasterp.bienestarapp.Modelos.SancionesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SancionesAPI {
    @GET("sanciones")
    Call<List<SancionesModel>> getSanciones();

    @GET("sanciones/usuario/{id}")
    Call<List<SancionesModel>> getSancionesXusuario(@Path("id") String idUsuario);
    @GET("sanciones/usuario/documento/{doc}")
    Call<List<SancionesModel>> getSancionesXusuarioXdoc(@Path("doc") String n_doc);
}
