package com.example.cs4084_project;

import java.util.ArrayList;

//A class for a Meal that will be used in the Meal Planner
public class Meal {
    String name;//Name of Meal
    int calories; //Number of calories in the Meal
    ArrayList<String> ingredients; //An ArrayList of Strings storing the ingredients used

    //Constructor for a Meal
    public Meal(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }

    //Getter for the name variable (Returns String)
    public String getName() {
        return name;
    }

    //Getter for the calories variable (Returns int)
    public int getCalories() {
        return calories;
    }

    //Getter for every ingredient in the ingredients ArrayList (Returns a single String)
    public String getIngredients() {
        String s = "";
        for(int i = 0; i < ingredients.size(); i++){
            s += ingredients.get(i) + " ";
        }
        return s;
    }

    //Add an ingredient to the ingredients ArrayList
    public void addIngredient(String s){
        ingredients.add(s);
    }

    //A toString method returning the Meal info
    @Override
    public String toString(){
        return "Name: " + name + "\n Ingredients: " + getIngredients() +"\nCalories: " + calories;
    }
}
