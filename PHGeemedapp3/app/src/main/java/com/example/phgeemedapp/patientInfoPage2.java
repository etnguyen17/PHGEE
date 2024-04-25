package com.example.phgeemedapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class patientInfoPage2 extends AppCompatActivity {

    Button Back, Foward;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info_page2);

        Back =findViewById(R.id.button4);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenBack();
            }
        });
        Foward = findViewById(R.id.button5);
        Foward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFoward();
            }
        });
    }
    public void OpenBack(){
        Intent intent = new Intent(this, patientInfoPage.class);
        startActivity(intent);
    }
    public void OpenFoward(){
        Intent intent = new Intent(this, patientInfoPage3.class);
        startActivity(intent);
    }

}