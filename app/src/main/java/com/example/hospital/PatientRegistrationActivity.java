package com.example.hospital;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.MediaStore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PatientRegistrationActivity extends AppCompatActivity {
    private TextView regpageQ;
    private EditText regfullname,regidn,regphone,logemail,logpassword;
    private Button regButton;

    private ProgressBar progressbar;
    private CircleImageView profileImage;
    String reg_fname, reg_mname, reg_lname, reg_mobnum, reg_cnic, reg_addr, reg_password, reg_gender, reg_dob;
    private Uri resultUri;
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;
    private ProgressDialog loader;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_registration);
        regpageQ=findViewById(R.id.loginPageQ);

        progressbar = findViewById(R.id.progressBar);
        regpageQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PatientRegistrationActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        regfullname=findViewById(R.id.rfullname);
        regidn=findViewById(R.id.ridnumber);
        regphone=findViewById(R.id.rphone);
        logemail=findViewById(R.id.loginEmail);
         logpassword=findViewById(R.id.loginPassword);
         regButton=findViewById(R.id.regButton);
         profileImage=findViewById(R.id.profileImage);

         loader=new ProgressDialog(this);
         mAuth=FirebaseAuth.getInstance();

         profileImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent =new Intent(Intent.ACTION_PICK);
                 intent.setType("image/*");
                 startActivityForResult(intent,1);
             }
         });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=logemail.getText().toString().trim();
                final String password=logpassword.getText().toString().trim();
                final String fullname=regfullname.getText().toString().trim();
                final String idnumber=regidn.getText().toString().trim();
                final String phone=regphone.getText().toString().trim();
                progressbar.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(email))
                {
                    logemail.setError("Email is required");

                    progressbar.setVisibility(View.GONE);
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    logpassword.setError("Password is required");

                    progressbar.setVisibility(View.GONE);
                    return;
                }
                if(TextUtils.isEmpty(fullname))
                {
                    regfullname.setError("Full name is required");

                    progressbar.setVisibility(View.GONE);
                    return;
                }
                if(TextUtils.isEmpty(idnumber))
                {
                    regidn.setError("ID number is required");

                    progressbar.setVisibility(View.GONE);
                    return;
                }

                if(TextUtils.isEmpty(phone))
                {
                    regphone.setError("Phone number is required");

                    progressbar.setVisibility(View.GONE);
                    return;
                }
                if(resultUri==null)
                {

                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(PatientRegistrationActivity.this, "Profile is required", Toast.LENGTH_SHORT).show();
                }
                else{
                    loader.setMessage("Registering....");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                String error=task.getException().toString();
                                Toast.makeText(PatientRegistrationActivity.this, "Error Occured" +error, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String currentUserId=mAuth.getCurrentUser().getUid();
                                userDatabaseRef= FirebaseDatabase.getInstance().getReference().
                                        child("Users").child(currentUserId);
                                HashMap userInfo=new HashMap();
                                userInfo.put("name",fullname);
                                userInfo.put("email",email);
                                userInfo.put("pid",idnumber);
                                userInfo.put("phonenumber",phone);
                                userInfo.put("password",password);
                                userInfo.put("type","patient");
                            userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){
                                            //Toast.makeText(PatientRegistrationActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(PatientRegistrationActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                                        }
                                        finish();
                                        loader.dismiss();
                                    }
                                });


                               reg_cnic= idnumber.toString().trim();

                                db.collection("Users").document("Patient").collection("Patients").document(reg_cnic).set(userInfo)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(), "Registration Error", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                if(resultUri!=null){
                                    final StorageReference filepath= FirebaseStorage.getInstance().getReference().child("profile Pictures")
                                            .child(currentUserId);
                                    Bitmap bitmap=null;
                                    try{
                                        bitmap= MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),resultUri);
                                    }
                                    catch(IOException e){
                                        e.printStackTrace();
                                    }
                                    ByteArrayOutputStream baos=new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG,20,baos);
                                    byte[] data=baos.toByteArray();
                                    UploadTask uploadTask=filepath.putBytes(data);
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            finish();
                                            return;
                                        }
                                    });
                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            if(taskSnapshot.getMetadata()!=null){
                                                Task<Uri> result=taskSnapshot.getStorage().getDownloadUrl();
                                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String imageUrl=uri.toString();
                                                        Map newImageMap=new HashMap();
                                                        newImageMap.put("profilepictureurl",imageUrl);

                                                        userDatabaseRef.updateChildren(newImageMap).
                                                                addOnCompleteListener(new OnCompleteListener() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task task) {
                                                                        if(task.isSuccessful()){
                                                                         //   Toast.makeText(PatientRegistrationActivity.this, "Reg successfull", Toast.LENGTH_SHORT).show();
                                                                                 }
                                                                        else{
                                                                            Toast.makeText(PatientRegistrationActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                        finish();

                                                    }
                                                });
                                            }
                                        }
                                    });

                                    Intent intent=new Intent(PatientRegistrationActivity.this,
                                            PatientDashboard.class);
                                    startActivity(intent);
                                    finish();
                                    loader.dismiss();

                                }

                            }
                        }
                    });

                }

            }
        });


    }
@Override
    protected void onActivityResult(int requestCode, int resultcode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultcode,data);
        if(requestCode==1 &&resultcode== Activity.RESULT_OK && data!=null){
            resultUri=data.getData();
            profileImage.setImageURI(resultUri);
        }


}

}