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

import sisalfa.android.com.appsisalfa.adapter.ListaContextosAdapter;
import sisalfa.android.com.appsisalfa.retrofinterface.RetrofitInterface;
import sisalfa.android.com.appsisalfa.sisalfapi.SisalfaService;
import sisalfa.android.com.appsisalfa.model.Contexto;

public class ContextoActivity extends AppCompatActivity {

    private RetrofitInterface retrofitInterface;
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
        obterDados();
        retrofitInterface = new RetrofitConcrete();
    }

    public void obterDados() {
        SisalfaService service = retrofitInterface.CallSisalfaApi();
        Call<List<Contexto>> contextos = service.getAllContexts();
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
