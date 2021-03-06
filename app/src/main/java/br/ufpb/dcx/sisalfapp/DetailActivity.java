package br.ufpb.dcx.sisalfapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import br.ufpb.dcx.sisalfapp.adapter.ChallengeListAdapter;
import br.ufpb.dcx.sisalfapp.model.Challenge;
import br.ufpb.dcx.sisalfapp.sisalfapi.SisalfaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChallengeListAdapter challengeListAdapter;
    private static final String TAG = "SISALFA_DESAFIO";
    private ServiceGenerator serviceGenerator = new ServiceGenerator();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        final long challengeId = Long.parseLong(intent.getStringExtra("ID"));
        recyclerView = findViewById(R.id.recyclerview);
        challengeListAdapter = new ChallengeListAdapter(this);
        recyclerView.setAdapter(challengeListAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        getChallenges(challengeId);

    }

    public void getChallenges(long challengeId){
        SisalfaService sisalfaService = serviceGenerator.loadApiCt(this);
        Call<List<Challenge>> request = sisalfaService.getChallengeByContext(challengeId);
        request.enqueue(new Callback<List<Challenge>>() {
            @Override
            public void onResponse(Call<List<Challenge>> call, Response<List<Challenge>> response) {
                if(response.isSuccessful()){
                    List<Challenge> listChallenge = response.body();
                    challengeListAdapter.adicionarListaDesafios(listChallenge);

                }
            }

            @Override
            public void onFailure(Call<List<Challenge>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void redirectAddChallengeScreen(View view){
        startActivity(new Intent(this, AddChallengeActivity.class));
    }


}
