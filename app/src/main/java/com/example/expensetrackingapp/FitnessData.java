package com.example.expensetrackingapp;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class FitnessData extends  AppCompatActivity{

    EditText editTextYourName, editTextMonday,editTextTuesday,editTextWednesday;
    Button addWorkout, deleteWorkout;

    String  yourname, monday,tuesday,wednesday;

    //FirebaseFirestore db;

    TextView WorkoutSplit;

    List<String> dataList = new ArrayList<>();

    FitnessAdapter adapter = new FitnessAdapter(dataList);

   // @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fitness_data);


        RecyclerView FitnessRecycler = findViewById(R.id.FitnessRecycler);
        FitnessRecycler.setLayoutManager(new LinearLayoutManager(this));
        FitnessRecycler.setAdapter(adapter);


        editTextYourName = findViewById(R.id.editTextYourName);
        editTextMonday =findViewById(R.id.editTextMonday);
        editTextTuesday =findViewById(R.id.editTextTuesday);
        editTextWednesday =findViewById(R.id.editTextWednesday);




        addWorkout=findViewById(R.id.addWorkout);
        deleteWorkout=findViewById(R.id.deleteWorkout);


        WorkoutSplit =findViewById(R.id.WorkoutSplit);


        yourname = editTextYourName.getText().toString();
        monday =editTextMonday.getText().toString();
        tuesday =editTextTuesday.getText().toString();
        wednesday =editTextWednesday.getText().toString();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference("Fitness Data:");



        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataList.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //String name = snapshot.getKey();// This retrieves the name from the database
                    String yourname = snapshot.getKey(); // This retrieves the name from the database
                    String monday = snapshot.child("monday").getValue(String.class);
                    String tuesday = snapshot.child("tuesday").getValue(String.class);
                    String wednesday = snapshot.child("wednesday").getValue(String.class);

                    String data = yourname + "\nMonday: " + monday + ", Tuesday: " + tuesday + ", Wednesday: " + wednesday;
                    dataList.add(data);
                }
                adapter.notifyDataSetChanged();
                Log.d(TAG, "Value is: " + yourname);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                yourname =editTextYourName.getText().toString();
                monday =editTextMonday.getText().toString();
                tuesday =editTextTuesday.getText().toString();
                wednesday =editTextWednesday.getText().toString();


                 // validating the text fields if empty or not.
               if (TextUtils.isEmpty(yourname)) {
                   editTextYourName.setError("Please enter your name");
               } else if  (TextUtils.isEmpty(monday)) {
                    editTextMonday.setError("Please enter push,pull,or legs ");
                } else if (TextUtils.isEmpty(tuesday)) {
                    editTextTuesday.setError("Please enter one of the remaining two options");
                } else if (TextUtils.isEmpty(wednesday)) {
                    editTextWednesday.setError("Please enter the last option");

                } else {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore( yourname,monday, tuesday, wednesday);
                }
            }
        });



     deleteWorkout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            yourname =editTextYourName.getText().toString();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Fitness Data:");
            myRef.child(yourname).removeValue();
        }
    });

}


    public void addDataToFirestore( String yourname, String monday, String tuesday, String wednesday){


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(" Fitness Data:").child(yourname);

        myRef.child("Day 1").setValue(monday);
        myRef.child("Day 2").setValue(tuesday);
        myRef.child("Day 3").setValue(wednesday);
       // myRef.child("yourname").setValue(yourname);
    }

}


