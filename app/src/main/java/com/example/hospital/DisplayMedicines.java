package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DisplayMedicines extends AppCompatActivity {
    private TextView t1,t3;
    private String medname,meddesc,price,quantity,data,quantity1,patcnic;
    StringBuilder fulldata=new StringBuilder();
    EditText patID;
    private Spinner spinner_medicine;
    Button btn3;
    private EditText e1;
    private Button b1;
    ScrollView sc;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference md=db.collection("Medicines");
    private DocumentReference patRef = db.collection("Appointments").document("Patient");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_medicines);
        t1=(TextView)findViewById(R.id.medicine_name);
        e1=(EditText)findViewById(R.id.med_e1);
        b1=(Button)findViewById(R.id.med_btn);
   //     sc=(ScrollView) findViewById(R.id.scv);
        btn3=(Button)findViewById(R.id.button3);
        patID=(EditText)findViewById(R.id.patid);
        t3=(TextView)findViewById(R.id.textview);
        spinner_medicine=(Spinner)findViewById(R.id.spinner_medicine);
        setSpinner();
    }
    private void setSpinner(){
        ArrayAdapter<CharSequence> adapter_gender = ArrayAdapter.createFromResource(getApplicationContext(), R.array.meds, android.R.layout.simple_spinner_item);
        adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_medicine.setAdapter(adapter_gender);
        spinner_medicine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                medname = parent.getItemAtPosition(position).toString();
                setTextView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setTextView(){
        if (medname.equals("Lipitor")){
            meddesc="Cholestrol-lowering statin drug";
            price="$7.2 billion";
        }
        if (medname.equals("Nexium")){
            meddesc="Antacid drug";
            price="$6.3 billion";
        }
        if (medname.equals("Plavix")){
            meddesc="Blood thinner";
            price="$6.1 billion";
        }
        if (medname.equals("Avair Diskus")){
            meddesc="Asthma Inhaler";
            price="$4.7 billion";
        }
        if (medname.equals("Abilify")){
            meddesc="Antipsychotic drug";
            price="$4.6 billion";
        }
        md.document(medname).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                quantity = documentSnapshot.getString("quantity");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                quantity=" ";
            }
        });

        data = "Medicine: " + medname +
                "\nPrice per med: " + price +
                "\nDescription: " + meddesc +
                "\nQuantity: " + quantity +
                "\n";

        t1.setText(data.replace("\\n", "\n"));

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity1=e1.getText().toString();

                if (quantity1.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter quantity to set", Toast.LENGTH_SHORT).show();
                }
                else{
                    Map<String,Object> med=new HashMap<>();
                  //  med.put("quantity",quantity1);
                    data = "Medicine: " + medname +
                            "\nPrice per med: " + price +
                            "\nDescription: " + meddesc +
                            "\nQuantity: " +quantity1 +
                            "\n";

                //    md.document(medname).set(med);
               //     md.document(medname).update("quantity",val);
                    fulldata.append(data);
                    //t1.setText(data.replace("\\n", "\n"));
                    t3.setText(fulldata);
                }

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patcnic=patID.getText().toString();
                patRef.collection("Patients").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            String data = "";
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                String patientcnic = documentSnapshot.getString("patientcnic");

                                if (patcnic.equals(patientcnic)) {

                                    String docemail = documentSnapshot.getString("demail");

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String email2 = user.getEmail();
                                    if (Objects.equals(docemail, email2)) {


                                        Map<String, Object> status1 = new HashMap<>();


                                        String docname = documentSnapshot.getString("doctorname");
                                        String name_pat = documentSnapshot.getString("patientname");
                                        String status = documentSnapshot.getString("status");
                                        String time = documentSnapshot.getString("time");

                                        Map<String, Object> appointment = new HashMap<>();
                                        appointment.put("doctorname", docname);
                                        appointment.put("patientname", name_pat);
                                        appointment.put("patientcnic", patientcnic);
                                        appointment.put("prescription", fulldata.toString());
                                        appointment.put("demail", docemail);
                                        appointment.put("time", time);

                                        appointment.put("status", status);
                                        db.collection("Appointments").document("Patient").collection("Prescription").document().set(appointment);
                                        //appointment.put("doctorcnic",doccnic);
                                        db.collection("Appointments").document("Doctors").collection("Prescription").document().set(appointment);
                                        Toast.makeText(getApplicationContext(), "Completed", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(DisplayMedicines.this,DisplayMedicines.class);
                                        startActivity(intent);

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