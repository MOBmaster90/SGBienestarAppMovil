package com.mobmasterp.bienestarapp.Interface;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mobmasterp.bienestarapp.Modelos.AuthRequest;
import com.mobmasterp.bienestarapp.Modelos.AuthUsuario;
import com.mobmasterp.bienestarapp.Modelos.RegistroPost;
import com.mobmasterp.bienestarapp.Modelos.UsuarioModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsuarioAPI {
    @POST("registro")
    Call<JsonElement> setUsuario(@Body RegistroPost usuario);

    @POST("registro/login")
    Call<AuthRequest> authLogin(@Body AuthUsuario credenciales);

    @GET("registro/usuario/{id}")
    Call<UsuarioModel> getUsuarioById(@Path("id") String idUsuario);
}
