package com.example.phgeemedapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomepagePatientFragment extends Fragment {
    public HomepagePatientFragment() {
        // Required empty public constructor
    }
    TextView editName, barName, editEmail, editPhone;

    String sName, sName2, sEmail, sPhone;
    FirebaseAuth auth;
    FirebaseUser user;
    NavigationView navigationView;
    DatabaseReference doctor, patient;
    String role;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage_patient, container, false);
        editName = view.findViewById(R.id.textViewName2);
        editEmail = view.findViewById(R.id.textViewEmail2);
        editPhone = view.findViewById(R.id.textViewNumber2);
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null) {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
        } else {
            //role = checkType(firebaseUser);
            showUserProfile(firebaseUser);
        }
        return view;
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("user2");
        referenceProfile.child("Patients").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Users users = snapshot.getValue(Users.class);
                patient patient = snapshot.getValue(patient.class);
                if (patient != null) {
                   // sName = "Name: " + patient.firstName + patient.lastName;
                    sEmail = "Email: " + patient.pemail;
                    sPhone = "Phone Number: " + patient.phone;
                   //editName.setText(sName);
                    editEmail.setText(sEmail);
                    editPhone.setText(sPhone);
                } else {

                   // editName.setText("Name-Error, Couldn't reach user info");
                    editEmail.setText("Email-Error");
                    editPhone.setText("Phone-Error");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}