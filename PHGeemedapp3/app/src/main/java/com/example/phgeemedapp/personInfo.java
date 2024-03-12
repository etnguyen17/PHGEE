package com.example.phgeemedapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class personInfo extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        button =(Button) findViewById(R.id.returnHome);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {openHomepage();}
        });
    }
    public void openHomepage(){
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }
}