package com.example.project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.chatf.adapter.CommunicationRecyclerAdapter;
import com.example.project.chatf.model.dataCommunication;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SupportActivity extends AppCompatActivity {
    EditText sendMessage;
    FloatingActionButton fab_send;
    RecyclerView recyclerView;
    String userId;
    String name;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<dataCommunication> listCommunication = new ArrayList<>();
    ArrayList<String> listData = new ArrayList<>();
    CommunicationRecyclerAdapter communicationRecyclerAdapter;
    Context context;
    private String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support2);

        context = this;
        sendMessage = findViewById(R.id.sendMessage);
        fab_send = findViewById(R.id.fab_send);
        recyclerView = findViewById(R.id.recyclerView);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        UserId = firebaseAuth.getCurrentUser().getUid();
//
//        //GETTING THE EXTRA WHICH  IS THE USER ID
//
//        Intent intent=getIntent();
//        userId=intent.getStringExtra("userId");
        fab_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMessage();
            }
        });
        sendDataMessageShow();
    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        //HERE WE WANT TO GET BACK ONCE IN THE MAIN ACTIVITY ONCE THE USER HAS PRESSED BACK
//        if (FirebaseAuth.getInstance().getCurrentUser().getUid().startsWith("6YT")){
//            Intent intent=new Intent(this, MainAdminPanelActivity.class);
//            intent.putExtra("userID",FirebaseAuth.getInstance().getCurrentUser().getUid());
//            startActivity(intent);
//            finish();
//        }
//        Intent intent=new Intent(this, MainActivity.class);
//        intent.putExtra("userID",FirebaseAuth.getInstance().getCurrentUser().getUid());
//        startActivity(intent);
//        finish();
//    }

    private void inputMessage() {
        String send = sendMessage.getText().toString();
        if (send.isEmpty()){
            //SETTING ENTER A MESSAGE AS HINT TEXT IN THE EDITTEXT
            sendMessage.setError("Enter a message");
            sendMessage.requestFocus();

        }else {

            String id = userId;

            Locale locale = new Locale("en", "ID");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy", locale);
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(UserId);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    name = snapshot.child("full").getValue().toString();
                    //WE'RE CREATING THIS CHILD CALLED message in firebase
                    database.child("support").child(UserId)
                            .push()
                            .setValue(new dataCommunication(
                                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    name,
                                    send,
                                    simpleDateFormat.format(System.currentTimeMillis()),
                                    System.currentTimeMillis(),
                                    "text"))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //if the message is successfully sent
                                    Toast.makeText(context, "message sent successfully", Toast.LENGTH_SHORT).show();
                                    sendMessage.setText("");

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //if the message fail to be sent
                            Toast.makeText(context, "message failed to send", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

        }

    }

    private void sendDataMessageShow() {
        database.child("support").child(UserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCommunication.clear();
                listData.clear();
                for(DataSnapshot item : dataSnapshot.getChildren()){
                    dataCommunication communication = item.getValue(dataCommunication.class);
                    listData.add(communication != null ? communication.getDate(): null);
                    listCommunication.add(communication);
                }
                //assigning to the adapter
                communicationRecyclerAdapter = new CommunicationRecyclerAdapter(context, listCommunication, listData);
                recyclerView.setAdapter(communicationRecyclerAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}