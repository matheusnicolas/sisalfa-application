package br.ufpb.dcx.sisalfapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import br.ufpb.dcx.sisalfapp.ServiceGenerator;
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
                    Toast.makeText(context.getApplicationContext(), "Já existe um usuário com esse email ou houve um erro no sistema, desculpa!: " + response.code(), Toast.LENGTH_LONG).show();
                    Log.i("ENTROU1", "1");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("ENTROU2", "2");
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



}
