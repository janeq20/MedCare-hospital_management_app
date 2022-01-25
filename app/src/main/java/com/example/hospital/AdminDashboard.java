package com.example.hospital;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.os.Bundle;

public class AdminDashboard extends AppCompatActivity {


    private Button img_docs, img_pats,Delete,logout;
    private TextView user_docs, users_dets;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference patsRef = db.collection("Users").document("Patient").collection("Patients");
    private CollectionReference docsRef = db.collection("Users").document("Doctor").collection("Doctors");
    private CollectionReference pharmsRef = db.collection("Users").document("Pharmacist").collection("Pharmacists");

    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        img_docs = (Button) findViewById(R.id.btn_get_doc);
        img_pats = (Button) findViewById(R.id.btn_get_pats);
        logout=(Button)findViewById(R.id.btnLogOut);
        Delete=(Button)findViewById(R.id.del_users);
        user_docs = (TextView) findViewById(R.id.text_getdoc1);
        users_dets = (TextView) findViewById(R.id.text_getdoc2);
       img_pats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadPatients();
            }
        });
       Delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(AdminDashboard.this,Delete_users.class);

               startActivity(i);
           }
       });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboard.this);

                builder.setMessage("Do you want to exit ?");

                builder.setTitle("Alert !");

                builder.setCancelable(false);

                builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which)
                    {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(AdminDashboard.this, "Log Out Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminDashboard.this, LoginActivity.class));
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

        img_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             loadDoctors();
            }
        });

    }
  private void loadPatients() {
        user_docs.setText("Patients");
        count = 0;

        patsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String data = "";
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        String fname = documentSnapshot.getString("name");
                        String mobnum = documentSnapshot.getString("phonenumber");
                        String Email = documentSnapshot.getString("email");
                        String patID = documentSnapshot.getString("pid");
                        String cnicnum = documentSnapshot.getId();

                        data += "ID: " + cnicnum +
                                "\nFirst Name: " +  fname +
                                "\nMobile Number: " + mobnum +
                                "\nEmail: " + Email +
                                "\nPatient ID: " +patID +
                                "\n" + "\n";

                        count++;

                    }
                    data += "Total Patients: " + count;
                    users_dets.setText(data.replace("\\n", "\n"));
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot load patients' data", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void loadDoctors() {
        user_docs.setText("Doctors");
        count = 0;

        docsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String data = "";
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {


                        String fname = documentSnapshot.getString("firstname");
                        String speciality = documentSnapshot.getString("speciality");
                        String Email = documentSnapshot.getString("demail");
                 //       String mobnum = documentSnapshot.getString("contact");
                        String cnicnum = documentSnapshot.getId();

                        if (speciality.equals("cardiology")) {
                            speciality = "Cardiology";
                        } else if (speciality.equals("dermatology")) {
                            speciality = "Dermatology";
                        } else if (speciality.equals("allergyandimmunology")) {
                            speciality = "Allergy and Immunology";
                        } else if (speciality.equals("infectiousdisease")) {
                            speciality = "Infectious Disease";
                        }

                        data += "ID: " + cnicnum +
                                "\nFirst Name: " + fname +
                                "\nLast Name: " +Email +
                                "\nSpeciality: " + speciality +
                                "\n" + "\n";

                        count++;

                    }
                    data += "Total Doctors: " + count;
                    users_dets.setText(data.replace("\\n", "\n"));
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot load doctors' data", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}