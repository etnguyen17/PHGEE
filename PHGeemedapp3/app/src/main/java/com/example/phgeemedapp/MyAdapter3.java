package com.example.phgeemedapp;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder2> {

    FirebaseAuth auth;
    Context context;
    ArrayList<Users> list;

    MyAdapter3.OnButtonClickListener buttonClickListener;

    public interface OnButtonClickListener {
        void onButtonClick(int position);
    }

    public void setOnButtonClickListener(MyAdapter3.OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    @NonNull
    @Override
    public MyAdapter3.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item3, parent,false);
        return new MyAdapter3.MyViewHolder2((v));
    }

    public MyAdapter3(Context context, ArrayList<Users> list) {
        this.context = context;
        this.list = list;
        auth = FirebaseAuth.getInstance();
    }
    @Override
    public void onBindViewHolder(@NonNull MyAdapter3.MyViewHolder2 holder, @SuppressLint("RecyclerView") int position) {
        Users user = list.get(position);
        if(user!=null) {
            holder.fullName.setText(user.name);
        }

        holder.fullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch new activity here
                openDetailsPage(user);
            }
        });

        holder.bRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickListener != null) {
                    buttonClickListener.onButtonClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount(){ return list.size();}
    public static class MyViewHolder2 extends RecyclerView.ViewHolder{

        TextView fullName;
        Button bRemove;

        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.namepatient);
            bRemove = itemView.findViewById(R.id.selectbutton);
        }
    }
    public void filterList(ArrayList<Users> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
    private void openDetailsPage(Users user) {
        // Open the new activity to view user details
       // Intent intent = new Intent(context, patientInfoPage.class); // Replace UserDetailsActivity with the actual activity class
        // Pass any necessary data to the new activity using intent extras
        //NEED TO CHANGE BOTTOM TEXT, SO THE PAGES COULD CARRY USER INFO, or something similar
        //intent.putExtra("userId", user.getId()); // Example of passing user ID
       // context.startActivity(intent);
    }
}
