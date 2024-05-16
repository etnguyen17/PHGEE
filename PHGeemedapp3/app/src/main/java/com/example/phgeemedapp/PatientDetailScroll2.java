package com.example.phgeemedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class PatientDetailScroll2 extends AppCompatActivity {

    Button bHome, bBaisc, bEContact, bHealth, bResults;

    TextView pName;//, pPhone, pEmail, pDob, pBlood, pMartial, /*pAge,*/ pAddress, pEName, pEEmail, pEPhone, pEAddress;

    EditText pPhone, pEmail, pDob, pBlood, pMartial, /*pAge,*/ pAddress, pEName, pEEmail, pEPhone, pEAddress, pPrevMed, pCurrMed, pAllergies;

    FirebaseAuth auth;

    FirebaseUser firebaseUser;

    Users selectedUser;

    String key;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail_scroll2);

        bHome = findViewById(R.id.buttonH);
        bHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {openHomepage();}
            }
        });

        pName = findViewById(R.id.textView19);
        pPhone = findViewById(R.id.editPhone);
        pEmail = findViewById(R.id.editEmail);
        pDob = findViewById(R.id.editDob);
        pBlood = findViewById(R.id.editBlood);
        pMartial = findViewById(R.id.EditMartial);
        //pMartial = findViewById(R.id.stringMartial);
        pAddress = findViewById(R.id.editAddress);
        //pAge = findViewById(R.id.stringAge);
        pEName = findViewById(R.id.editEName);
        pEEmail= findViewById(R.id.editEEmail);
        pEPhone = findViewById(R.id.editEPhone);
        pEAddress = findViewById(R.id.editEAddress);
        pPrevMed = findViewById((R.id.editEPrevMed));
        pCurrMed = findViewById(R.id.editECurrIll);
        pAllergies = findViewById(R.id.editSpecificAllergies);


        bBaisc = findViewById(R.id.button14);


        bEContact = findViewById(R.id.button15);
        bHealth = findViewById(R.id.button16);
        bResults = findViewById(R.id.button17);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        FirebaseUser firebaseUser = auth.getCurrentUser();
       /* if(firebaseUser != null) {
            showUserProfile(firebaseUser);
        }*/

        Intent intent = getIntent();
        if (intent.hasExtra("SelectedUser")) {
            // Retrieve the SelectedUser object from the intent extras
            selectedUser = (Users) intent.getSerializableExtra("SelectedUser");
            getKey(selectedUser);
            showUserProfile(selectedUser);
        }

        bHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bHealth.getText().toString().equals("Edit")){
                    pPrevMed.setEnabled(true);
                    pPrevMed.setFocusable(true);
                    pPrevMed.setFocusableInTouchMode(true);
                    pPrevMed.requestFocus();
                    //
                    pCurrMed.setEnabled(true);
                    pCurrMed.setFocusable(true);
                    pCurrMed.setFocusableInTouchMode(true);
                    pCurrMed.requestFocus();
                    //
                    pAllergies.setEnabled(true);
                    pAllergies.setFocusable(true);
                    pAllergies.setFocusableInTouchMode(true);
                    pAllergies.requestFocus(); // Optional: Set focus to the EditText
                    // Change button text to indicate editing
                    bHealth.setText("Save");
                }
                else{
                    selectedUser.userInformation.setPrevMed(pPrevMed.getText().toString());
                    selectedUser.userInformation.setCurrIll((pCurrMed.getText().toString()));
                    selectedUser.userInformation.setSpecficAllergies(pAllergies.getText().toString());

                    pPrevMed.setEnabled(false);
                    pPrevMed.setFocusable(false);
                    pPrevMed.setFocusableInTouchMode(false);

                    pCurrMed.setEnabled(false);
                    pCurrMed.setFocusable(false);
                    pCurrMed.setFocusableInTouchMode(false);

                    pAllergies.setEnabled(false);
                    pAllergies.setFocusable(false);
                    pAllergies.setFocusableInTouchMode(false);

                    updateFirebase4();

                    bHealth.setText("Edit");

                }
            }
        });

        bEContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bEContact.getText().toString().equals("Edit")){
                    pEName.setEnabled(true);
                    pEName.setFocusable(true);
                    pEName.setFocusableInTouchMode(true);
                    pEName.requestFocus();
                    //
                    pEEmail.setEnabled(true);
                    pEEmail.setFocusable(true);
                    pEEmail.setFocusableInTouchMode(true);
                    pEEmail.requestFocus();
                    //
                    pEPhone.setEnabled(true);
                    pEPhone.setFocusable(true);
                    pEPhone.setFocusableInTouchMode(true);
                    pEPhone.requestFocus();
                    //
                    pEAddress.setEnabled(true);
                    pEAddress.setFocusable(true);
                    pEAddress.setFocusableInTouchMode(true);
                    pEAddress.requestFocus(); // Optional: Set focus to the EditText
                    // Change button text to indicate editing
                    bEContact.setText("Save");
                }
                else{
                    selectedUser.userInformation.setNameE(pEName.getText().toString());
                    selectedUser.userInformation.setEmailE((pEEmail.getText().toString()));
                    selectedUser.userInformation.setPhoneE(pEPhone.getText().toString());
                    selectedUser.userInformation.setAddressE(pEAddress.getText().toString());

                    pEName.setEnabled(false);
                    pEName.setFocusable(false);
                    pEName.setFocusableInTouchMode(false);

                    pEEmail.setEnabled(false);
                    pEEmail.setFocusable(false);
                    pEEmail.setFocusableInTouchMode(false);

                    pEPhone.setEnabled(false);
                    pEPhone.setFocusable(false);
                    pEPhone.setFocusableInTouchMode(false);

                    pEAddress.setEnabled(false);
                    pEAddress.setFocusable(false);
                    pEAddress.setFocusableInTouchMode(false);

                    updateFirebase3();

                    bEContact.setText("Edit");

                }
            }
        });

        bBaisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bBaisc.getText().toString().equals("Edit")){
                    pAddress.setEnabled(true);
                    pAddress.setFocusable(true);
                    pAddress.setFocusableInTouchMode(true);
                    pAddress.requestFocus();
                    //
                    pDob.setEnabled(true);
                    pDob.setFocusable(true);
                    pDob.setFocusableInTouchMode(true);
                    pDob.requestFocus();
                    //
                    pBlood.setEnabled(true);
                    pBlood.setFocusable(true);
                    pBlood.setFocusableInTouchMode(true);
                    pBlood.requestFocus();
                    //
                    pMartial.setEnabled(true);
                    pMartial.setFocusable(true);
                    pMartial.setFocusableInTouchMode(true);
                    pMartial.requestFocus(); // Optional: Set focus to the EditText
                    // Change button text to indicate editing
                    bBaisc.setText("Save");
                }
                else{
                    selectedUser.userInformation.setBloodType(pBlood.getText().toString());
                    selectedUser.userInformation.setAddress((pAddress.getText().toString()));
                    selectedUser.userInformation.setDob(pDob.getText().toString());
                    selectedUser.userInformation.setMartialStatus(pMartial.getText().toString());

                    pDob.setEnabled(false);
                    pDob.setFocusable(false);
                    pDob.setFocusableInTouchMode(false);

                    pAddress.setEnabled(false);
                    pAddress.setFocusable(false);
                    pAddress.setFocusableInTouchMode(false);

                    pBlood.setEnabled(false);
                    pBlood.setFocusable(false);
                    pBlood.setFocusableInTouchMode(false);

                    pMartial.setEnabled(false);
                    pMartial.setFocusable(false);
                    pMartial.setFocusableInTouchMode(false);

                    updateFirebase2();

                    bBaisc.setText("Edit");
                }
            }
        });

    }



    private void showUserProfile(Users selectedUsers){
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String userID = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                String ref = snapshot.child("userRef").getValue(String.class);
                referenceProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                            String userEmail = dataSnapshot.child("email").getValue(String.class);
                            if(userEmail.equals(ref)){
                                key = dataSnapshot.getKey();
                                pDob.setText(dataSnapshot.child("Info").child("dob").getValue(String.class));
                                pBlood.setText(dataSnapshot.child("Info").child("blood").getValue(String.class));
                                pMartial.setText(dataSnapshot.child("Info").child("martial").getValue(String.class));
                                pAddress.setText(dataSnapshot.child("Info").child("address").getValue(String.class));

                                pEName.setText(dataSnapshot.child("Info").child("EmergencyN").getValue(String.class));
                                pEEmail.setText(dataSnapshot.child("Info").child("EmergencyEmail").getValue(String.class));
                                pEPhone.setText(dataSnapshot.child("Info").child("EmergencyPhone").getValue(String.class));
                                pEAddress.setText(dataSnapshot.child("Info").child("EmergencyAdd").getValue(String.class));

                                pPrevMed.setText(dataSnapshot.child("Info").child("PrevMedical").getValue(String.class));
                                pCurrMed.setText(dataSnapshot.child("Info").child("CurrMedical").getValue(String.class));
                                pAllergies.setText(dataSnapshot.child("Info").child("Allergies").getValue(String.class));


                                break;
                            }


                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        pName.setText(selectedUsers.getName());
        pEmail.setText((selectedUsers.getEmail()));
        pPhone.setText(selectedUsers.getPhonenum());

    }
    public void getKey(Users user){
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String userID = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                String ref = snapshot.child("userRef").getValue(String.class);
                referenceProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                            String userEmail = dataSnapshot.child("email").getValue(String.class);
                            if(userEmail.equals(ref)){
                                key = dataSnapshot.getKey();
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    public void updateFirebase2(){
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                referenceProfile.child(key).child("Info").child("dob").setValue(selectedUser.userInformation.getDob());
                selectedUser.userInformation.setDob(String.valueOf(referenceProfile.child(key).child("Info").child("dob")));

                referenceProfile.child(key).child("Info").child("blood").setValue(selectedUser.userInformation.getBloodType());
                selectedUser.userInformation.setDob(String.valueOf(referenceProfile.child(key).child("Info").child("blood")));

                referenceProfile.child(key).child("Info").child("martial").setValue(selectedUser.userInformation.getMartialStatus());
                selectedUser.userInformation.setDob(String.valueOf(referenceProfile.child(key).child("Info").child("martial")));

                referenceProfile.child(key).child("Info").child("address").setValue(selectedUser.userInformation.getAddress());
                selectedUser.userInformation.setDob(String.valueOf(referenceProfile.child(key).child("Info").child("address")));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateFirebase3(){
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                referenceProfile.child(key).child("Info").child("EmergencyN").setValue(selectedUser.userInformation.getNameE());
                selectedUser.userInformation.setDob(String.valueOf(referenceProfile.child(key).child("Info").child("EmergencyN")));

                referenceProfile.child(key).child("Info").child("EmergencyEmail").setValue(selectedUser.userInformation.getEmailE());
                selectedUser.userInformation.setDob(String.valueOf(referenceProfile.child(key).child("Info").child("EmergencyEmail")));

                referenceProfile.child(key).child("Info").child("EmergencyPhone").setValue(selectedUser.userInformation.getPhoneE());
                selectedUser.userInformation.setDob(String.valueOf(referenceProfile.child(key).child("Info").child("EmergencyPhone")));

                referenceProfile.child(key).child("Info").child("EmergencyAdd").setValue(selectedUser.userInformation.getAddressE());
                selectedUser.userInformation.setDob(String.valueOf(referenceProfile.child(key).child("Info").child("EmergencyAdd")));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void updateFirebase4(){
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                referenceProfile.child(key).child("Info").child("PrevMedical").setValue(selectedUser.userInformation.getPrevMed());
                selectedUser.userInformation.setDob(String.valueOf(referenceProfile.child(key).child("Info").child("PrevMedical")));

                referenceProfile.child(key).child("Info").child("CurrMedical").setValue(selectedUser.userInformation.getCurrIll());
                selectedUser.userInformation.setDob(String.valueOf(referenceProfile.child(key).child("Info").child("CurrMedical")));

                referenceProfile.child(key).child("Info").child("Allergies").setValue(selectedUser.userInformation.getSpecficAllergies());
                selectedUser.userInformation.setDob(String.valueOf(referenceProfile.child(key).child("Info").child("Allergies")));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openHomepage() {
        Intent intent = new Intent (this, Homepage.class);
        startActivity(intent);
        finish();
    }
}