package com.astememe.openani.Ventanas;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.astememe.openani.Django_Manager.Interfaces.DjangoClient;
import com.astememe.openani.Django_Manager.Models.UserDataModel;
import com.astememe.openani.R;

import java.util.List;

import io.woong.shapedimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherProfile extends AppCompatActivity {
    CircleImageView foto_perfil;
    TextView descripcion;
    TextView nombre_usuario;
    CardView send_message;
    TextView title;

    ConstraintLayout flecha_atras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_other_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String other_username = getIntent().getExtras().getString("other_username");
        String token = "Bearer " + sharedPreferences.getString("token", "");

        foto_perfil = this.findViewById(R.id.fotoperfil);
        descripcion = this.findViewById(R.id.descripcion);
        nombre_usuario = this.findViewById(R.id.username);
        title = this.findViewById(R.id.title);
        flecha_atras = this.findViewById(R.id.flechaAtrasAcountView);

        flecha_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DjangoClient.getUserAPI_Interface().getProfiles(token, other_username).enqueue(new Callback<List<UserDataModel.UserData>>() {
            @Override
            public void onResponse(Call<List<UserDataModel.UserData>> call, Response<List<UserDataModel.UserData>> response) {

                UserDataModel.UserData user = response.body().get(0);
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/drawable/foto_de_perfil_" + user.getImagen());
                foto_perfil.setImageURI(uri);
                nombre_usuario.setText(user.getUsername());
                title.setText(user.getUsername());
                descripcion.setText(user.getDescripcion());

                Log.d("Nombre otro usuario", user.getUsername());
                Log.d("Foto otro usuario", user.getImagen());
                Log.d("Descripción", user.getDescripcion());
            }

            @Override
            public void onFailure(Call<List<UserDataModel.UserData>> call, Throwable t) {

            }
        });
    }
}