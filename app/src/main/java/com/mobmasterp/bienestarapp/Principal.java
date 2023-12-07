package com.mobmasterp.bienestarapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.view.menu.MenuView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.mobmasterp.bienestarapp.Modelos.AuthRequest;
import com.mobmasterp.bienestarapp.databinding.ActivityPrincipalBinding;

public class Principal extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPrincipalBinding binding;

    AuthRequest.TokenModel usuarioInfo;
    FuncionesGenerales fg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fg = new FuncionesGenerales(Principal.this);
        fg.WriteSharedPreferences("USUARIO_INFO", getIntent().getStringExtra("Usuario"));
        usuarioInfo = new Gson().fromJson(getIntent().getStringExtra("Usuario"), AuthRequest.TokenModel.class);

        setSupportActionBar(binding.appBarPrincipal.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_prestamo,
                R.id.nav_historial_prestamo,
                R.id.nav_prestamos,
                R.id.nav_crear_usuario,
                R.id.nav_sanciones,
                R.id.nav_mensajes,
                R.id.nav_perfil)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principal);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerView = navigationView.getHeaderView(0);
        TextView txtTituloNavHeader = headerView.findViewById(R.id.txtTituloNavHeader);
        TextView txtDescNavHeader = headerView.findViewById(R.id.txtDescNavHeader);
        TextView txtRolNavHeader = headerView.findViewById(R.id.txtRolNavHeader);

        txtTituloNavHeader.setText(usuarioInfo.getNombre() + " " + usuarioInfo.getApellidos());
        txtDescNavHeader.setText(usuarioInfo.getCorreo_inst());
        txtRolNavHeader.setText(usuarioInfo.getRol());

        Menu menu = navigationView.getMenu();
        if(usuarioInfo.getPrivilegio() == 1){ // Administrador

        }else if(usuarioInfo.getPrivilegio() == 2){ // Instructor
            //menu.findItem(R.id.nav_prestamos).setVisible(false);
        }else{ // Otro - Aprendiz
            //menu.findItem(R.id.nav_prestamos).setVisible(false);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principal);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //fg.WriteSharedPreferences("USUARIO_INFO", "");
    }
}