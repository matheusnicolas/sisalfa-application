package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.application;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sisalfa.android.com.appsisalfa.R;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.connection.UrlRequest;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model.User;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.sisalfapi.UsuarioService;

//Activity o qual o usuário é redirecionado a partir da tela de login
public class MainActivity extends AppCompatActivity {

    private TextView tUserName;
    private TextView tUserEmail;
    private String userEmail;
    private ImageButton btnUser;
    private static UrlRequest urlT = new UrlRequest();
    private static final String BASE_URL = urlT.getTesteLocal();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tUserName = (TextView)findViewById(R.id.user_name);
        tUserEmail = (TextView)findViewById(R.id.user_email);
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        UsuarioService service = retrofit.create(UsuarioService.class);

        User u = new User();
        u.setUserEmail(userEmail);
        Log.i("EMAIL", "Email do usuário: " + u.getUserEmail());

        Call<Boolean> usuario = service.insertUser(u);
        usuario.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()){
                        Toast.makeText(getApplicationContext(), "Usuário cadastrado com Sucesso!",  Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Usuário não foi cadastrado!", Toast.LENGTH_LONG).show();
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

    public void UserInformation(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    public void challengeScreen(View view) {
        Intent intent = new Intent(this, DesafioActivity.class);
        startActivity(intent);
    }

    public void contextoScreen(View view){
        Intent intent = new Intent(this, ContextoActivity.class);
        startActivity(intent);
    }
}