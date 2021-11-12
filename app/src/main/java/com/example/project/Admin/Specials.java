package com.example.project.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Activity.MainActivity;
import com.example.project.CategoriesDetails.CategorieAdapter;
import com.example.project.CategoriesDetails.CategoryModel;
import com.example.project.CategoriesDetails.UploadImageInfo;
import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Specials extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SpecialsAdapter Adapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private ArrayList<UploadImageInfo> list;
    private ImageView im;
    private String UserId;
//    TextView add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_order_retrieve);
        recyclerView = findViewById(R.id.retrieve);
//        add = findViewById(R.id.addBtn);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        im = findViewById(R.id.retour);
//
//        im.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Specials.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        UserId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = firebaseDatabase.getInstance().getReference("Images").child(UserId);
        recyclerView.setLayoutManager(new LinearLayoutManager(Specials.this));
        //recyclerView.setLayoutManager(new LinearLayoutManager(Categories.this));
        recyclerView.setHasFixedSize(false);
        list = new ArrayList<UploadImageInfo>();
        list.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChildren()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UploadImageInfo uploadImageInfo = snapshot.getValue(UploadImageInfo.class);
                    list.add(uploadImageInfo);
                }
                Adapter = new SpecialsAdapter(Specials.this,list);
                Adapter.notifyDataSetChanged();
                recyclerView.setAdapter(Adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Specials.this, "Error!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}