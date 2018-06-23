package br.ufpb.dcx.sisalfapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import br.ufpb.dcx.sisalfapp.ServiceGenerator;
import br.ufpb.dcx.sisalfapp.model.Token;
import br.ufpb.dcx.sisalfapp.model.User;
import br.ufpb.dcx.sisalfapp.sisalfapi.SisalfaService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by matheusnicolas on 03/06/2018.
 */

public class UserRegistration {

    private ServiceGenerator serviceGenerator = new ServiceGenerator();
    private long authorId;
    private SharedPreferences sharedPreferences;

    public void sendUser(String username, String password, String email, String firstName, String lastName, final Context context){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        SisalfaService service = serviceGenerator.loadApiCt(context);
        Call<User> request = service.addUser(user);
        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        Toast.makeText(context.getApplicationContext(), "Usuário Cadastrado com Sucesso!", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(context.getApplicationContext(), "Já existe um usuário com esse email ou houve um erro no sistema, desculpa!: " + response.code() + " Response: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public long getUser(final String username, final Context context){
        SisalfaService service = serviceGenerator.loadApiCt(context);
        Call<User> request = service.getUser(username);
        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    authorId =  response.body().getId();
                    Toast.makeText(context.getApplicationContext(), "Bem vindo, " + username + "!", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return authorId;
    }

    public void getUserInformation(final Context context, final String token, final String username, final String password){
        SisalfaService service = serviceGenerator.loadApiCt(context);
        Call<User> request = service.getUserInformation(token);
        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    SharedPreferences.Editor s = sharedPreferences.edit();
                    s.putString("username", username);
                    s.putString("password", password);
                    s.putString("token", token);
                    s.putString("author", Long.toString(response.body().getId()));
                    s.putString("firstName", response.body().getFirstName().toString());
                    s.putString("lastName", response.body().getLastName().toString());
                    s.putString("email", response.body().getEmail().toString());
                    s.commit();
                    Log.i("USERNAME", "username listado: " + username + token + response.body().getFirstName().toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }



}
