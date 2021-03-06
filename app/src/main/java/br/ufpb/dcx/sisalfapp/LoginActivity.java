package br.ufpb.dcx.sisalfapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import br.ufpb.dcx.sisalfapp.model.Login;
import br.ufpb.dcx.sisalfapp.model.Token;
import br.ufpb.dcx.sisalfapp.sisalfapi.SisalfaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText etUsername, etPassword;
    private ServiceGenerator serviceGenerator = new ServiceGenerator();
    private String token;
    private SharedPreferences sharedPreferences;
    //private UserRegistration userRegistration = new UserRegistration();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestPermission();
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress_bar);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        if(sharedPreferences.contains("username") && sharedPreferences.contains("password")){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }


    public void redirectRegistrationScreen(View view) {
        startActivity(new Intent(this, RegisterOptionActivity.class));
    }

    public void login(View view) {
        boolean pass = true;
        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();
        View focus = null;
        if(TextUtils.isEmpty(username)){
            etUsername.setError("Campo Vazio!");
            focus = etUsername;
            focus.requestFocus();
            pass = false;

        }
        if(TextUtils.isEmpty(password)){
            etPassword.setError("Campo Vazio!");
            focus = etPassword;
            focus.requestFocus();
            pass = false;
        }
        if(pass == true){
            Toast.makeText(getApplicationContext(), "Autenticando!", Toast.LENGTH_SHORT).show();
            validateUserToLogin(username, password);



        }
    }

    public void validateUserToLogin(final String username, final String password) {

        Login login = new Login(username, password);
        SisalfaService service = serviceGenerator.loadApiCt(this);
        Call<Token> request = service.authenticate(login) ;
        request.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()){
                    token = response.body().getToken();
                    successful(username, password, token);
                    Log.i("TOKEN", token);
                }else{
                    Toast.makeText(getApplicationContext(), "Login incorreto!", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void successful(String username, String password, String token){
        storeUserLoginOnSharedPreferences(username, password, token);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void storeUserLoginOnSharedPreferences(String username, String password, String token) {
        SharedPreferences.Editor s = sharedPreferences.edit();
        s.putString("username", username);
        s.putString("password", password);
        s.putString("token", token);
        s.commit();


    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, 1);
    }


}




