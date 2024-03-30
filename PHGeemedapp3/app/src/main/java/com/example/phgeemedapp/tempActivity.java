package com.example.phgeemedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class tempActivity extends AppCompatActivity {
    String Role ="";
    String user ;
    Users users;
    FirebaseAuth auth,mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    //changgRole();
                    sleep(1000);
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    auth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    user = auth.getCurrentUser().getUid();
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
                    referenceProfile.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            users = snapshot.getValue(Users.class);
                            if (users != null) {
                                Role = users.getRole();
                                if(Role.equals("Patient")){
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), HomepagePatientActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), Homepage.class);
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        };
        thread.start();
    }
    public void changgRole(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id = currentUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                if (users != null) {
                    Role = users.getRole();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}