package com.example.phgeemedapp;

import  android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    FirebaseAuth auth;
    Context context;
    ArrayList<Users> list;

    OnButtonClickListener buttonClickListener;

    public interface OnButtonClickListener {
        void onButtonClick(int position);
    }
    public MyAdapter(Context context, ArrayList<Users> list) {
        this.context = context;
        this.list = list;
        auth = FirebaseAuth.getInstance();
    }


    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder((v));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Users user = list.get(position);
        holder.fullName.setText(user.name);
        holder.email.setText((user.email));
        holder.phoneNumber.setText(user.phonenum);
        holder.sRole.setText(user.role);

        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickListener != null) {
                    buttonClickListener.onButtonClick(position);
                    String userID = auth.getCurrentUser().getUid();
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
                    referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Users currentUser = snapshot.getValue(Users.class);
                            if (currentUser != null) {
                                if (!containsPatient(currentUser.getPatients(), user)){
                                    currentUser.addPatient(user);
                                    referenceProfile.child(userID).setValue(currentUser); // Update user in database
                                    // Hide the add button to prevent adding the same patient multiple times
                                    holder.addButton.setVisibility(View.GONE);
                                    Toast.makeText(context, "Patient added successfully", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(context, "Patient already added", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(context, "Error: Current user not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(context, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fullName, email, phoneNumber,sRole;
        Button addButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.editTextListName);
            email = itemView.findViewById(R.id.editTextListEmail);
            phoneNumber = itemView.findViewById((R.id.editTextListPhone));
            sRole = itemView.findViewById(R.id.editTextListRole);
            addButton = itemView.findViewById(R.id.bAddPatient);
        }
    }


    public void filterList(ArrayList<Users> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
    private boolean containsPatient(List<Users> patientsList, Users patient) {
        for (Users p : patientsList) {
            if (p.getEmail().equals(patient.getEmail())){
                return true;
            }
        }
        return false;
    }
}
