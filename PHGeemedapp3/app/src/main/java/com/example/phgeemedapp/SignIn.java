package com.example.phgeemedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/* added
 buildFeatures{
        viewBinding = true;
    }
   to build.gradle.kts(Module : apps) remove if it causes problems
*/

public class SignIn extends AppCompatActivity {
    private Button clear,createAccount,forgot;

    EditText editTextEmail, editTextPassword;
    Button buttonLogin;

    TextView textView;

    FirebaseAuth mAuth;
    String email, password;

    @Override
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
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.username1);
        editTextPassword = findViewById(R.id.password1);
        buttonLogin = findViewById(R.id.Signin);
        createAccount = findViewById(R.id.create);
        clear = findViewById(R.id.clear);
        forgot = findViewById(R.id.forgotpass);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextEmail.setText("");
                editTextPassword.setText("");
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgotPass();
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
                startActivity(intent);
                finish();

            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(SignIn.this, "Enter email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignIn.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("user2");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    //assert user != null;
                                    String userID = user.getUid();
                                    referenceProfile.child("Doctor and Nurse").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Users users = snapshot.getValue(Users.class);
                                            if (users != null) {
                                                String temp = users.role;
                                                if(temp.equals("Doctor")||temp.equals("Nurse")){
                                                    Intent intent = new Intent(getApplicationContext(), Homepage.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    referenceProfile.child("Patients").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            patient patient = snapshot.getValue(patient.class);
                                            if(patient != null){
                                                String tempemail =  patient.pemail;
                                                if (email.equals(tempemail)) {
                                                    Toast.makeText(getApplicationContext(), "Going to PatientPage", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(), HomepagePatientActivity.class);
                                                    startActivity(intent);
                                                    
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignIn.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });


    }

    public void openForgotPass(){
        Intent intent = new Intent(this, forgotPass.class);
        startActivity(intent);
    }

}