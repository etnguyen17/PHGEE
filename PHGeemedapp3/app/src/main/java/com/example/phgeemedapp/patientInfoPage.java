package com.example.phgeemedapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class patientInfoPage extends AppCompatActivity {

    Button Back, Foward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info_page);

        Back =findViewById(R.id.button);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenBack();
            }
        });
        Foward = findViewById(R.id.button3);
        Foward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFoward();
            }
        });
    }
    public void OpenBack(){
        Intent intent = new Intent(this, PatientListFragment.class);
        startActivity(intent);
    }
    public void OpenFoward(){
        Intent intent = new Intent(this, patientInfoPage2.class);
        startActivity(intent);
    }

}