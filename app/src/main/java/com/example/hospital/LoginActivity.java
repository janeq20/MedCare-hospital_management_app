package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import android.widget.ProgressBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private TextView logq;
    private Spinner spinner_user;

    private Button et_log_buttonlog;

    private EditText et_cnic, et_password;

    private ProgressBar progressbar;
    private String email1,log_user, log_cnic, log_password, cloud_password, speciality, name;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EditText emailTextView, passwordTextView;
    private Button Btn;
    private FirebaseAuth mAuth;
    private CollectionReference docsRef = db.collection("Users").document("Doctor").collection("Doctors");
    private CollectionReference patref=db.collection("Users").document("Patient").collection("Patients");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        progressbar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        spinner_user = findViewById(R.id.spinner_user);

        emailTextView = findViewById(R.id.loginEmail);
        passwordTextView = findViewById(R.id.loginPassword);
        Btn = findViewById(R.id.loginButton);
        logq = findViewById(R.id.loginPageQ);


        et_cnic = (EditText) findViewById(R.id.loginnic);

        log_cnic=et_cnic.getText().toString();
       // et_password = (EditText)findViewById(R.id.loginPassword);
        setSpinnerUser();
        logq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent=new Intent(LoginActivity.this,RegistrationActivity.class);
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });




     Btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                    switch (log_user) {
                      case "Administrator":
           db.collection("Users").document("Admin").collection("Admins").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                          @Override
                          public void onComplete(@NonNull Task<QuerySnapshot> task) {
                              if (task.isSuccessful()) {
                                  String data = "";
                                  //Toast.makeText(getApplicationContext(),  et_cnic.getText().toString(), Toast.LENGTH_SHORT).show();
                                  for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                      log_password = passwordTextView.getText().toString();
                                      String cnicnum = documentSnapshot.getId();
                                      if (( et_cnic.getText().toString()).equals(cnicnum)) {

                                          cloud_password = documentSnapshot.getString("password");

                                          if (checkPassword()) {
                                              progressbar.setVisibility(View.GONE);
                                              Intent intent = new Intent(LoginActivity.this,AdminDashboard.class);
                                              startActivity(intent);
                                          }
                                      }
                                      else{
                                          progressbar.setVisibility(View.GONE);
                                           Toast.makeText(getApplicationContext(), "wrong id", Toast.LENGTH_SHORT).show();
                                      }

                                  }
                              }else {
                                  Toast.makeText(getApplicationContext(), "Cannot load doctors' data", Toast.LENGTH_SHORT).show();
                              }
                          }
                      });
                            break;

                      case "Doctor":
                          docsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                              @Override
                              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                  if (task.isSuccessful()) {
                                      String data = "";


                                     for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                          log_password = passwordTextView.getText().toString();
                                        //  String speciality = documentSnapshot.getString("speciality");
                                          String cnicnum = documentSnapshot.getId();
                                          if (( et_cnic.getText().toString()).equals(cnicnum)) {

                                              cloud_password = documentSnapshot.getString("dpassword");

                                              if (checkPassword()) {
                                                   email1 = documentSnapshot.getString("demail");

                                                  speciality = documentSnapshot.getString("speciality");
                                                  String fname = documentSnapshot.getString("firstname");
                                                  name = fname;
                                                  loginUserAccount();
                                                  Intent intent = new Intent(LoginActivity.this, DoctorDashboard.class);
                                                  startActivity(intent);
                                              }
                                          }
                                          else{
                                              progressbar.setVisibility(View.GONE);
                                              //   Toast.makeText(getApplicationContext(), "wrong id", Toast.LENGTH_SHORT).show();
                                          }

                                      }
                                  }else {
                                      Toast.makeText(getApplicationContext(), "Cannot load doctors' data", Toast.LENGTH_SHORT).show();
                                  }
                              }
                          });
                            break;

                       case "Patient":
                           patref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                               @Override
                               public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                   if (task.isSuccessful()) {
                                       String data = "";


                                       for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                           log_password = passwordTextView.getText().toString();
                                           String cnicnum = documentSnapshot.getId();
                                           if (( et_cnic.getText().toString()).equals(cnicnum)) {

                                               cloud_password = documentSnapshot.getString("password");

                                               if (checkPassword()) {
                                                   email1 = documentSnapshot.getString("email");

                                                   String fname = documentSnapshot.getString("name");
                                                   name = fname;
                                                   loginUserAccount();
                                                   Intent intent = new Intent(LoginActivity.this,PatientDashboard.class);
                                                   startActivity(intent);
                                               }
                                           }
                                           else{
                                               progressbar.setVisibility(View.GONE);

                                               // Toast.makeText(getApplicationContext(), "wrong id", Toast.LENGTH_SHORT).show();
                                           }

                                       }
                                   }else {
                                       progressbar.setVisibility(View.GONE);
                                       Toast.makeText(getApplicationContext(), "Cannot load doctors' data", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
                           break;

                      case "Pharmacist":
                            db.collection("Users").document("Pharmacist").collection("Pharmacists").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                          @Override
                          public void onComplete(@NonNull Task<QuerySnapshot> task) {
                              if (task.isSuccessful()) {
                                  String data = "";
                                  for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                      log_password = passwordTextView.getText().toString();
                                      String cnicnum = documentSnapshot.getId();
                                      if (( et_cnic.getText().toString()).equals(cnicnum)) {

                                          cloud_password = documentSnapshot.getString("password");

                                          if (checkPassword()) {
                                              progressbar.setVisibility(View.GONE);
                                              Intent intent = new Intent(LoginActivity.this,PharmacistDashboard.class);
                                              startActivity(intent);
                                          }
                                      }
                                      else{
                                          progressbar.setVisibility(View.GONE);

                                            Toast.makeText(getApplicationContext(), "wrong id", Toast.LENGTH_SHORT).show();
                                      }

                                  }
                              }else {
                                  progressbar.setVisibility(View.GONE);
                                  Toast.makeText(getApplicationContext(), "Cannot load doctors' data", Toast.LENGTH_SHORT).show();
                              }
                          }
                      });
                            break;


                    }
            }

        });
    }


    private boolean checkPassword() {
        if (cloud_password.equals(log_password)) {
            progressbar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            progressbar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void setSpinnerUser() {

        ArrayAdapter<CharSequence> adapter_gender = ArrayAdapter.createFromResource(getApplicationContext(), R.array.user, android.R.layout.simple_spinner_item);
        adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

       spinner_user.setAdapter(adapter_gender);
        spinner_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                log_user = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private void loginUserAccount()
    {


        String   password;
        password = passwordTextView.getText().toString();
        Toast.makeText(getApplicationContext(),email1, Toast.LENGTH_SHORT).show();


        mAuth.signInWithEmailAndPassword(email1, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task)
                            {

                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Login successful!!",
                                            Toast.LENGTH_LONG)
                                            .show();
                                    progressbar.setVisibility(View.GONE);

                                }

                                else {
                                    progressbar.setVisibility(View.GONE);
                                 /*   Toast.makeText(getApplicationContext(),
                                            "Login failed!!",
                                            Toast.LENGTH_LONG)
                                            .show();*/

                                }
                            }
                        });


    }

}

