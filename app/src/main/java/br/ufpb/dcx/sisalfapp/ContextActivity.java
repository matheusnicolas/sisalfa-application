package br.ufpb.dcx.sisalfapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import br.ufpb.dcx.sisalfapp.model.ContextM;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import br.ufpb.dcx.sisalfapp.adapter.ContextListAdapter;

public class ContextActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContextListAdapter contextListAdapter;
    private static final String TAG = "SISALFA_CONTEXTO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contexto);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        contextListAdapter = new ContextListAdapter(this);
        recyclerView.setAdapter(contextListAdapter);
        recyclerView.setHasFixedSize(true);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        obterDados();

    }

    public void obterDados() {
        Call<List<ContextM>> request = SisalfaRetrofitClient.SISALFASERVICE.getAllContexts();
        request.enqueue(new Callback<List<ContextM>>() {
            @Override
            public void onResponse(Call<List<ContextM>> call, Response<List<ContextM>> response) {
                if(response.isSuccessful()){
                    List<ContextM> contexts = response.body();
                    contextListAdapter.adicionarListaContextos(contexts);

                }
            }

            @Override
            public void onFailure(Call<List<ContextM>> call, Throwable t) {

            }
        });
    }


    public void redirectAddContextoScreen(View view) {
        Intent intent = new Intent(this, AddContextActivity.class);
        startActivity(intent);
    }
}
