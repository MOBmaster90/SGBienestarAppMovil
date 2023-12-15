package com.mobmasterp.bienestarapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mobmasterp.bienestarapp.ClienteAPI;
import com.mobmasterp.bienestarapp.FuncionesGenerales;
import com.mobmasterp.bienestarapp.Interface.ComentariosAPI;
import com.mobmasterp.bienestarapp.Modelos.AuthRequest;
import com.mobmasterp.bienestarapp.Modelos.ComentariosModel;
import com.mobmasterp.bienestarapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mensajes extends Fragment {

    ScrollView sview;
    LinearLayout llmsgs;

    EditText eTmsg;
    ImageView imgSend;
    AuthRequest.TokenModel usuarioInfo;
    FuncionesGenerales fg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fg = new FuncionesGenerales(getContext());
        usuarioInfo = new Gson().fromJson(fg.ReadSharedPreferences("USUARIO_INFO"), AuthRequest.TokenModel.class);
        View root =  inflater.inflate(R.layout.fragment_mensajes, container, false);
        eTmsg = (EditText)root.findViewById(R.id.eTmsg);
        imgSend = (ImageView)root.findViewById(R.id.imgSend);
        sview = (ScrollView)root.findViewById(R.id.sview);
        llmsgs = (LinearLayout)root.findViewById(R.id.llmsgs);

        final ComentariosAPI comentariosAPI = new ClienteAPI().getClient().create(ComentariosAPI.class);
        Call<List<ComentariosModel>> listCall = comentariosAPI.getComentariosXusuarioXid(usuarioInfo.getId());
        listCall.enqueue(new Callback<List<ComentariosModel>>() {
            @Override
            public void onResponse(Call<List<ComentariosModel>> call, Response<List<ComentariosModel>> response) {
                if(response.isSuccessful()){
                    llmsgs.removeAllViews();
                    List<ComentariosModel> comentariosModelsList = response.body();
                    for (ComentariosModel comentariosModel : comentariosModelsList) {
                        addViewMsg(true, comentariosModel.getMensaje());
                    }
                    sview.post(new Runnable() {
                        @Override
                        public void run() {
                            //sview.fullScroll(View.FOCUS_DOWN);
                            sview.smoothScrollTo(0, sview.getBottom());
                        }
                    });
                }else{
                    Log.i("PRUEBA", "ERROR ::: " + response.errorBody().source());
                }
            }

            @Override
            public void onFailure(Call<List<ComentariosModel>> call, Throwable t) {
                Log.i("PRUEBA", "ERROR : " + t.getMessage());
            }
        });


        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!eTmsg.getText().toString().isEmpty()){
                    eTmsg.setEnabled(false);
                    imgSend.setEnabled(false);
                    ComentariosModel comentariosModel = new ComentariosModel();
                    comentariosModel.setAsunto("Comentario de: " + usuarioInfo.getCorreo_inst());
                    comentariosModel.setMensaje(eTmsg.getText().toString());
                    comentariosModel.setCorreo(usuarioInfo.getCorreo_inst());

                    Call<ComentariosModel.requestComentarioModel> callEnviar = comentariosAPI.enviarComentario(comentariosModel);
                    callEnviar.enqueue(new Callback<ComentariosModel.requestComentarioModel>() {
                        @Override
                        public void onResponse(Call<ComentariosModel.requestComentarioModel> call, Response<ComentariosModel.requestComentarioModel> response) {
                            if(response.isSuccessful()){
                                addViewMsg(true, eTmsg.getText().toString());
                                eTmsg.setText("");
                            }else{
                                Log.i("PRUEBA", "ERROR ::: " + response.errorBody().source());
                            }
                            eTmsg.setEnabled(true);
                            imgSend.setEnabled(true);
                        }
                        @Override
                        public void onFailure(Call<ComentariosModel.requestComentarioModel> call, Throwable t) {
                            Log.i("PRUEBA", "ERROR : " + t.getMessage());
                            eTmsg.setEnabled(true);
                            imgSend.setEnabled(true);
                        }
                    });
                }
            }
        });

        return root;
    }

    private void addViewMsg(boolean send, String msg){
        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_msg_send, null);
        LinearLayout.LayoutParams lparam= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lparam.gravity = Gravity.RIGHT;
        lparam.setMargins(70, 5, 0, 0);
        if(!send){
            v = LayoutInflater.from(getContext()).inflate(R.layout.layout_msg_receive, null);
            lparam.gravity = Gravity.LEFT;
            lparam.setMargins(0, 5, 70, 0);
        }
        v.setLayoutParams(lparam);
        TextView txt = (TextView)v.findViewById(R.id.txts);
        txt.setText(msg);
        llmsgs.addView(v);
    }
}