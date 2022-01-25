package com.example.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;



public class AllSlots extends AppCompatActivity{

    public Spinner spinner_speciality;
    public Spinner spinner_doctor;
    public Spinner spinner_schedule;

    public TextView mDisplayStartDate;
    public TextView mDisplayEndDate;
    public Calendar mCurrentCalendar1, mCurrentCalendar2;
    public TextView confirmation;
    public TextView CNICnumber;
    public TextView pwd;
    public TextView text_schedule;

    public EditText cnic;
    public EditText password;

    public Button view_schedule;
    public Button next;
    public Button confirm;
    public int y, m, d;
    String startdate, enddate;
    Date date1, date2;
    private Vector<String> schedule = new Vector<String>();
    private List<String> cardioname = new ArrayList<>();
    private List<String> cardiocnic = new ArrayList<>();
    private List<String> dermname = new ArrayList<>();
    private List<String> dermcnic = new ArrayList<>();
    private List<String> endoname = new ArrayList<>();
    private List<String> endocnic = new ArrayList<>();
    private List<String> entname = new ArrayList<>();
    private List<String> entcnic = new ArrayList<>();
    private List<String> cardiomob = new ArrayList<>();
    private List<String> dermmob = new ArrayList<>();
    private List<String> endomob = new ArrayList<>();
    private List<String> entmob = new ArrayList<>();
    private List<String> cardioemail = new ArrayList<>();
    private List<String> dermemail = new ArrayList<>();
    private List<String> endoemail = new ArrayList<>();
    private List<String> entemail= new ArrayList<>();
    private List<String> state = null;
    private String timings[];
    private String docname, doccnic, docmob, patmob,docemail;
    private String text_cnic, cloud_password;
    private String text_password;
    private String time, name_pat;
    private String speciality;
    private int year1, month1, day1, year2, month2, day2;
    private int difference;
    private int pos, index;
    private int xx = 0, yy = 0;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference docsRef = db.collection("Users").document("Doctor").collection("Doctors");





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_slots);
        spinner_speciality = (Spinner) (findViewById(R.id.spinner_speciality));
        spinner_doctor = (Spinner) (findViewById(R.id.spinner_doctor));
        spinner_schedule = (Spinner) findViewById(R.id.spinner_schedule);

        mDisplayStartDate = (TextView) findViewById(R.id.date_start);
        mDisplayEndDate = (TextView) findViewById(R.id.date_end);
        text_schedule = (TextView) findViewById(R.id.text_schedule);
        confirmation = (TextView)findViewById(R.id.text_confirmation);
        CNICnumber = (TextView) findViewById(R.id.text_cnic);
        pwd = (TextView) findViewById(R.id.text_password);

        view_schedule = (Button) findViewById(R.id.view_schedule);
        next = (Button) findViewById(R.id.btn_next);
        confirm = (Button)findViewById(R.id.btn_confirm);

        cnic = (EditText) findViewById(R.id.edittext_cnic);
        password = (EditText) findViewById(R.id.edittext_password);

        mCurrentCalendar1 = Calendar.getInstance();

        mCurrentCalendar2 = Calendar.getInstance();
        timings = new String[]{"11:30:00", "12:00:00", "12:30:00", "13:00:00"};




        year1 = mCurrentCalendar1.get(Calendar.YEAR);
        month1 = mCurrentCalendar1.get(Calendar.MONTH);
        day1 = mCurrentCalendar1.get(Calendar.DAY_OF_MONTH);

        mDisplayStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DatePickerDialog datePickerDialog = new DatePickerDialog(AllSlots.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int month, int dayOfMonth) {

                                startdate = year + "-" + (month + 1) + "-" + dayOfMonth;
                                mDisplayStartDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                                y = year;
                                m = month + 1;
                                d = dayOfMonth;
                            }
                        }, year1, month1, day1);

                datePickerDialog.show();

                datePickerDialog.getDatePicker().setMinDate(mCurrentCalendar1.getTimeInMillis());
                mCurrentCalendar1.add(Calendar.MONTH, +1);
                mCurrentCalendar1.add(Calendar.DAY_OF_MONTH, -day1);
                datePickerDialog.getDatePicker().setMaxDate(mCurrentCalendar1.getTimeInMillis());
                mCurrentCalendar1.add(Calendar.MONTH, -1);
                mCurrentCalendar1.add(Calendar.DAY_OF_MONTH, day1);
                datePickerDialog.show();
            }
        });

        year2 = mCurrentCalendar2.get(Calendar.YEAR);
        month2 = mCurrentCalendar2.get(Calendar.MONTH);
        day2 = mCurrentCalendar2.get(Calendar.DAY_OF_MONTH);

        mDisplayEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(AllSlots.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                if (y == year && m == month + 1) {
                                    enddate = year + "-" + (month + 1) + "-" + dayOfMonth;
                                    mDisplayEndDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Select start date first", Toast.LENGTH_SHORT).show();
                                    next.setVisibility(View.INVISIBLE);
                                    text_schedule.setVisibility(View.INVISIBLE);
                                    spinner_schedule.setVisibility(View.INVISIBLE);
                                }
                            }
                        }, year2, month2, day2);

                datePickerDialog.getDatePicker().setMinDate(mCurrentCalendar2.getTimeInMillis());
                mCurrentCalendar2.add(Calendar.MONTH, +1);
                mCurrentCalendar2.add(Calendar.DAY_OF_MONTH, -day1);
                datePickerDialog.getDatePicker().setMaxDate(mCurrentCalendar2.getTimeInMillis());
                mCurrentCalendar2.add(Calendar.MONTH, -1);
                mCurrentCalendar2.add(Calendar.DAY_OF_MONTH, day1);
                datePickerDialog.show();
            }
        });

        getDoctorsDetails();


        ArrayAdapter<CharSequence> adapter_speciality = ArrayAdapter.createFromResource(getApplicationContext(), R.array.speciality, android.R.layout.simple_spinner_item);
        adapter_speciality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_speciality.setAdapter(adapter_speciality);
        spinner_speciality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                if (position == 0) {
                    state = new ArrayList<String>(Arrays.asList(" "));
                } else if (position == 2) {
                    state = cardioname;
                    speciality = "cardiology";
                } else if (position == 3) {
                    state = dermname;
                    speciality = "dermatology";
                } else if (position == 1) {
                    state = endoname;
                    speciality = "allergyandimmunology";
                } else if (position == 4) {
                    state = entname;
                    speciality = "infectiousdisease";
                }
                ArrayAdapter<String> adapter_doctor = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, state);
                spinner_doctor.setAdapter(adapter_doctor);
                spinner_doctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        docname = parent.getItemAtPosition(position).toString();
                        index = state.indexOf(docname);
                        if (pos == 2) {
                            doccnic = cardiocnic.get(index);
                            docemail=cardioemail.get(index);
                        }
                        if (pos == 3) {
                            doccnic = dermcnic.get(index);
                            docemail=dermemail.get(index);
                        }
                        if (pos == 1) {
                            doccnic = endocnic.get(index);
                            docemail=endoemail.get(index);
                        }
                        if (pos == 4) {
                            doccnic = entcnic.get(index);
                            docemail=entemail.get(index);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        setButtonAppointment();

    }
    private void setButtonAppointment() {

        view_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos != 0) {
                    if (startdate != null && enddate != null) {
                        // difference = Integer.parseInt(differenceDates(startdate, enddate));
                        // if (difference >= 0) {
                        text_schedule.setVisibility(View.VISIBLE);
                        spinner_schedule.setVisibility(View.VISIBLE);
                        setSpinnerSchedule();
                        setButtonNext();
                        //  }
                        //  else {
                        //  makeInvisible();
                    }
                    //   }
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                    next.setVisibility(View.INVISIBLE);
                    text_schedule.setVisibility(View.INVISIBLE);
                    spinner_schedule.setVisibility(View.INVISIBLE);
                }

            }
        });
    }
    private void setSpinnerSchedule() {
        schedule.clear();

        for (int i = 0; i < (difference + 1); i++) {
            for (int j = 0; j < timings.length; j++) {
                String addi = y + "-" + m + "-" + (d + i) + " " + timings[j];
                schedule.add(addi);
            }
        }

        ArrayAdapter<String> adapter_schedule = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, schedule);
        spinner_schedule.setAdapter(adapter_schedule);
        spinner_schedule.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                time = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void setButtonNext() {
        next.setVisibility(View.VISIBLE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonConfirm();
            }
        });
    }

    private void setButtonConfirm() {
        confirm.setVisibility(View.VISIBLE);
        confirmation.setVisibility(View.VISIBLE);
        CNICnumber.setVisibility(View.VISIBLE);
        pwd.setVisibility(View.VISIBLE);
        cnic.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePatient();

            }
        });
    }

    private void makeInvisible() {
        text_schedule.setVisibility(View.INVISIBLE);
        spinner_schedule.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
        text_schedule.setVisibility(View.INVISIBLE);
        spinner_schedule.setVisibility(View.INVISIBLE);
        confirmation.setVisibility(View.INVISIBLE);
        CNICnumber.setVisibility(View.INVISIBLE);
        cnic.setVisibility(View.INVISIBLE);
        pwd.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        confirm.setVisibility(View.INVISIBLE);
    }
    private void getDoctorsDetails() {
        docsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    String data = "";
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        String fname = documentSnapshot.getString("firstname");
                        String demail=documentSnapshot.getString("demail");
                 //       String lname = documentSnapshot.getString("lastname");
                    //    String mobnum = documentSnapshot.getString("contact");
                      //  String gender = documentSnapshot.getString("gender");
                        String speciality = documentSnapshot.getString("speciality");
                        String cnicnum = documentSnapshot.getId();

                        if (speciality.equals("cardiology")) {
                            cardioname.add(fname);
                            cardiocnic.add(cnicnum);
                            cardioemail.add(demail);
                        }
                        if (speciality.equals("dermatology")) {
                         //   dermname.add(fname + " " + lname);
                            dermname.add(fname);
                                    dermcnic.add(cnicnum);
                            dermemail.add(demail);
                         //   dermmob.add(mobnum);
                        }
                        if (speciality.equals("allergyandimmunology")) {
                            endoname.add(fname);
                            endocnic.add(cnicnum);
                            endoemail.add(demail);
                        }
                        if (speciality.equals("infectiousdisease")) {
                            entname.add(fname);
                             entcnic.add(cnicnum);
                            entemail.add(demail);

                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Cannot load doctors' data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }




    private void validatePatient() {
        text_cnic = cnic.getText().toString();
        db.collection("Users").document("Patient").collection("Patients").document(text_cnic).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            name_pat = documentSnapshot.getString("name") ;
                            patmob = documentSnapshot.getString("phonenumber");
                            cloud_password = documentSnapshot.getString("password");
                           text_password = password.getText().toString();
                         if (checkPassword()) {
                              Map<String, Object> appointment = new HashMap<>();
                                appointment.put("doctorname", docname);
                               appointment.put("patientname", name_pat);
                                appointment.put("patientcnic", text_cnic);
                              appointment.put("patientcontact", patmob);
                               appointment.put("speciality", speciality);
                             appointment.put("demail", docemail);
                                appointment.put("time", time);
                         //       appointment.put("doctorcontact", docmob);
                                appointment.put("status", " ");
                                db.collection("Appointments").document("Patient").collection("Patients").document(text_cnic).set(appointment);
                                appointment.put("doctorcnic",doccnic);
                                db.collection("Appointments").document("Doctors").collection(doccnic).document().set(appointment);


                                Toast.makeText(getApplicationContext(), "Appointment Successful", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Please register first", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean checkPassword() {
        if (cloud_password.equals(text_password)) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}



