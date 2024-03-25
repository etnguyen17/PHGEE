package com.example.phgeemedapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView editName, barName, editEmail, editPhone;

    String sName, sName2, sEmail, sPhone;
    FirebaseAuth auth;
    FirebaseUser user;
    NavigationView navigationView;
    DatabaseReference doctor, patient;
    String role;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home_page, container, false);
        editName = view.findViewById(R.id.textViewName);
        editEmail = view.findViewById(R.id.textViewEmail);
        editPhone = view.findViewById(R.id.textViewNumber);
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser == null) {
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
        referenceProfile.child("Doctors and Nurses").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                if (users != null) {
                    sName = "Name: " + users.getName();
                    sEmail = "Email: " + users.getEmail();
                    sPhone = "Phone Number: " + users.getPhonenum();
                    editName.setText(sName);
                    editEmail.setText(sEmail);
                    editPhone.setText(sPhone);
                } else {

                    editName.setText("Name-Error, Couldn't reach user info");
                    editEmail.setText("Email-Error");
                    editPhone.setText("Phone-Error");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
   /* public String checkType(FirebaseUser currentUser){
        String roles = "Doctor and Nurse";
        String roles2 = "Patient";
        String userID = currentUser.getUid();
        final String[] userIDTEMP = new String[1];
        final String[] temprole = new String[1];
        final Boolean[] temp = new Boolean[2];
        doctor = FirebaseDatabase.getInstance().getReference("user2");
        doctor.child("Doctor and Nurse").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                String roles = users.getRole();
                if(roles.equals("Doctor")||roles.equals("Nurse")){
                    temp[0] = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        doctor.child("Patient").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                assert users != null;
                String roles = users.getRole();
                if((roles.equals("Doctor"))||roles.equals("Nurse")) {
                    temp[1] = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if(temp[0] != null){
            return roles;
        }
        else if(temp[1]!= null) {
            return roles2;
        }
        return "";
    }*/
    }
}