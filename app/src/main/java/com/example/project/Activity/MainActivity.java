package com.example.project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Adapter.PopularAdapter;
import com.example.project.CategoriesDetails.Special_Oder;
import com.example.project.Domain.FoodDomain;
import com.example.project.Frontcategories.FrontCatAdapter;
import com.example.project.Frontcategories.FrontCatModel;
import com.example.project.R;
import com.example.project.UserSettings.settingsUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
//import com.smarteist.autoimageslider.SliderAnimations;
//
//import com.smarteist.autoimageslider.SliderView;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter2;
    private FrontCatAdapter frontCatAdapter;
    private ArrayList<FrontCatModel> list;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    int ACTION_PICK = 10001;
    TextView textView1;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private String UserId;
    CircleImageView prof;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView loc;

    private RecyclerView recyclerView, recyclerViewPopularList;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout2, linearLayout3;

//    SliderView sliderView;
//    int[] images = {R.drawable.cooo,
//            R.drawable.chips0,
//            R.drawable.chips0,
//            R.drawable.chips0,
//            R.drawable.chips0,
//            R.drawable.chips0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loc = findViewById(R.id.textView4);
        textView1 = findViewById(R.id.textView4);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        linearLayout = findViewById(R.id.set);
        linearLayout2 = findViewById(R.id.order);
        linearLayout3 = findViewById(R.id.Chat);
        prof = findViewById(R.id.profile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        UserId = firebaseUser.getUid();

        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, ACTION_PICK);
                }

            }
        });

        databaseReference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null){
                    String full = user.full;

                    textView1.setText("Welcome "+full);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, settingsUI.class);
                startActivity(intent);

            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Special_Oder.class);
                startActivity(intent);

            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SupportActivity.class);
                startActivity(intent);

            }
        });

//        sliderView = findViewById(R.id.image_slider);
//
//        SliderAdapter sliderAdapter = new SliderAdapter(images);
//
//        sliderView.setSliderAdapter(sliderAdapter);
//        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
//        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
//        sliderView.startAutoCycle();

        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
        LocationSet();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_PICK)
//            && resultCode==RESULT_OK)
        {
            switch (resultCode) {
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    prof.setImageBitmap(bitmap);
                    handleUpload(bitmap);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.getPhotoUrl() != null) {
                        Glide.with(this)
                                .load(user.getPhotoUrl())
                                .into(prof);
                    }
            }


        }
    }

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(uid + ".png");
        Task upload=reference.putBytes(byteArrayOutputStream.toByteArray()).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                Toast.makeText(MainActivity.this, "uploading", Toast.LENGTH_SHORT).show();
            }
        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "upload Complete", Toast.LENGTH_SHORT).show();
                    return reference.getDownloadUrl();
                }else {
                    Toast.makeText(MainActivity.this, "they was an error uploading ", Toast.LENGTH_SHORT).show();
                    throw task.getException();
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    String url = (task.getResult()).toString();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("profile",url);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(firebaseAuth.getUid());
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                    try {
                                        Glide.with(getApplicationContext())
                                                .load(snapshot.child("profile").getValue().toString())
                                                .centerCrop()
                                                .placeholder(R.drawable.ic_launcher_foreground)
                                                .into(prof);

                                    } catch (Exception e) {
//                    Toast.makeText(MainActivity.this, "" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                }
                            });
                            Toast.makeText(MainActivity.this,"profile updated", Toast.LENGTH_SHORT).show();
                            //   textView11.setText(taskSnapshot.getUploadSessionUri().getPath());

                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void LocationSet() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            GetLocation();

        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void GetLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(),1);
                    loc.setText(Html.fromHtml("<font color='#6200EE'><b>Address: </b><br></font>"
                    + addresses.get(0).getAddressLine(0)));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.card_btn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
    }

    private void recyclerViewPopular() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodlist = new ArrayList<>();
        foodlist.add(new FoodDomain("Pepperoni pizza", "pizza1", "slices pepperoni ,mozzarella cheese, fresh oregano,  ground black pepper, pizza sauce", 9.76));
        foodlist.add(new FoodDomain("Cheese Burger", "burger", "beef, Gouda Cheese, Special sauce, Lettuce, tomato ", 8.79));
        foodlist.add(new FoodDomain("Vegetable pizza", "pizza2", " olive oil, Vegetable oil, pitted Kalamata, cherry tomatoes, fresh oregano, basil", 8.5));

        adapter2 = new PopularAdapter(foodlist);
        recyclerViewPopularList.setAdapter(adapter2);

    }

    private void recyclerViewCategory() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getInstance().getReference("Menu");
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<FrontCatModel>();

        list.clear();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    FrontCatModel frontCatModel = snapshot1.getValue(FrontCatModel.class);
                    list.add(frontCatModel);
                }
                frontCatAdapter = new FrontCatAdapter(MainActivity.this,list);
                frontCatAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(frontCatAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerViewCategoryList = findViewById(R.id.recyclerView);
//        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
//
//        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
//        categoryList.add(new CategoryDomain("Pizza", "cat_1"));
//        categoryList.add(new CategoryDomain("Burger", "cat_2"));
//        categoryList.add(new CategoryDomain("Hotdog", "cat_3"));
//        categoryList.add(new CategoryDomain("Drink", "cat_4"));
//        categoryList.add(new CategoryDomain("Dount", "cat_5"));
//
//
//        adapter = new CategoryAdapter(categoryList);
//        recyclerViewCategoryList.setAdapter(adapter);
    }
}