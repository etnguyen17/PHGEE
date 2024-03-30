package com.example.phgeemedapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phgeemedapp.databinding.FragmentPersonInfoBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class personInfoFragment extends Fragment {
    TextView editName,editEmail, editPhone,edPass;
    Button bName, bEmail, bPhone, bPass;
    ImageView edProfilePic;
    FirebaseAuth auth;
    FirebaseUser user;
    Users users;
    String sName, sEmail, sPhone,sPass, name, email, phone, pass, role;
    public personInfoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person_info, container, false);
        editName = view.findViewById(R.id.name);
        editEmail = view.findViewById(R.id.email2);
        editPhone = view.findViewById(R.id.phonenum);
        edPass = view.findViewById(R.id.pass);
        bName = view.findViewById(R.id.edname);
        bEmail = view.findViewById(R.id.edemail);
        bPhone = view.findViewById(R.id.edPhone);
        bPass = view.findViewById(R.id.edpass);
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser == null) {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
        } else {
            //role = checkType(firebaseUser);
            showUserProfile(firebaseUser);
        }
       /*TRYING TO MAKE IS YOU CAN CHANGE THE PERSONAL INFO OF THE USER, DOENT WORK CORRECTY STARTS OF NOT EDITABLE
        when you click edit then you are able to edit Name but cant save and stop editing you edit the whole thing.*/
        bName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bName.getText().toString().equals("edit")){
                    editName.setEnabled(true);
                editName.setFocusableInTouchMode(true);
                }
                bName.setText("save");
                String textbname = bName.getText().toString();
                if(textbname.equals("save")){
                    bName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            bName.setText("edit");
                            String userID = firebaseUser.getUid();
                            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
                            referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Users users = snapshot.getValue(Users.class);
                                    //patient patient = snapshot.getValue(patient.class);

                                    if (users != null) {
                                        sName = editName.getText().toString();
                                        name = sName;
                                        email = users.email;
                                        phone = users.phonenum;
                                        pass = users.password;
                                        role = users.role;
                                        users = new Users(name,email,phone, pass,role);
                                        referenceProfile.child(userID).setValue(users);
                                        editName.setText(sName);
                                    } else {
                                        editName.setText("Name-Error, Couldn't reach user info");
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            editName.setEnabled(false);
                            editName.setFocusableInTouchMode(false);
                        }
                    });

                }
            }
        });
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
                    sName = "Name: " + users.name;
                    sEmail = "Email: " + users.email;
                    sPhone = "Phone Number: " + users.phonenum;
                    sPass = "Password: " + users.password;
                    editName.setText(sName);
                    editEmail.setText(sEmail);
                    editPhone.setText(sPhone);
                    edPass.setText(sPass);
                } else {
                    editName.setText("Name-Error, Couldn't reach user info");
                    editEmail.setText("Email-Error");
                    editPhone.setText("Phone-Error");
                    editPhone.setText("Pass-Error");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}