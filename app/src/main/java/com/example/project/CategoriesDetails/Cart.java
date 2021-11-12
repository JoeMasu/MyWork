package com.example.project.CategoriesDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Activity.User;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CategorieAdapter Adapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<CategoryModel> list;
    private ImageView im;
    Double price;
    int quant;
    String image, decription, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent i = getIntent();
        price = Double.parseDouble(i.getStringExtra("Price"));
        image = i.getStringExtra("Image");
        decription = i.getStringExtra("Descr");
        type = i.getStringExtra("Type");
        quant = i.getIntExtra("Quantity", 1);
        recyclerView = findViewById(R.id.cart);
//        CategoryModel categoryModel = new CategoryModel(String type, String price, String image, String addBtn, String desc)

////        add = findViewById(R.id.addBtn);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = FirebaseDatabase.getInstance().getReference("Categories").child("Pizza");
//        recyclerView.setHasFixedSize(true);
//        list = new ArrayList<CategoryModel>();
//
//        list.clear();
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
////                if (dataSnapshot.hasChildren()){
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    CategoryModel categoryModel = snapshot.getValue(CategoryModel.class);
//                    list.add(categoryModel);
//                }
//                Adapter = new CategorieAdapter(Cart.this,list);
//                Adapter.notifyDataSetChanged();
//                recyclerView.setAdapter(Adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(Cart.this, "Error!!", Toast.LENGTH_SHORT).show();
//            }
//        });






    }
}