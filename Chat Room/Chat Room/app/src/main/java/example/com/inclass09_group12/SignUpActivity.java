package example.com.inclass09_group12;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {



    DatabaseReference mroot= FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mauth;
    FirebaseAuth.AuthStateListener mAuthListner;
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private EditText emailEdit;
    private EditText passwordEdit;
    private EditText confirmPasswordEdit;
    private Button cancelButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SignUpActivity.this.setTitle("Sign Up");
        firstNameEdit = findViewById(R.id.editTextFirstName);
        lastNameEdit = findViewById(R.id.editTextLastName);
        emailEdit = findViewById(R.id.editTextEmail);
        passwordEdit = findViewById(R.id.editTextPassword);
        confirmPasswordEdit = findViewById(R.id.editTextConfirmPassword);
        cancelButton = findViewById(R.id.buttonCancel);
        signUpButton = findViewById(R.id.buttonSignUp);
        mauth=FirebaseAuth.getInstance();
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUP();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void SignUP()
    {

        final String Name=firstNameEdit.getText().toString();
        final String lastName=lastNameEdit.getText().toString();
        final String Email=emailEdit.getText().toString();
        final String Password=passwordEdit.getText().toString();
        final String ConfirmPass=confirmPasswordEdit.getText().toString();
        if (firstNameEdit.getText() == null || firstNameEdit.getText().toString().equalsIgnoreCase("")) {
            firstNameEdit.setError("Enter First Name");
        } else if (lastNameEdit.getText() == null || lastNameEdit.getText().toString().equalsIgnoreCase("")) {
            lastNameEdit.setError("Enter Last Name");
        } else if (emailEdit.getText() == null || emailEdit.getText().toString().equalsIgnoreCase("")) {
            emailEdit.setError("Enter Email Id");
        } else if (passwordEdit.getText() == null || passwordEdit.getText().toString().equalsIgnoreCase("")) {
            passwordEdit.setError("Enter Password");
        } else if (confirmPasswordEdit.getText() == null || confirmPasswordEdit.getText().toString().equalsIgnoreCase("")) {
            confirmPasswordEdit.setError("Enter Confirm Password");
        } else if (passwordEdit.getText() != null
                && !passwordEdit.getText().toString().equalsIgnoreCase("")
                && confirmPasswordEdit.getText() != null
                && !confirmPasswordEdit.getText().toString().equalsIgnoreCase("")
                && !passwordEdit.getText().toString().equalsIgnoreCase(confirmPasswordEdit.getText().toString())) {
            confirmPasswordEdit.setError("Password and Confirm Password does not match");
        }
        else {


            mauth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        String userId = mauth.getCurrentUser().getUid();
                        DatabaseReference users = mroot.child("Users");
                        DatabaseReference current_user = users.child(userId);

                        current_user.child("UserId").setValue(userId);
                        current_user.child("FirstName").setValue(Name);
                        current_user.child("LastName").setValue(lastName);
                        current_user.child("Email").setValue(Email);
                        current_user.child("Password").setValue(Password);


                        Toast.makeText(SignUpActivity.this, "The user has been created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, ChatRoomActivity.class));


                    }
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(SignUpActivity.this, "Sign Up Unsucusseful" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

}
