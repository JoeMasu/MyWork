package com.example.project.CategoriesDetails;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Activity.User;
import com.example.project.Domain.FoodDomain;
import com.example.project.Helper.ManagementCart;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductDetails extends AppCompatActivity {

    TextView title, price, descr, AddCart,numberOrderTxt;;
    ImageView imageView;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private int numberOrder = 1;
    private FoodDomain object;
    private ManagementCart managementCart;
    private ImageView plusBtn, minusBtn, picFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
        title = findViewById(R.id.titleTxt);
        price = findViewById(R.id.priceTxt);
        AddCart = findViewById(R.id.addToCardBtn);
        descr = findViewById(R.id.descriptionTxt);
        imageView = findViewById(R.id.foodPic);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        numberOrderTxt = findViewById(R.id.numberOrderTxt);
        picFood = findViewById(R.id.foodPic);
        numberOrderTxt.setText(String.valueOf(numberOrder));
        title.setText(object.getTitle());
        price.setText("$" + object.getFee());
        descr.setText(object.getDescription());

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrder = numberOrder + 1;
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 1) {
                    numberOrder = numberOrder - 1;
                }
                numberOrderTxt.setText(String.valueOf(numberOrder));
            }
        });
        AddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                object.setNumberInCart(numberOrder);
                managementCart.insertFood(object);

            }
        });


        imageView.setImageResource(getIntent().getIntExtra("Img",0));
        title.setText(getIntent().getStringExtra("Type"));
        price.setText(getIntent().getStringExtra("Price"));

        databaseReference = firebaseDatabase.getInstance().getReference("Categories").child("Pizza");
        databaseReference.child("obj1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                CategoryModel categoryModel = snapshot.getValue(CategoryModel.class);
                if (categoryModel != null){
                    String desc = categoryModel.desc;
                    descr.setText(desc);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}
