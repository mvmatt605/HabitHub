package com.example.expensetrackingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class MainActivity extends AppCompatActivity {
    Button expenseButton;

    Button homeScreenButton;
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
        homeScreenButton=findViewById(R.id.homeScreenButton);

        expenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Database.class);
                startActivity(i);
            }
        });

        homeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HomeScreen.class);
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
        // Set the percentage of language used
        control.open();
        int b = control.CountCategory("bills");
        int g = control.CountCategory("groceries");
        int s= control.CountCategory("savings");
        int e = control.CountCategory("extra");
        control.close();
        double total = b+g+s+e;
        double B= (b/total) *100;
        double G=(g/total)*100;
        double S= (s/total)*100;
        double E=(e/total)*100;
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
                        "Extra",
                        Float.parseFloat(tvExtra.getText().toString()),
                        Color.parseColor("#88c1bf")));

        // To animate the pie chart
        pieChart.startAnimation();

    }
}