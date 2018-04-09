package br.ufpb.dcx.sisalfapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import br.ufpb.dcx.sisalfapp.model.Challenge;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import br.ufpb.dcx.sisalfapp.adapter.ChallengeListAdapter;

public class ChallengeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChallengeListAdapter challengeListAdapter;
    private static final String TAG = "SISALFA_DESAFIO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafio);
        new SisalfaRetrofitClient().loadAPI();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        challengeListAdapter = new ChallengeListAdapter(this);
        recyclerView.setAdapter(challengeListAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        getData();
    }

    public void getData(){
        Call<List<Challenge>> request = SisalfaRetrofitClient.SISALFASERVICE.getAllChallenges();
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

            }
        });
    }

    public void redirectAddChallengeScreen(View view) {
        Intent intent = new Intent(this, AddChallengeActivity.class);
        startActivity(intent);

    }
}
