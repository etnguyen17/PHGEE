package com.example.phgeemedapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView date1Text, date2Text, date3Text, editPhone;

    String date1, date2, date3, sPhone;
    FirebaseAuth auth;
    FirebaseUser User;
    List<appointment> appointments;
    FirebaseUser user;

    NavigationView navigationView;
    DatabaseReference doctor, patient;
    String role;
    private RecyclerView recyclerView;
    private ArrayList<appointment> appointmentArrayList;
    ArrayList<appointment> list;

    public HomePageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        date1Text = view.findViewById(R.id.date1);
        date2Text = view.findViewById(R.id.date2);
        date3Text = view.findViewById(R.id.date3);
        //editName = view.findViewById(R.id.textViewName);
      //  editEmail = view.findViewById(R.id.textViewEmail);
       // editPhone = view.findViewById(R.id.textViewNumber);
        appointmentArrayList = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        User = auth.getCurrentUser();
        String userID = User.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                if(user!=null){
                    appointments = user.getAppointments();
                    //E.C added this snip cause when merged, issues
                    if (appointments != null) {
                        if(appointments.get(0)!=null &&appointments.size()==1) {
                            date1 = appointments.get(0).toString();
                            date2 = "";
                            date3 = "";
                        }

                        else if(appointments.get(1)!=null && appointments.size()==2) {
                            date1 = appointments.get(0).toString();

                            date2 = appointments.get(1).toString();
                            date3 = "";

                        }

                        else if(appointments.get(2)!=null && appointments.size()==3) {
                            date1 = appointments.get(0).toString();

                            date2 = appointments.get(1).toString();
                            date3 = appointments.get(2).toString();

                        }
                        else{
                            date1 = "No Appointments";
                            date2 = "";
                            date3 = "";
                        }

                        date1Text.setText(date1);
                        date2Text.setText(date2);
                        date3Text.setText(date3);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser == null) {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
        } else {
            //role = checkType(firebaseUser);
            //showUserProfile(firebaseUser);
        }
        return view;
    }


    /*private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                //patient patient = snapshot.getValue(patient.class);
                if (users != null) {
                    sName = "Name: " + users.name;
                    sEmail = "Email: " + users.email;
                    sPhone = "Phone Number: " + users.phonenum;
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
    }*/
    private void checkList(){
        String userID = User.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                if(user!=null){
                    appointments = user.getAppointments();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}