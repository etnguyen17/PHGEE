package com.example.phgeemedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPass extends AppCompatActivity {
    private Button button, submit;
    EditText edEmail, edNumber;
    String Email, Number;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        submit = findViewById(R.id.submit1);
        edEmail = findViewById(R.id.EmailAddress);
        edNumber = findViewById(R.id.ResetNumber);
        mAuth = FirebaseAuth.getInstance();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = edEmail.getText().toString().trim();
                Number = edNumber.getText().toString().trim();
                if(!TextUtils.isEmpty(Email) && !TextUtils.isEmpty(Number)){
                    ResetPasswordByEmail();
                }
                else if(!TextUtils.isEmpty(Email) && TextUtils.isEmpty(Number)){
                    ResetPasswordByEmail();
                }
                else if(TextUtils.isEmpty(Email) && !TextUtils.isEmpty(Number)){
                    ResetPasswordByEmail();
                }
                else{
                    edEmail.setError("Email cannot be empty");
                    edNumber.setError("Phone Number cannot be empty");
                }
            }
        });
        button = (Button) findViewById(R.id.back1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);

    }
    private void ResetPasswordByEmail(){
        submit.setVisibility(View.INVISIBLE);
        mAuth.sendPasswordResetEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(forgotPass.this, "Reset  Password link has been sent to your registered Email", Toast.LENGTH_SHORT);
                        openMainActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(forgotPass.this, "Error"+ e.getMessage(), Toast.LENGTH_SHORT);
                    }
                });
    }
}