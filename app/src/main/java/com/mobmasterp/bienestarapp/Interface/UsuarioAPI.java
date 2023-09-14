package com.mobmasterp.bienestarapp.Interface;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mobmasterp.bienestarapp.Modelos.RegistroPost;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioAPI {
    @POST("registro")
    Call<JsonElement> setUsuario(@Body RegistroPost usuario);
}
