package example.com.inclass09_group12;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mauth;
    FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuthListner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null) // Now user is logged in
                {

                    startActivity(new Intent(MainActivity.this,ChatRoomActivity.class));
                }
            }
        };

        mauth=FirebaseAuth.getInstance();
        Button btnLogin=(Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login();
            }
        });

        Button btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int_sign=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(int_sign);


            }
        });






    }
    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mAuthListner);
    }


    public void Login()
    {
        EditText editEmail=(EditText)findViewById(R.id.edit_email);
        EditText editPassword=(EditText)findViewById(R.id.edit_password);
        String email=editEmail.getText().toString();
        String password=editPassword.getText().toString();
        if (editEmail.getText().toString().trim().length() == 0) {
            Toast.makeText(MainActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
        }

        else if (editPassword.getText().toString().trim().length() == 0) {
            Toast.makeText(MainActivity.this, "Enter a password !!", Toast.LENGTH_SHORT).show();
        }

        else {
            mauth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) //If there is problem with user login
                    {


                        startActivity(new Intent(MainActivity.this,ChatRoomActivity.class));
                        //Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "User is invalid.. Please Sign Up", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}
