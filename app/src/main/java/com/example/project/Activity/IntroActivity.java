package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project.R;

public class IntroActivity extends AppCompatActivity {
    AppCompatButton button1;
    AppCompatButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        button1 = findViewById(R.id.btn1);
        button = findViewById(R.id.btn);

        button1.setOnClickListener(v -> {
            Intent intent = new Intent(IntroActivity.this, signin.class);
            startActivity(intent);
        });
        button.setOnClickListener(v -> {
            Intent b = new Intent(IntroActivity.this, SIngUp.class);
            startActivity(b);
//                Toast.makeText(MainActivity.this, "hey", Toast.LENGTH_SHORT).show();
        });
    }
}