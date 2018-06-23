package br.ufpb.dcx.sisalfapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.ufpb.dcx.sisalfapp.model.User;
import br.ufpb.dcx.sisalfapp.sisalfapi.SisalfaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Activity o qual o usuário é redirecionado a partir da tela de authenticate
public class MainActivity extends AppCompatActivity {

    private TextView tvUsername;
    private ServiceGenerator serviceGenerator = new ServiceGenerator();
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvUsername = findViewById(R.id.user_name);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String token = sharedPreferences.getString("token", "");
        tvUsername.setText("Bem vindo, " + username + "!");
        storeUser(token);

        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);







        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    }

    public void storeUser(final String token){
        SisalfaService service = serviceGenerator.loadApiCt(this);
        Call<User> request = service.getUserInformation(token);
        //System.out.println("store user token: " + token);
        //Log.i("USERNAME", "Username: " + username + " Token: " + token);
        final SharedPreferences.Editor s = sharedPreferences.edit();
        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println("Entrou no storeUser parte 1");
                if(response.isSuccessful()){
                    System.out.println("Entrou no storeUser parte 2");
                    s.putString("author", Long.toString(response.body().getId()));
                    s.putString("firstName", response.body().getFirstName().toString());
                    s.putString("lastName", response.body().getLastName().toString());
                    s.putString("email", response.body().getEmail().toString());
                    s.commit();
                    Log.i("USERNAME", "author: " + Long.toString(response.body().getId()) + " First name: " + response.body().getFirstName().toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    //Método que retorna para tela de authenticate
    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    /*
    //Dentro da activity_main existe um botão para deslogar, este método é responvável pela ação de deslogar
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }*/

    public void redirectUserInformation(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    public void redirectChallengeScreen(View view) {
        Intent intent = new Intent(this, ChallengeActivity.class);
        startActivity(intent);
    }

    public void redirectContextoScreen(View view){
        Intent intent = new Intent(this, ContextActivity.class);
        startActivity(intent);
    }

    public void logout(View view){
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor s = sharedPreferences.edit();
        s.clear();
        s.commit();
        startActivity(new Intent (this, LoginActivity.class));
        finish();
    }
}