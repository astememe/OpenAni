package com.astememe.openani.Ventanas;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astememe.openani.API_Manager.API_Client;
import com.astememe.openani.API_Manager.API_Interface;
import com.astememe.openani.API_Manager.Data;
import com.astememe.openani.Adaptador_Evento.TorrentModel;
import com.astememe.openani.R;
import com.astememe.openani.Adaptador_Evento.TorrentAdapter;
import com.astememe.openani.RecyclerView.RecyclerViewAdapter;
import com.bumptech.glide.load.model.Model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Arrays;
import java.util.List;

public class MainAnime extends AppCompatActivity {
    public ArrayList<TorrentModel> torrentModelArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    EditText barra_busqueda;

    ImageView barra_lateral_icono;
    LayoutInflater inflador_menu_lateral;
    LinearLayout contenedor_menu_lateral;
    LinearLayout sombra_menu_lateral;
    View menu_lateral;
    ImageView cerrar_menu_lateral;

    TextView header_categoria;
    TextView header_subcategoria;

    TextView anime;
    TextView anime_music_video;
    TextView anime_non_english;
    TextView anime_original;
    TextView manga;
    TextView manga_english;
    TextView manga_non_english;
    TextView manga_original;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fillItems();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_anime);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.torrentRecycle);
        barra_busqueda = findViewById(R.id.searchbar_anime);

        barra_lateral_icono = findViewById(R.id.side_nav_main_anime);
        contenedor_menu_lateral = findViewById(R.id.contenedormenulateral);
        sombra_menu_lateral = findViewById(R.id.sombramenulateral);
        header_categoria = findViewById(R.id.categoria);
        header_subcategoria = findViewById(R.id.subcategoria);

        sombra_menu_lateral.setVisibility(INVISIBLE);

        inflador_menu_lateral = LayoutInflater.from(this);

        barra_lateral_icono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sombra_menu_lateral.setVisibility(VISIBLE);
                contenedor_menu_lateral.removeAllViews();
                menu_lateral = inflador_menu_lateral.inflate(R.layout.menu_lateral, contenedor_menu_lateral, true);
                Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_from_left);
                Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_from_right);
                menu_lateral.setAnimation(slide_in);
                cerrar_menu_lateral = menu_lateral.findViewById(R.id.cerrar_menu_lateral);
                anime = menu_lateral.findViewById(R.id.anime);
                manga = menu_lateral.findViewById(R.id.manga);


                List<TextView> subcategorias_anime = new ArrayList<>(Arrays.asList(
                    anime_music_video = menu_lateral.findViewById(R.id.music_video),
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

                anime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        header_categoria.setText("Anime");
                        header_subcategoria.setText("Most Recent");
                        cerrar_menu_lateral(slide_out);
                    }
                });

                manga.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        header_categoria.setText("Manga");
                        header_subcategoria.setText("Most Recent");
                        cerrar_menu_lateral(slide_out);
                    }
                });


                for (TextView subcategoria: subcategorias_anime) {
                    subcategoria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            header_categoria.setText("Anime");
                            header_subcategoria.setText(subcategoria.getText().toString());
                            cerrar_menu_lateral(slide_out);
                        }
                    });
                }

                for (TextView subcategoria: subcategorias_manga) {
                    subcategoria.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            header_categoria.setText("Manga");
                            header_subcategoria.setText(subcategoria.getText().toString());
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

    }

    private void filtrarTexto(String texto, TorrentAdapter adapter) {
        ArrayList<TorrentModel> filtrados = new ArrayList<>();

        for (TorrentModel item : torrentModelArrayList) {
            if (item.getTitulo_torrent().toLowerCase().contains(texto.toLowerCase())) {
                filtrados.add(item);
            }
        }

        adapter.filtrar(filtrados);
    }

    public void fillItems() {
        API_Interface appInterface = API_Client.getClient().create(API_Interface.class);
        Call<Data> call = appInterface.getRecent(null);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {

                List<Data.Torrent> items = (List<Data.Torrent>) response.body();

                for (Data.Torrent item : items) {
                    torrentModelArrayList.add(
                            new TorrentModel(
                                    item.titulo,
                                    item.tamano,
                                    item.fecha,
                                    item.seeders,
                                    item.leechers,
                                    item.enlace
                            )
                    );
                }

                TorrentAdapter adapter = new TorrentAdapter(getApplicationContext(), torrentModelArrayList);
                GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);

                barra_busqueda.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                    @Override
                    public void afterTextChanged(Editable s) {
                        filtrarTexto(s.toString(), adapter);
                    }
                });
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("Error!!", t.toString());
            }
        });

    }
}