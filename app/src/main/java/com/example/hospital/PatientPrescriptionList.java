package com.example.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PatientPrescriptionList extends ArrayAdapter<Prescription> {
    Activity context;
    List<Prescription> pres;

    public PatientPrescriptionList (Activity context, ArrayList<Prescription> pres)
    {
        super(context,R.layout.lv_all_pres,pres);
        this.context = context;
        this.pres = pres;
    }

    }

