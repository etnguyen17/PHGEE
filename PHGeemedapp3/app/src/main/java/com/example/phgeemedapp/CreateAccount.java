package com.example.phgeemedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phgeemedapp.Homepage;
import com.example.phgeemedapp.Users;
//import com.example.phgeemedapp.databinding.ActivityCreateAccountBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {
    EditText editTextEmail, editTextPassword, editTextName, editTextPhone;

    Spinner spinner;
    Button buttonReg,back;

    TextView textView;

    FirebaseAuth mAuth;

    FirebaseDatabase database;

    DatabaseReference reference;
    static final String USER = "user2";
    static final String TAG = "RegisterActivity";
    String[] roles = new String[]{"Patient","Nurse","Doctor"};
    private DatabaseReference mDatabase;

    Users users;
    patient patient;
    //BasicInfo basicInfo;
    String name,email,phoneNumber,password,patientID, firstName, middleName, lastName,pemail, dateBirth,
            bloodType, RHfactor, maritalStatus, age, phone, mobile, ememail, emName, emPhone, currentIllnesses, previousIllnesses, allergies;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), Homepage.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword =findViewById(R.id.password2);
        buttonReg = findViewById(R.id.create);
        textView = findViewById(R.id.textView5);
        editTextName = findViewById(R.id.name);
        editTextPhone = findViewById(R.id.Phone);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String>adapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,roles);
        adapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item));
        spinner.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference(USER);
        mDatabase = FirebaseDatabase.getInstance().getReference("users2");
        back = findViewById(R.id.back2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
                finish();

            }
        });


        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password, name, phoneNumber,role,patientID, firstName, middleName, lastName,pemail, dateBirth,
                        bloodType, RHfactor, maritalStatus, age, phone, mobile, ememail, emName, emPhone, currentIllnesses, previousIllnesses, allergies;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                name = String.valueOf(editTextName.getText());
                phoneNumber = String.valueOf(editTextPhone.getText());
                role =String.valueOf(spinner.getSelectedItem().toString());
                patientID = "";
                firstName = "";
                middleName = "";
                lastName = "";
                pemail = String.valueOf(editTextEmail.getText());
                dateBirth = "";
                bloodType = "";
                RHfactor = "";
                maritalStatus = "";
                age = "";
                phone = String.valueOf(editTextPhone.getText());
                mobile = String.valueOf(editTextPhone.getText());
                ememail = "";
                emName = "";
                emPhone = "";
                currentIllnesses = "";
                previousIllnesses = "";
                allergies = "";
                FirebaseUser user = mAuth.getCurrentUser();
                //String userID = getUserID(user);
                users = new Users(name,email,phoneNumber,password);
                patient = new patient(patientID, firstName, middleName, lastName,pemail, dateBirth,
                        bloodType, RHfactor, maritalStatus, age, phone, mobile, ememail, emName, emPhone, currentIllnesses, previousIllnesses, allergies);
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(CreateAccount.this, "Enter email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(CreateAccount.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(CreateAccount.this, "Account created",
                                            Toast.LENGTH_SHORT).show();
                                    users.setRole(role);
                                    //Users.BasicInfo basicInfo = users.new BasicInfo();
                                    //reference.child(name).setValue(helperClass);
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    assert user != null;
                                    users.setUserID(user.getUid());
                                    if(role.equals("Doctor")||role.equals("Nurse")) {
                                        updateUIDoc(user);
                                    } else  {
                                        updateUIPatient(user);
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(CreateAccount.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

    }

    public void updateUIDoc(FirebaseUser currentUser){
        //Try AuthID to actually replace the new userID....This means we get the auth ID keys and paste it to the corresponding user ID keys
        //This relates to getting the correct info displayed from homepage
        String userID = currentUser.getUid();//THIS IS NEW
        String keyId = reference.push().getKey();
        reference.child("Doctors and Nurses").child(userID).setValue(users);
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
    }
    public void updateUIPatient(FirebaseUser currentUser){
        //Try AuthID to actually replace the new userID....This means we get the auth ID keys and paste it to the corresponding user ID keys
        //This relates to getting the correct info displayed from homepage
        String userID = currentUser.getUid();//THIS IS NEW
        String keyId = reference.push().getKey();
        reference.child("Patients").child(userID).setValue(patient);
        Intent intent = new Intent(this, HomepagePatientActivity.class);
        startActivity(intent);
    }
    public void writeNewUser() {

    }
    public String getUserID(FirebaseUser currentUser){
        return currentUser.getUid();
    }
}