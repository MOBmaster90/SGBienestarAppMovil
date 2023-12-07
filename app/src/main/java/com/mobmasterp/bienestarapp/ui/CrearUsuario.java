package com.mobmasterp.bienestarapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.mobmasterp.bienestarapp.FuncionesGenerales;
import com.mobmasterp.bienestarapp.Modelos.AuthRequest;
import com.mobmasterp.bienestarapp.R;

public class CrearUsuario extends Fragment {

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
        View root = inflater.inflate(R.layout.fragment_crear_usuario, container, false);
        return root;
    }
}