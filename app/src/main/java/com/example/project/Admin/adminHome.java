package com.example.project.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.project.Activity.SupportActivity;
import com.example.project.Activity.User;
import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class adminHome extends AppCompatActivity {
    String UserId;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    TextView textView1;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    CardView SpecialOrder, Support;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        textView1 = findViewById(R.id.AdminName);
        SpecialOrder = findViewById(R.id.cardview1);
        Support = findViewById(R.id.cardView2);

        firebaseAuth = FirebaseAuth.getInstance();

        SpecialOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminHome.this, Specials.class);
                startActivity(intent);

            }
        });
        Support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startSupportChat();
                Intent intent=new Intent(adminHome.this, Support.class);
                intent.putExtra("userId",firebaseAuth.getCurrentUser().getUid());
                intent.putExtra("support",true);
                startActivity(intent);
            }
        });




        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        UserId = firebaseUser.getUid();
        databaseReference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null){
                    String full = user.getFull();

                    textView1.setText("Welcome "+full);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}