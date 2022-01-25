package com.example.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DoctorPrescription extends AppCompatActivity {
    private TextView t;
    private String patcnic;
    Button button;
    EditText ed1;
    TextView textView,searchh;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef = db.collection("Appointments").document("Doctors");

   // private DocumentReference docRef1 = db.collection("Users").document("Patient");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_prescription);
        textView=(TextView)findViewById(R.id.textview44);
        button=(Button)findViewById(R.id.button4);
        searchh=(TextView)findViewById(R.id.searchbtn);
        ed1=(EditText)findViewById(R.id.search);
        String pid=ed1.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DoctorPrescription.this,DoctorDashboard.class);
                startActivity(i);
            }
        });

        searchh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                docRef.collection("Prescription").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String data3 = "";
                            StringBuilder data2=new StringBuilder();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                String doctmail = documentSnapshot.getString("demail");

                                String patientid = documentSnapshot.getString("patientcnic");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String email2 = user.getEmail();


                                if (email2.equals(doctmail)  ) {
                                    if( ed1.getText().toString().equals(patientid)) {
                                        String docname = documentSnapshot.getString("doctorname");
                                        String patname = documentSnapshot.getString("patientname");
                                        String pres = documentSnapshot.getString("prescription");
                                        String time = documentSnapshot.getString("time");

                                        data3 = "\nDr. " + docname +
                                                "\nPatient ID: " + patientid +
                                                "\nPatient Name: " + patname +
                                                "\nPrescription Given: " + pres +
                                                "\nDate and Time of the Appointment : " + time +
                                                "\n";
                                        data2.append(data3);

                                        textView.setText(data2.toString());
                                    }
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

        docRef.collection("Prescription").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String data = "";
                    StringBuilder data1=new StringBuilder();
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                        String doctmail = documentSnapshot.getString("demail");
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String email2 = user.getEmail();
                        if (email2.equals(doctmail)) {
                            String docname = documentSnapshot.getString("doctorname");
                            String patientid = documentSnapshot.getString("patientcnic");
                            String patname= documentSnapshot.getString("patientname");
                            String pres= documentSnapshot.getString("prescription");
                            String status = documentSnapshot.getString("status");
                            String time = documentSnapshot.getString("time");

                            data = "\nDr. " + docname +
                                    "\nPatient ID: " + patientid  +
                                    "\nPatient Name: " + patname  +
                                    "\nPrescription Given: " + pres +
                                    "\nDate and Time of the Appointment : " + time  +
                                    "\n";
                            data1.append(data);

                            textView.setText(data1.toString());

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
}