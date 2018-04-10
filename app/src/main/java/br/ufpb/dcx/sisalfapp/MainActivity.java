package br.ufpb.dcx.sisalfapp;

import android.content.Intent;
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

import br.ufpb.dcx.sisalfapp.sisalfapi.SisalfaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import br.ufpb.dcx.sisalfapp.model.User;

//Activity o qual o usuário é redirecionado a partir da tela de login
public class MainActivity extends AppCompatActivity {

    private TextView tUserName;
    private String userEmail;
    private ImageButton btnUser;
    private ServiceGenerator serviceGenerator = new ServiceGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tUserName = (TextView)findViewById(R.id.user_name);
        btnUser = (ImageButton) findViewById(R.id.button_user);
        //Retorna o usuário atualmente logado
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.userEmail = user.getEmail();

        //Se o usuário não for nulo, existe algum usuário assim, as informações do usuário são recuperadas a seguir, senão se assume que o usuário deslogou, voltando para a tela de login
        if(user != null){
            sendUserToSisalfaApi();
            String name = user.getDisplayName();
            String email = user.getEmail();
            tUserName.setText("Agora você está logado, " + name + "!");
        }else{
            goLoginScreen();
        }
    }


    public void sendUserToSisalfaApi(){
        User u = new User();
        u.setUserEmail(userEmail);
        Log.i("EMAIL", "Email do usuário: " + u.getUserEmail());
        SisalfaService service = serviceGenerator.loadApiCt(this);
        Call<User> request = service.addUser(u);
        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(getApplicationContext(), "Usuário logado com sucesso!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("ERRO", t.getMessage());
            }
        });



    }

    //Método que retorna para tela de login
    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //Dentro da activity_main existe um botão para deslogar, este método é responvável pela ação de deslogar
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }

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
}