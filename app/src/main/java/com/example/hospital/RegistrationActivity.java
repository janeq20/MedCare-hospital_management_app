package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {
    private TextView back;
    private Button doctorreg,patientreg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_registration);

       back=findViewById(R.id.back);
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(RegistrationActivity.this,LoginActivity.class);
               startActivity(intent);
           }
       });

       patientreg=findViewById(R.id.pat_registration);
       doctorreg=findViewById(R.id.doc_registration);



        doctorreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent=new Intent(RegistrationActivity.this,DoctorRegistrationActivity.class);
               Intent intent=new Intent(RegistrationActivity.this,DoctorRegistrationActivity.class);
                startActivity(intent);
            }
        });
        patientreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(RegistrationActivity.this,PatientRegistrationActivity.class);
                Intent intent=new Intent(RegistrationActivity.this,PatientRegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}