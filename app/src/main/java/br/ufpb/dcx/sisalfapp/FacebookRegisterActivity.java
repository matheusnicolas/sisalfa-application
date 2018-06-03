package br.ufpb.dcx.sisalfapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FacebookRegisterActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private EditText etUsername, etPassword;
    private UserRegistration userRegistration = new UserRegistration();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_facebook);


        tvWelcome = findViewById(R.id.welcome_txt);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        tvWelcome.setText("Bem vindo ao Sisalfa, " + user.getDisplayName() + "!");
    }

    public void createAccount(View view){
        boolean pass = true;
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
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
            userRegistration.sendUser(username, password, user.getEmail(), user.getDisplayName(), user.getDisplayName(), getApplicationContext());

            Log.i("USER", "Email: " + user.getEmail() + " Nome: " + user.getDisplayName());

            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            startActivity(new Intent(this, LoginActivity.class));

        }
    }
}
