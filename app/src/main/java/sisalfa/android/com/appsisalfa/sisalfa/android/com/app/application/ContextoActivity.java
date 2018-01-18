package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.sisalfapi.ContextoService;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model.Contexto;

public class ContextoActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://192.168.0.115:8080/meuProjetoWeb/webapi/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contexto);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        ContextoService contexto = retrofit.create(ContextoService.class);
        Call<List<Contexto>> contextos = contexto.getContextos();
        contextos.enqueue(new Callback<List<Contexto>>() {
            @Override
            public void onResponse(Call<List<Contexto>> call, Response<List<Contexto>> response) {
                if(response.isSuccessful()){
                    List<Contexto> contexts = response.body();
                    for(Contexto c: contexts){
                        Log.i("CONTEXTO", "Contexto: " + c.getPalavra_contexto() + " ID: " + c.getId());
                        Log.i("CONTEXTO", "----------------------´-----------------´---------");
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Erro: " + response.errorBody(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Contexto>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    public void addContextoScreen(View view) {
        Intent intent = new Intent(this, AddContextoActivity.class);
        startActivity(intent);

    }
}
