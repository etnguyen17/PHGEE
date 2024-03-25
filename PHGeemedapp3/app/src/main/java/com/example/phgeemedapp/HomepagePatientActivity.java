package com.example.phgeemedapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
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

public class HomepagePatientActivity extends AppCompatActivity {
    private Button button;
    TextView editName, barName, editEmail, editPhone;

    String sName, sName2, sEmail, sPhone;
    FirebaseAuth auth;
    FirebaseUser user;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_patient);
        drawerLayout = findViewById(R.id.drawer_layout2);
        navigationView = findViewById(R.id.nav_view2);
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        user = auth.getCurrentUser();
        findViewById(R.id.menuIcon2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
        });
        NavController navController = Navigation.findNavController(this,R.id.navigationHostFragment2);
        NavigationUI.setupWithNavController(navigationView,navController);
        findViewById(R.id.moreIcon2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Going to PatientPage", Toast.LENGTH_SHORT).show();
                PopupMenu popupMenu = new PopupMenu(HomepagePatientActivity.this, v);
                popupMenu.getMenuInflater().inflate(R.menu.dots_menu2, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();
                        if(itemId== R.id.logoutUser2){
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(getApplicationContext(), SignIn.class);
                            startActivity(intent);
                            Toast.makeText(HomepagePatientActivity.this, "You have been signed out", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        updateNavHeader();


      /*  if (firebaseUser == null) {
            Toast.makeText(Homepage.this, "Error", Toast.LENGTH_LONG).show();
        } else {
            showUserProfile(firebaseUser);
        }*/
        }
    /*private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("user");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                if (users != null) {
                    sName = "Name: " + users.name;
                    // sName2 = "Hi," + users.name;
                    sEmail = "Email: " + users.email;
                    sPhone = "Phone Number: " + users.phonenum;
                    editName.setText(sName);
                    // barName.setText(sName);
                    editEmail.setText(sEmail);
                    editPhone.setText(sPhone);
                } else {

                    editName.setText("Name-Error, Couldn't reach user info");
                    editEmail.setText("Email-Error");
                    editPhone.setText(userID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }*/

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //@Override
    /*public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.logoutUser) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), SignIn.class);
            startActivity(intent);
            finish();
        }
        //drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }*/

    public void updateNavHeader() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        View headerView = navigationView.getHeaderView(0);
        TextView navName = headerView.findViewById(R.id.nav_name);
        String userID = user.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("user2");
        referenceProfile.child("Patients").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patient patient = snapshot.getValue(patient.class);
                if (patient != null) {
                    navName.setText("Hi, " +patient.pemail);
                }
                else{
                    navName.setText("No username");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dots_menu2, menu);
        return true;
    }
}