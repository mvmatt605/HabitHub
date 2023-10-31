package com.example.expensetrackingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Database extends AppCompatActivity {

    EditText nameEdit;
    Spinner spinner;
    DatabaseControl control;
    Button addButton;
    Spinner colorSpinner;
    Button deleteButton;
    RecyclerView recyclerView;
    TextView result;
    EditText editTextPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        nameEdit= findViewById(R.id.nameEdit);
        spinner=findViewById(R.id.spinner);
        addButton= findViewById(R.id.addButton);
        colorSpinner=findViewById(R.id.colorSpinner);
        deleteButton=findViewById(R.id.deleteButton);
        recyclerView= findViewById(R.id.recyclerView);
        result = findViewById(R.id.result);
        editTextPrice=findViewById(R.id.editTextPrice);

        control= new DatabaseControl(this);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expense=nameEdit.getText().toString();
                control.open();
                control.delete(expense);
                control.close();
                onResume();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expense= nameEdit.getText().toString();
                String category =((TextView) spinner.getSelectedView()).getText().toString();
                String date=((TextView) colorSpinner.getSelectedView()).getText().toString();
                String price = editTextPrice.getText().toString();
                control.open();
                boolean itWorked =control.insert(expense,category,date,price);
                control.close();
                if(itWorked) //come back may need to add color here
                    Toast.makeText(getApplicationContext(),"added "+expense+" "+category+date+price,Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"failed "+expense+" "+category+date+price,Toast.LENGTH_SHORT).show();

                onResume();

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.date, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(adapter2);

    }

    @Override
    public void onResume(){
        super.onResume();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        control.open();
        String[] list=control.getAllNamesArray();
        control.close();
        Adapter adapt =new Adapter(list);
        adapt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Adapter.ViewHolder viewHolder =(Adapter.ViewHolder) view.getTag();
                TextView textView = viewHolder.getTextView();
                String name = textView.getText().toString();
                control.open();
                String data= control.getExpense(name);
                control.close();
                result.setText(name+": "+data);
            }
        });
        recyclerView.setAdapter(adapt);
    }
    }
