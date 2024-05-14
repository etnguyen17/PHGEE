package com.example.phgeemedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Users> list;
   // ArrayList<patient> list2;

    Button addButton;
    SearchView searchView;
    FirebaseAuth auth;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        auth = FirebaseAuth.getInstance();

        addButton = findViewById(R.id.bAddPatient);
        searchView = findViewById(R.id.bSearch);
        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference("users3");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        //list2 = new ArrayList<>();
        myAdapter = new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        myAdapter.setOnButtonClickListener(new MyAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(int position) {
                // Handle button click event here
                Users user = list.get(position);
                // Perform actions based on the clicked user
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission (if needed)
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query text change
                filter(newText);
                return true;
            }
        });



        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Users user = dataSnapshot.getValue(Users.class);
                    //only patients will be show on card is search patients
                    //assert user != null;
                    if(user!=null) {
                        if (Objects.equals(user.getRole(), "Patient")) {
                            list.add(user);

                        }
                    }
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button =(Button) findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {openHomepage();}
        });
    }

    private void filter(String text) {
        ArrayList<Users> filteredList = new ArrayList<>();
        for (Users user : list) {
            // Filter logic: Check if user's name contains the search query
            if (user.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(user);
            }
        }
        // Update the RecyclerView with the filtered list
        myAdapter.filterList(filteredList);
    }

    public  void openHomepage(){
        Intent intent = new Intent (this, Homepage.class);
        startActivity(intent);
        finish();
    }

}