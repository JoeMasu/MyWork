package com.example.project.UserSettings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Activity.IntroActivity;
import com.example.project.Activity.MainActivity;
import com.example.project.Activity.User;
import com.example.project.Activity.signin;
import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class settingsUI extends AppCompatActivity {
    ImageView imageView;
    AppCompatButton BtnDelete;
    AppCompatButton ChangePassword;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView textView1, textView2, textView3, logout;
    private DatabaseReference reference;
    AppCompatButton Edit;
    private String UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_ui);
        imageView = findViewById(R.id.retour);
        BtnDelete = findViewById(R.id.delete);
        textView1 = findViewById(R.id.Uname);
        textView2 = findViewById(R.id.UEmail);
        textView3 = findViewById(R.id.UNumber);
        Edit = findViewById(R.id.edit);
        logout = findViewById(R.id.logout);
        ChangePassword = findViewById(R.id.ChangePassword);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserId = firebaseUser.getUid();

        reference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
       @Override
       public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
           User user = snapshot.getValue(User.class);
           if (user != null){
               String name = user.getFull();
               String email = user.getEmail();
               String number = user.getPhone();

               textView1.setText(name);
               textView2.setText(email);
               textView3.setText(number);

           }
       }

       @Override
       public void onCancelled(@NonNull @NotNull DatabaseError error) {
           Toast.makeText(settingsUI.this, "Something wrong happened", Toast.LENGTH_SHORT).show();
       }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(settingsUI.this, IntroActivity.class));
            }
        });
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settingsUI.this, EditPassword.class);
                startActivity(intent);
            }
        });

        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(settingsUI.this, ChangePassword.class);
                startActivity(intent);

            }
        });

        BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder passwordDialog = new AlertDialog.Builder(v.getContext());
                passwordDialog.setTitle("Delete Account");
                passwordDialog.setMessage("If You Delete Your Account you will Have to Register Again");

                passwordDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(settingsUI.this, "Your Account Has Benn Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(settingsUI.this, signin.class);
                                    finish();

                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                                }else {
                                    Toast.makeText(settingsUI.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

                    }
                });
                passwordDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();

                    }
                });
                AlertDialog alertDialog = passwordDialog.create();
                alertDialog.show();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settingsUI.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}


