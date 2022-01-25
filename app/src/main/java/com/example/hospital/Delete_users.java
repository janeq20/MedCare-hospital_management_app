package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import android.os.Bundle;

public class Delete_users extends AppCompatActivity {
    private EditText et_fname, et_lname, et_cnic, et_password;

    private Button reg_buttonreg;

    private String reg_fname, reg_cnic, reg_password, reg_user;
    private String doc_fname, doc_lname, admin_password;

    private Spinner spinner_user;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference adminRef = db.collection("Users").document("Admin").collection("Admins").document("12345");
    private CollectionReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_users);

        et_fname = (EditText) findViewById(R.id.rem_doc_fname);
        et_cnic = (EditText) findViewById(R.id.rem_doc_cnic);
        et_password = (EditText) findViewById(R.id.rem_doc_password);
        spinner_user = (Spinner) findViewById(R.id.spinner_user90);
        reg_buttonreg = (Button) findViewById(R.id.rem_doc_buttonreg);
        setSpinnerUser();

        reg_buttonreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeUser();
            }
        });
    }
    private void setSpinnerUser() {

        ArrayAdapter<CharSequence> adapter_gender = ArrayAdapter.createFromResource(getApplicationContext(), R.array.remove, android.R.layout.simple_spinner_item);
        adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_user.setAdapter(adapter_gender);
        spinner_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reg_user = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void removeUser() {
        reg_cnic = et_cnic.getText().toString();


        reg_fname = et_fname.getText().toString();
        reg_password = et_password.getText().toString();

        switch (reg_user) {
            case "Doctor":
                userRef = db.collection("Users").document("Doctor").collection("Doctors");
                userRef.document(reg_cnic).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    doc_fname = documentSnapshot.getString("firstname");
                                }
                            }
                        });
                break;
            case "Pharmacist":
                userRef = db.collection("Users").document("Pharmacist").collection("Pharmacists");
                break;
            case "Patient":
                userRef = db.collection("Users").document("Patient").collection("Patients");
                userRef.document(reg_cnic).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    doc_fname = documentSnapshot.getString("name");
                                }
                            }
                        });
                break;
        }

   /*         userRef.document(reg_cnic).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                doc_fname = documentSnapshot.getString("name");
                               }
                        }
                    });
        */

            adminRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                admin_password = documentSnapshot.getString("password");
                            } else {
                            }
                        }
                    });

            if (reg_fname.equals(doc_fname)  && reg_password.equals(admin_password)) {
                userRef.document(reg_cnic).delete();
                switch (reg_user) {
                    case "Doctor":
                        Toast.makeText(getApplicationContext(), "Doctor Removed", Toast.LENGTH_SHORT).show();
                        break;
                    case "Pharmacist":
                        Toast.makeText(getApplicationContext(), "Pharmacist Removed", Toast.LENGTH_SHORT).show();
                        break;
                    case "Patient":
                        Toast.makeText(getApplicationContext(), "Patient Removed", Toast.LENGTH_SHORT).show();
                        break;
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please provide correct details", Toast.LENGTH_SHORT).show();
            }

    }

}