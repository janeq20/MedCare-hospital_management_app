package com.example.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.auth.FirebaseAuth;

import android.widget.EditText;



public class P_Prescriptions extends AppCompatActivity {

    private TextView t;
    private String patcnic;
    Button button;
    TextView textView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference patRef = db.collection("Appointments").document("Patient");

    private DocumentReference patRef1 = db.collection("Users").document("Patient");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pprescriptions);
        textView=(TextView)findViewById(R.id.textview44);
        button=(Button)findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(P_Prescriptions.this,PatientDashboard.class);
                startActivity(i);
            }
        });


                patRef1.collection("Patients").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                String patemail = documentSnapshot.getString("email");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String email2 = user.getEmail();
                                if (email2.equals(patemail)) {
                                    patcnic = documentSnapshot.getString("pid");

                                }
                            }
                        }
                    }
                });

                patRef.collection("Prescription").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String data = "";
                            StringBuilder data1=new StringBuilder();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                String patientcnic = documentSnapshot.getString("patientcnic");

                                if (patcnic.equals(patientcnic)) {

                                    Toast.makeText(getApplicationContext(), "Helooo", Toast.LENGTH_SHORT).show();
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