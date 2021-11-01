package com.example.project.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Objects;
public class SIngUp extends AppCompatActivity implements View.OnClickListener{

    private EditText MFullName, MEmail,MPhone,MPassword;
    private AppCompatButton SignUp;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private TextView textView;
//    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        MFullName = findViewById(R.id.Name);
        MEmail = findViewById(R.id.Email);
        MPhone = findViewById(R.id.Number);
        MPassword = findViewById(R.id.Password);
        SignUp = findViewById(R.id.SignUP);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.reg);
        textView.setOnClickListener(this);
        SignUp.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg:
                startActivity(new Intent(this, signin.class));
                break;
            case R.id.SignUP:
                RegisterUser();
//                checkEmail(v);
                break;
        }
    }
            private void RegisterUser() {
                String full = MFullName.getText().toString().trim();
                String num = MPhone.getText().toString().trim();
                String email = MEmail.getText().toString().trim();
                String password = MPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    MEmail.setError("Email is Required");
                    MEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(num)){
                    MPhone.setError("Email is phone number");
                    MPhone.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(full)){
                    MFullName.setError("Full name is Required");
                    MFullName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    MPassword.setError("Password is Required");
                    MPassword.requestFocus();
                    return;
                }
                if (password.length() <6){
                    MPassword.setError("Password must be >=6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    User user = new User(email,full,num);
                                    String email = user.email;
                                    String full = user.full;
                                    String num = user.phone;
                                    MFullName.setText(full);
                                    MEmail.setText(email);
                                    MPhone.setText(num);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {

                                            if (task.isSuccessful()){
                                                Toast.makeText(SIngUp.this, "User Registered", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(SIngUp.this,signin.class);
                                                startActivity(intent);
                                                finish();
                                            }else {
                                                Toast.makeText(SIngUp.this, "Failed to Register, Try Again", Toast.LENGTH_SHORT).show();
                                            }
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(SIngUp.this, "Failed To Register" +task.getException(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });

            }


}