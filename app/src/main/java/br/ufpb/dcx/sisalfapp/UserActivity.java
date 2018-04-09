package br.ufpb.dcx.sisalfapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.ufpb.dcx.sisalfapp.R;

public class UserActivity extends AppCompatActivity {

    private TextView tName;
    private TextView tEmail;
    private TextView tUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        tName = (TextView)findViewById(R.id.user_name);
        tEmail = (TextView)findViewById(R.id.user_email);
        tUID = (TextView)findViewById(R.id.user_uid);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        String email = user.getEmail();
        String uid = user.getUid();

        tName.setText(name);
        tEmail.setText(email);
        tUID.setText(uid);
    }
}
