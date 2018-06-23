package br.ufpb.dcx.sisalfapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class RegisterOptionActivity extends AppCompatActivity {


    private LoginButton btnLogin;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private UserRegistration userRegistration = new UserRegistration();
    private ProgressBar progressBar;
    private EditText etName, etLastname, etEmail, etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_registration);
        etName = findViewById(R.id.name);
        etLastname = findViewById(R.id.last_name);
        etEmail = findViewById(R.id.email);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);

        callbackManager = CallbackManager.Factory.create();
        btnLogin = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.progressBar);
        btnLogin.setReadPermissions(Arrays.asList("email", "public_profile"));
        btnLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken.getCurrentAccessToken().getPermissions();
                handleFacebookAccessToken(loginResult.getAccessToken());
                //loginResult.getAccessToken().getPermissions();

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    goFacebookRegisterActivity();
                }
            }
        };
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), R.string.firebase_error_login, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void goFacebookRegisterActivity() {
        Intent intent = new Intent(this, FacebookRegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }


    public void authServiceWithoutFacebook(View view) {
        boolean pass = true;
        String firstName = etName.getText().toString();
        String lastName = etLastname.getText().toString();
        String email = etEmail.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        View focus = null;

        if(TextUtils.isEmpty(firstName)){
            etName.setError("Campo Vazio!");
            focus = etName;
            focus.requestFocus();
            pass = false;
        }

        if(TextUtils.isEmpty(lastName)){
            etLastname.setError("Campo Vazio!");
            focus = etLastname;
            focus.requestFocus();
            pass = false;
        }

        if(TextUtils.isEmpty(email)){
            etEmail.setError("Campo Vazio!");
            focus = etEmail;
            focus.requestFocus();
            pass = false;
        }

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
            userRegistration.sendUser(username, password, email, firstName, lastName, getApplicationContext());

            Log.i("LOGIN", username + " " +  password  + " " + email + " " + lastName);
            startActivity(new Intent(this, LoginActivity.class));

        }





    }
}

