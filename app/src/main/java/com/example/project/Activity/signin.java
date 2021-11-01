package com.example.project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import org.jetbrains.annotations.NotNull;
public class signin extends AppCompatActivity {
    EditText MEmail, MPassword;
    AppCompatButton siginn;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    TextView textView;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        MEmail = findViewById(R.id.phone);
        MPassword = findViewById(R.id.password);
        siginn = findViewById(R.id.signin);
        progressBar = findViewById(R.id.progressBar);
        siginn.getResources().getColor(R.color.food);
        textView = findViewById(R.id.sig);
        textView2 = findViewById(R.id.forgot);

        firebaseAuth = FirebaseAuth.getInstance();
        siginn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = MEmail.getText().toString().trim();
                String password = MPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    MEmail.setError("Email is Required");
                    MEmail.setFocusable(true);
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    MPassword.setError("Password is Required");
                    MPassword.setFocusable(true);
                    return;
                }
                if (password.length() <6){
                    MPassword.setError("Password must be >=6 characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Intent intent = new Intent(signin.this,MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                            Toast.makeText(signin.this, "logged successfully ", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(signin.this, "Can't login, Try Again" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SIngUp.class));
                finish();
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText reset = new EditText(v.getContext());
                //creating an alert dialog
                AlertDialog.Builder passwordDialog = new AlertDialog.Builder(v.getContext());
                passwordDialog.setTitle("Reset Password");
                passwordDialog.setMessage("Enter Your Email To Receive The Reset Link");
                passwordDialog.setView(reset);
                passwordDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = reset.getText().toString().trim();
                        //requesting the link from firebase to reset the password
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(signin.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();

                            }
                            //on failure to send the link,  Toast.makeText(LoginActivity.this, "Error! Reset Link Is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(signin.this, "Error! Reset Link Is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                passwordDialog.create().show();
            }
        });
    }
}