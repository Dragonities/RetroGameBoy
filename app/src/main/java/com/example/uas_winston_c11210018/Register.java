package com.example.uas_winston_c11210018;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {
    private EditText nama, email, pass, passconf;
    private Button btnlogin,btnregist;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        nama = findViewById(R.id.nameR);
        email = findViewById(R.id.Email);
        pass = findViewById(R.id.Password);
        passconf = findViewById(R.id.confpass);
        btnregist = findViewById(R.id.Registerbtn);
        btnlogin = findViewById(R.id.LoginBtn);

        progressDialog = new ProgressDialog(Register.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Silahkan tunggu");
        progressDialog.setCancelable(false);

       btnlogin.setOnClickListener(view -> {
           finish();
       });
        btnregist.setOnClickListener(view -> {
            if(nama.getText().length()>0 && email.getText().length()>0 && pass.getText().length()>0 && passconf.getText().length()>0){
                    if (pass.getText().toString().equals(passconf.getText().toString())){
                        register(nama.getText().toString(), email.getText().toString(), pass.getText().toString());
                    }else{
                        Toast.makeText(getApplicationContext(), "Password tidak sama", Toast.LENGTH_SHORT).show();
                    }
            } else{
            Toast.makeText(getApplicationContext(), "Silahkan isi semua", Toast.LENGTH_SHORT).show();
        }
        });
    }

    private void register(String nama, String email, String pass){
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() && task.getResult()!=null) {


                            FirebaseUser user = task.getResult().getUser();
                            if (user!=null) {
                                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(nama)
                                        .build();
                                user.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        reload();
                                    }
                                });
                            }else{
                                Toast.makeText(getApplicationContext(),"Register gagal",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {


                            Toast.makeText(getApplicationContext(),task.getException().getLocalizedMessage(),
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

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

}