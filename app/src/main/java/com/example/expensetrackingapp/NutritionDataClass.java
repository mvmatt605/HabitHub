package com.example.expensetrackingapp;

public class NutritionDataClass {
    private String dataFoodItem;
    private String dataProtein;
    private String dataCarbs;
    private String dataFats;
    private String dataCalories;

    //constructor
    public NutritionDataClass(){

    }

    public void setDataFoodItem(String dataFoodItem) {
        this.dataFoodItem = dataFoodItem;
    }

    public void setDataProtein(String dataProtein) {
        this.dataProtein = dataProtein;
    }

    public void setDataCarbs(String dataCarbs) {
        this.dataCarbs = dataCarbs;
    }

    public void setDataFats(String dataFats) {
        this.dataFats = dataFats;
    }

    public void setDataCalories(String dataCalories) {
        this.dataCalories = dataCalories;
    }

    public String getDataFoodItem() {
        return dataFoodItem;
    }

    public String getDataProtein() {
        return dataProtein;
    }

    public String getDataCarbs() {
        return dataCarbs;
    }

    public String getDataFats() {
        return dataFats;
    }

    public String getDataCalories() {
        return dataCalories;
    }

    public NutritionDataClass(String dataFoodItem, String dataProtein, String dataCarbs, String dataFats, String dataCalories) {
        this.dataFoodItem = dataFoodItem;
        this.dataProtein = dataProtein;
        this.dataCarbs = dataCarbs;
        this.dataFats = dataFats;
        this.dataCalories = dataCalories;
    }

}
