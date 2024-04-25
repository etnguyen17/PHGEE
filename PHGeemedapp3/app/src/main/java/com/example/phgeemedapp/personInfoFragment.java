package com.example.phgeemedapp;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class personInfoFragment extends Fragment {
    TextView editName, editEmail, editPhone, edPass;
    Button bName, bEmail, bPhone, bPass;
    CircleImageView edProfilePic;
    private Uri imagePath;
    StorageReference userProfileImage;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    Users users;
    String sName, sEmail, sPhone, sPass, name, email, phone, pass, role, downloadURL;

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
        edProfilePic = view.findViewById(R.id.profilepic);
        userProfileImage = FirebaseStorage.getInstance().getReference().child("Profile Images");
        //bEmail = view.findViewById(R.id.edemail);
        // bPhone = view.findViewById(R.id.edPhone);
        //bPass = view.findViewById(R.id.edpass);
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null) {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
        } else {
            //role = checkType(firebaseUser);
            showUserProfile(firebaseUser);
        }
        edProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
                        intent.setAction(MediaStore.ACTION_PICK_IMAGES);
                    } else {
                        intent.setAction(Intent.ACTION_PICK);
                    }
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 1);
                } catch (ActivityNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
       /*TRYING TO MAKE IS YOU CAN CHANGE THE PERSONAL INFO OF THE USER, DOENT WORK CORRECTY STARTS OF NOT EDITABLE
        when you click edit then you are able to edit Name but cant save and stop editing you edit the whole thing.*/
        bName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference reference =userProfileImage.child(firebaseUser.getUid()+".jpg");
                if(imagePath!=null) {
                    reference.putFile(imagePath);
                }
                userProfileImage.child(firebaseUser.getUid()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("TAG", "Download url is:" +uri.toString());
                        downloadURL = uri.toString();
                        String userID = firebaseUser.getUid();
                        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
                        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Users users = snapshot.getValue(Users.class);
                                if(users!=null) {
                                    //users.setProfilePic(downloadURL);
                                   // referenceProfile.child(userID).setValue(users);
                                    sName = editName.getText().toString();
                                    name = sName;
                                    email = editEmail.getText().toString();
                                    phone = editPhone.getText().toString();
                                    pass = users.password;
                                    role = users.role;
                                    users.setName(sName);
                                    users.setEmail(email);
                                    users.setPhonenum(phone);
                                    users.setPassword(pass);
                                    users.setProfilePic(downloadURL);
                                   // users = new Users(name, email, phone, pass, role);
                                    //users.setProfilePic(downloadURL);
                                    // users.setProfilePic(downloadURL);
                                    referenceProfile.child(userID).setValue(users);
                                    editName.setText(sName);
                                    editEmail.setText(email);
                                    editPhone.setText(phone);
                                } else {
                                    editName.setText("Name-Error, Couldn't reach user info");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                /*String userID = firebaseUser.getUid();
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
                referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Users users = snapshot.getValue(Users.class);
                        //patient patient = snapshot.getValue(patient.class);

                        if (users != null) {
                            sName = editName.getText().toString();
                            name = sName;
                            email = editEmail.getText().toString();
                            phone = editPhone.getText().toString();
                            pass = users.password;
                            role = users.role;
                            users = new Users(name, email, phone, pass, role);
                            //users.setProfilePic(downloadURL);
                           // users.setProfilePic(downloadURL);
                            referenceProfile.child(userID).setValue(users);
                            editName.setText(sName);
                            editEmail.setText(email);
                            editPhone.setText(phone);
                        } else {
                            editName.setText("Name-Error, Couldn't reach user info");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
               // showUserProfile(firebaseUser);
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
                    sName = users.name;
                    sEmail = users.email;
                    sPhone = users.phonenum;
                    sPass = users.password;
                    downloadURL = users.profileURl;
                    editName.setText(sName);
                    editEmail.setText(sEmail);
                    editPhone.setText(sPhone);
                    edPass.setText(sPass);
                    if(downloadURL!= null){
                        Picasso.get().load(downloadURL).into(edProfilePic);
                    }
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data!=null){
            try{
                imagePath = data.getData();
            } catch(Exception e){
                Toast.makeText(getActivity(), "No Image Selected", Toast.LENGTH_SHORT).show();
            }
                edProfilePic.setImageURI(imagePath);
        }
    }
}