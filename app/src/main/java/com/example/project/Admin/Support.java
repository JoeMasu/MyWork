

package com.example.project.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project.Activity.signin;
import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class Support extends AppCompatActivity {
    AppCompatButton add_notification, AdminLogout;
    EditText notification_title;
    EditText notification_content;
    AppCompatButton notifications,message;
    RecyclerView recyclerView;
    MessagesAdapter messagesAdapter;
    LinearLayout messageView,notificationtab;
    ArrayList<String> listString;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        notifications=findViewById(R.id.message);
        message=findViewById(R.id.notifications);
        notificationtab=findViewById(R.id.notificationtab);
        messageView=findViewById(R.id.messageView);
        add_notification = findViewById(R.id.add_notification);
        notification_title = findViewById(R.id.notification_title);
        notification_content = findViewById(R.id.notification_content);
        listString=new ArrayList<>();
        AdminLogout = findViewById(R.id.AdminLogout);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

////        notifications.setBackgroundColor(getResources().getColor(R.color.main));
//        message.setBackgroundColor(getResources().getColor(R.color.main));
//        notifications.setBackgroundResource(R.color.main);


        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageView.setVisibility(View.GONE);
                notificationtab.setVisibility(View.VISIBLE);


            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageView.setVisibility(View.VISIBLE);
                notificationtab.setVisibility(View.GONE);
            }
        });



        add_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dialog = new ProgressDialog(Support.this);
                dialog.setMessage("sending the Notification");
                dialog.show();
                String title = notification_title.getText().toString();
                String content = notification_content.getText().toString();

                HashMap<String, String> map = new HashMap<>();
                map.put("title", title);
                map.put("content", content);

                reference.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        dialog.dismiss();
                        notification_content.setText("");
                        notification_title.setText("");
                        Toast.makeText(Support.this, "notification sent Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(Support.this, "Could not Send the notification check you network and try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        AdminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }

            private void logout() {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(Support.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Support.this, signin.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        messages();

    }
    //        @Override
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
    private void messages(){

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("support");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                listString.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    listString.add(dataSnapshot.getKey());
                }
                messagesAdapter=new MessagesAdapter(Support.this,listString);
                recyclerView.setAdapter(messagesAdapter);
                recyclerView.addItemDecoration(new DividerItemDecoration(Support.this, LinearLayoutManager.VERTICAL));

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

    }

    @Override
    public void onBackPressed() {
        Support.this.finishAffinity();
    }

}