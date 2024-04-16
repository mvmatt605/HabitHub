package com.example.expensetrackingapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

//firebase
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.text.TextUtils;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button expenseButton;
    DatabaseControl control;
    // Create the object of TextView and PieChart class
    TextView tvBills, tvGroceries, tvSavings, tvExtra;
    PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link those objects with their respective
// id's that we have given in .XML file
        tvBills = findViewById(R.id.tvBills);
        tvGroceries = findViewById(R.id.tvGroceries);
        tvSavings = findViewById(R.id.tvSavings);
        tvExtra = findViewById(R.id.tvExtra);
        pieChart = findViewById(R.id.piechart);
        control = new DatabaseControl(getApplicationContext());

        expenseButton=findViewById(R.id.expenseButton);

        expenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Expenses.class);
                startActivity(i);
            }
        });
    }

    public void onResume(){
        super.onResume();
        // clear data first
        pieChart.clearChart();
        setData();
    }

    private void setData(){
        float bills = 0;
        float billCount =0;
        //get data from firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference("Expense Data:");
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                float bills = 0;
                float groceries = 0;
                float savings = 0;
                float misc = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren() ) {
                    String expense = snapshot.getKey();
                    String category = snapshot.child("category").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);
                    String price = snapshot.child("price").getValue(String.class);
                    if("Bill".equals(category) && price != null && !price.isEmpty()) {
                        bills = bills + Float.parseFloat(price.trim());
                    }
                    if("Groceries".equals(category) && price != null && !price.isEmpty()) {
                        groceries = groceries + Float.parseFloat(price.trim());
                    }
                    if("Savings".equals(category) && price != null && !price.isEmpty()) {
                        savings = savings + Float.parseFloat(price.trim());
                    }
                    if("Misc".equals(category) && price != null && !price.isEmpty()) {
                        misc = misc + Float.parseFloat(price.trim());
                    }
                }

                //set data
                double total = bills + groceries + savings+misc;
                double B= (bills/total) *100;
                double G=(groceries/total)*100;
                double S= (savings/total)*100;
                double E=(misc/total)*100;
                tvBills.setText(String.format("%.2f",B)+"");
                tvGroceries.setText(String.format("%.2f",G)+"");
                tvSavings.setText(String.format("%.2f",S)+"");
                tvExtra.setText(String.format("%.2f",E)+"");

                // Set the data and color to the pie chart
                pieChart.addPieSlice(
                        new PieModel(
                                "Bills",
                                Float.parseFloat(tvBills.getText().toString()),
                                Color.parseColor("#555687")));
                pieChart.addPieSlice(
                        new PieModel(
                                "Groceries",
                                Float.parseFloat(tvGroceries.getText().toString()),
                                Color.parseColor("#556490")));
                pieChart.addPieSlice(
                        new PieModel(
                                "Savings",
                                Float.parseFloat(tvSavings.getText().toString()),
                                Color.parseColor("#698fa8")));
                pieChart.addPieSlice(
                        new PieModel(
                                "Misc",
                                Float.parseFloat(tvExtra.getText().toString()),
                                Color.parseColor("#88c1bf")));

                // To animate the pie chart
                pieChart.startAnimation();

                Log.d(TAG, "total bills and bill count: " + bills+billCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}