package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sisalfa.android.com.appsisalfa.R;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.adapter.ListaDesafiosAdapter;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.deserializer.DesafioDec;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.sisalfapi.DesafioService;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model.Desafio;

public class DesafioActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://192.168.0.100:8080/meuProjetoWeb/webapi/";
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private ListaDesafiosAdapter listaDesafiosAdapter;
    private static final String TAG = "SISALFA_DESAFIO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        listaDesafiosAdapter = new ListaDesafiosAdapter(this);
        recyclerView.setAdapter(listaDesafiosAdapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);


        Gson g = new GsonBuilder().registerTypeAdapter(Desafio.class, new DesafioDec()).create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(g)).build();
        obterDados();
    }

    public void obterDados(){
        DesafioService service = retrofit.create(DesafioService.class);
        Call<List<Desafio>> desafios = service.getDesafios();
        desafios.enqueue(new Callback<List<Desafio>>() {
            @Override
            public void onResponse(Call<List<Desafio>> call, Response<List<Desafio>> response) {
                if(response.isSuccessful()){
                    List<Desafio> listaDesafio = response.body();
                    listaDesafiosAdapter.adicionarListaDesafios(listaDesafio);
                }
            }

            @Override
            public void onFailure(Call<List<Desafio>> call, Throwable t) {

            }
        });
    }

    public void addChallengeScreen(View view) {
        Intent intent = new Intent(this, AddDesafioActivity.class);
        startActivity(intent);

    }
}
