package com.astememe.openani.Ventanas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.astememe.openani.Django_Manager.Interfaces.DjangoClient;
import com.astememe.openani.Django_Manager.Models.RoomModel;
import com.astememe.openani.R;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherProfile extends AppCompatActivity {

    ConstraintLayout send_message_button;
    TextView username_otherprofile;

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

        send_message_button = findViewById(R.id.send_message_button);
        username_otherprofile = findViewById(R.id.username_otherprofile);

        send_message_button.setOnClickListener(v -> {
            String otherUserUsername = username_otherprofile.getText().toString();
            iniciarChat(otherUserUsername);
        });
    }

    private void iniciarChat(String otherUsername) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String token = preferences.getString("token", "");

        if (token.isEmpty()) {
            Toast.makeText(this, "Sesión no válida", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> body = new HashMap<>();
        body.put("other_user_username", otherUsername);

        DjangoClient.getMessages_Interface().createRoom("Bearer " + token, body)
                .enqueue(new Callback<RoomModel.RoomDetail>() {
                    @Override
                    public void onResponse(Call<RoomModel.RoomDetail> call, Response<RoomModel.RoomDetail> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            int roomId = response.body().getId();

                            Intent intent = new Intent(OtherProfile.this, Chat.class);
                            intent.putExtra("ROOM_ID", roomId);
                            intent.putExtra("OTHER_USERNAME", otherUsername);
                            startActivity(intent);
                        } else {
                            Log.e("ChatError", "Error código: " + response.code());
                            Toast.makeText(OtherProfile.this, "Error al obtener la sala", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RoomModel.RoomDetail> call, Throwable t) {
                        Log.e("NetworkError", t.getMessage());
                        Toast.makeText(OtherProfile.this, "Sin conexión con el servidor", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}