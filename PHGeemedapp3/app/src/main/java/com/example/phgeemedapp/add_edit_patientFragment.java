package com.example.phgeemedapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phgeemedapp.databinding.FragmentPersonInfoBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class add_edit_patientFragment extends Fragment {
    Button button;
    EditText editTextPatientID,editTextFirstName, editTextMiddleName, editTextLastName, editTextPEmail, editTextDateBirth, editTextbloodType, editTextRHfactor, editTextmaritalStatus,
            editTextage, editTextphone, editTextmobile,
            editTextememail, editTextemName, editTextemPhone, editTextcurrentIllnesses, editTextpreviousIllnesses, editTextallergies;
    TextView textView;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    patient patient;
    String patientID, firstName, middleName, lastName,pemail, dateBirth, bloodType, RHfactor, maritalStatus,
            age, phone, mobile, ememail, emName, emPhone, currentIllnesses, previousIllnesses, allergies;
    static final String USER = "Patient";
    FragmentPersonInfoBinding binding;

    public add_edit_patientFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_add_edit_patient, container, false);
        mAuth = FirebaseAuth.getInstance();
        button = view.findViewById(R.id.button8);
        editTextPatientID = view.findViewById(R.id.PatientID);
        editTextFirstName = view.findViewById(R.id.firstname);
        editTextMiddleName = view.findViewById(R.id.middle);
        editTextLastName = view.findViewById(R.id.lastname);
        editTextPEmail = view.findViewById(R.id.patientEmail);
        editTextDateBirth = view.findViewById(R.id.dateBirth);
        editTextbloodType = view.findViewById(R.id.bloodgroup);
        editTextRHfactor = view.findViewById(R.id.rhFactor);
        editTextmaritalStatus = view.findViewById(R.id.maritalStat);
        editTextage = view.findViewById(R.id.age);
        editTextphone = view.findViewById(R.id.phoneNumber2);
        editTextmobile = view.findViewById(R.id.mobilePhone);
        editTextememail = view.findViewById(R.id.emEmail);
        editTextemName = view.findViewById(R.id.emFullName);
        editTextemPhone = view.findViewById(R.id.emPhone);
        editTextcurrentIllnesses = view.findViewById(R.id.currentIll);
        editTextpreviousIllnesses = view.findViewById(R.id.previousIll);
        editTextallergies = view.findViewById(R.id.allergies);
        mAuth = FirebaseAuth.getInstance();
        //database = FirebaseDatabase.getInstance();
        //reference = database.getReference(USER);
        binding = FragmentPersonInfoBinding.inflate(getLayoutInflater());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientID = editTextPatientID.getText().toString();
                firstName = editTextFirstName.getText().toString();
                middleName = editTextMiddleName.getText().toString();
                lastName = editTextLastName.getText().toString();
                pemail = editTextPEmail.getText().toString();
                dateBirth = editTextDateBirth.getText().toString();
                bloodType = editTextbloodType.getText().toString();
                RHfactor = editTextRHfactor.getText().toString();
                maritalStatus = editTextmaritalStatus.getText().toString();
                age = editTextage.getText().toString();
                phone = editTextphone.getText().toString();
                mobile = editTextmobile.getText().toString();
                ememail = editTextememail.getText().toString();
                emName = editTextemName.getText().toString();
                emPhone = editTextemPhone.getText().toString();
                currentIllnesses = editTextcurrentIllnesses.getText().toString();
                previousIllnesses = editTextpreviousIllnesses.getText().toString();
                allergies = editTextallergies.getText().toString();
                if(!patientID.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && !dateBirth.isEmpty() && !pemail.isEmpty() && !currentIllnesses.isEmpty() && !previousIllnesses.isEmpty() && !allergies.isEmpty()){
                    patient = new patient(patientID, firstName, middleName, lastName, pemail, dateBirth, bloodType, RHfactor, maritalStatus, age, phone, mobile, ememail, emName, emPhone, currentIllnesses, previousIllnesses, allergies);
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("Patients");
                    reference.child(pemail).setValue(patient).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            editTextPatientID.setText("");
                            editTextFirstName.setText("");
                            editTextMiddleName.setText("");
                            editTextLastName.setText("");
                            editTextPEmail.setText("");
                            editTextDateBirth.setText("");
                            editTextbloodType.setText("");
                            editTextRHfactor.setText("");
                            editTextmaritalStatus.setText("");
                            editTextage.setText("");
                            editTextphone.setText("");
                            editTextmobile.setText("");
                            editTextememail.setText("");
                            editTextemName.setText("");
                            editTextemPhone.setText("");
                            editTextcurrentIllnesses.setText("");
                            editTextpreviousIllnesses.setText("");
                            editTextallergies.setText("");
                            Toast.makeText(getActivity(), "Successfully added patient", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
        return view;
    }
}