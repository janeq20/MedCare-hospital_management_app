package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
public class PatientComments extends AppCompatActivity {
    ListView lvAllComms;
    DatabaseReference DBcomments;
//    ArrayList<DocComment> comments = new ArrayList<DocComment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_comments);
        lvAllComms = findViewById(R.id.lvAllComs);
        Intent iac = getIntent();
        final String uid = (String) iac.getSerializableExtra("ID");
        //String id = p.getId();

        DBcomments = FirebaseDatabase.getInstance().getReference("comments");
/*
        DBcomments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                comments.clear();
                for(DataSnapshot CommentSS : dataSnapshot.getChildren()){
                    String date = CommentSS.getValue(DocComment.class).getDate();
                    String text = CommentSS.getValue(DocComment.class).getText();
                    String id = CommentSS.getValue(DocComment.class).getId();
                    if(id.equals(uid)) {
                        DocComment dc = new DocComment(text, date, id);
                        comments.add(dc);
                    }
                }
                CommentList adapter = new CommentList(AllComments.this,comments);
                lvAllComms.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PatientComments.this, "ERROR!", Toast.LENGTH_SHORT).show();
            }
        });

*/

    }
}