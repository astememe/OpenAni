package com.astememe.openani.Ventanas;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.astememe.openani.Django_Manager.Interfaces.DjangoClient;
import com.astememe.openani.Django_Manager.Models.ConfirmPasswordResetModel;
import com.astememe.openani.Django_Manager.Models.RequestPasswordResetModel;
import com.astememe.openani.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperarContrasenaView extends AppCompatActivity {

    EditText correo_recuperar, codigo_recuperar, password_recuperar;
    ConstraintLayout generar_codigo;
    ConstraintLayout aceptar_recuperar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperar_contrasena);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        correo_recuperar = findViewById(R.id.correo_recuperar);
        codigo_recuperar = findViewById(R.id.codigo_recuperar);
        password_recuperar = findViewById(R.id.password_recuperar);
        aceptar_recuperar = findViewById(R.id.aceptar_recuperar);
        generar_codigo = findViewById(R.id.generar_code);

        aceptar_recuperar.setVisibility(INVISIBLE);
        password_recuperar.setVisibility(INVISIBLE);


        generar_codigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correo_recuperar.getText().toString().isEmpty()) {
                    correo_recuperar.setError("Por favor, introduzca su email");
                    return;
                }

                password_recuperar.setVisibility(VISIBLE);
                aceptar_recuperar.setVisibility(VISIBLE);

                RequestPasswordResetModel requestPasswordResetModel = new RequestPasswordResetModel(correo_recuperar.getText().toString());
                DjangoClient.getResetPasswordAPI_Interface().requestReset(requestPasswordResetModel).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject respuesta = new JSONObject(response.body().string());
                                codigo_recuperar.setVisibility(VISIBLE);
                                generar_codigo.setVisibility(INVISIBLE);
                                Log.d("Código", respuesta.getString("code"));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                JSONObject errores = new JSONObject(response.errorBody().string());
                                if (errores.has("email")) {
                                    correo_recuperar.setError(errores.getString("email"));
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });
            }
        });

        aceptar_recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codigo_recuperar.getText().toString().isEmpty()) {
                    codigo_recuperar.setError("Por favor, introduzca su código");
                    return;
                }
                if (password_recuperar.getText().toString().isEmpty()) {
                    password_recuperar.setError("Por favor, introduzca la nueva contraseña");
                    return;
                }

                ConfirmPasswordResetModel confirmPasswordResetModel = new ConfirmPasswordResetModel(correo_recuperar.getText().toString(), password_recuperar.getText().toString(), Integer.parseInt(codigo_recuperar.getText().toString()));
                DjangoClient.getResetPasswordAPI_Interface().confirmReset(confirmPasswordResetModel).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            try {
                                JSONObject errores = new JSONObject(response.errorBody().string());
                                if (errores.has("code")) {
                                    codigo_recuperar.setError(errores.getString("code"));
                                }
                                if (errores.has("password")) {
                                    password_recuperar.setError(errores.getString("password"));
                                }
                                if (errores.has("email")) {
                                    correo_recuperar.setError(errores.getString("email"));
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });
            }
        });

    }
}