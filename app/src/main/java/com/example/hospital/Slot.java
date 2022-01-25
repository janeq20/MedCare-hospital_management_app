package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Slot extends AppCompatActivity {

    private TextView t;
    private String patcnic;

    EditText Pid;
    String PID;
    Button button;
    TextView textView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference patRef = db.collection("Appointments").document("Patient");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot);
       Pid=(EditText)findViewById(R.id.editTextTextPersonName);
       textView=(TextView)findViewById(R.id.slotsdisp);
       button=(Button)findViewById(R.id.button);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               textView.setText(Pid.getText());


        patcnic=Pid.getText().toString();

        patRef.collection("Patients").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String data = "";
                    StringBuilder data1=new StringBuilder();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        String patientcnic = documentSnapshot.getString("patientcnic");

                        if (patcnic.equals(patientcnic)) {
                            String docname = documentSnapshot.getString("doctorname");

                            String patname = documentSnapshot.getString("patientname");
                            String mobnum = documentSnapshot.getString("doctorcontact");
                            String status = documentSnapshot.getString("status");
                            String time = documentSnapshot.getString("time");

                            data = "\nDr. " + docname +
                                    "\nPatient ID: " + patientcnic +
                                    "\nAppointment Time: " + time +
                                    "\nPatient Name: "+patname+
                                    "\nAppointment Status: " + status +
                                    "\n";
                            data1.append(data);

                           // textView.setText(data.replace("\\n", "\n"));
                            textView.setText(data1);

                            if (status.equals("Accepted"))
                                textView.setBackgroundColor(Color.parseColor("#7FFF00"));
                            else if (status.equals("Declined"))
                                textView.setBackgroundColor(Color.parseColor("#FF0000"));


                        }


                         else {
                           continue;
                       }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot load patients' data", Toast.LENGTH_SHORT).show();
                }
            }
        });
           }
       });
    }
}