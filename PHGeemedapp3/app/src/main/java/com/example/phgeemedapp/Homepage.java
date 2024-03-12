package com.example.phgeemedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Homepage extends AppCompatActivity {
    private Button button,logout;

    TextView editName, editEmail, editPhone;

    String sName,sEmail,sPhone;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        logout = (Button) findViewById(R.id.button3);

        editName = findViewById(R.id.textViewName);
        editEmail = findViewById(R.id.textViewEmail);
        editPhone = findViewById(R.id.textViewNumber);


        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();


        if(firebaseUser ==null){
            Toast.makeText(Homepage.this, "Error",Toast.LENGTH_LONG).show();
        }
        else{
            showUserProfile(firebaseUser);
        }


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),SignIn.class);
                startActivity(intent);
                finish();
            }
        });

        button = (Button) findViewById(R.id.searchUsers);//added
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchActivity();
            }
        });
        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdd_edit_patient();
            }
        }));

        button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAppointments();
            }
        }));

        button = (Button) findViewById(R.id.button5);
        button.setOnClickListener(( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPersonInfo();
            }
        }));

    }

    public void openSearchActivity(){//added
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);

    }
    public void openAdd_edit_patient(){
        Intent intent = new Intent(this, add_edit_patient.class);
        startActivity(intent);
    }

    public void openAppointments() {
        Intent intent = new Intent(this, Appointments.class);
        startActivity(intent);
    }
    public void openPersonInfo() {
        Intent intent = new Intent(this, personInfo.class);
        startActivity(intent);
    }
    private void showUserProfile(FirebaseUser firebaseUser){
        String userID = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("user");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                if(users !=null){
                    sName ="Name: " + users.name;
                    sEmail ="Email: " + users.email;
                    sPhone ="Phone Number: " +  users.phonenum;
                    editName.setText(sName);
                    editEmail.setText(sEmail);
                    editPhone.setText(sPhone);
                }
                else{

                    editName.setText("Name-Error, Couldn't reach user info");
                    editEmail.setText("Email-Error");
                    editPhone.setText(userID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}