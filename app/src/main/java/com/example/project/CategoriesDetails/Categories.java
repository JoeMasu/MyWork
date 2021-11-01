package com.example.project.CategoriesDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.project.Activity.MainActivity;
import com.example.project.Activity.ShowDetailActivity;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class Categories extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CategorieAdapter Adapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<CategoryModel> list;
    private ImageView im;
//    TextView add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        recyclerView = findViewById(R.id.recyclervi);
//        add = findViewById(R.id.addBtn);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        im = findViewById(R.id.retour);

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categories.this, MainActivity.class);
                startActivity(intent);
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("Categories").child("Pizza");
        //recyclerView.setLayoutManager(new LinearLayoutManager(Categories.this));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<CategoryModel>();

        list.clear();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChildren()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    CategoryModel categoryModel = snapshot.getValue(CategoryModel.class);
                    list.add(categoryModel);
                }
                Adapter = new CategorieAdapter(Categories.this,list);
                Adapter.notifyDataSetChanged();
                recyclerView.setAdapter(Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Categories.this, "Error!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}