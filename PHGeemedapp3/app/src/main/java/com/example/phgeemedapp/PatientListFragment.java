package com.example.phgeemedapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PatientListFragment extends Fragment {


    TextView DoctorsName, PatientName;
    RecyclerView recyclerView;

    FirebaseAuth auth;
    // FirebaseUser firebaseUser;
    String sName, sPatientNames;

    MyAdapter2 myAdapter2;
    ArrayList<Users> list;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientListFragment() {
        // Required empty public constructor
    }


    public static PatientListFragment newInstance(String param1, String param2) {
        PatientListFragment fragment = new PatientListFragment();
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
        View view = inflater.inflate(R.layout.fragment_patient_list, container, false);
        DoctorsName = view.findViewById(R.id.textView17);
        PatientName = view.findViewById(R.id.textView14);

        auth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.userList2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();

        myAdapter2 = new MyAdapter2(this,list);
        recyclerView.setAdapter(myAdapter2);

        myAdapter2.setOnButtonClickListener(new MyAdapter2.OnButtonClickListener() {
            @Override
            public void onButtonClick(int position) {
                // Handle button click event here
                Users user = list.get(position);
                // Perform actions based on the clicked user
                String userID = auth.getCurrentUser().getUid();
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
                referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users currentUser = snapshot.getValue(Users.class);
                        if(currentUser != null){
                            int posNum = currentUser.getPosition(user);
                            String pos = String.valueOf(currentUser.getPosition(user));

                            if(posNum>=0 && posNum <currentUser.patientsList.size()){
                                currentUser.removePatient(user);
                                list.remove(user);

                                referenceProfile.child(userID).child("patients").removeValue()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                for(int i=0;i< currentUser.patientsList.size();i++) {
                                                    if (i >= posNum) {
                                                        referenceProfile.child(userID).child("patients").child(String.valueOf(i)).setValue(currentUser.patientsList.get(i + 1));

                                                    } else {
                                                        referenceProfile.child(userID).child("patients").child(String.valueOf(i)).setValue(currentUser.patientsList.get(i));

                                                    }
                                                }
                                                // Patient removed successfully from Firebase
                                                //currentUser.removePatient(user);
                                                myAdapter2.notifyDataSetChanged();
                                                Toast.makeText(getContext(), "Patient removed successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        })

                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Failed to remove patient from Firebase
                                            }
                                        });

                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser == null) {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
        } else {
            showUserProfile(firebaseUser);
        }

        return view;
    }
    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                //patient patient = snapshot.getValue(patient.class);
                if (users != null) {
                    sName = "Doctor: " + users.getName();
                    DoctorsName.setText(sName);
                    sPatientNames = "";
                    List<Users> PatientList = users.getPatients();
                    list.clear();
                    if (users.getPatients()==null) {
                        PatientName.setText("Empty List");
                    }
                    else{
                        for(Users Patients : PatientList) {
                            list.add(Patients);
                        }
                        myAdapter2.notifyDataSetChanged();

                    }
                } else {
                    DoctorsName.setText("Error");
                    PatientName.setText("Error");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void openDetailsPage(Users user) {
        // Check if the current destination is not already PatientDetailFragment
        //NavController navController = NavHostFragment.findNavController(this);
        // NavDestination currentDestination = navController.getCurrentDestination();
        // if (currentDestination != null && currentDestination.getId() != R.id.patientDetailScroll1) {
        // Navigate to the destination fragment using Navigation Component
        //  navController.navigate(R.id.patientDetailScroll1);
        // }
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String userID = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users thisUser = snapshot.getValue(Users.class);
                String ref = user.getEmail();
                thisUser.setRef(ref);

                /*
                Users thisUser = snapshot.getValue(Users.class);
                thisUser.setSelectedUser(user);
                Log.d("User", "Retrieved user: " + thisUser.getName());
                Log.d("SelectedUser", "Selected user: " + thisUser.getSelectedUser());*/
                //
                //spaghetti code, didnt work how i wanted, but this works too
                //the intent.putExtra grabs the object and throws it to the next page,,had to put serializable implement on Users class
                Intent intent = new Intent(getContext(), PatientDetailScroll2.class);
                //intent.putExtra("SelectedUser", (Serializable) user);
                startActivity(intent);
                if (getActivity() != null) {
                    getActivity().finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}