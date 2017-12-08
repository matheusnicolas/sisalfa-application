package sisalfa.android.com.appsisalfa.sisalfa.android.com.app.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import sisalfa.android.com.appsisalfa.R;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.connection.UrlRequest;
import sisalfa.android.com.appsisalfa.sisalfa.android.com.app.model.User;

//Activity o qual o usuário é redirecionado a partir da tela de login
public class MainActivity extends AppCompatActivity {

    private RequestQueue queueVolley;
    private boolean successRequest;
    private TextView tUserName;
    private UrlRequest urlRequest;
    User usuario = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queueVolley = Volley.newRequestQueue(this);
        successRequest = true;
        tUserName = (TextView)findViewById(R.id.user_name);

        //Retorna o usuário atualmente logado
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Se o usuário não for nulo, existe algum usuário assim, as informações do usuário são recuperadas a seguir, senão se assume que o usuário deslogou, voltando para a tela de login
        if(user != null){
            String name = user.getDisplayName();
            tUserName.setText("Agora você está logado, " + name + "!");
            usuario.setEmail(user.getEmail());
            JsonObjectRequest request = enviarUsuario();
        }else{
            goLoginScreen();
        }

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

    private JsonObjectRequest enviarUsuario(){
        String url = "http://app.sisalfa.dcx.ufpb.br/api/addUser";
        JSONObject usuario = montagemUsuario();
        JsonObjectRequest request = new JsonObjectRequest(
                url,
                usuario,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Você está cadastrado no Sisalfa!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Erro de sincronização com o serviço!", Toast.LENGTH_SHORT).show();
                        Log.i("Request erro", error.toString());
                        successRequest = false;
                    }
                });
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 12000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 12000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        queueVolley.add(request);
        return request;
    }

    private JSONObject montagemUsuario(){
        JSONObject json = new JSONObject();
        try {
            json.put("nome", usuario.getEmail());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

}