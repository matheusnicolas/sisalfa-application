package br.ufpb.dcx.sisalfapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufpb.dcx.sisalfapp.adapter.ContextListAdapter;
import br.ufpb.dcx.sisalfapp.model.ContextM;
import br.ufpb.dcx.sisalfapp.sisalfapi.SisalfaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContextActivity extends AppCompatActivity implements ContextListAdapter.OnItemClickListener{

    private ServiceGenerator serviceGenerator = new ServiceGenerator();
    private RecyclerView recyclerView;
    private ContextListAdapter contextListAdapter;
    private static final String TAG = "SISALFA_CONTEXTO";
    private SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contexto);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        contextListAdapter = new ContextListAdapter(this);
        recyclerView.setAdapter(contextListAdapter);
        recyclerView.setHasFixedSize(true);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        contextListAdapter.setOnItemClickListener(ContextActivity.this);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        getContext();
    }

    public void getContext(){
        SisalfaService service = serviceGenerator.loadApiCt(this);
        Call<List<ContextM>> request = service.getAllContexts();
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
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void redirectAddContextoScreen(View view) {
        startActivity(new Intent(this, AddContextActivity.class));
    }

    @Override
    public void onItemClickListener(int position) {
        SharedPreferences.Editor s = sharedPreferences.edit();
        Intent detailIntent = new Intent(this, DetailActivity.class);
        List<ContextM> contextList = contextListAdapter.getAllContexts();
        ContextM clickedItem = contextList.get(position);
        String id = Long.toString(clickedItem.getId());
        s.putString("idContextoDesafio", id);
        s.commit();
        detailIntent.putExtra("ID", id);
        startActivity(detailIntent);
    }
}
