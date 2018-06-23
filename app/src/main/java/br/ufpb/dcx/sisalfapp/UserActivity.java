package br.ufpb.dcx.sisalfapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity {

    private TextView tName;
    private TextView tEmail;
    private TextView authorId;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        tName = findViewById(R.id.user_name);
        tEmail = findViewById(R.id.user_email);
        authorId = findViewById(R.id.user_uid);
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String name = sharedPreferences.getString("firstName", "");
        String email = sharedPreferences.getString("email", "");
        String uid = sharedPreferences.getString("author", "");

        tName.setText(name);
        tEmail.setText(email);
        authorId.setText(uid);
    }
}
