package com.mobmasterp.bienestarapp.Interface;

import com.mobmasterp.bienestarapp.Modelos.ComentariosModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ComentariosAPI {

    @POST("mail/usuario/notificacion")
    Call<ComentariosModel.requestComentarioModel> enviarComentario(@Body ComentariosModel comentariosModel);

    @GET("mail/comentario/usuario/{id}")
    Call<List<ComentariosModel>> getComentariosXusuarioXid(@Path("id") String idUsuario);
}
