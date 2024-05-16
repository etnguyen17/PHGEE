package com.example.phgeemedapp;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;



import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder2> {

    FirebaseAuth auth;

    private PatientListFragment mFragment;
    private Context mContext;
    Context context;
    ArrayList<Users> list;

    MyAdapter2.OnButtonClickListener buttonClickListener;

    public interface OnButtonClickListener {
        void onButtonClick(int position);
    }

    public void setOnButtonClickListener(MyAdapter2.OnButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    @NonNull
    @Override
    public MyAdapter2.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.item2,parent,false);
        return new MyAdapter2.MyViewHolder2((v));
    }

    public MyAdapter2(PatientListFragment fragment, ArrayList<Users> list) {
        mFragment = fragment;
        this.list = list;
        auth = FirebaseAuth.getInstance();
    }
    @Override
    public void onBindViewHolder(@NonNull MyAdapter2.MyViewHolder2 holder, int position) {
        Users user = list.get(position);
        holder.fullName.setText(user.name);

        holder.fullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch new activity here
                mFragment.openDetailsPage(user);

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
            fullName = itemView.findViewById(R.id.textView15);
            bRemove = itemView.findViewById(R.id.button2);
        }
    }
    public void filterList(ArrayList<Users> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }
}