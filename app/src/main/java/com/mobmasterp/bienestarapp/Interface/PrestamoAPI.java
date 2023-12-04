package com.mobmasterp.bienestarapp.Interface;

import com.mobmasterp.bienestarapp.Modelos.Prestamo.PrestamoFinalizarModel;
import com.mobmasterp.bienestarapp.Modelos.Prestamo.PrestamoGetModel;
import com.mobmasterp.bienestarapp.Modelos.PrestamoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PrestamoAPI {
    @POST("prestamos")
    Call<String> crearPrestamo(@Body PrestamoModel prestamoModel);

    @POST("prestamos/finalizar")
    Call<Void> finalizarPrestamo(@Body PrestamoFinalizarModel prestamoFinalizarModel);

    @GET("prestamos")
    Call<List<PrestamoGetModel>> getPrestamos();

    @GET("prestamos/{id}")
    Call<PrestamoGetModel> getPrestamo(@Path("id") String prestamoID);

    @GET("prestamos/usuario/{id}")
    Call<List<PrestamoGetModel>> getPrestamoByUsuario(@Path("id") String userID);

    @GET("prestamos/aprobar/{id}")
    Call<Void> aprobarPrestamo(@Path("id") String prestamoID);
}
