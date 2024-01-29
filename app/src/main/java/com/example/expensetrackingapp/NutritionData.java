package com.example.expensetrackingapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.text.TextUtils;


public class NutritionData extends AppCompatActivity {

    EditText editTextFoodName,editTextProtein,editTextCarbs,editTextFats,editTextCalories;
    Button addFood;

    String name,protein,carbs,fats,calories;

   // FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_nutrition_data);

      // db = FirebaseFirestore.getInstance();

        editTextFoodName = findViewById(R.id.editTextFoodName);
        editTextProtein =findViewById(R.id.editTextProtein);
        editTextCarbs =findViewById(R.id.editTextCarbs);
        editTextFats =findViewById(R.id.editTextFats);
        editTextCalories =findViewById(R.id.editTextCalories);

        addFood=findViewById(R.id.addFood);

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
                   // addDataToFirestore(name, protein, carbs, fats, calories);
                }
            }
        });
    }

//      public void addDataToFirestore(String name, String protein, String carbs, String fats, String calories){
//        CollectionReference dbNutrition = db.collection("Nutrition");
//
//        NutritionDataClass nutritionDataClass = new NutritionDataClass(name, protein, carbs, fats, calories);
//
//        dbNutrition.add(nutritionDataClass).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//            @Override
//            public void onSuccess(DocumentReference documentReference) {
//                Toast.makeText(NutritionData.this, "Your data has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(NutritionData.this, "Fail to add \n" + e, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}