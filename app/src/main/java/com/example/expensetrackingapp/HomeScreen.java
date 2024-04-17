package com.example.expensetrackingapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity {

    Button expenseScreenButton;
    Button habitScreenButton;
    Button NutritionScreenButton;
    Button FitnessScreenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        expenseScreenButton=findViewById(R.id.expenseScreenButton);
        expenseScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

        habitScreenButton=findViewById(R.id.habitScreenButton);
        habitScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),HabitData.class);
                startActivity(i);
            }
        });

        NutritionScreenButton=findViewById(R.id.NutritionScreenButton);
        NutritionScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),NutritionData.class);
                startActivity(i);
            }
        });

        FitnessScreenButton=findViewById(R.id.FitnessScreenButton);
        FitnessScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), FitnessData.class);
                startActivity(i);
            }
        });

    }
}
