package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sisalfa.android.com.appsisalfa.R;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.sisalfapi.DesafioService;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model.Desafio;

public class AddDesafioActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://192.168.0.111:8080/meuProjetoWeb/webapi/";
    private EditText ePalavra;
    private EditText eLink;
    private EditText eUsuario;
    private Button btnCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_desafio);

        ePalavra = (EditText)findViewById(R.id.palavra);
        eLink = (EditText)findViewById(R.id.link);
        eUsuario = (EditText)findViewById(R.id.usuario);
        btnCadastro = (Button)findViewById(R.id.btn_cadastrar);
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String palavra = ePalavra.getText().toString();
                String link = eLink.getText().toString();
                String usuario = eUsuario.getText().toString();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

                DesafioService service = retrofit.create(DesafioService.class);
                Desafio d = new Desafio();
                d.setPalavra_desafio(palavra);
                d.setId_usuario(usuario);
                d.setImagem(link);
                Call<Boolean> challenge = service.insertDesafio(d);
                challenge.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()){
                            if(response.body()){
                                Toast.makeText(getApplicationContext(), "Cadastrado com Sucesso!",  Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Não foi cadastrado!", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Erro: " + response.code(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });
            }
        });


    }
}
