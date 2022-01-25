package com.example.hospital;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PharmacistDashboard extends AppCompatActivity {
    private TextView t1,t3;
    private String medname,meddesc,price,quantity,data,quantity1;
    private Spinner spinner_medicine;

    private EditText e1;
    private Button b1,b3,logout;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference md=db.collection("Medicines");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacist_dashboard);
        t1=(TextView)findViewById(R.id.medicine_name);
        e1=(EditText)findViewById(R.id.med_e1);
        b1=(Button)findViewById(R.id.med_btn);
        b3=(Button)findViewById(R.id.button3);
        logout=(Button)findViewById(R.id.btnLogOut);
        t3=(TextView)findViewById(R.id.textView23);
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
                    med.put("quantity",quantity1);
                    data = "Medicine: " + medname +
                            "\nPrice per med: " + price +
                            "\nDescription: " + meddesc +
                            "\nQuantity: " + quantity1 +
                            "\n";

                   // md.document(medname).set(med);

                    md.document(medname).update("quantity",quantity1.toString());

                    t1.setText(data.replace("\\n", "\n"));

                    Toast.makeText(getApplicationContext(), "The quantity has been added successfully\n", Toast.LENGTH_SHORT).show();




                }

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PharmacistDashboard.this);

                builder.setMessage("Do you want to exit ?");

                builder.setTitle("Alert !");

                builder.setCancelable(false);

                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which)
                    {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(PharmacistDashboard.this, "Log Out Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PharmacistDashboard.this, LoginActivity.class));
                        finish();
                    }
                });

                // Set the Negative button with No name OnClickListener method is use of DialogInterface interface.
                builder
                        .setNegativeButton(
                                "No",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which)
                                    {
                                        // If user click no then dialog box is canceled.
                                        dialog.cancel();
                                    }
                                });


                AlertDialog alertDialog = builder.create();

                alertDialog.show();

            }
        });


    }
}