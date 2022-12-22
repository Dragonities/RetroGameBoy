package com.example.uas_winston_c11210018;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.jetbrains.annotations.Nullable;

public class LoginActivity extends AppCompatActivity {
    private EditText email,pass;
    private Button login,register;
    private SignInButton google;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
   private GoogleSignInClient mgoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.Email);
        pass = findViewById(R.id.Password);
        login = findViewById(R.id.LoginBtn);
        register = findViewById(R.id.Registerbtn);
        google = findViewById(R.id.btngogle);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan tunggu");
        progressDialog.setCancelable(false);

        google.setOnClickListener(view -> {
            Googlesignin();
        });
        register.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),Register.class));
        });
        login.setOnClickListener(view -> {
            if (email.getText().length()>0 && pass.getText().length()>0){
                logins(email.getText().toString(),pass.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(), "Silahkan isi semua", Toast.LENGTH_SHORT).show();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("148502148294-u5ap6g6pg6prrms9g9t8bdqssqlpslpr.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mgoogleSignInClient = GoogleSignIn.getClient(this,gso);
    }

    private void Googlesignin(){
        Intent signinIntent = mgoogleSignInClient.getSignInIntent();
        startActivityForResult(signinIntent, 1001);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {
         Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
               GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("GOOGLE SIGN IN", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.w("GOOGLE SIGN IN", "Google Sign in Failed", e);
            }
        }
        }
        private void firebaseAuthWithGoogle(String idToken){
            progressDialog.show();
            AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
            mAuth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("GOOGLE SIGN IN", "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("GOOGLE SIGN IN", "signInWithCredential:failure", task.getException());

                            }
                            reload();
                            progressDialog.dismiss();
                        }
                    });
        }






    private void logins(String email, String Password){
        progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful() && task.getResult()!=null) {
                        if (task.getResult().getUser()!=null) {
                            reload();
                        }else{
                            Toast.makeText(getApplicationContext(),"Login gagal",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(),"Login gagal",
                                Toast.LENGTH_SHORT).show();

                    }
                    progressDialog.dismiss();
                }
            });
    }
    private void reload(){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

}