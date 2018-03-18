package sisalfa.android.com.appsisalfa.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sisalfa.android.com.appsisalfa.R;
import sisalfa.android.com.appsisalfa.adapter.ListaDesafiosAdapter;
import sisalfa.android.com.appsisalfa.model.Desafio;
import sisalfa.android.com.appsisalfa.retrofinterface.RetrofitInterface;
import sisalfa.android.com.appsisalfa.sisalfapi.SisalfaService;

public class DesafioActivity extends AppCompatActivity {

    private RetrofitInterface retrofitInterface;
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
        retrofitInterface = new RetrofitConcrete();
        obterDados();
    }

    public void obterDados(){
        SisalfaService service = retrofitInterface.CallSisalfaApi();
        Call<List<Desafio>> desafios = service.getAllChallenges();
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
