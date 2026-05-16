package com.astememe.openani.Ventanas;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.Adaptador_Evento.ChatAdapter;
import com.astememe.openani.Django_Manager.Models.MessageModel;
import com.astememe.openani.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class Chat extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText inputMensaje;
    private ConstraintLayout btnEnviar, flecha_atras;
    private List<MessageModel.MessageDetail> listaMensajes = new ArrayList<>();
    private WebSocket webSocket;
    private String roomId;
    private String miUsuario;
    private ChatAdapter adapter;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        roomId = getIntent().getStringExtra("ROOM_ID");
        String other_username = getIntent().getStringExtra("other_username");
        miUsuario = preferences.getString("nombre", "");

        recyclerView = findViewById(R.id.chat_messages_recycler_view);
        inputMensaje = findViewById(R.id.escribir_mensaje);
        title = findViewById(R.id.username_chat);
        title.setText(other_username);
        btnEnviar = findViewById(R.id.send_button);
        flecha_atras = findViewById(R.id.flechaAtrasAcountView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(this, listaMensajes, miUsuario);
        recyclerView.setAdapter(adapter);

        flecha_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iniciarWebSocket();
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensaje();
            }
        });
    }

    private void iniciarWebSocket() {
        OkHttpClient client = new OkHttpClient();

//        String urlWebSocket = "ws://25.43.166.83:8000/ws/chat/" + roomId + "/";

        String urlWebSocket = "ws://10.0.2.2:8000/ws/chat/" + roomId + "/";

        Request request = new Request.Builder()
                .url(urlWebSocket)
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                Log.d("WebSocket", "Websocket conectado");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recibirMensajeDelServidor(text);
                    }
                });
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
                Log.e("WebSocket", "Error en la conexión: " + t.getMessage());
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                Log.d("WebSocket", "Cerrando conexión: " + reason);
            }
        });
    }

    private void enviarMensaje() {
        String texto = inputMensaje.getText().toString();

        if (!texto.isEmpty() && webSocket != null) {
            try {
                JSONObject json = new JSONObject();
                json.put("message", texto);
                json.put("sender_username", miUsuario);

                webSocket.send(json.toString());
                inputMensaje.setText("");

            } catch (JSONException e) {
                Log.e("WebSocket", "Error al armar el JSON de envío", e);
            }
        }
    }

    private void recibirMensajeDelServidor(String jsonString) {
        try {
            JSONObject json = new JSONObject(jsonString);

            String emisor = json.getString("sender_username");
            String contenido = json.getString("message");
            String fecha = json.getString("timestamp");

            MessageModel.MessageDetail nuevoMensaje = new MessageModel.MessageDetail(emisor, contenido, fecha);
            listaMensajes.add(nuevoMensaje);

            adapter.notifyItemInserted(listaMensajes.size() - 1);
            recyclerView.scrollToPosition(listaMensajes.size() - 1);

        } catch (JSONException e) {
            Log.e("WebSocket", "No se pudo recibir el mensaje", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webSocket != null) {
            webSocket.close(1000, "Websocket cerrado");
        }
    }
}