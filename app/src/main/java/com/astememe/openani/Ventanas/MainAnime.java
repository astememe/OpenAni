package com.astememe.openani.Ventanas;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.API_Manager.APIClient;
import com.astememe.openani.API_Manager.DataModel;
import com.astememe.openani.Django_Manager.Interfaces.DjangoClient;
import com.astememe.openani.Django_Manager.Models.TorrentsModel;
import com.astememe.openani.R;
import com.astememe.openani.Adaptador_Evento.TorrentAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainAnime extends AppCompatActivity {

    TorrentAdapter adapter;
    EditText busqueda;
    List<DataModel.Torrent> torrentList = new ArrayList<>();
    List<TorrentsModel.TorrentBBDD> torrentsbbdd = new ArrayList<>();
    RecyclerView torrentRecycle;
    ImageView barra_lateral_icono;
    LayoutInflater inflador_menu_lateral;
    LinearLayout contenedor_menu_lateral;
    LinearLayout sombra_menu_lateral;
    Boolean menu_lateral_visible = false;
    View menu_lateral;
    ImageView cerrar_menu_lateral;

    TextView header_categoria;
    TextView header_subcategoria;

    TextView anime;
    TextView anime_music_video;
    TextView anime_english;
    TextView anime_non_english;
    TextView anime_original;
    TextView manga;
    TextView manga_english;
    TextView manga_non_english;
    TextView manga_original;
    ImageView foto_perfil;
    TextView nombre_usuario;
    ImageButton boton_descargar;

    Handler buscarDelayer = new Handler();
    Runnable buscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_anime);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Boolean esInvitado = esInvitado();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String nombre_de_usuario = preferences.getString("nombre", "Invitado");

        torrentRecycle = findViewById(R.id.torrentRecycle);
        fillTorrents("anime");
        torrentRecycle.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new TorrentAdapter(this, torrentList);
        torrentRecycle.setAdapter(adapter);
        busqueda = findViewById(R.id.searchbar_anime);

        boton_descargar = findViewById(R.id.imagebutton_download);

        barra_lateral_icono = findViewById(R.id.side_nav_main_anime);
        contenedor_menu_lateral = findViewById(R.id.contenedormenulateral);
        sombra_menu_lateral = findViewById(R.id.sombramenulateral);
        header_categoria = findViewById(R.id.categoria);
        header_subcategoria = findViewById(R.id.subcategoria);

        header_categoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DjangoClient.getAPI_Interface().getTorrents().enqueue(new Callback<TorrentsModel>() {
                    @Override
                    public void onResponse(Call<TorrentsModel> call, Response<TorrentsModel> response) {
                        torrentsbbdd.clear();
                        torrentsbbdd.addAll(response.body().torrentsbbdd);
                        Log.d("Respuesta", response.body().torrentsbbdd.toString());
                    }

                    @Override
                    public void onFailure(Call<TorrentsModel> call, Throwable t) {
                        Log.e("Error_API", "Causa real: " + t.getMessage());
                        t.printStackTrace();
                    }
                });
                for (TorrentsModel.TorrentBBDD torrent: torrentsbbdd) {
                    Log.d("Respuesta", torrent.getEnlace_bbdd());
                }
            }
        });

        sombra_menu_lateral.setVisibility(INVISIBLE);

        inflador_menu_lateral = LayoutInflater
                .from(this);


        barra_lateral_icono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_lateral_visible = !menu_lateral_visible;
                sombra_menu_lateral.setVisibility(VISIBLE);
                contenedor_menu_lateral.removeAllViews();
                menu_lateral = inflador_menu_lateral.inflate(R.layout.menu_lateral, contenedor_menu_lateral, true);
                Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_from_left);
                Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_from_right);
                menu_lateral.setAnimation(slide_in);
                cerrar_menu_lateral = menu_lateral.findViewById(R.id.cerrar_menu_lateral);
                anime = menu_lateral.findViewById(R.id.anime);
                manga = menu_lateral.findViewById(R.id.manga);
                foto_perfil = menu_lateral.findViewById(R.id.imagen_perfil);
                nombre_usuario = menu_lateral.findViewById(R.id.nombre_usuario);

                if (esInvitado()) {
                    nombre_usuario.setText("Invitado");
                    foto_perfil.setImageResource(R.drawable.foto_perfil_default);
                } else {
                    nombre_usuario.setText(nombre_de_usuario);
                    foto_perfil.setImageURI(Uri.parse("android.resource://" + getPackageName() + "/drawable/foto_de_perfil_" + preferences.getString("imagen", "")));
                }


                sombra_menu_lateral.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        menu_lateral_visible = !menu_lateral_visible;
                        cerrar_menu_lateral(slide_out);
                    }
                });


                List<TextView> subcategorias_anime = new ArrayList<>(Arrays.asList(
                    anime_music_video = menu_lateral.findViewById(R.id.music_video),
                    anime_english = menu_lateral.findViewById(R.id.anime_english),
                    anime_non_english = menu_lateral.findViewById(R.id.anime_non_english),
                    anime_original = menu_lateral.findViewById(R.id.anime_original)
                ));

                List<TextView> subcategorias_manga = new ArrayList<>(Arrays.asList(
                    manga_english = menu_lateral.findViewById(R.id.manga_english),
                    manga_non_english = menu_lateral.findViewById(R.id.manga_non_english),
                    manga_original = menu_lateral.findViewById(R.id.manga_original)
                ));


                cerrar_menu_lateral.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cerrar_menu_lateral(slide_out);
                    }
                });

                foto_perfil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (esInvitado()) {
                            errorInvitado();
                        } else {
                            Intent intent = new Intent(MainAnime.this, AccountView.class);
                            startActivity(intent);
                        }
                    }
                });

                anime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        header_categoria.setText("Anime");
                        header_subcategoria.setText("Most Recent");
                        fillTorrents("anime");
                        cerrar_menu_lateral(slide_out);
                    }
                });

                manga.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        header_categoria.setText("Manga");
                        header_subcategoria.setText("Most Recent");
                        fillTorrents("manga");
                        cerrar_menu_lateral(slide_out);
                    }
                });


                for (TextView subcategoria: subcategorias_anime) {
                    subcategoria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String textoSub = subcategoria.getText().toString();
                            header_categoria.setText("Anime");
                            header_subcategoria.setText(textoSub);
                            filtrarPorNombre(busqueda.getText().toString(), "anime", textoSub, adapter);
                            cerrar_menu_lateral(slide_out);
                        }
                    });
                }

                for (TextView subcategoria: subcategorias_manga) {
                    subcategoria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String textoSub = subcategoria.getText().toString();
                            header_categoria.setText("Manga");
                            header_subcategoria.setText(textoSub);
                            filtrarPorNombre(busqueda.getText().toString(), "manga", textoSub, adapter);
                            cerrar_menu_lateral(slide_out);
                        }
                    });
                }
            }

            public void cerrar_menu_lateral(Animation slide_out) {
                menu_lateral.setAnimation(slide_out);
                sombra_menu_lateral.setVisibility(INVISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        contenedor_menu_lateral.removeAllViews();
                    }
                }, slide_out.getDuration());
            }
        });

        busqueda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                buscar = new Runnable() {
                    @Override
                    public void run() {
                        filtrarPorNombre(s.toString(), header_categoria.getText().toString().toLowerCase(), header_subcategoria.getText().toString(), adapter);;
                    }
                };
                buscarDelayer.postDelayed(buscar, 800);
            }
        });
    }
    private void fillTorrents(String categoria) {
        APIClient.getAPI_Interface().getByCategory(categoria).enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                torrentList.clear();
                if (response.isSuccessful() && response.body() != null) {
                    torrentList.addAll(response.body().torrents);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filtrarPorNombre(String texto, String categoria, String subcategoria, TorrentAdapter adapter) {
        torrentList.clear();
        if (texto.isEmpty() && subcategoria == "most recent") {
            fillTorrents(categoria.toLowerCase());
            return;
        }
        if (convertirSubcategoria(subcategoria).isEmpty()) {
            APIClient.getAPI_Interface().getByNameandCategory(Map.of("q", texto, "category", categoria.toLowerCase())).enqueue(new Callback<DataModel>() {
                @Override
                public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                    torrentList.clear();
                    if (response.isSuccessful() && response.body() != null) {
                        torrentList.addAll(response.body().torrents);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<DataModel> call, Throwable t) {
                    Log.d("Error", "Error");
                }
            });
        } else {
            APIClient.getAPI_Interface().getByNameandCategoryandSubCategory(Map.of("q", texto, "category", categoria, "sub_category", convertirSubcategoria(subcategoria), "sort", "seeders", "order", "desc")).enqueue(new Callback<DataModel>() {
                @Override
                public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                    Log.d("Respuesta", response.toString());
                    torrentList.clear();
                    if (response.isSuccessful() && response.body() != null) {
                        torrentList.addAll(response.body().torrents);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<DataModel> call, Throwable t) {
                    Log.d("Error", "Error");
                }
            });
        }
    }

    private String convertirSubcategoria(String header_subcategoria) {
        String subcategoria = "";
        if (header_subcategoria.equals("Music Video")) {
            subcategoria = "amv";
        } else if (header_subcategoria.equals("English")) {
            subcategoria = "eng";
        } else if (header_subcategoria.equals("Non English")) {
            subcategoria = "non-eng";
        } else if (header_subcategoria.equals("Original")) {
            subcategoria = "raw";
        }

        return subcategoria;
    }

    private boolean esInvitado() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getBoolean("invitado", false);
    }

    private void errorInvitado() {
        new AlertDialog.Builder(this)
                .setTitle("Logged in as Guest")
                .setMessage("Please log in or make an account.")
                .setIcon(R.drawable.alert)
                .setPositiveButton("Registrarse", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainAnime.this, RegisterView.class);
                        startActivity(intent);
                    }
                })
                .setNeutralButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainAnime.this, LoginView.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Close", null)
                .show();
    }
}