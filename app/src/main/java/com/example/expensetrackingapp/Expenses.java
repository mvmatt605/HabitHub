package com.example.expensetrackingapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.text.TextUtils;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;

public class Expenses extends AppCompatActivity {
    EditText expenseName;
    Button addButton;
    Button deleteButton;
    RecyclerView recyclerView;
    TextView result;
    EditText editTextPrice;
    EditText date;
    EditText type;

    List<String> dataList = new ArrayList<>();
    ExpenseAdapter adapter = new ExpenseAdapter(dataList);

    String ExpenseName, Type, Date, Price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);


        expenseName= findViewById(R.id.expenseName);
        addButton= findViewById(R.id.addButton);
        deleteButton=findViewById(R.id.deleteButton);
        editTextPrice=findViewById(R.id.editTextPrice);
        date=findViewById(R.id.date);
        type=findViewById(R.id.type);

        recyclerView= findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Ref = database.getReference("Expense Data:");

         ExpenseName = expenseName.getText().toString();
         Type = type.getText().toString();
         Date = date.getText().toString();
         Price = editTextPrice.getText().toString();

         Ref.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 float bills = 0;
                 dataList.clear();

                 for (DataSnapshot snapshot : dataSnapshot.getChildren() ) {
                     String expense = snapshot.getKey();
                     String category = snapshot.child("category").getValue(String.class);
                     String date = snapshot.child("date").getValue(String.class);
                     String price = snapshot.child("price").getValue(String.class);
                     if("bill".equals(category) && price != null && !price.isEmpty()) {
                         bills = bills + Float.parseFloat(price.trim());
                     }

                     String data ="("+category+") "+expense+" $"+price+" on "+ date;
                     dataList.add(data);
                 }
                 adapter.notifyDataSetChanged();
                 Log.d(TAG, "total bills: " + bills);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 // Failed to read value
                 Log.w(TAG, "Failed to read value.", error.toException());
             }
         });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 ExpenseName= expenseName.getText().toString();
                 Type = type.getText().toString();
                 Date = date.getText().toString();
                 Price = editTextPrice.getText().toString();
                addToDatabase(ExpenseName,Type, Date, Price);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenseName= expenseName.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Expense Data:");
                myRef.child(ExpenseName).removeValue();
            }
        });
    }

    public void addToDatabase(String expense, String type, String date, String price){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Expense Data:").child(expense);
        myRef.child("category").setValue(type);
        myRef.child("date").setValue(date);
        myRef.child("price").setValue(price);
    }
}