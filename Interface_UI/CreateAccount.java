package com.example.phgeemedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.phgeemedapp.databinding.ActivityCreateAccountBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {
    private Button button;
    private Button create;
    ActivityCreateAccountBinding binding;
    String name, email, phonenum,username, password;
    FirebaseDatabase userDB;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //create = (Button) findViewById(R.id.create);
        binding.create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = binding.name.getText().toString();
                email = binding.email.getText().toString();
                phonenum = binding.Phone.getText().toString();
                username = binding.username2.getText().toString();
                password = binding.password2.getText().toString();

                if(!name.isEmpty() && !email.isEmpty() && !phonenum.isEmpty() && !username.isEmpty() && !password.isEmpty()){
                    Users users = new Users(name, email, phonenum, username, password);
                    userDB = FirebaseDatabase.getInstance();
                    reference = userDB.getReference("Users");

                    reference.child(username).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            binding.name.setText("");
                            binding.email.setText("");
                            binding.Phone.setText("");
                            binding.username2.setText("");
                            binding.password2.setText("");
                            Toast.makeText( CreateAccount.this, "Successfully created Account", Toast.LENGTH_SHORT).show();
                            openMainActivity();
                        }
                    });
                }
            }
        });


        button = (Button) findViewById(R.id.back2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

    }
    public void openMainActivity(){
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);

    }
}