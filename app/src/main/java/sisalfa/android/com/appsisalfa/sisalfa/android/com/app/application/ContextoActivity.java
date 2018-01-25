package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sisalfa.android.com.appsisalfa.R;

import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.adapter.ListaContextosAdapter;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.deserializer.ContextoDec;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.sisalfapi.ContextoService;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model.Contexto;

public class ContextoActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://192.168.0.100:8080/meuProjetoWeb/webapi/";
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private ListaContextosAdapter listaContextosAdapter;
    private static final String TAG = "SISALFA_CONTEXTO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contexto);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        listaContextosAdapter = new ListaContextosAdapter(this);
        recyclerView.setAdapter(listaContextosAdapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        Gson g = new GsonBuilder().registerTypeAdapter(Contexto.class, new ContextoDec()).create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        obterDados();


    }

    public void obterDados() {

        ContextoService contexto = retrofit.create(ContextoService.class);
        Call<List<Contexto>> contextos = contexto.getContextos();
        contextos.enqueue(new Callback<List<Contexto>>() {
            @Override
            public void onResponse(Call<List<Contexto>> call, Response<List<Contexto>> response) {
                if(response.isSuccessful()){
                    List<Contexto> contexts = response.body();
                    listaContextosAdapter.adicionarListaContextos(contexts);

                }
            }

            @Override
            public void onFailure(Call<List<Contexto>> call, Throwable t) {

            }
        });
    }


    public void addContextoScreen(View view) {
        Intent intent = new Intent(this, AddContextoActivity.class);
        startActivity(intent);

    }
}
