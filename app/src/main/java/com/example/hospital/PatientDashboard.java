package com.example.hospital;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class PatientDashboard extends AppCompatActivity {


    TextView tvPGreet;
    Button btnApt, btnPPrescription, btnComments, btnPtLogOut,btnBookedSlots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
        final String pname = getIntent().getStringExtra("name");
        final String pid = getIntent().getStringExtra("pid");
        tvPGreet = findViewById(R.id.tvPGreet);
        btnPPrescription = findViewById(R.id.btnPPrescription);
        btnApt = findViewById(R.id.btnApt);
        btnPtLogOut = findViewById(R.id.btnPtLogOut);
        btnBookedSlots=findViewById(R.id.btnslot);
        tvPGreet.setText("Welcome!!");



        btnApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PatientDashboard.this, AllSlots.class);
                i.putExtra("pid",pid);
                i.putExtra("pname",pname);
                startActivity(i);
            }
        });

        btnPPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PatientDashboard.this, P_Prescriptions.class);
                i.putExtra("pid",pid);
                i.putExtra("pname",pname);
                startActivity(i);
            }
        });
        btnBookedSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PatientDashboard.this,Slot.class);
                i.putExtra("ID",pid);
                i.putExtra("pname",pname);
                startActivity(i);
            }
        });

        btnPtLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PatientDashboard.this);

                builder.setMessage("Do you want to exit ?");

                builder.setTitle("Alert !");

                builder.setCancelable(false);

                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which)
                    {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(PatientDashboard.this, "Log Out Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PatientDashboard.this, LoginActivity.class));
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