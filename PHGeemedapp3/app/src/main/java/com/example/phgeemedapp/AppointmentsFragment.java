package com.example.phgeemedapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.usage.NetworkStats;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AppointmentsFragment extends Fragment {
    public AppointmentsFragment() {
        // Required empty public constructor
    }
    TextView PatientName;
    MyAdapter3 myAdapter3;
    ArrayList<Users> list;
    List<appointment> appointments;
    RecyclerView recyclerView;
    FirebaseAuth auth;
    FirebaseUser User;
    String sPatientNames, name, dateText, timeText;
    Button button;
    ImageButton datebutton, timebutton;
    TextView Date, time;
    appointment appointment;
    int size;
    //appointment appointment = new appointment(Date.toString(), time.toString());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointments, container, false);
        button = view.findViewById(R.id.button10);
        datebutton = view.findViewById(R.id.dateset);
        timebutton = view.findViewById(R.id.settime);
        Date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        auth = FirebaseAuth.getInstance();
        PatientName = view.findViewById(R.id.selectedpatient);
        recyclerView = view.findViewById(R.id.userList3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();
        appointments = new ArrayList<>();
        User = auth.getCurrentUser();
        checkList();
       // if(User!=null) {
       //     getSize(User);
       // }

        myAdapter3 = new MyAdapter3(getActivity(),list);
        recyclerView.setAdapter(myAdapter3);

        myAdapter3.setOnButtonClickListener(new MyAdapter3.OnButtonClickListener() {
            @Override
            public void onButtonClick(int position) {
                // Handle button click event here
                Users user = list.get(position);
                name = user.getName();
                PatientName.setText("Patient Name: " + name);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User = auth.getCurrentUser();
                if(name != null && dateText != null && timeText != null) {
                    appointment = new appointment(name, dateText, timeText);
                    if (appointments == null) {
                        appointments = new ArrayList<>();
                    }
                    appointments.add(appointment);
                }
                else{
                    Toast.makeText(getActivity(), "Did not select Patient or date or time.", Toast.LENGTH_LONG).show();
                }
                if (User != null) {
                    String userID = User.getUid();
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
                    //updateSize();
                   // referenceProfile.child(userID).child("Total Appointments").setValue(String.valueOf(size));
                    referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Users user = snapshot.getValue(Users.class);
                            if(user != null){
                                user.setAppointments(appointments);
                                referenceProfile.child(userID).setValue(user);
                                Toast.makeText(getActivity(), "Appointment Added", Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker();
            }
        });
        timebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker();
            }
        });
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser == null) {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
        } else {
            showUserProfile(firebaseUser);
        }
        return view;
    }

    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        Calendar mcalendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);
        int maxYear = mcalendar.get(Calendar.YEAR);
        int maxMonth = mcalendar.get(Calendar.MONTH)+3;
        int maxDate = mcalendar.get(Calendar.DATE) ;
        mcalendar.set(maxYear, maxMonth, maxDate);
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                dateText = DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString();
                Date.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.getDatePicker().setMaxDate(mcalendar.getTimeInMillis());
        datePickerDialog.show();
    }
    private void openTimePicker(){
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(getActivity());
        TimePickerDialog timePickerDialog = new TimePickerDialog(requireActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                timeText = DateFormat.format("hh:mm", calendar1).toString();
                time.setText(timeText);

            }
        }, 15, 30, true);

        timePickerDialog.show();
    }
    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                //patient patient = snapshot.getValue(patient.class);
                if (users != null) {
                    sPatientNames = "";
                    List<Users> PatientList = users.getPatients();
                    list.clear();
                    if (users.getPatients()==null) {
                        PatientName.setText("Empty List");
                    }
                    else{
                        for(Users Patients : PatientList) {
                            list.add(Patients);
                        }
                        myAdapter3.notifyDataSetChanged();

                    }
                } else {
                    PatientName.setText("Error");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void checkList(){
        String userID = User.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                if(user!=null){
                    appointments = user.getAppointments();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
   /* private void getSize(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                //patient patient = snapshot.getValue(patient.class);
                if (users != null) {
                    size = Integer.parseInt(users.getTotalAppointments());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
    /*private void updateSize(){
        String userID = User.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users3");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user = snapshot.getValue(Users.class);
                if(user!=null) {
                    user.setTotalAppointments(String.valueOf(size));
                    referenceProfile.child(userID).setValue(user);
                    Log.d("Size", String.valueOf(size));
                    Log.d("Size", user.getTotalAppointments());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
}