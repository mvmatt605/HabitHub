package com.example.expensetrackingapp;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.text.TextUtils;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NutritionData extends AppCompatActivity {

    EditText editTextFoodName,editTextProtein,editTextCarbs,editTextFats,editTextCalories;
    Button addFood;
    Button deleteFood;
    String name,protein,carbs,fats,calories;
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
        deleteFood=findViewById(R.id.deleteFood);

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
                    String protein = snapshot.child("protein").getValue(String.class);
                    String carbs = snapshot.child("carbs").getValue(String.class);
                    String fats = snapshot.child("fats").getValue(String.class);
                    String calories = snapshot.child("calories").getValue(String.class);

                    // Create a string with all the data
                    String data = name + "\nProtein: " + protein + ", Carbs: " + carbs + ", Fats: " + fats + ", Calories: " + calories;
                    dataList.add(data);
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

        deleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name =editTextFoodName.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Nutrition Data:");
                myRef.child(name).removeValue();
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