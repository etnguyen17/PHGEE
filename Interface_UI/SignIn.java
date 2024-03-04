package com.example.phgeemedapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {
    private Button button;
    private TextView password;
    private TextView username;
    private Button button2;
    private EditText edUsername;
    private EditText edPassword;
    private String adminUsername = "Admin";
    private String adminPassword = "Admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edUsername =(EditText) findViewById(R.id.username1);
        edPassword =(EditText) findViewById(R.id.password1);
        button = (Button) findViewById(R.id.Signin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String InputUser = edUsername.getText().toString();
                String InputPassword = edPassword.getText().toString();
                if(InputUser.isEmpty() || InputPassword.isEmpty()){
                    Toast.makeText(SignIn.this, "Enter Username and Password." , Toast.LENGTH_SHORT).show();
                } else if (validate(InputUser, InputPassword) == true) {
                    openHomepage();
                }
                else{
                    Toast.makeText(SignIn.this, "Incorrect Username and Password." , Toast.LENGTH_SHORT).show();
                }
            }
        });
        button = (Button) findViewById(R.id.forgotpass);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForgotPass();
            }
        });
        button = (Button) findViewById(R.id.create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreate();
            }
        });
        username = (TextView) findViewById(R.id.username1);
        password = (TextView) findViewById(R.id.password1);
        button = (Button) findViewById(R.id.clear);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setText("");
                username.setText("");
            }
        });

    }
    public void openHomepage(){
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);

    }
    public void openForgotPass(){
        Intent intent = new Intent(this, forgotPass.class);
        startActivity(intent);
    }
    public void openCreate(){
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }
    private boolean validate(String user, String pass){
        if(user.equals(adminUsername) && pass.equals(adminPassword)){
            return true;
        }
        return false;
    }
}