package com.example.project.UserSettings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.Activity.User;
import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditPassword extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    EditText Username,email,phone1;
    String name,mail, phone;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        Username=findViewById(R.id.newName);
        email=findViewById(R.id.newEmail);
        phone1=findViewById(R.id.newPhone);

        button=findViewById(R.id.EditP);
        user= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users");
        userID=user.getUid();
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User usersnapshot=snapshot.getValue(User.class);
                if(usersnapshot!=null){
                    String username=usersnapshot.getFull();
                    String Email=usersnapshot.getEmail();
                    String phone=usersnapshot.getPhone();
                    Username.setText(username);
                    email.setText(Email);
                    phone1.setText(phone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        button.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                update();


            }
        });

    }
    public void update(){
        if(isNamechanged()||isEmailchanged()||isPhoneChanged()){
            Toast.makeText(this,"Data has been updated",Toast.LENGTH_LONG).show();
            isPhoneChanged();
            isEmailchanged();
            isNamechanged();
        }
    }
    public void onCancelled(@NonNull DatabaseError databaseError) { throw databaseError.toException(); }

    private boolean isEmailchanged() {
        if (mail == null || !mail.equals(email.getText().toString()))  {
            reference.child(userID).child("full").setValue(email.getText().toString());
            mail=email.getText().toString();
            return true;
        } else {
            return false;
        }
    }
    private boolean isNamechanged() {
        if (name == null || !name.equals(Username.getText().toString())) {
            reference.child(userID).child("phone").setValue(Username.getText().toString());
            name=Username.getText().toString();
            return true;
        }
        else {
            return false;
        }
    }
    private boolean isPhoneChanged() {
        if (phone == null || !phone.equals(phone1.getText().toString())) {
            reference.child(userID).child("email").setValue(phone1.getText().toString());
            name=phone1.getText().toString();
            return true;
        }
        else {
            return false;
        }
    }



}