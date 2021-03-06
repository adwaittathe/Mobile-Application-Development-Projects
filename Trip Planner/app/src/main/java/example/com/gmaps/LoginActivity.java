package example.com.gmaps;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private static int RC_SIGN_IN=100;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseDatabase database;
    DatabaseReference mroot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = FirebaseDatabase.getInstance();
        mroot = database.getReference();


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            String Email=account.getEmail();
            String firstName= account.getDisplayName();
            String lastName= account.getGivenName();

            String userId=account.getId();
            //String Idtoken=account.getIdToken();
           // Log.d("signIn","LogIN Details " + Name + "  " + Id + "  "+ Idtoken + " "+a+" "+b+" "+c);

            DatabaseReference users = mroot.child("Users");
            DatabaseReference current_user = users.child(userId);
            current_user.child("UserId").setValue(userId);
            current_user.child("FirstName").setValue(firstName);
            current_user.child("LastName").setValue(lastName);
            current_user.child("Email").setValue(Email);

            Intent int_trip=new Intent(LoginActivity.this,CreateTripActivity.class);
            int_trip.putExtra("Name",firstName);
            int_trip.putExtra("Id",userId);

            startActivity(int_trip);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("signIn", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null) {
            String Name = account.getEmail();
            String Id = account.getId();
            String Idtoken = account.getIdToken();
            String firstName= account.getDisplayName();
            String userId=account.getId();
            Log.d("signIn", "LogIN Details On Start" + Name + "  " + Id + "  " + Idtoken);
            Intent int_trip=new Intent(LoginActivity.this,CreateTripActivity.class);
            int_trip.putExtra("Name",firstName);
            int_trip.putExtra("Id",userId);

            startActivity(int_trip);
        }
        //updateUI(account);
    }
}
