package com.mobmasterp.bienestarapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.mobmasterp.bienestarapp.Interface.UsuarioAPI;
import com.mobmasterp.bienestarapp.Modelos.AuthRequest;
import com.mobmasterp.bienestarapp.Modelos.AuthUsuario;

import java.security.Key;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Locator;
import io.jsonwebtoken.security.Keys;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText eTmailInst, eTpass;
    CheckBox chBrecordar;
    Button btnRegistrate, btnIniciar;
    TextView txtOlvido;

    FuncionesGenerales fg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eTmailInst = (EditText)findViewById(R.id.eTmailInst);
        eTpass = (EditText)findViewById(R.id.eTpass);
        chBrecordar = (CheckBox) findViewById(R.id.chBrecordar);
        btnRegistrate = (Button)findViewById(R.id.btnRegistrate);
        btnIniciar = (Button)findViewById(R.id.btnIniciar);
        txtOlvido = (TextView)findViewById(R.id.txtOlvido);

        fg = new FuncionesGenerales(MainActivity.this);

        String saveUser = fg.ReadSharedPreferences("USUARIO");
        String savePass = fg.ReadSharedPreferences("PASS");
        if(!saveUser.isEmpty() && !savePass.isEmpty()){
            eTmailInst.setText(saveUser);
            eTpass.setText(savePass);
        }

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = fg.getTextEditText(eTmailInst);
                String pass = fg.getTextEditText(eTpass);
                if(!usuario.isEmpty() && !pass.isEmpty()){
                    AuthUsuario authUsuario = new AuthUsuario();
                    authUsuario.setCorreo_inst(usuario);
                    authUsuario.setContrasena(pass);
                    UsuarioAPI usuarioAPI = ClienteAPI.getClient().create(UsuarioAPI.class);
                    Call<AuthRequest> authRequestCall = usuarioAPI.authLogin(authUsuario);
                    authRequestCall.enqueue(new Callback<AuthRequest>() {
                        @Override
                        public void onResponse(Call<AuthRequest> call, Response<AuthRequest> response) {
                            if(response.isSuccessful()){
                                if(chBrecordar.isChecked()){
                                    fg.WriteSharedPreferences("USUARIO", usuario);
                                    fg.WriteSharedPreferences("PASS", pass);
                                }
                                Log.i("PRUEBA", response.body().getToken());
                                Claims claims = decodeJWT(response.body().getToken(), "SistemaGestionBienestar2023*");
                                Log.i("PRUEBA", "  Claims: " + claims);
                            }else{
                                Log.i("PRUEBA", "ERROR :::: " + response.errorBody());
                            }
                        }
                        @Override
                        public void onFailure(Call<AuthRequest> call, Throwable t) {
                            Log.i("PRUEBA", "ERROR : " + t.getMessage());
                        }
                    });
                }
            }
        });

        btnRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Registro.class));
            }
        });

        txtOlvido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public Claims decodeJWT(String jwt, String key){
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
                    .build().parseClaimsJws(jwt)
                    .getBody();
            return claims;
        }catch (JwtException Ex){
            Log.i("PRUEBA", "ERROR ::: " + Ex.getMessage());
            return null;
        }
    }
}