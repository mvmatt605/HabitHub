package com.example.expensetrackingapp;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.text.TextUtils;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class NutritionData extends AppCompatActivity {

    EditText editTextFoodName,editTextProtein,editTextCarbs,editTextFats,editTextCalories;
    Button addFood;

    String name,protein,carbs,fats,calories;

    FirebaseFirestore db;

    TextView NutritionResults;

    List<String> dataList = new ArrayList<>();

    NutritionAdapter adapter = new NutritionAdapter(dataList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_data);


        RecyclerView NutritionRecycler = findViewById(R.id.NutritionRecycler);
        NutritionRecycler.setLayoutManager(new LinearLayoutManager(this));
        NutritionRecycler.setAdapter(adapter);


        editTextFoodName = findViewById(R.id.editTextFoodName);
        editTextProtein =findViewById(R.id.editTextProtein);
        editTextCarbs =findViewById(R.id.editTextCarbs);
        editTextFats =findViewById(R.id.editTextFats);
        editTextCalories =findViewById(R.id.editTextCalories);

        addFood=findViewById(R.id.addFood);

      //  NutritionRecycler=findViewById(R.id.NutritionRecycler);

        NutritionResults =findViewById(R.id.NutritionResults);


        name =editTextFoodName.getText().toString();
        protein =editTextProtein.getText().toString();
        carbs =editTextCarbs.getText().toString();
        fats =editTextFats.getText().toString();
        calories =editTextCalories.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference("Nutrition Data:");



        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataList.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.getKey(); // This retrieves the name from the database
                    dataList.add(name);
                }
                adapter.notifyDataSetChanged();
                Log.d(TAG, "Value is: " + name);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name =editTextFoodName.getText().toString();
                protein =editTextProtein.getText().toString();
                carbs =editTextCarbs.getText().toString();
                fats =editTextFats.getText().toString();
                calories =editTextCalories.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(name)) {
                    editTextFoodName.setError("Please enter name");
                } else if (TextUtils.isEmpty(protein)) {
                    editTextProtein.setError("Please enter protein");
                } else if (TextUtils.isEmpty(carbs)) {
                    editTextCarbs.setError("Please enter carbs");
                } else if (TextUtils.isEmpty(fats)) {
                    editTextFats.setError("Please enter fats");
                } else if (TextUtils.isEmpty(calories)) {
                    editTextCalories.setError("Please enter calories");
                } else {
                    // calling method to add data to Firebase Firestore.
                    addDataToFirestore(name, protein, carbs, fats, calories);
                }
            }
        });

    }

      public void addDataToFirestore(String name, String protein, String carbs, String fats, String calories){

          FirebaseDatabase database = FirebaseDatabase.getInstance();
          DatabaseReference myRef = database.getReference("Nutrition Data:").child(name);

          myRef.child("protein").setValue(protein);
          myRef.child("carbs").setValue(carbs);
          myRef.child("fats").setValue(fats);
          myRef.child("calories").setValue(calories);
    }

}