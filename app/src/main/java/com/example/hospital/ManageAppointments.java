package com.example.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ManageAppointments extends AppCompatActivity {

    private TextView t;
    private String patcnic,docid;
    Button button,acc,dec;
    EditText ed1;
    TextView textView,searchh;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef = db.collection("Appointments").document("Doctors");
    private DocumentReference patRef = db.collection("Appointments").document("Patient");

    // private DocumentReference docRef1 = db.collection("Users").document("Patient");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_appointments);
        textView=(TextView)findViewById(R.id.textview44);
        button=(Button)findViewById(R.id.button4);
        searchh=(TextView)findViewById(R.id.searchbtn);
        ed1=(EditText)findViewById(R.id.search);
        String pid=ed1.getText().toString();
        acc=(Button)findViewById(R.id.accept) ;
        dec=(Button)findViewById(R.id.reject) ;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ManageAppointments.this,DoctorDashboard.class);
                startActivity(i);
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String docemail= user.getEmail();
        patRef.collection("Patients").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

                                String docname = documentSnapshot.getString("doctorname");
                                String patname = documentSnapshot.getString("patientname");
                                String time = documentSnapshot.getString("time");
                                String status = documentSnapshot.getString("status");

                                data3 = "\nDr. " + docname +
                                        "\nPatient ID: " + patientid +
                                        "\nPatient Name: " + patname +
                                        "\nStatus: " + status +
                                        "\nDate and Time of the Appointment : " + time +
                                        "\n";
                                data2.append(data3);

                                textView.setText(data2.toString());

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


        acc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                patRef.collection("Patients").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            String data3 = "";
                            StringBuilder data2=new StringBuilder();

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                String doctmail = documentSnapshot.getString("demail");

                                String patientid = documentSnapshot.getString("patientcnic");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String email2 = user.getEmail();


                                if (email2.equals(doctmail)  ) {

                                    if( ed1.getText().toString().equals(patientid)) {
                                        String docname = documentSnapshot.getString("doctorname");
                                        String patname = documentSnapshot.getString("patientname");
                                        //String pres = documentSnapshot.getString("prescription");
                                        String time = documentSnapshot.getString("time");
                                        String status1 = documentSnapshot.getString("status");
                                        Map<String, Object> status = new HashMap<>();
                                        data3 = "\nDr. " + docname +
                                                "\nPatient ID: " + patientid +
                                                "\nPatient Name: " + patname +
                                                "\nStatus: " + status1 +
                                                "\nDate and Time of the Appointment : " + time +
                                                "\n";
                                        data2.append(data3);

                                        textView.setText(data2.toString());
                                      //  status.put("status", "Accepted");
                                        patRef.collection("Patients").document(patientid).update("status","Accepted");
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
        dec.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                patRef.collection("Patients").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            String data3 = "";
                            StringBuilder data2=new StringBuilder();

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                String doctmail = documentSnapshot.getString("demail");

                                String patientid = documentSnapshot.getString("patientcnic");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String email2 = user.getEmail();


                                if (email2.equals(doctmail)  ) {

                                    if( ed1.getText().toString().equals(patientid)) {
                                        String docname = documentSnapshot.getString("doctorname");
                                        String patname = documentSnapshot.getString("patientname");
                                        //String pres = documentSnapshot.getString("prescription");
                                        String time = documentSnapshot.getString("time");
                                        String status1 = documentSnapshot.getString("status");
                                        Map<String, Object> status = new HashMap<>();
                                        data3 = "\nDr. " + docname +
                                                "\nPatient ID: " + patientid +
                                                "\nPatient Name: " + patname +
                                                "\nStatus: " + status1 +
                                                "\nDate and Time of the Appointment : " + time +
                                                "\n";
                                        data2.append(data3);

                                        textView.setText(data2.toString());
                                        //  status.put("status", "Accepted");
                                        patRef.collection("Patients").document(patientid).update("status","Declined");
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






    }
}
/*



 */