package com.example.expensetrackingapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseControl {


    SQLiteDatabase database;
    DatabaseHelper helper;

    public DatabaseControl(Context context){
        helper= new DatabaseHelper(context);
    }

    public void open(){
        database=helper.getWritableDatabase();
    }

    public void close(){
        helper.close();
    }

    public boolean insert(String expense, String category, String date, String price){
        ContentValues values = new ContentValues();
        values.put("expense", expense);
        values.put("category", category);
        values.put("date", date);
        values.put("price", price);
        return database.insert("expenses", null, values)>0;
    }

    public void delete(String expense){
        database.delete("expenses", "expense= \""+expense+"\"", null);
    }

    public String getExpense(String expense){
        String query= "select category, date, price from expenses where expense=\""+expense+"\"";
        Cursor cursor= database.rawQuery(query,null);
        cursor.moveToFirst();
        String category = cursor.getString(0);
        String date = cursor.getString(1);
        String price =cursor.getString(2);
        cursor.close();
        return category +" "+ date+" "+ price;
    }
    public int CountCategory(String category){
        String query ="select * from expenses where category =\""+category+"\"";
        Cursor cursor= database.rawQuery(query,null);
        cursor.moveToFirst();
       int c = cursor.getCount();
       cursor.close();
       return c;
    }

    public String[] getAllNamesArray(){
        String query= "select expense from expenses";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        ArrayList<String> list = new ArrayList<String>();
        //while not at the end of the table
        while(!cursor.isAfterLast()){
            String expense= cursor.getString(0);
            list.add(expense);
            cursor.moveToNext();
        }
        cursor.close();
        String[] array= new String[list.size()];
        return list.toArray(array);
    }


}
